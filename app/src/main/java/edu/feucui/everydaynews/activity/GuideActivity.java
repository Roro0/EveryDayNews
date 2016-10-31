package edu.feucui.everydaynews.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.util.ArrayList;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.adapter.GuiderAdapter;
import edu.feucui.everydaynews.net.Constant;

public class GuideActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    SharedPreferences sharedPreferences;
    ArrayList<ImageView> mImage;//将图片方到ImageView中
    Button mEnter;//直接进入按钮


int[] imgSrc=new int[]{R.mipmap.bd,R.mipmap.welcome,R.mipmap.wy,R.mipmap.small};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_guide);//调用父类的，会自动调用Init()
        mEnter.setOnClickListener(this);
        if (!getData()){//如果不是第一次进入,直接进入主界面
            Intent intent =new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void init() {

        mBaseHead.setVisibility(View.GONE);//

        ViewPager mViewPager = (ViewPager) findViewById(R.id.iv_guide_viewpager);
          mEnter = (Button) findViewById(R.id.btn_guide_enter);
        //将图片添加到ImageView中----数据源
       mImage = new ArrayList<ImageView>();
        for (int i=0;i<imgSrc.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imgSrc[i]);
            mImage.add(imageView);
        }
        //设置适配器
        GuiderAdapter adapter= new GuiderAdapter(mImage);
        mViewPager.setAdapter(adapter);
        mEnter.setVisibility(View.GONE);
        mViewPager.setOnPageChangeListener(this);
    }

    /**
     * 通过SharedPreferences存储是否首次进入的数据
     * @return
     */
    private boolean getData(){
          sharedPreferences = getSharedPreferences(Constant.PREFERENCE_NAME,MODE_PRIVATE);
        boolean isFirst =  sharedPreferences.getBoolean("isFirst",true);
        return isFirst;
    }

    /**
     * 直接进入按钮的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putBoolean("isFirst",false);
        editor.commit();

     Intent intent = new Intent(this,HomeActivity.class);
     startActivity(intent);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 当页面被选中的时候，将相应的页面展示出来
     * @param position
     */
    @Override
    public void onPageSelected(int position) {

for (ImageView  img : mImage){
    mEnter.setVisibility(View.GONE);
        if (position==3) {
            mEnter.setVisibility(View.VISIBLE);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GuideActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
