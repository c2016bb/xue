package com.taoxue.ui.model;

/**
 * Created by User on 2017/4/20.
 */

public class ImageCatalog extends BaseModel {
             private String author;
            private String copyrightOwner; //版权拥有者	拥有资源版权的个人或团体。
            private String discription;
            private String keyword;   //关键词	直接以自然语言中未经控制或只作少量控制的语词标引资源的主题内容。
            private String title;

    @Override
    public String toString() {
        return "ImageResultModel{" +
                "author='" + author + '\'' +
                ", copyrightOwner='" + copyrightOwner + '\'' +
                ", discription='" + discription + '\'' +
                ", keyword='" + keyword + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getCopyrightOwner() {
        return copyrightOwner;
    }

    public void setCopyrightOwner(String copyrightOwner) {
        this.copyrightOwner = copyrightOwner;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
