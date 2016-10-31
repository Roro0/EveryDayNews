package edu.feucui.everydaynews.net;

import java.util.Map;
import java.util.Set;

/**
 * 工具类
 * Created by Administrator on 2016/9/22.
 */
public class ParamUtil {
    public static String getUrl(Map<String,String> param) {
        StringBuffer buffer = new StringBuffer();

        if (param!=null&&param.size()!=0){
            buffer.append("?");
            Set<String> keySet = param.keySet();
            for (String key:keySet) {
                buffer.append(key).append("=").append(param.get(key)).append("&");
            }
            buffer.deleteCharAt(buffer.length()-1);//如果是最后一个，没有&
        }
        return buffer.toString();
    }
}
