package pl.polak.android.architecture.mvp.model.di;

import dagger.Component;
import pl.polak.android.architecture.mvp.model.di.modules.ActivityModule;

@ActivityScope
@Component(dependencies = AppGraphComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {}
