package com.feicui.apphx.basemvp;

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/10/15 0015.
 */
public abstract class MvpPresenter<V extends MvpView> {
    private V view;

    /**
     * Presenter的创建
     * 在Activity和Fragment的onCreate()方法中调用
     */
    public final void onCreate() {
        EventBus.getDefault().register(this);
    }

    /**
     * Presenter和视图关联
     * 在Activity的onDestroy()中调用
     * 在Fragment的onViewDestroyed()中调用
     */
    public final void attachView(V view) {
        this.view = view;
    }

    /**
     * Presenter和视图解除关联
     * 在Activity的onDestroy()中调用
     * 在Fragment的onViewDestroyed()中调用
     */
    public final void detachView() {
        // 使用Null Object Pattern，避免使用view时，频繁的检测null值情况
        this.view = getNullObject();
    }

    /**
     * Presenter的销毁
     * 在Activity或Fragment的onDestroy()方法中调用
     */
    public final void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    protected final V getView() {
        return view;
    }

    protected abstract
    @NonNull
    V getNullObject();
}
