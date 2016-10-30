package pl.polak.android.architecture.mvp.presenter;

import pl.polak.android.architecture.mvp.view.repositories.RepositoryListView;

public interface IRepositoryListPresenter extends Presenter<RepositoryListView> {

    void loadRepositories(String username);

}
