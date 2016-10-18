package com.feicui.apphx.presentation.convertsation;


import android.support.annotation.NonNull;

import com.feicui.apphx.basemvp.MvpPresenter;
import com.feicui.apphx.model.HxMessageManager;
import com.feicui.apphx.model.event.HxNewMsgEvent;
import com.hyphenate.chat.EMConversation;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HxConversationListPresenter extends MvpPresenter<HxConversationListView> {


    @NonNull
    @Override protected HxConversationListView getNullObject() {
        return HxConversationListView.NULL;
    }

    public void deleteConversation(EMConversation conversation, boolean deleteMessage) {
        HxMessageManager.getInstance().deleteConversation(conversation.getUserName(), deleteMessage);
        getView().refreshConversations();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxNewMsgEvent event) {
        // 收到会话,刷新会话列表视图
        getView().refreshConversations();
    }
}
