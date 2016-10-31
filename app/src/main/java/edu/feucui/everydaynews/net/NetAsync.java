package edu.feucui.everydaynews.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/22.
 */
public class NetAsync extends AsyncTask<Request,Object,Response> {

    ProgressDialog progressDialog;
    SetResultFinishListener mListener;
    public NetAsync(Context context, SetResultFinishListener mListener){
         progressDialog = ProgressDialog.show(context,"","加载中");
        this.mListener=mListener;
    }

    @Override
    protected Response doInBackground(Request... params) {//子线程
        Request request = params[0];
       Response response = new Response();
        HttpURLConnection httpURLConnectionttpConn = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(request.url);
              httpURLConnectionttpConn = (HttpURLConnection) url.openConnection();
            httpURLConnectionttpConn.setConnectTimeout(Constant.CONNECTION_TIME_OUE);
            httpURLConnectionttpConn.setReadTimeout(Constant.READ_TIME_OUE);
            Log.e("aac", "doInBackground:进");
            //设置请求的方法
            if (request.Type== Constant.GET){
                Log.e("aac", "doInBackground:进1");
                httpURLConnectionttpConn.setRequestMethod("GET");
            }else {
                httpURLConnectionttpConn.setRequestMethod("POST");
                httpURLConnectionttpConn.setDoOutput(true);//可以向服务器写
                OutputStream out = httpURLConnectionttpConn.getOutputStream();
                out.write(ParamUtil.getUrl(request.params).getBytes());
            }
            Log.e("aac", "doInBackground:进2");
            int code = httpURLConnectionttpConn.getResponseCode();
            Log.e("aac", "doInBackground:进3");
            response.code=code;
            Log.e("code----------","---------"+code);
            if (code==httpURLConnectionttpConn.HTTP_OK){

           inputStream = httpURLConnectionttpConn.getInputStream();
                byte[] bytes = new byte[1024];
                int len;
                StringBuffer buffer = new StringBuffer();
                while ((len=inputStream.read(bytes))!=-1){
                buffer.append(new String(bytes,0,len));
                }//拿到了结果
                response.result = buffer.toString();
                Log.e("响应说明：","---"+response.result+"----------"+response.code);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnectionttpConn!=null){
                httpURLConnectionttpConn.disconnect();

            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response o) {//主线程

        super.onPostExecute(o);
        progressDialog.dismiss();
        Response response=o;
        if (o.code!= HttpURLConnection.HTTP_OK){
         //失败
         mListener.failure(response);
            progressDialog.dismiss();
        }else{
           //成功
            mListener.success(response);
            progressDialog.dismiss();

        }
    }
}
