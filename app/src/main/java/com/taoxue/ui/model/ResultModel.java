package com.taoxue.ui.model;

/**
 * Created by User on 2017/4/7.
 */

public class ResultModel extends BaseModel {
    private String author;
    private String discription;
    private String file_type;
    private String gys_id;
    private String id;
    private String publisher;
    private String resource_picture;
    private String title;
    private String url;
    private String CLC;
    private String collection_num;
    private String comment_num;
    private String customclass;
    private String praise_num;
    private String read_num;
    private String score_num;

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

    public String getCustomclass() {
        return customclass;
    }

    public void setCustomclass(String customclass) {
        this.customclass = customclass;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getScore_num() {
        return score_num;
    }

    public void setScore_num(String score_num) {
        this.score_num = score_num;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "author='" + author + '\'' +
                ", discription='" + discription + '\'' +
                ", file_type='" + file_type + '\'' +
                ", gys_id='" + gys_id + '\'' +
                ", id='" + id + '\'' +
                ", publisher='" + publisher + '\'' +
                ", resource_picture='" + resource_picture + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", CLC='" + CLC + '\'' +
                ", collection_num='" + collection_num + '\'' +
                ", comment_num='" + comment_num + '\'' +
                ", customclass='" + customclass + '\'' +
                ", praise_num='" + praise_num + '\'' +
                ", read_num='" + read_num + '\'' +
                ", score_num='" + score_num + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getResource_picture() {
        return resource_picture;
    }

    public void setResource_picture(String resource_picture) {
        this.resource_picture = resource_picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
