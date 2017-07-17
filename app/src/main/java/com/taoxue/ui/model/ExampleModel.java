package com.taoxue.ui.model;

/**
 * Created by User on 2017/3/31.
 */

public class ExampleModel extends BaseModel{

    private String ID;
    private String classcode;
    private String wordcode;
    private String wordname;
    private String orderby;
    private String pid;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getClasscode() {
        return classcode;
    }

    public void setClasscode(String classcode) {
        this.classcode = classcode;
    }

    public String getWordcode() {
        return wordcode;
    }

    public void setWordcode(String wordcode) {
        this.wordcode = wordcode;
    }

    public String getWordname() {
        return wordname;
    }

    public void setWordname(String wordname) {
        this.wordname = wordname;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
