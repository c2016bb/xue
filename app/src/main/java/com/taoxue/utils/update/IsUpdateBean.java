package com.taoxue.utils.update;


import com.taoxue.ui.model.BaseResultModel;

/**
 * Created by Administrator on 2016/8/4.
 */
public class IsUpdateBean extends BaseResultModel {

    /**
     * url : http://192.168.1.200:8083/app.apk
     * isUpdate : 1
     */
    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String url;
        private String isUpdate;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIsUpdate() {
            return isUpdate;
        }

        public void setIsUpdate(String isUpdate) {
            this.isUpdate = isUpdate;
        }
    }
}
