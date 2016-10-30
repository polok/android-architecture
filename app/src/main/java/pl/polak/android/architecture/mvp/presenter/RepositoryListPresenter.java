package pl.polak.android.architecture.mvp.presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.polak.android.architecture.mvp.model.network.handler.repository.IGithubRepositoryRequestHandler;
import pl.polak.android.architecture.mvp.view.repositories.RepositoryListView;

public class RepositoryListPresenter implements IRepositoryListPresenter {

    private IGithubRepositoryRequestHandler repositoryRequestHandler;

    private RepositoryListView repositoriesView;
    private Disposable disposable;

    @Inject
    public RepositoryListPresenter(IGithubRepositoryRequestHandler repositoryRequestHandler) {
        this.repositoryRequestHandler = repositoryRequestHandler;
    }

    @Override
    public void loadRepositories(String username) {
        disposable = repositoryRequestHandler.loadRepositoriesFor(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(repositoriesView::showRepositories, repositoriesView::showLoadingError, repositoriesView::onLoadingCompleted);
    }

    @Override
    public void attachView(RepositoryListView view) {
        repositoriesView = view;
    }

    @Override
    public void detachView() {
        repositoriesView = null;
        if (disposable != null) {
            disposable.dispose();
        }
    }

}