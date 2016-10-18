package com.feicui.apphx.presentation.convertsation;


import com.feicui.apphx.basemvp.MvpView;

public interface HxConversationListView extends MvpView {

    /** 刷新会话列表视图*/
    void refreshConversations();

    HxConversationListView NULL = new HxConversationListView() {
        @Override public void refreshConversations() {
        }
    };
}
