package pl.polak.android.architecture.model.network.handler.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import pl.polak.android.architecture.model.network.api.GithubRepositoryApi;
import pl.polak.android.architecture.model.network.model.Repository;

public class GithubRepositoryRequestHandler implements IGithubRepositoryRequestHandler {

    private GithubRepositoryApi repositoryApi;

    @Inject
    public GithubRepositoryRequestHandler(GithubRepositoryApi repositoryApi) {
        this.repositoryApi = repositoryApi;
    }

    @Override
    public Observable<List<Repository>> loadRepositoriesFor(String username) {
        if(username.isEmpty()) {
            return Observable.error(new IllegalArgumentException("User name can't be empty"));
        }

        return repositoryApi.getRepositories(username);
    }
}
