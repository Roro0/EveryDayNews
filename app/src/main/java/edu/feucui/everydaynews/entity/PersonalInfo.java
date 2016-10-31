package edu.feucui.everydaynews.entity;

import java.util.ArrayList;

/**
 * 个人信息
 * Created by Administrator on 2016/10/18.
 */
public class PersonalInfo {

   public String uid;//用户名
    public String portrait;//头像
    public int integration;//积分
    public   int comnum;//跟帖数量
    public   ArrayList<LoginLog> loginlog;//登陆日志
}
