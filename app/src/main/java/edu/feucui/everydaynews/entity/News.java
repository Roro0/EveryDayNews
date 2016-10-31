package edu.feucui.everydaynews.entity;

/**
 * 新闻实体类
 * Created by Administrator on 2016/9/30.
 */
public class News {

    public String summary;//摘要
   public String icon;//图标的链接
    public int nid;//标识---在上拉操作时需要更新更多新闻时，以此新闻id可以查询更早的新闻数据。
   public  String title;//标题
    int type;//类型
   public String link;//具体新闻的链接
   public String stamp;//时间戳--新闻发布的时间
}
