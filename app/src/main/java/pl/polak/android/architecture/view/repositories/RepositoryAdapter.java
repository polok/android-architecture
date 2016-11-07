package pl.polak.android.architecture.view.repositories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.polak.android.architecture.R;
import pl.polak.android.architecture.viewmodel.RepositoryItemViewModel;


public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private List<RepositoryItemViewModel> repositories = new ArrayList<>();
    private OnRepositorySelectListener onRepositorySelectListener = repository -> {};

    public interface OnRepositorySelectListener {

        void onRepositorySelect(RepositoryItemViewModel repository);

    }

    public void setOnRepositorySelectListener(OnRepositorySelectListener onRepositorySelectListener) {
        this.onRepositorySelectListener = onRepositorySelectListener;
    }

    public void setRepositories(List<RepositoryItemViewModel> repositories) {
        this.repositories = repositories;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_repository, parent, false);
        RepositoryViewHolder viewHolder = new RepositoryViewHolder(view);
        viewHolder.contentLayout.setOnClickListener(__ -> onRepositorySelectListener.onRepositorySelect(viewHolder.repositoryItemViewModel));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        RepositoryItemViewModel repositoryItemViewModel = repositories.get(position);

        Context context = holder.titleTextView.getContext();
        holder.repositoryItemViewModel = repositoryItemViewModel;

        holder.titleTextView.setText(repositoryItemViewModel.getTitle());
        holder.descriptionTextView.setText(repositoryItemViewModel.getDescription());
        holder.watchersTextView.setText(context.getResources().getString(R.string.text_watchers, repositoryItemViewModel.getWatchers()));
        holder.starsTextView.setText(context.getResources().getString(R.string.text_stars, repositoryItemViewModel.getStars()));
        holder.forksTextView.setText(context.getResources().getString(R.string.text_forks, repositoryItemViewModel.getForks()));
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_content)
        View contentLayout;

        @BindView(R.id.text_repo_title)
        TextView titleTextView;

        @BindView(R.id.text_repo_description)
        TextView descriptionTextView;

        @BindView(R.id.text_watchers)
        TextView watchersTextView;

        @BindView(R.id.text_stars)
        TextView starsTextView;

        @BindView(R.id.text_forks)
        TextView forksTextView;

        RepositoryItemViewModel repositoryItemViewModel;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
