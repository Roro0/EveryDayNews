package edu.feucui.everydaynews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import edu.feucui.everydaynews.R;

/**
 * Created by Administrator on 2016/10/10.
 */
public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_forget_pwd);

    }

    @Override
    void init() {
        EditText forgetEdit = (EditText) findViewById(R.id.forget_edit_text);
        TextView confirm = (TextView) findViewById(R.id.tv_forget_confirm);
         String getMail= forgetEdit.getText().toString();
        setOnClickListeners(this,mBaseLeft,confirm);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        //点击某个控件后，键盘自动缩回
        InputMethodManager inputMethodMgr= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodMgr.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        switch (v.getId()){
            case R.id.iv_base_left:
                intent.setClass(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget_confirm:
               //TODO
                break;

        }
    }
}
