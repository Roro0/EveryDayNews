package edu.feucui.everydaynews.entity;

/**
 * 登录响应
 * Created by Administrator on 2016/10/11.
 */
public class Login {
   public int result;//登录状态
    String explain;//登陆说明
   public String token;//用户令牌

    @Override
    public String toString() {
        return "Login{" +
                "result=" + result +
                ", explain='" + explain + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
