package edu.feucui.everydaynews.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.adapter.SlideLeftAdapter;
import edu.feucui.everydaynews.entity.SlideLeftEntity;

/**
 * Created by Administrator on 2016/9/27.
 */
public abstract class BaseActivity extends FragmentActivity {
    ImageView mBaseLeft;
    TextView mBaseTitle;
    ImageView mBaseRight;
    LayoutInflater inflater;
    RelativeLayout mContent;
    LinearLayout mBaseHead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//给其子类应用，调用父类的onCreate方法
        super.setContentView(R.layout.activity_base);//调用父类的，否则会自动调用本类的

          mBaseHead = (LinearLayout) findViewById(R.id.ll_guide_head);
        mBaseLeft = (ImageView) findViewById(R.id.iv_base_left);
        mBaseTitle = (TextView) findViewById(R.id.tv_base_title);
        mBaseRight = (ImageView) findViewById(R.id.iv_base_right);
          mContent = (RelativeLayout) findViewById(R.id.ll_base_content);

        inflater = getLayoutInflater();

    }

    /**
     * 模版方法设计模式
     * @param layoutId
     */
    public void setContentView(int layoutId) {
        //将子布局加载到base当中
        inflater.inflate(layoutId,mContent);
        init();
    }
      abstract void init();


    /**
     *通过View进行绑定点击事件
     */
    public  void setOnClickListeners(View.OnClickListener listener, View... view){
        if (listener==null){
            return;
        }else {
            for (View v:view){
                v.setOnClickListener(listener);
            }
        }
    }

    public void addTextChangedLiteners(TextWatcher listener,TextView... view){
        if (listener==null){
            return;
        }else{
            for(TextView tv:view){
                tv.addTextChangedListener(listener);
            }
        }

    }
}
