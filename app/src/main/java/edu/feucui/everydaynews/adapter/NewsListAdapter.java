package edu.feucui.everydaynews.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.entity.News;

/**
 * 新闻列表的适配器类
 * Created by Administrator on 2016/9/30.
 */
public class NewsListAdapter extends BaseAdapter<News> {

   public static  News news;
    public NewsListAdapter(Context context, ArrayList<News> mList, int layoutId) {
        super(context, mList, layoutId);
    }

    /**
     * 渲染
     * @param holder
     * @param convertView
     * @param position
     * @param news
     */
    @Override
    public void putView(MyHolder holder, View convertView, int position, News news) {
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_news_show_title);
        tvTitle.setText(news.title);//
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_news_show_icon);
        Glide.with(mContext).load(news.icon).into(ivIcon);//icon的渲染
        TextView tvSummary = (TextView) convertView.findViewById(R.id.tv_news_show_summary);
        tvSummary.setText(news.summary);
        TextView tvStamp = (TextView)convertView.findViewById(R.id.tv_news_show_stamp);
        tvStamp.setText(news.stamp);
        this.news = news;

//        myClickListener.getCurrentNews(news);//通过回调将当前新闻发给调用的类
    }
//
//    static MyClickListener myClickListener;
//    public static void setCurrentNews(MyClickListener clickListener){
//         myClickListener = clickListener;
//    }
//    public interface MyClickListener{
//        void getCurrentNews(News news);
//    }
}
