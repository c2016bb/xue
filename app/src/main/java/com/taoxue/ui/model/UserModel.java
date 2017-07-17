package com.taoxue.ui.model;

/**
 * Created by Administrator on 2016/12/20.
 */

public class UserModel extends BaseModel  {

// addtime='1491903045000',
// email='',
// identity_type='mobile',
// jinfen='0',
// last_login_ip='',
// last_login_time='1491903045000',
// mobile='13365654953',
// name='朱鹏',
// nick='',
// photo='',
// sex='',
// status='1',
// type='10',
// user_id='402883bc5b5c59bd015b5c59dd8d0001'

    private String addtime;
    private String email;
    private String identity_type;
    private String jinfen;
    private String last_login_ip;
    private String last_login_time;
    private String mobile;
    private String name;
    private String nick;
    private String photo;
    private String sex;
    private String status;
    private String type;
    private String user_id;

    /**
     * name : LOL同位语兔兔
     * mobile : 13365654953
     * identity_type : mobile
     * email :
     * sex : 男
     * addtime : 1491903045000
     * last_login_time : 1491903045000
     * last_login_ip :
     * jinfen : 0
     * status : 1
     * type : 10
     * photo :
     * cardid :
     * school :
     * family_address :
     * education : 后台无数据
     * hangye : 研究生
     * now_address :
     * job : 社会闲散人员
     * birth_year : 2017-04-26
     */

    private String cardid;
    private String school;
    private String family_address;
    private String education;
    private String hangye;
    private String now_address;
    private String job;
    private String birth_year;

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFamily_address() {
        return family_address;
    }

    public void setFamily_address(String family_address) {
        this.family_address = family_address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHangye() {
        return hangye;
    }

    public void setHangye(String hangye) {
        this.hangye = hangye;
    }

    public String getNow_address() {
        return now_address;
    }

    public void setNow_address(String now_address) {
        this.now_address = now_address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type;
    }

    public String getJinfen() {
        return jinfen;
    }

    public void setJinfen(String jinfen) {
        this.jinfen = jinfen;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id+"";
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "addtime='" + addtime + '\'' +
                ", email='" + email + '\'' +
                ", identity_type='" + identity_type + '\'' +
                ", jinfen='" + jinfen + '\'' +
                ", last_login_ip='" + last_login_ip + '\'' +
                ", last_login_time='" + last_login_time + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", photo='" + photo + '\'' +
                ", sex='" + sex + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
