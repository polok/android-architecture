package pl.polak.android.architecture.model.network.handler.repository;

import java.util.List;

import io.reactivex.Observable;
import pl.polak.android.architecture.model.network.model.Repository;

public interface IGithubRepositoryRequestHandler {

    Observable<List<Repository>> loadRepositoriesFor(String username);

}
