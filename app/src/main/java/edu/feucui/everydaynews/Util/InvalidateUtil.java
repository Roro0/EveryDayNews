package edu.feucui.everydaynews.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证类：用户名。密码，邮箱
 * Created by Administrator on 2016/10/11.
 */
public class InvalidateUtil {
    /**
     * 邮箱验证
     * @param strEmail
     * @return
     */
    public  static boolean isEmail(String strEmail) {
        //^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
        //^[a-zA-Z][\w\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]$
        String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 账户验证
     * @param strAccount
     * @return
     */
    public static  boolean isAccount(String strAccount){
        String strPattern = "[0-9a-zA-Z]{6,24}";//6,24位英文字母、数字、下划线组成
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strAccount);
        return m.matches();
    }

    /**
     * 密码验证
     * @param strPwd
     * @return
     */
    public static boolean isPwd(String strPwd){
        String strPattern = "^\\w{6,24}$";//6~24位，可用符号有：英文字母，下划线和数字
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strPwd);
        return m.matches();
    }
}
