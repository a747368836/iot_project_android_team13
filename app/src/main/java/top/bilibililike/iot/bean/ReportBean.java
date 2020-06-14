package top.bilibililike.iot.bean;

import org.litepal.crud.LitePalSupport;

public class ReportBean extends LitePalSupport {
    //userid	username	time	issick	address
    public String userId;
    String userName;
    String time;
    boolean isSick;
    String address;
    boolean isFamilySick;
    String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean isFamilySick() {
        return isFamilySick;
    }

    public void setFamilySick(boolean familySick) {
        isFamilySick = familySick;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSick() {
        return isSick;
    }

    public void setSick(boolean sick) {
        isSick = sick;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
