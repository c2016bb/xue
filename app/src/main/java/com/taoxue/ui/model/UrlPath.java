package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/5/2.
 */

public class UrlPath extends BaseModel {
    private List<UrlModel> urlModel;

    public List<UrlModel> getUrlModel() {
        return urlModel;
    }

    public void setUrlModel(List<UrlModel> urlModel) {
        this.urlModel = urlModel;
    }

    @Override
    public String toString() {
        return "UrlPath{" +
                "urlModel=" + urlModel +
                '}';
    }
}
