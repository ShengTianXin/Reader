package com.feicui.apphx.presentation.contact.list;

import android.support.annotation.NonNull;

import com.feicui.apphx.basemvp.MvpPresenter;
import com.feicui.apphx.model.HxContactManager;
import com.feicui.apphx.model.event.HxErrorEvent;
import com.feicui.apphx.model.event.HxEventType;
import com.feicui.apphx.model.event.HxRefreshContactEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import timber.log.Timber;

/**
 * 环信联系人列表页面 - Presenter
 * <p/>
 * MVP的Presenter:主要负责执行model层业务,接收model层数据,触发view层视图
 * <p/>
 * Created by Administrator on 2016/10/17 0017.
 */
public class HxContactListPresenter extends MvpPresenter<HxContactListView> {

    @NonNull
    @Override
    protected HxContactListView getNullObject() {
        return HxContactListView.NULL;
    }

    public void loadContacts() {
        HxContactManager.getInstance().getContacts();
    }

    public void deleteContact(String hxId) {
        HxContactManager.getInstance().asyncDeleteContact(hxId);
    }

    /**
     * 接受model层刷新新联系人事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxRefreshContactEvent event) {
        // 是否有更新
        if (event.changed) {
            // 设置视图
            getView().setContacts(event.contacts);
        }
        Timber.d(" ---------------  " + event.contacts.size());
        getView().refreshContacts();
    }

    /**
     * 接受model层错误事件
     */
    public void onEvent(HxErrorEvent event) {
        // 不是删除联系人的错误事件。不作处理
        if (event.type != HxEventType.DELETE_CONTACT.DELETE_CONTACT) {
            return;
        }
        getView().showDeleteContactFail(event.toString());
    }
}
