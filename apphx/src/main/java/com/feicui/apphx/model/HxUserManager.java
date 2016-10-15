package com.feicui.apphx.model;

import android.support.annotation.NonNull;

import com.feicui.apphx.model.event.HxLoginEvent;
import com.feicui.apphx.model.event.HxRegisterEvent;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * Model层, 环信用户基本功能管理，登录、注册、登出
 * Created by Administrator on 2016/10/15 0015.
 */
public class HxUserManager {

    private static HxUserManager hxUserManager;

    public static HxUserManager getInstance() {
        if (hxUserManager == null) {
            hxUserManager = new HxUserManager();
        }
        return hxUserManager;
    }

    private final EMClient emClient;
    private final ExecutorService executorService;
    private final EventBus eventBus;

    private HxUserManager(){
        emClient = EMClient.getInstance();
        executorService = Executors.newSingleThreadExecutor();
        eventBus = EventBus.getDefault();
    }

    /**
     * 环信异步注册(用于测试,后期将通过自己应用服务进行注册)
     */
    public void asyncRegister(@NonNull final String hxId, @NonNull final String password) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    emClient.createAccount(hxId,password);
                    Timber.d("%s RegisterHx success",hxId);
                    eventBus.post(new HxRegisterEvent());
                } catch (HyphenateException e) {
                    Timber.d("RegisterHx fail");
                    eventBus.post(new HxRegisterEvent());
                }
            }
        };
        // 提交，线程池处理此Runnable
        executorService.submit(runnable);
    }

    /**
     * 环信异步登录
     */
    public void asyncLogin(@NonNull final String hxId, @NonNull final String password) {
        emClient.login(hxId, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                Timber.d("%s LoginHx success", hxId);
                eventBus.post(new HxLoginEvent());
            }

            @Override
            public void onError(int code, String message) {
                Timber.d("%s LoginHx error, code is %s.", hxId, code);
                eventBus.post(new HxLoginEvent(code, message));
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
