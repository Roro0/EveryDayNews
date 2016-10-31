package edu.feucui.everydaynews.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.net.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Administrator on 2016/10/17.
 */
public class OkHttpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_load_test);
//        get();
post();
    }

    /**
     * get请求
     */
    public void get(){
        //1.实例化一个OKHttpClient对象
       OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(3000,TimeUnit.MILLISECONDS).build();
        //2.新建一个请求
        Request request = new Request.Builder().get()
                             .url("http://118.244.212.82:9092/newsClient/news_sort?ver=000000&imei=000000000000000").build();
        //3.加入请求
        Call call = okHttpClient.newCall(request);
        //4.执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFaulure","onFaulure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("onResponse","onResponse----"+str);
            }
        });
    }

    /**
     * post请求
     */
    public void post(){
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(3,TimeUnit.MILLISECONDS).build();
       FormBody body =  new FormBody.Builder().add("ver","0000000")
                .add("imei","000000000000000").build();
        Request request =  new Request.Builder().post(body).url("http://118.244.212.82:9092/newsClient/news_sort").build();
                Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFaulure","onFaulure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("onResponse","onResponse----"+str);
            }
        });
    }
}
