package edu.feucui.everydaynews.entity;

/**
 * 用户评论
 * Created by Administrator on 2016/10/24.
 */
public class SubmitComment {
    /**
     * 0:评论已发送
     -1:非法关键字
     -2:禁止评论(政治敏感新闻)
     -3:禁止评论(用户被禁言)
     */
     public int  result;
   public String explain;//发布成功
}
