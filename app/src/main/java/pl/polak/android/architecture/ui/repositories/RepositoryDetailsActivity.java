package pl.polak.android.architecture.ui.repositories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.polak.android.architecture.R;
import pl.polak.android.architecture.network.model.Repository;

public class RepositoryDetailsActivity extends AppCompatActivity {

    public static final String REPOSITORY_DETAILS_MODEL_EXTRA = "repository_details_model_extra";

    public static void start(Activity activity, Repository repository) {
        Intent intent = new Intent(activity, RepositoryDetailsActivity.class);
        intent.putExtra(REPOSITORY_DETAILS_MODEL_EXTRA, repository);
        activity.startActivity(intent);
    }

    @BindView(R.id.text_repo_details_title)
    TextView tvTitle;

    @BindView(R.id.text_repo_details_description)
    TextView tvDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);

        ButterKnife.bind(this);

        bindData(getIntent().getExtras().getParcelable(REPOSITORY_DETAILS_MODEL_EXTRA));
    }

    private void bindData(Repository repository) {
        tvTitle.setText(repository.getName());
        tvDescription.setText(repository.getDescription());
    }
}
