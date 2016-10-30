package pl.polak.android.architecture.mvp.view.repositories;

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
import pl.polak.android.architecture.GithubApplication;
import pl.polak.android.architecture.R;
import pl.polak.android.architecture.mvp.model.di.DaggerGithubRepositoryComponent;
import pl.polak.android.architecture.mvp.model.di.GithubRepositoryComponent;
import pl.polak.android.architecture.mvp.model.di.modules.ActivityModule;
import pl.polak.android.architecture.mvp.model.di.modules.GithubRepositoryModule;
import pl.polak.android.architecture.mvp.model.network.model.Repository;
import pl.polak.android.architecture.mvp.presenter.IRepositoryListPresenter;

public class RepositoryListActivity extends AppCompatActivity implements RepositoryListView {

    @Inject
    IRepositoryListPresenter presenter;

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

    private GithubRepositoryComponent repositoryComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        repositoryComponent = DaggerGithubRepositoryComponent.builder()
                .appGraphComponent(GithubApplication.component())
                .activityModule(new ActivityModule(this))
                .githubRepositoryModule(new GithubRepositoryModule())
                .build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repositoryComponent.inject(this);
        ButterKnife.bind(this);

        presenter.attachView(this);

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
        progressBar.setVisibility(View.VISIBLE);
        repositoriesRecycleView.setVisibility(View.GONE);
        infoTextView.setVisibility(View.GONE);

        presenter.loadRepositories(username);
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
        presenter.detachView();
    }

    @Override
    public void showRepositories(List<Repository> repositories) {
        RepositoryAdapter adapter = (RepositoryAdapter) repositoriesRecycleView.getAdapter();
        adapter.setRepositories(repositories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingError(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        if (throwable instanceof HttpException && ((HttpException) throwable).code() == 404) {
            infoTextView.setText(R.string.error_username_not_found);
        } else {
            infoTextView.setText(R.string.error_loading_repos);
        }
        infoTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingCompleted() {
        progressBar.setVisibility(View.GONE);
        if (repositoriesRecycleView.getAdapter().getItemCount() > 0) {
            repositoriesRecycleView.requestFocus();
            repositoriesRecycleView.setVisibility(View.VISIBLE);
            hideSoftKeyboard();
        } else {
            infoTextView.setText(R.string.text_empty_repos);
            infoTextView.setVisibility(View.VISIBLE);
        }
    }
}
