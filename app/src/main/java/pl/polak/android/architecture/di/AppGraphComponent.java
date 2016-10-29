package pl.polak.android.architecture.di;

import javax.inject.Singleton;

import dagger.Component;
import pl.polak.android.architecture.GithubApplication;
import pl.polak.android.architecture.ui.repositories.RepositoryListActivity;
import pl.polak.android.architecture.di.modules.ApiModule;
import pl.polak.android.architecture.di.modules.AppModule;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class
})
public interface AppGraphComponent {

    void inject(RepositoryListActivity repositoryListActivity);

    abstract class Initializer {

        public static AppGraphComponent init(GithubApplication app) {
            return DaggerAppGraphComponent.builder()
                    .appModule(new AppModule(app))
                    .apiModule(new ApiModule())
                    .build();
        }

    }

}
