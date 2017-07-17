package com.taoxue.ui.model;

import com.danxx.mdplayer.model.Model;

/**
 * Created by User on 2017/4/11.
 */

public class ResourceDetailModel extends BaseModel{

    private String file_format;
    private String file_path;
    private String file_type;
    private String filesize;
    private String gys_id;
    private String pingfen;
    private String pinglun_num;
    private String resource_id;
    private String resource_name;
    private String resource_picture;
    private String status;
    private String upload_time;
    private String upload_user_ip;
    private String upoad_user_id;
    private String url;
    private String yuedu_num;

    @Override
    public String toString() {
        return "ResourceDetailModel{" +
                "file_format='" + file_format + '\'' +
                ", file_path='" + file_path + '\'' +
                ", file_type='" + file_type + '\'' +
                ", filesize='" + filesize + '\'' +
                ", gys_id='" + gys_id + '\'' +
                ", pingfen='" + pingfen + '\'' +
                ", pinglun_num='" + pinglun_num + '\'' +
                ", resource_id='" + resource_id + '\'' +
                ", resource_name='" + resource_name + '\'' +
                ", resource_picture='" + resource_picture + '\'' +
                ", status='" + status + '\'' +
                ", upload_time='" + upload_time + '\'' +
                ", upload_user_ip='" + upload_user_ip + '\'' +
                ", upoad_user_id='" + upoad_user_id + '\'' +
                ", url='" + url + '\'' +
                ", yuedu_num='" + yuedu_num + '\'' +
                '}';
    }

    public String getFile_format() {
        return file_format;
    }

    public void setFile_format(String file_format) {
        this.file_format = file_format;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getGys_id() {
        return gys_id;
    }

    public void setGys_id(String gys_id) {
        this.gys_id = gys_id;
    }

    public String getPingfen() {
        return pingfen;
    }

    public void setPingfen(String pingfen) {
        this.pingfen = pingfen;
    }

    public String getPinglun_num() {
        return pinglun_num;
    }

    public void setPinglun_num(String pinglun_num) {
        this.pinglun_num = pinglun_num;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getUpload_user_ip() {
        return upload_user_ip;
    }

    public void setUpload_user_ip(String upload_user_ip) {
        this.upload_user_ip = upload_user_ip;
    }

    public String getUpoad_user_id() {
        return upoad_user_id;
    }

    public void setUpoad_user_id(String upoad_user_id) {
        this.upoad_user_id = upoad_user_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYuedu_num() {
        return yuedu_num;
    }

    public void setYuedu_num(String yuedu_num) {
        this.yuedu_num = yuedu_num;
    }
}
