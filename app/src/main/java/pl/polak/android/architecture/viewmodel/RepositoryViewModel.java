package pl.polak.android.architecture.viewmodel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import pl.polak.android.architecture.model.network.handler.repository.IGithubRepositoryRequestHandler;

public class RepositoryViewModel {

    public PublishSubject<List<RepositoryItemViewModel>> repositoriesStream = PublishSubject.create();

    private IGithubRepositoryRequestHandler repositoryRequestHandler;

    @Inject
    public RepositoryViewModel(IGithubRepositoryRequestHandler repositoryRequestHandler) {
        this.repositoryRequestHandler = repositoryRequestHandler;
    }

    public void loadRepositories(String username) {
        repositoryRequestHandler.loadRepositoriesFor(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(list -> list)
                .map(RepositoryItemViewModel::new)
                .toList()
                .subscribe(repositoriesStream::onNext, repositoriesStream::onError);
    }

    public Observable<List<RepositoryItemViewModel>> repositoryListObservable() {
        return repositoriesStream;
    }

}
