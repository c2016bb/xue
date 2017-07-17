package com.taoxue.ui.model;

import java.io.Serializable;

/**
 * Created by User on 2017/4/14.
 */

public class ResourceModel extends BaseModel{
            private String catalog;
            private String collection_num;
            private String comment_num;
            private String discription;
            private String file_format;
            private String file_type;
            private String gys_id;
            private String gys_name;
            private String is_collection;
            private String praise_num;
            private String read_num;
            private String resource_id;
            private String resource_name;
            private String resource_picture;
            private String score_num;

    @Override
    public String toString() {
        return "ResourceModel{" +
                "catalog='" + catalog + '\'' +
                ", collection_num='" + collection_num + '\'' +
                ", comment_num='" + comment_num + '\'' +
                ", discription='" + discription + '\'' +
                ", file_format='" + file_format + '\'' +
                ", file_type='" + file_type + '\'' +
                ", gys_id='" + gys_id + '\'' +
                ", gys_name='" + gys_name + '\'' +
                ", is_collection='" + is_collection + '\'' +
                ", praise_num='" + praise_num + '\'' +
                ", read_num='" + read_num + '\'' +
                ", resource_id='" + resource_id + '\'' +
                ", resource_name='" + resource_name + '\'' +
                ", resource_picture='" + resource_picture + '\'' +
                ", score_num='" + score_num + '\'' +
                '}';
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCollection_num() {
        return collection_num;
    }

    public void setCollection_num(String collection_num) {
        this.collection_num = collection_num;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getFile_format() {
        return file_format;
    }

    public void setFile_format(String file_format) {
        this.file_format = file_format;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getGys_id() {
        return gys_id;
    }

    public void setGys_id(String gys_id) {
        this.gys_id = gys_id;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public String getGys_name() {
        return gys_name;
    }

    public void setGys_name(String gys_name) {
        this.gys_name = gys_name;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public String getResource_picture() {
        return resource_picture;
    }

    public void setResource_picture(String resource_picture) {
        this.resource_picture = resource_picture;
    }

    public String getScore_num() {
        return score_num;
    }

    public void setScore_num(String score_num) {
        this.score_num = score_num;
    }
}
