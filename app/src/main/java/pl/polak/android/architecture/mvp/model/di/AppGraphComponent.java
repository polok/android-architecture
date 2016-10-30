package pl.polak.android.architecture.mvp.model.di;

import javax.inject.Singleton;

import dagger.Component;
import pl.polak.android.architecture.GithubApplication;
import pl.polak.android.architecture.mvp.model.di.modules.ApiModule;
import pl.polak.android.architecture.mvp.model.di.modules.AppModule;
import pl.polak.android.architecture.mvp.model.network.handler.repository.IGithubRepositoryRequestHandler;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class
})
public interface AppGraphComponent {

    IGithubRepositoryRequestHandler githubRepositoryRequestHandler();

    abstract class Initializer {

        public static AppGraphComponent init(GithubApplication app) {
            return DaggerAppGraphComponent.builder()
                    .appModule(new AppModule(app))
                    .apiModule(new ApiModule())
                    .build();
        }

    }

}
