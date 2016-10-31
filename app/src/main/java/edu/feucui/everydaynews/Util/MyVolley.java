package edu.feucui.everydaynews.Util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/10/26.
 */
public class MyVolley {

    public static void get(Context context, String url, Map<String ,String> params, final setResultListener mListener){

        RequestQueue queue = Volley.newRequestQueue(context);
       StringRequest request = new StringRequest(Request.Method.GET, url+ParamUtil.getUrl(params), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
            mListener.success(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mListener.failer(volleyError);
            }
        });
        queue.add(request);

    }
    public void post(Context context, String url,final setResultListener mListener){
        RequestQueue queue = Volley.newRequestQueue(context);
      StringRequest request  =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getPostParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("","");
                return map;
            }
        };
        queue.add(request);
    }


    public interface setResultListener{
       public abstract void success(String s);
        public abstract    void failer(VolleyError volleyError);
    }

    /**
     * 设置参数
     */
    public static class ParamUtil{
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
}

