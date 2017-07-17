package com.taoxue.ui.model;

/**
 * Created by User on 2017/3/1.
 */

public class CheckSignModel extends BaseModel {
    private int code;// "400",
    private String msg;//

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CheckSignModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
