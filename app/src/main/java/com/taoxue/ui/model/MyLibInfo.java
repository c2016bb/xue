package com.taoxue.ui.model;

/**
 * Created by User on 2017/5/3.
 */

public class MyLibInfo extends BaseModel {
            private String user_id;
            private String reader_card_id;
            private String cgs_id;
            private String name;
            private String short_name;
            private String cgs_code;
            private String address;
            private String logo;
            private String url;

    @Override
    public String toString() {
        return "MyLibInfo{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReader_card_id() {
        return reader_card_id;
    }

    public void setReader_card_id(String reader_card_id) {
        this.reader_card_id = reader_card_id;
    }

    public String getCgs_id() {
        return cgs_id;
    }

    public void setCgs_id(String cgs_id) {
        this.cgs_id = cgs_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getCgs_code() {
        return cgs_code;
    }

    public void setCgs_code(String cgs_code) {
        this.cgs_code = cgs_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
