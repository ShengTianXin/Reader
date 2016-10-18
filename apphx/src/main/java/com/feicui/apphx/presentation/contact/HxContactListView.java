package com.feicui.apphx.presentation.contact;

import com.feicui.apphx.basemvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public interface HxContactListView extends MvpView {

    /** 将联系人设置到视图上*/
    void setContacts(List<String> contacts);
    /** 刷新视图上的联系人*/
    void refreshContacts();

    void showDeleteContactFail(String msg);

    HxContactListView NULL = new HxContactListView() {
        @Override public void setContacts(List<String> contacts) {

        }

        @Override public void refreshContacts() {

        }

        @Override public void showDeleteContactFail(String msg) {

        }
    };
}
