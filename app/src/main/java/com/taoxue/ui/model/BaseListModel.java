package com.taoxue.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 2017/4/5.
 */

public class BaseListModel<Data extends Serializable> extends BaseModel{

    private int code;//1;
    private String msg;// "success",
    private List<Data> item;

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

    public List<Data> getItem() {
        return item;
    }

    public void setItem(List<Data> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "BaseListModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", item=" + item +
                '}';
    }
}
