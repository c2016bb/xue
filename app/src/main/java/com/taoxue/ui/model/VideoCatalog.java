package com.taoxue.ui.model;

/**
 * Created by User on 2017/4/20.
 */

public class VideoCatalog extends BaseModel {
    private String 	discription;//	描述	资源的说明解释。
    private String 	keyword;//	关键词	直接以自然语言中未经控制或只作少量控制的语词标引资源的主题内容。多个关键词中间用,分割
    private String 	ISRC;//	ISRC	国际标准音像制品编码是正式出版发行的国际通用的音像制品的代码。
    private String 	duration;//	时长	（1） 时长仅供视音频资源用。（2） 采用“ HH:MM:SS.SS”格式
    private String 	title;//	题名
    private String 	copyrightOwner;//	版权拥有者	拥有资源版权的个人或团体。

    @Override
    public String toString() {
        return "VideoResultModel{" +
                "discription='" + discription + '\'' +
                ", keyword='" + keyword + '\'' +
                ", ISRC='" + ISRC + '\'' +
                ", duration='" + duration + '\'' +
                ", title='" + title + '\'' +
                ", copyrightOwner='" + copyrightOwner + '\'' +
                '}';
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getISRC() {
        return ISRC;
    }

    public void setISRC(String ISRC) {
        this.ISRC = ISRC;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCopyrightOwner() {
        return copyrightOwner;
    }

    public void setCopyrightOwner(String copyrightOwner) {
        this.copyrightOwner = copyrightOwner;
    }
}
