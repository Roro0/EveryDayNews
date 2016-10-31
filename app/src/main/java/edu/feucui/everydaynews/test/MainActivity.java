package edu.feucui.everydaynews.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feucui.everydaynews.R;

/**
 * Created by Administrator on 2016/10/12.
 */
public class MainActivity extends Activity implements View.OnClickListener {
  static final  int CHOOSE_PEO=0;
    TextView response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_popwindow);
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        LinearLayout mSetting = (LinearLayout) findViewById(R.id.ll_pop_seting);
//          response = (TextView) findViewById(R.id.tv_pop_setting_response);
        mSetting.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        Log.e("onclick","进");
        Intent intent = new Intent(this,ChooseActivity.class);
//        startActivity(intent);//该方法只是跳转，没有传递数据
        startActivityForResult(intent,CHOOSE_PEO);//具有返回数据的跳转
    }

    /**
     * 拿到回传数据的结果
     * @param requestCode 请求吗   区分返回结果的请求
     * @param resultCode   结果码   区分请求结果是否成功
     * @param data  回传的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHOOSE_PEO:
             if (resultCode==RESULT_OK){//有数据
                 //拿数据
                 ArrayList<String> list = data.getStringArrayListExtra("data1");
                 //将结果展示出来
                 StringBuffer buff = new StringBuffer();
                 for (String string:list) {
                     buff.append(string).append(",");
                 }
                 buff.deleteCharAt(buff.length()-1);
                response.setText(buff.toString());
             }
        }
    }
}
