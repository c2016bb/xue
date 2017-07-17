package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/6/7.
 */

public class MyLibBean extends BaseModel {
    private List<MyLibInfo>myLibInfos;

    public List<MyLibInfo> getMyLibInfos() {
        return myLibInfos;
    }

    public void setMyLibInfos(List<MyLibInfo> myLibInfos) {
        this.myLibInfos = myLibInfos;
    }
}
