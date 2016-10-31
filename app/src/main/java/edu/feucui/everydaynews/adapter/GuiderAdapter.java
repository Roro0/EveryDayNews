package edu.feucui.everydaynews.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * 引导页的适配器类
 * Created by Administrator on 2016/9/27.
 */
public class GuiderAdapter extends PagerAdapter {

    ArrayList<ImageView> mListImg ;
    public GuiderAdapter(ArrayList<ImageView> mListImg) {
        this.mListImg=mListImg;
    }

    @Override
    public int getCount() {
        return mListImg==null?0:mListImg.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.e("aaaaa","---"+(view==object));
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView img = mListImg.get(position);
        container.addView(img);
        return img;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(mListImg.get(position));
    }
}
