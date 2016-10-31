package edu.feucui.everydaynews.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.Util.InvalidateUtil;
import edu.feucui.everydaynews.entity.RegistResponse;
import edu.feucui.everydaynews.net.Constant;
import edu.feucui.everydaynews.net.MyHttp;
import edu.feucui.everydaynews.net.Response;
import edu.feucui.everydaynews.net.SetResultFinishListener;

/**
 * 用户注册
 * Created by Administrator on 2016/10/10.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener {
    EditText mRegistMail,mRegistName,mRegistPwd;
    String mail,account,pwd;
    TextView mHint;
    CheckBox mCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       super.setContentView(R.layout.activity_register);
        mBaseTitle.setText("用户注册");

    }

    @Override
    void init() {
          mRegistMail = (EditText) findViewById(R.id.EditText_registe_mail_ads);
          mRegistName = (EditText) findViewById(R.id.EditText_registe_name);
          mRegistPwd = (EditText) findViewById(R.id.EditText_registe_pwd);
          mHint = (TextView) findViewById(R.id.tv_activity_register_hint);

        TextView mRegistNow = (TextView) findViewById(R.id.tv_registe_now);//立即注册TextView
          mCheckBox = (CheckBox) findViewById(R.id.checkbox_activity_register_sevicer);
        setOnClickListeners(this,mRegistNow,mBaseLeft);

    }
    public void getHttpData() {

        mail = mRegistMail.getText().toString();
        account = mRegistName.getText().toString();
        pwd = mRegistPwd.getText().toString();
        if (mail.equals("")) {
            mRegistMail.setError("邮箱不能为空！");
        } else if(account.equals("")){
            mRegistName.setError("帐号不能为空！");
        }else if(pwd.equals("")){
            mRegistPwd.setError("密码不能为空！");
        }else if (!InvalidateUtil.isEmail(mail)  ) {
            mRegistMail.setError("邮箱格式不正确！");
        }else if(!InvalidateUtil.isAccount(account)) {
            mRegistName.setError("帐号格式不正确！");
        }else if(!InvalidateUtil.isPwd(pwd)) {
            mRegistPwd.setError("密码格式不正确！");
        }else if(mCheckBox.isChecked()==false) {
            mHint.setTextColor(getResources().getColor(R.color.red_head));
            mHint.setText("您没有同意相关条款！无法进行注册！");
        }else{
            Log.e("==============","验证成功");
            Map<String, String> param = new HashMap<>();
            //user_register?ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
            //user_register?ver=0000000&uid=%22admin%22&email=%22admin@sina.cn%22&pwd=%22admin%22
            //{"message":"OK","status":0,"data":{"result":-3,"token":null,"explain":"邮箱已注册！"}}
            param.put("ver", "0000000");//版本号，默认0000000
            param.put("uid", account);//用户名，6至24位字符串，只能是数字、大小写英文字母或下划线
            param.put("email", mail);//邮箱
            param.put("pwd", pwd);//密码：用户密码长度为6~24位，可用符号有：英文字母，下划线和数字
            MyHttp.get(this, Constant.REGISTE_LIST, param, new SetResultFinishListener() {
                @Override
                public void success(Response response) {
                    //网络请求成功
                    Gson gson = new Gson();
                    RegistResponse array = gson.fromJson(response.result.toString(), RegistResponse.class);
                    Log.e("Gson", array.data.toString());
                    //在此处拿到

                    switch (array.data.result){
                        case 0://正常注册
                            //拿到用户令牌
                            String token = array.data.token;
                            mHint.setText("注册成功！");
                            Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
                            intent.putExtra("token",token);
                            startActivity(intent);
                            break;
                        case -1://服务器不允许注册(用户数量已满)
                            mHint.setTextColor(getResources().getColor(R.color.red_head));
                            mHint.setText("用户数量已满!");
                            break;
                        case -2://用户名重复
                            mHint.setTextColor(getResources().getColor(R.color.red_head));
                            mHint.setText("用户名重复!");
                            break;
                        case -3://邮箱重复
                            mHint.setTextColor(getResources().getColor(R.color.red_head));
                            mHint.setText("邮箱重复!");
                            break;
                    }



                }

                @Override
                public void failure(Response response) {
                    Toast.makeText(RegistActivity.this, "failer", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.iv_base_left://返回主界面
                intent.setClass(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_registe_now://立即注册
                //点击立即注册，键盘自动缩回
                InputMethodManager inputMethodMgr= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodMgr.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                 getHttpData();

                break;

        }
    }

    /**
//     *点击其他地方键盘自动缩回
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//            InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                if (this.getCurrentFocus() != null) {
//                    if ( this.getCurrentFocus().getWindowToken() != null) {
//                        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
//                                InputMethodManager.HIDE_NOT_ALWAYS);
//                    }
//                }
//            }
//            return super.onTouchEvent(event);
//        }

    }

