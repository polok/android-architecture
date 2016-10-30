package pl.polak.android.architecture.mvp.model.di.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import pl.polak.android.architecture.mvp.model.di.ActivityScope;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }
}
