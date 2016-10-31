package edu.feucui.everydaynews.entity;

/**
 *
 * Created by Administrator on 2016/10/18.
 */
public class LoginLog {

    public  String time;//登录时间
    public  String address;//登录地址
    public  int device;//登录设备

    @Override
    public String toString() {
        return "LoginLog{" +
                "time='" + time + '\'' +
                ", address='" + address + '\'' +
                ", device=" + device +
                '}';
    }
}
