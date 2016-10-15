package com.feicui.reader.user.login;

import android.support.annotation.NonNull;

import com.feicui.apphx.basemvp.MvpPresenter;
import com.feicui.apphx.model.HxUserManager;
import com.feicui.apphx.model.event.HxLoginEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/10/15 0015.
 */
public class LoginPresenter extends MvpPresenter<LoginView>{

    /**
     * 登录,协调人要做的主要工作
     */
    public void login(@NonNull final String hxId, @NonNull final String password) {
        // 协调视图那边的变化
        getView().showLoading();
        // 安排业务人员去做事,(等业务人员返结果<EventBus>)
        HxUserManager.getInstance().asyncLogin(hxId,password);
    }
    // 业务人员返结果<EventBus>
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxLoginEvent event){
        // 协调视图那边的变化
        getView().hideLoading();
        // 看业务数据结果
        if(event.isSuccess()){
            // 协调视图那边的变化
            getView().navigateToHome();
        }else{
            // 协调视图那边的变化
            String msg = String.format("失败原因: %s",event.getErrorMessage());
            getView().showMessage(msg);
        }
    }
    @NonNull
    @Override
    protected LoginView getNullObject() {
        return LoginView.NULL_VIEW;
    }
}
