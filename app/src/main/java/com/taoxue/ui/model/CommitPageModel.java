package com.taoxue.ui.model;

import java.io.Serializable;

/**
 * Created by CC on 2016/12/11.
 */

public class CommitPageModel<Page extends Serializable> extends BaseModel {
    private int code;//200;
    private String msg;// "success",
    private Page page;// {

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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "CommitPageModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", page=" + page +
                '}';
    }
}
