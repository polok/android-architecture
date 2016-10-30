package pl.polak.android.architecture.mvp.view.repositories;

import java.util.List;

import pl.polak.android.architecture.mvp.model.network.model.Repository;
import pl.polak.android.architecture.mvp.view.IView;

public interface RepositoryListView extends IView {

    void showRepositories(List<Repository> repositories);

    void showLoadingError(Throwable throwable);

    void onLoadingCompleted();

}
