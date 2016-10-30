package pl.polak.android.architecture.mvp.model.di;

import dagger.Component;
import pl.polak.android.architecture.mvp.model.di.modules.ActivityModule;
import pl.polak.android.architecture.mvp.model.di.modules.GithubRepositoryModule;
import pl.polak.android.architecture.mvp.view.repositories.RepositoryDetailsActivity;
import pl.polak.android.architecture.mvp.view.repositories.RepositoryListActivity;

@ActivityScope
@Component(dependencies = AppGraphComponent.class, modules = {ActivityModule.class, GithubRepositoryModule.class})
public interface GithubRepositoryComponent extends ActivityComponent {

    void inject(RepositoryListActivity activity);

    void inject(RepositoryDetailsActivity activity);

}
