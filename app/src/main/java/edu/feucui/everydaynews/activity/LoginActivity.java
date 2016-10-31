package edu.feucui.everydaynews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.Util.InvalidateUtil;
import edu.feucui.everydaynews.entity.LoginResponse;
import edu.feucui.everydaynews.net.Constant;
import edu.feucui.everydaynews.net.MyHttp;
import edu.feucui.everydaynews.net.Response;
import edu.feucui.everydaynews.net.SetResultFinishListener;

/**
 * 用户登陆界面
 * Created by Administrator on 2016/10/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    String account,pwd;//账户和密码输入内容
    TextInputEditText mEditAccount,mEditPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        mBaseTitle.setText("用户登录");
    }

    @Override
    void init(){

          mEditAccount = (TextInputEditText) findViewById(R.id.login_edit_account);
        mEditPwd = (TextInputEditText) findViewById(R.id.login_edit_password);
        Button mRegist = (Button)  findViewById(R.id.btn_regist);
        Button mForgetPwd = (Button)findViewById(R.id.btn_forget_pwd);
        Button mLogin = (Button)  findViewById(R.id.btn_login);

        setOnClickListeners(this,mBaseLeft,mRegist,mForgetPwd,mLogin);



    }

    /**
     * 请求网络
     */
    public void getHttpData(){
        //获取文本内容
        account = mEditAccount.getText().toString();
        pwd = mEditPwd.getText().toString();
//        Drawable drawable = getResources().getDrawable(R.mipmap.warning);
        if (account.equals("")){//注意：帐号和密码不是null,判空用equals
            mEditAccount.setError("帐号或密码不能为空！");//提示显示在输入框右下角
        }else if( pwd.equals("")){
            mEditPwd.setError("帐号或密码不能为空！");
        }else if (!InvalidateUtil.isAccount(account)){
            mEditAccount.setError("帐号格式不正确！");
        }else if(!InvalidateUtil.isPwd(pwd)){
            mEditPwd.setError("密码格式不正确！");
        }else{

    //user_login?ver=版本号&uid=用户名&pwd=密码&device=0
        Map<String,String> param = new HashMap<>();
        param.put("ver","0000000");
        param.put("uid",account);
        param.put("pwd",pwd);
        param.put("device","0");
        MyHttp.get(this, Constant.LOGIN_URL, param, new SetResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(response.result.toString(), LoginResponse.class);
                Log.e("login", loginResponse.data.toString());
                String token = loginResponse.data.token;
                Log.e("token=",token);
                switch (loginResponse.data.result) {
                    /**
                     * 0:正常登陆,-1:用户名或密码错误
                     * -2:限制登陆(禁言,封IP)
                     * -3:限制登陆(异地登陆等异常)
                     */
                    case 0:
                       Intent intent = new Intent(LoginActivity.this,PersonalWebActivity.class);
                       intent.putExtra("token",token);
                       startActivity(intent);
                        finish();
                        break;
                    case -1:
                        Toast.makeText(LoginActivity.this, "用户名或密码错误！或者该用户不存在！", Toast.LENGTH_LONG).show();
                    case -2:
                        Toast.makeText(LoginActivity.this, "限制登录(禁言,封IP)", Toast.LENGTH_LONG).show();
                    case -3:
                        Toast.makeText(LoginActivity.this, "限制登录(异地登陆等异常)", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void failure(Response response) {
                Toast.makeText(LoginActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
            }
        });

}
    }

    /**
     * 登陆界面的3个按钮的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        //点击后，键盘自动缩回
        InputMethodManager inputMethodMgr= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodMgr.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        switch (v.getId()){
            case R.id.iv_base_left://返回home界面
             finish();
                break;
            case R.id.btn_regist://转到注册界面
                intent.setClass(this,RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_forget_pwd://转到忘记密码界面
                intent.setClass(this,ForgetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login://转到个人界面---
             getHttpData();
             break;
        }
    }


}
