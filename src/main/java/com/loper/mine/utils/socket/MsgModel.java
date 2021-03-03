package com.loper.mine.utils.socket;


/**
 * Created on 2019-08-15 14:02.
 *
 * @author zgq7
 */
public class MsgModel {
    private String ip;

    private String msg;

    private boolean statu;

    public String getIp() {
        return ip;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isStatu() {
        return statu;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatu(boolean statu) {
        this.statu = statu;
    }

    public MsgModel(String ip, String msg) {
        this.ip = ip;
        this.msg = msg;
        this.statu = false;
    }
}
