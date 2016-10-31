package edu.feucui.everydaynews.entity;

/**
 * Created by Administrator on 2016/10/10.
 */
public class Registe {
    /**
     * 0:正常注册
     *-1:服务器不允许注册(用户数量已满)
     *-2:用户名重复
     *-3:邮箱重复
     */
 public int result;//注册状态
  public   String explain;//注册说明
 public String token;//用户令牌,result=0时必须
}
