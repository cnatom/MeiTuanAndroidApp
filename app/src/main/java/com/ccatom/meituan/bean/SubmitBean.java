package com.ccatom.meituan.bean;

import com.google.gson.annotations.SerializedName;

public class SubmitBean {

    @SerializedName("msg")
    private String msg;
    @SerializedName("path")
    private String path;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
