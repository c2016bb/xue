package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/6/21.
 */

public class ResourceLibModel extends BaseModel {
            private List<CgsModel> buy_drdata_cgs;
            private String description;
            private List<DrResourceModel> drdata_resource;
            private String gys_name;
            private String logo;
            private String name;
            private String resource_num;

    @Override
    public String toString() {
        return "ResourceLibModel{" +
                "buy_drdata_cgs=" + buy_drdata_cgs +
                ", description='" + description + '\'' +
                ", drdata_resource=" + drdata_resource +
                ", gys_name='" + gys_name + '\'' +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", resource_num='" + resource_num + '\'' +
                '}';
    }

    public List<CgsModel> getBuy_drdata_cgs() {
        return buy_drdata_cgs;
    }

    public void setBuy_drdata_cgs(List<CgsModel> buy_drdata_cgs) {
        this.buy_drdata_cgs = buy_drdata_cgs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DrResourceModel> getDrdata_resource() {
        return drdata_resource;
    }

    public void setDrdata_resource(List<DrResourceModel> drdata_resource) {
        this.drdata_resource = drdata_resource;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGys_name() {
        return gys_name;
    }

    public void setGys_name(String gys_name) {
        this.gys_name = gys_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResource_num() {
        return resource_num;
    }

    public void setResource_num(String resource_num) {
        this.resource_num = resource_num;
    }
}
