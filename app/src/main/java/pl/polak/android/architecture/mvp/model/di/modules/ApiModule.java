package pl.polak.android.architecture.mvp.model.di.modules;

import android.content.res.Resources;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import pl.polak.android.architecture.R;
import pl.polak.android.architecture.mvp.model.network.handler.repository.GithubRepositoryRequestHandler;
import pl.polak.android.architecture.mvp.model.network.handler.repository.IGithubRepositoryRequestHandler;
import pl.polak.android.architecture.mvp.model.network.interceptor.AuthorizationTokenHeaderInterceptor;
import pl.polak.android.architecture.mvp.model.network.api.GithubRepositoryApi;
import pl.polak.android.architecture.mvp.model.di.GithubToken;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    OkHttpClient okHttpClient(@GithubToken String token) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationTokenHeaderInterceptor(token))
                .build();
    }

    @Provides
    @Singleton
    GithubRepositoryApi provideGithubRepositoryService(OkHttpClient okHttpClient, Resources resources) {
        return new Retrofit.Builder()
                .baseUrl(resources.getString(R.string.base_url))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubRepositoryApi.class);
    }

    @Singleton
    @Provides
    IGithubRepositoryRequestHandler provideGithubRepositoryRequestManager(GithubRepositoryApi repositoryApi) {
        return new GithubRepositoryRequestHandler(repositoryApi);
    }

    @Provides
    @Singleton
    @GithubToken
    String provideGithubToken(Resources resources) {
        return resources.getString(R.string.github_token);
    }

}
