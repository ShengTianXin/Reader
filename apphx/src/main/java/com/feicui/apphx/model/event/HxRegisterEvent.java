package com.feicui.apphx.model.event;

import com.hyphenate.exceptions.HyphenateException;

/**
 * 事件：环信注册结果
 * Created by Administrator on 2016/10/15 0015.
 */
public class HxRegisterEvent {
    // 是否成功
    private final boolean success;
    // 错误
    private HyphenateException exception;

    public HxRegisterEvent() {
        this.success = true;
    }

    public HxRegisterEvent(HyphenateException exception) {
        this.success = false;
        this.exception = exception;
    }

    public boolean isSuccess() {
        return success;
    }

    public HyphenateException getException() {
        return exception;
    }
}
