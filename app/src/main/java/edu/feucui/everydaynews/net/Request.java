package edu.feucui.everydaynews.net;

import java.util.Map;

/**
 * 请求所用的数据
 * Created by Administrator on 2016/9/22.
 */
public class Request {
    public String url;//请求的路径
    public Map<String ,String> params;//请求的参数
    public int Type;//请求的类型---GET=10,POST=11

}
