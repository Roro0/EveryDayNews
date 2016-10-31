package edu.feucui.everydaynews.Util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.net.SetResultFinishListener;

/**
 * json的封装和解析
 * Created by Administrator on 2016/9/30.
 */
public class VolleyTest extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_load_test);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
      Button mButton = (Button) findViewById(R.id.btn_down_load);
        mButton.setOnClickListener(this);
    }

    /**
     * 网络请求
     */
    public void toJson(){
    final String TAG="斑猪拖拖";
    String url = "http://118.244.212.82:9092/newsClient/user_home";
    Map<String,String> param = new HashMap<>();
    param.put("imei","000000000000000");
    param.put("token","");
    param.put("ver","0000000");
   MyVolley.get(this, url, param, new MyVolley.setResultListener() {
       @Override
       public void success(String s) {
           Log.e(TAG, "success: "+s.toString());
       }

       @Override
       public void failer(VolleyError volleyError) {
           Log.e(TAG, "failer: "+volleyError.toString() );
       }
   });


}


public  void toJava(Context context,String url){
    RequestQueue requestQueue1 = Volley.newRequestQueue(context);
    JSONObject jsonObject= new JSONObject();


      JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject jsonObject) {
              //取消弹框
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
       Log.e("bbbbbb","出错啦");

          }
      });
        requestQueue1.add(jsonObjectRequest);
}


    @Override
    public void onClick(View v) {
        toJson();
    }
}
