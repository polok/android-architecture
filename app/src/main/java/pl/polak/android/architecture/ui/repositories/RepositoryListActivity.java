package pl.polak.android.architecture.ui.repositories;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pl.polak.android.architecture.GithubApplication;
import pl.polak.android.architecture.R;
import pl.polak.android.architecture.network.handler.repository.IGithubRepositoryRequestHandler;
import pl.polak.android.architecture.network.model.Repository;

public class RepositoryListActivity extends AppCompatActivity {

    @Inject
    IGithubRepositoryRequestHandler repositoryRequestHandler;

    @BindView(R.id.repositories_recycler_view)
    RecyclerView repositoriesRecycleView;

    @BindView(R.id.edit_text_username)
    EditText usernameEditText;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.text_info)
    TextView infoTextView;

    @BindView(R.id.button_search)
    ImageButton searchButton;

    private Disposable disposable;

    private Consumer<List<Repository>> showRepositories = repositories -> {
        RepositoryAdapter adapter = (RepositoryAdapter) repositoriesRecycleView.getAdapter();
        adapter.setRepositories(repositories);
        adapter.notifyDataSetChanged();
    };

    private Action onLoadingComplete = () -> {
        progressBar.setVisibility(View.GONE);
        if (repositoriesRecycleView.getAdapter().getItemCount() > 0) {
            repositoriesRecycleView.requestFocus();
            repositoriesRecycleView.setVisibility(View.VISIBLE);
            hideSoftKeyboard();
        } else {
            infoTextView.setText(R.string.text_empty_repos);
            infoTextView.setVisibility(View.VISIBLE);
        }
    };

    private Consumer<Throwable> showError = throwable -> {
        progressBar.setVisibility(View.GONE);
        if (throwable instanceof HttpException && ((HttpException) throwable).code() == 404) {
            infoTextView.setText(R.string.error_username_not_found);
        } else {
            infoTextView.setText(R.string.error_loading_repos);
        }

        infoTextView.setVisibility(View.VISIBLE);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GithubApplication.component().inject(this);
        ButterKnife.bind(this);

        initUI();

    }

    private void initUI() {
        setupRepositoriesRecycleView();
        setupUsernameEditText();
        setupSearchButton();
    }

    private void setupRepositoriesRecycleView() {
        RepositoryAdapter adapter = new RepositoryAdapter();
        adapter.setOnRepositorySelectListener(repository -> {
            RepositoryDetailsActivity.start(this, repository);
        });
        repositoriesRecycleView.setAdapter(adapter);
        repositoriesRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupUsernameEditText() {
        usernameEditText.addTextChangedListener(usernameTextWatcher);
        usernameEditText.setOnEditorActionListener((view, actionId, event) -> {
            if (EditorInfo.IME_ACTION_SEARCH != actionId) {
                return false;
            }
            loadGitHubRepositories(usernameEditText.getText().toString());
            return true;
        });
    }

    private void setupSearchButton() {
        searchButton.setOnClickListener(__ -> loadGitHubRepositories(usernameEditText.getText().toString()));
    }

    private void loadGitHubRepositories(String username) {
        disposable = repositoryRequestHandler.loadRepositoriesFor(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressBar.setVisibility(View.VISIBLE);
                    repositoriesRecycleView.setVisibility(View.GONE);
                    infoTextView.setVisibility(View.GONE);
                })
                .subscribe(showRepositories, showError, onLoadingComplete);
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(usernameEditText.getWindowToken(), 0);
    }

    private TextWatcher usernameTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            searchButton.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void afterTextChanged(Editable editable) {}

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
