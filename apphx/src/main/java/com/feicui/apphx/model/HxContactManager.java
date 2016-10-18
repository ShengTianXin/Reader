package com.feicui.apphx.model;

import com.feicui.apphx.model.event.HxErrorEvent;
import com.feicui.apphx.model.event.HxEventType;
import com.feicui.apphx.model.event.HxRefreshContactEvent;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContactManager;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * 环信联系人管理类
 * Created by Administrator on 2016/10/17 0017.
 */
public class HxContactManager implements EMContactListener, EMConnectionListener {

    private static HxContactManager hxContactManager;

    public static HxContactManager getInstance() {
        if (hxContactManager == null) {
            hxContactManager = new HxContactManager();
        }
        return hxContactManager;
    }

    /**
     * 联系人列表集合
     */
    private List<String> contacts;
    private String currentUserId;

    private final EMContactManager emContactManager;
    private final EventBus eventBus;
    private final ExecutorService executorService;

    public HxContactManager() {
        /**环信联系人相关操作SDK*/
        eventBus = EventBus.getDefault();
        /**线程池*/
        executorService = Executors.newSingleThreadExecutor();
        /**环信连接监听*/
        EMClient.getInstance().addConnectionListener(this);
        /**环信联系人相关操作
         * 监听放下边，否则会空*/
        emContactManager = EMClient.getInstance().contactManager();
        emContactManager.setContactListener(this);

    }


    /**
     * 获取联系人
     */
    public void getContacts() {
        /**以获取过联系人(不用重复获取)*/
        if (contacts != null) {
            notifyContactsRefresh();
        }
        /**还未获取过联系人*/
        else {
            asyncGetContactsFromServer();
        }
    }

    private void asyncGetContactsFromServer() {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    contacts = emContactManager.getAllContactsFromServer();
                    notifyContactsRefresh();
                } catch (HyphenateException e) {
                    Timber.e(e, "asyncGetContactsFromServer");
                }
            }
        };
        executorService.submit(runnable);
    }

    private void notifyContactsRefresh() {
        /**相当于复制一份联系人，再传出去*/
        List<String> currentContacts;
        if (contacts == null) {
            currentContacts = Collections.emptyList();
        } else {
            currentContacts = new ArrayList<>(contacts);
        }
        eventBus.post(new HxRefreshContactEvent(currentContacts));
    }

    /**
     * 删除联系人
     * 如果删除成功，会自己触发{@link #onContactDeleted(String)}
     * <p/>
     * 注意：A将B删除了, B客户端的{@link #onContactDeleted(String)}也会触发
     *
     * @param hxId
     */
    public void deleteContact(final String hxId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    emContactManager.deleteContact(hxId);
                } catch (HyphenateException e) {
                    Timber.e(e, "deleteContact");
                    // 删除失败
                    eventBus.post(new HxErrorEvent(HxEventType.DELETE_CONTACT, e));
                }
            }
        };
        executorService.submit(runnable);
    }

    public void setCurrentUser(String hxId) {
        this.currentUserId = hxId;
    }

    public void reset() {
        contacts = null;
        currentUserId = null;
    }

    /**
     * start-interface: EMConnectionListener
     */
    @Override
    public void onConnected() {
        if (contacts == null) {
            asyncGetContactsFromServer();
        }
    }

    @Override
    public void onDisconnected(int i) {

    }
    /**end-interface: EMConnectionListener*/

    /**
     * start contact ContactListener---------------------
     */

    // 添加联系人
    @Override
    public void onContactAdded(String hxId) {
        if (contacts == null) {
            asyncGetContactsFromServer();
        } else {
            contacts.add(hxId);
            notifyContactsRefresh();
        }
    }

    // 删除联系人
    @Override
    public void onContactDeleted(String hxId) {
        if (contacts == null) {
            asyncGetContactsFromServer();
        } else {
            contacts.remove(hxId);
            notifyContactsRefresh();
        }
    }

    // 收到好友邀请
    @Override
    public void onContactInvited(String hxId, String reason) {
        Timber.d("onContactInvited %s, reason: %s", hxId, reason);
    }

    // 好友请求被同意
    @Override
    public void onContactAgreed(String hxId) {
        Timber.d("onContactAgreed %s", hxId);
    }

    // 好友请求被拒绝
    @Override
    public void onContactRefused(String hxId) {
        Timber.d("onContactRefused %s", hxId);
    }

    /**end contact ContactListener-----------------------*/
}
