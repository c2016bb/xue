package com.taoxue.ui.model.homefrag;

import com.taoxue.ui.model.BaseResultModel;

import java.util.List;

/**
 * Created by User on 2017/4/6.
 */

public class ApiOneBean extends BaseResultModel {
    @Override
    public String toString() {
        return "ApiOneBean{" +
                "bdqd=" + bdqd.size() +
                ", cnxh=" + cnxh.size() +
                ", gctj=" + gctj.size() +
                ", rmhs=" + rmhs.size() +
                '}';
    }

    private List<BdqdBean> bdqd;
    private List<BdqdBean> cnxh;
    private List<BdqdBean> gctj;
    private List<BdqdBean> rmhs;

    public List<BdqdBean> getBdqd() {
        return bdqd;
    }

    public void setBdqd(List<BdqdBean> bdqd) {
        this.bdqd = bdqd;
    }

    public List<BdqdBean> getCnxh() {
        return cnxh;
    }

    public void setCnxh(List<BdqdBean> cnxh) {
        this.cnxh = cnxh;
    }

    public List<BdqdBean> getGctj() {
        return gctj;
    }

    public void setGctj(List<BdqdBean> gctj) {
        this.gctj = gctj;
    }

    public List<BdqdBean> getRmhs() {
        return rmhs;
    }

    public void setRmhs(List<BdqdBean> rmhs) {
        this.rmhs = rmhs;
    }

    public static class BdqdBean {
        /**
         * discription : 儿歌《两只老虎》曲调简洁优美，朗朗上口。
         * id : 402883bc5b416064015b422316850010
         * resource_picture : http://117.71.57.47:9000/DRIS_manager_V1.0.1/upload/2017/04/17/402883bc5b79d323015b79dbd461000e_150.jpg
         * title : 两只老虎
         */

        private String discription;
        private String id;
        private String resource_picture;
        private String title;
        private String file_type;

        @Override
        public String toString() {
            return "BdqdBean{" +
                    "discription='" + discription + '\'' +
                    ", id='" + id + '\'' +
                    ", resource_picture='" + resource_picture + '\'' +
                    ", title='" + title + '\'' +
                    ", file_type='" + file_type + '\'' +
                    '}';
        }

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getResource_picture() {
            return resource_picture;
        }

        public void setResource_picture(String resource_picture) {
            this.resource_picture = resource_picture;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
