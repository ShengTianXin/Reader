package com.feicui.apphx.model.event;

/**
 * 环信登录结果
 * Created by Administrator on 2016/10/15 0015.
 */
public class HxLoginEvent {
    // 是否成功
    private final boolean success;
    // 错误码
    private int errorCode;
    // 错误描述
    private String errorMessage;

    public HxLoginEvent() {
        this.success = true;
    }

    public HxLoginEvent(int errorCode, String errorMessage) {
        this.success = false;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
