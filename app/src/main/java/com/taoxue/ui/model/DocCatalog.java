package com.taoxue.ui.model;

/**
 * Created by User on 2017/4/14.
 */

public class DocCatalog extends BaseModel {
    private String 	keyword;//				关键词	直接以自然语言中未经控制或只作少量控制的语词标引资源的主题内容。多个关键词中间用,分割
    private String 	copyrightOwner;//		版权拥有者	拥有资源版权的个人或团体。
    private String 	cutomorClass_5;//		文档自定义分类_1
    private String 	dateOfpublic;//		出版日期	资源正式出版的日期。
    private String 	publisher;//			出版者	出版者包括个人、组织或某项服务。通常用出版者的名称来标识。
    private String 	discription;//		描述	资源的说明解释。
    private String 	customclass;//			自定义分类法
    private String 	author;//				作者
    private String 	CLC;//					中图法
    private String 	title;//				题名
    @Override
    public String toString() {
        return "Catalog{" +
                "keyword='" + keyword + '\'' +
                ", copyrightOwner='" + copyrightOwner + '\'' +
                ", cutomorClass_5='" + cutomorClass_5 + '\'' +
                ", dateOfpublic='" + dateOfpublic + '\'' +
                ", publisher='" + publisher + '\'' +
                ", discription='" + discription + '\'' +
                ", customclass='" + customclass + '\'' +
                ", author='" + author + '\'' +
                ", CLC='" + CLC + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getCopyrightOwner() {
        return copyrightOwner;
    }

    public void setCopyrightOwner(String copyrightOwner) {
        this.copyrightOwner = copyrightOwner;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDateOfpublic() {
        return dateOfpublic;
    }

    public void setDateOfpublic(String dateOfpublic) {
        this.dateOfpublic = dateOfpublic;
    }

    public String getCutomorClass_5() {
        return cutomorClass_5;
    }

    public void setCutomorClass_5(String cutomorClass_5) {
        this.cutomorClass_5 = cutomorClass_5;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getCustomclass() {
        return customclass;
    }

    public void setCustomclass(String customclass) {
        this.customclass = customclass;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCLC() {
        return CLC;
    }

    public void setCLC(String CLC) {
        this.CLC = CLC;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
