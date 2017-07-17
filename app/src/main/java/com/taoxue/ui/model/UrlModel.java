package com.taoxue.ui.model;

import com.taoxue.utils.UtilTools;

/**
 * Created by User on 2017/4/25.
 */

public class UrlModel extends BaseModel {
         private String resource_name;
          private String url;

    @Override
    public String toString() {
        return "UrlModel{" +
                "resource_name='" + resource_name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
