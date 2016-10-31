package edu.feucui.everydaynews.net;

import android.content.Context;
import android.util.Log;

import java.util.Map;

/**
 * 网络请求类:封装了get/post
 * Created by Administrator on 2016/9/22.
 */
public class MyHttp {
    public static void get(Context context, String url, Map<String ,String> params, SetResultFinishListener mListener){
//进行网络请求

        Request request = new Request();
        request.params=params;
        request.Type= Constant.GET;
        request.url=url+ ParamUtil.getUrl(params);
        Log.e("URL",request.url);
        //请求
        NetAsync netAsync = new NetAsync(context,mListener);
        netAsync.execute(request);//一个异步任务对象只能开启一个任务
    }
    public static void post(Context context, String url, Map<String ,String> params, SetResultFinishListener mListener){
        Request request = new Request();
        request.params=params;
        request.Type= Constant.POST;
        request.url=url;
        //请求
        NetAsync netAsync = new NetAsync(context,mListener);
        netAsync.execute(request);
    }
}
