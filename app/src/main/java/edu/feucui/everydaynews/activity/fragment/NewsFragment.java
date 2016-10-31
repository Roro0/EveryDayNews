package edu.feucui.everydaynews.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.activity.NewsDatileActivity;
import edu.feucui.everydaynews.adapter.NewsListAdapter;
import edu.feucui.everydaynews.entity.News;
import edu.feucui.everydaynews.entity.NewsArray;
import edu.feucui.everydaynews.entity.NewsSortResponse;
import edu.feucui.everydaynews.net.Constant;
import edu.feucui.everydaynews.net.MyHttp;
import edu.feucui.everydaynews.net.Response;
import edu.feucui.everydaynews.net.SetResultFinishListener;
import edu.feucui.everydaynews.xlistview.XListView;

/**
 * 新闻 界面
 * Created by Administrator on 2016/9/29.
 */
public class NewsFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener, View.OnClickListener {


    FragmentActivity activity;//获取当前activity
    NewsListAdapter newsListAdapter;//新闻列表的适配器
    NewsArray array;//新闻列表
    NewsSortResponse newsSortResponse;//新闻分类
    XListView mNewsShows;//新闻列表
    View mView;//声明一个View

    TextView mLimitary,mSocal,mFund,mStock,mMobile,mNba,mEngland;//子条目
    int dir = 1;//方向--1:刷新，2：加载
    int nid = 1;//新闻id,此新闻id可以查询更早的新闻数据---dir=2必须

    public static News detaileNews;//新闻详情

    /**
     * 创建碎片view时调用
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_news, container, false);
        return view;//界面的返回值
    }


    /**
     * View已经创建好了之后，调用
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = getView();
        mNewsShows = (XListView) mView.findViewById(R.id.lv_news_show);

        activity = getActivity();//谁加载，activity就表示谁的对象。---进行activity跳转
        newsListAdapter = new NewsListAdapter(activity, null, R.layout.item_news_show);
          mLimitary = (TextView) view.findViewById(R.id.tv_news_list_limitary);
          mSocal = (TextView) view.findViewById(R.id.tv_news_list_socal);
          mFund = (TextView) view.findViewById(R.id.tv_news_list_fund);
          mStock = (TextView) view.findViewById(R.id.tv_news_list_stock);
          mMobile = (TextView) view.findViewById(R.id.tv_news_list_mobile);
          mNba = (TextView) view.findViewById(R.id.tv_news_list_NBA);
          mEngland = (TextView) view.findViewById(R.id.tv_news_list_england);


        mNewsShows.setAdapter(newsListAdapter);
        //可以进行上拉加载
        mNewsShows.setPullLoadEnable(true);
        //可以进行下拉刷新
        mNewsShows.setPullRefreshEnable(true);

        mNewsShows.setXListViewListener(this);

        mLimitary.setOnClickListener(this);
        mSocal.setOnClickListener(this);
        mFund.setOnClickListener(this);
        mStock.setOnClickListener(this);
        mMobile.setOnClickListener(this);
        mNba.setOnClickListener(this);
        mEngland.setOnClickListener(this);
        getHttp();
        mNewsShows.setOnItemClickListener(this);
    }

    /**
     * 网络请求  新闻分类
     */
    public void getHttpDataSort() {
        //news_sort?ver=版本号&imei=手机标识符
        //http://118.244.212.82:9092/newsClient/news_sort?ver=0000000&imei=000000000000000
        Map<String, String> param = new HashMap<>();
        param.put("ver", "0000000");
        param.put("imei", "000000000000000");
        MyHttp.get(activity, Constant.NEWS_SORT, param, new SetResultFinishListener() {
            @Override
            public void success(Response response) {
                getHttp();
            }
            @Override
            public void failure(Response response) {
            }
        });
    }

    /**
     * 请求网络：新闻列表
     */
    int subid = 1;
    public void getHttp() {
        Map<String, String> param = new HashMap<>();
        //news_list?ver=0000000&subid=1&dir=1&nid=1&stamp=20140321&cnt=20
        param.put("ver", "0000000");//版本号
        param.put("subid", "" + subid);//分类名
        param.put("dir", "" + dir);//上拉加载2，下拉刷新1
        param.put("nid", "" + nid);//新闻id--dir=2时必须  表示最后一条新闻的id
        param.put("stamp", "20140321000000");//新闻时间--dir=2时必须
        param.put("cnt", "20");//最大更新条目
        MyHttp.get(activity, Constant.NEWS_LIST, param, new SetResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson = new Gson();
                array = gson.fromJson(response.result.toString(), NewsArray.class);
                //         array = gson.fromJson(response.result.toString(),new TypeToken<NewsArray>(){}.getType());？？？？？？？
                /**
                 * 有数据  进行添加并刷新
                 */
                if (array.data != null && array.data.size() > 0) {
                    newsListAdapter.mList.addAll(array.data);//加载数据源
                    newsListAdapter.notifyDataSetChanged();
                }
                if (dir == 1) {
                    Date date=new Date();
                   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    mNewsShows.setRefreshTime(format.format(date));
                }

                //关闭上拉加载以及下拉刷新框
                mNewsShows.stopRefresh();
                mNewsShows.stopLoadMore();
            }

            @Override
            public void failure(Response response) {
                Log.e("如果", "进 " + response.result.toString());
                Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
                //关闭上拉加载以及下拉刷新框
                mNewsShows.stopRefresh();
                mNewsShows.stopLoadMore();
            }
        });
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        Toast.makeText(activity,"刷新",Toast.LENGTH_SHORT).show();
        dir = 1;
        newsListAdapter.mList.clear();
        getHttp();
    }
    /**
     * 下拉加载
     */
    @Override
    public void onLoadMore() {
        dir = 2;
        if (newsListAdapter.mList.size() > 0) {
            News news = newsListAdapter.mList.get(newsListAdapter.mList.size() - 1);
            nid = news.nid;
        }
        getHttp();
    }
    /**
     * 新闻详情
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(activity, "" + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        //获取当前新闻详情
        detaileNews = array.data.get(position - 1);//position-1是因为下拉刷新占用了一个位置。上拉加载不用管
        intent.putExtra("link", detaileNews.link);
        intent.putExtra("nid", detaileNews.nid);
        //判断    如果已经登陆-->拿到令牌
        intent.setClass(activity, NewsDatileActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_news_list_limitary:
                //显示军事列表
                subid = 1;
                break;
            case R.id.tv_news_list_socal:
                //显示社会列表
                subid = 2;

                break;
            case R.id.tv_news_list_stock:
                //显示股票列表
                subid = 3;
                break;
            case R.id.tv_news_list_fund:
                //显示基金列表
                subid = 4;
                break;
            case R.id.tv_news_list_mobile:;
                //显示手机列表
                subid = 5;
                break;
            case R.id.tv_news_list_NBA:;
                //显示NBA列表
                subid = 8;
                break;
            case R.id.tv_news_list_england:
                //显示英超列表
                subid = 7;
                break;
        }
        onRefresh();
    }
}
