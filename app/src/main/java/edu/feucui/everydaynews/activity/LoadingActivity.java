package edu.feucui.everydaynews.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import edu.feucui.everydaynews.R;

/**
 * 加载界面
 * Created by Administrator on 2016/10/13.
 */
public class LoadingActivity  extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        imageView.setBackgroundResource(R.color.loadingPageRed);
        imageView.setImageResource(R.mipmap.logo);

        //TODO  logo随着加载进度的变化颜色变深-----动画/时间延迟器
        AlphaAnimation animation = new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(1000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(animation);
        linearLayout.addView(imageView);




    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }
}
