package pl.polak.android.architecture.mvp.model.network.handler.repository;

import java.util.List;

import io.reactivex.Observable;
import pl.polak.android.architecture.mvp.model.network.model.Repository;

public interface IGithubRepositoryRequestHandler {

    Observable<List<Repository>> loadRepositoriesFor(String username);

}
