package pl.polak.android.architecture;

import android.app.Application;

import pl.polak.android.architecture.model.di.AppGraphComponent;

public class GithubApplication extends Application {

    private static AppGraphComponent appGraphComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appGraphComponent = AppGraphComponent.Initializer.init(this);
    }

    public static AppGraphComponent component() {
        return appGraphComponent;
    }
}
