package pl.polak.android.architecture.mvp.model.di.modules;

import dagger.Module;
import dagger.Provides;
import pl.polak.android.architecture.mvp.model.di.ActivityScope;
import pl.polak.android.architecture.mvp.model.network.handler.repository.IGithubRepositoryRequestHandler;
import pl.polak.android.architecture.mvp.presenter.IRepositoryListPresenter;
import pl.polak.android.architecture.mvp.presenter.RepositoryListPresenter;

@Module
public class GithubRepositoryModule {

    @Provides
    @ActivityScope
    public IRepositoryListPresenter provideRepositoryListPresenter(IGithubRepositoryRequestHandler repositoryRequestHandler) {
        return new RepositoryListPresenter(repositoryRequestHandler);
    }

}
