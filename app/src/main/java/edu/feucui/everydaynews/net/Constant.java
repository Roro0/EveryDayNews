package edu.feucui.everydaynews.net;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Constant {
    public static  final int GET=10;
    public static  final int POST=11;
    public static final int READ_TIME_OUE=5000;
    public static final int CONNECTION_TIME_OUE=5000;
    /**
     * 根链接
     */
    public static final String BASE_URL="http://118.244.212.82:9092/newsClient";
    /**
     * 子链接：新闻列表
     */
    public static final String NEWS_LIST=BASE_URL+"/news_list";
    /**
     * 新闻分类
     */
    public static final String NEWS_SORT=BASE_URL+"/news_sort";
    /**
     * 子链接：用户注册列表
     */

    public static final String REGISTE_LIST=BASE_URL+"/user_register";
    /**
     * 用户登录
     */
    public static final String LOGIN_URL=BASE_URL+"/user_login";
    /**
     * 用户中心
     */
    public static final String PERSONAL_WEB=BASE_URL+"/user_home";
    /**
     * 上传头像
     */
    public static final String UPLOAD_PHOTO=BASE_URL+"/user_image";
    /**
     * 评论数量
     */
    public static final String COMMENT_COUNT=BASE_URL+"/cmt_num";
    /**
     * 显示评论
     */
    public static final String COMMENT_SHOW=BASE_URL+"/cmt_list";
    //sharedPreference文件名
    public static final String  PREFERENCE_NAME="preference_setting";
    /**
     * 创建存储照片的路径
     */
   public static final  String DIR_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"everyDayNews";
    /**
     * 保存照片的具体位置
     */
    public static final  String PHOTO_FILE_PATH=DIR_PATH + File.separator+"photo"+System.currentTimeMillis()+".jpg";
}
