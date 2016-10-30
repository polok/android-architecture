package pl.polak.android.architecture.mvp.model.network.api;

import java.util.List;

import io.reactivex.Observable;
import pl.polak.android.architecture.mvp.model.network.model.Repository;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubRepositoryApi {

    @GET("users/{username}/repos")
    Observable<List<Repository>> getRepositories(@Path("username") String username);

}
