package com.taoxue.ui.model;

/**
 * Created by User on 2017/4/18.
 */

public class ReaderCodeModel extends BaseModel {
    private String cgs_id;
    private String cgs_name;
    private String reader_id;
    private String realname;

    @Override
    public String toString() {
        return "ReaderCodeModel{" +
                "cgs_id='" + cgs_id + '\'' +
                ", cgs_name='" + cgs_name + '\'' +
                ", reader_id='" + reader_id + '\'' +
                ", realname='" + realname + '\'' +
                '}';
    }

    public String getCgs_id() {
        return cgs_id;
    }

    public void setCgs_id(String cgs_id) {
        this.cgs_id = cgs_id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getReader_id() {
        return reader_id;
    }

    public void setReader_id(String reader_id) {
        this.reader_id = reader_id;
    }

    public String getCgs_name() {
        return cgs_name;
    }

    public void setCgs_name(String cgs_name) {
        this.cgs_name = cgs_name;
    }
}
