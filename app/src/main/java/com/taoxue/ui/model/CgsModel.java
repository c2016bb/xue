package com.taoxue.ui.model;

/**
 * Created by User on 2017/6/21.
 */

public class CgsModel extends BaseModel {
             private String cgs_name;
            private String logo;

    public String getCgs_name() {
        return cgs_name;
    }

    public void setCgs_name(String cgs_name) {
        this.cgs_name = cgs_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "CgsModel{" +
                "cgs_name='" + cgs_name + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
