package pl.polak.android.architecture.mvp.presenter;

import pl.polak.android.architecture.mvp.view.IView;

public interface Presenter<V extends IView> {

    void attachView(V view);

    void detachView();

}