package com.taoxue.ui.model;

import java.io.Serializable;

/**
 * Created by User on 2017/4/10.
 */

public class  GysDataBean implements Serializable {
    /**
     * file_format : mp4
     * file_path :
     * file_type : video
     * filesize :
     * gys_id : 1
     * pingfen : 0
     * pinglun_num : 0
     * resource_id : 402883bc5b3d56fb015b3d8ab79b0007
     * resource_name : 弟子规故事——小兔种葡萄
     * resource_picture : http://117.71.57.47:9000/DRIS_manager_V1.0.1//upload/2017/04/07/402883bc5b479bb9015b47a4672a0008_150.png
     * status : 1
     * upload_time : 2017-04-07 17:00:27
     * upload_user_ip : 117.71.57.47
     * upoad_user_id :
     * url : http://117.71.57.47:9000/DRIS_manager_V1.0.1//upload/402883bc5b479bb9015b47a4a9930009.mp4
     * yuedu_num : 0
     */

    private String file_format;
    private String file_path;
    private String file_type;
    private String filesize;
    private String gys_id;
    private int pingfen;
    private int pinglun_num;
    private String resource_id;
    private String resource_name;
    private String resource_picture;
    private String status;
    private String upload_time;
    private String upload_user_ip;
    private String upoad_user_id;
    private String url;
    private int yuedu_num;

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

    public int getPingfen() {
        return pingfen;
    }

    public void setPingfen(int pingfen) {
        this.pingfen = pingfen;
    }

    public int getPinglun_num() {
        return pinglun_num;
    }

    public void setPinglun_num(int pinglun_num) {
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

    public int getYuedu_num() {
        return yuedu_num;
    }

    public void setYuedu_num(int yuedu_num) {
        this.yuedu_num = yuedu_num;
    }
}
