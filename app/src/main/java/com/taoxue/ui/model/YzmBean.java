package com.taoxue.ui.model;

import com.taoxue.ui.model.BaseModel;

import java.io.Serializable;

/**
 * Created by User on 2017/4/5.
 */

public class YzmBean extends BaseModel {
    private String yzm;

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    @Override
    public String toString() {
        return "YzmBean{" +
                "yzm='" + yzm + '\'' +
                '}';
    }
}
