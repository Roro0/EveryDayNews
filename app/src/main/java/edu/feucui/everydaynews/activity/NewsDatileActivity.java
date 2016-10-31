package edu.feucui.everydaynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.adapter.NewsListAdapter;
import edu.feucui.everydaynews.entity.CommentCount;
import edu.feucui.everydaynews.entity.News;
import edu.feucui.everydaynews.net.Constant;
import edu.feucui.everydaynews.net.MyHttp;
import edu.feucui.everydaynews.net.Response;
import edu.feucui.everydaynews.net.SetResultFinishListener;

/**
 * 新闻详情界面
 * WebView
 * Created by Administrator on 2016/10/8.
 */
public class NewsDatileActivity extends Activity implements View.OnClickListener  {
     WebView mWebView;//新闻详情展示控件
    ImageView mCollect;//新闻详情
    View popCollect;//布局加载popWindow
    PopupWindow popWindow;
    TextView mTie;//跟帖
    int nid;//当前新闻的编号
  public static  ArrayList<News> collecd;//收藏的新闻
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_show_detaile);

    }

    @Override
    public void onContentChanged(){
        super.onContentChanged();
        mWebView = (WebView)findViewById(R.id.webView_news_dateil);
        mCollect = (ImageView) findViewById(R.id.iv_news_detail_collect);
        mTie = (TextView) findViewById(R.id.tv_news_show_detaile_tie);
        //加载收藏PoWindow
        popCollect = LayoutInflater.from(this).inflate(R.layout.view_collect_popwindow,null);
        LinearLayout mPop = (LinearLayout) popCollect.findViewById(R.id.ll_news_detail_pop_collect);
        popWindow = new PopupWindow(popCollect, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //webView添加设置
        WebSettings webSettings = mWebView.getSettings();
        //支持JavaScript写的网页
//        webSettings.setJavaScriptEnabled(true);
        //适应屏幕大小
//        webSettings.setUseWideViewPort(true);
        //支持缩放视图
        webSettings.setBuiltInZoomControls(true);
        //支持缩放功能
//        webSettings.setSupportZoom(true);
        //可以按照任意比例进行缩放
//        webSettings.setLoadWithOverviewMode(true);
        //加载链接
//        mWebView.loadUrl(NewsFragment.detaileNews.link);
        Intent intent= getIntent();
        String link = intent.getStringExtra("link");
        nid = intent.getIntExtra("nid",0);
        mWebView.loadUrl(link);
        //为了使用超链接功能
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }
        });


        mCollect.setOnClickListener(this);
        mPop.setOnClickListener(this);
        getHttpData();
        //拿到评论数量后点击显示评论列表
        mTie.setOnClickListener(this);
    }

    /**
     * 网络请求  ：查找评论数量
     */
    public void getHttpData(){
//cmt_num?ver=版本号& nid=新闻编号评论数量
        Map<String,String> param = new HashMap<>();
        param.put("ver","0000000");
        param.put("nid",""+nid);
        MyHttp.get(this, Constant.COMMENT_COUNT, param, new SetResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson = new Gson();
               CommentCount commentResponse =  gson.fromJson(response.result.toString(),CommentCount.class);
               mTie.setText(commentResponse.data+" 跟帖");
            }
            @Override
            public void failure(Response response) {
             Toast.makeText(NewsDatileActivity.this,"跟帖数量请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 可以安返回键----没有此方法，默认finish();
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
         mWebView.goBack();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_news_detail_collect://最右边的4个点
                 //执行加入收藏操
                popWindow.showAsDropDown(mCollect,Gravity.RIGHT,0,0);//该POP的位置
                break;
            case R.id.ll_news_detail_pop_collect://popwindow
                //将信息传给收藏-----因为收藏没有接口，所以在本地显示
                collecd = new ArrayList<>();
                collecd.add(NewsListAdapter.news);
                Log.e("COLLECT",NewsListAdapter.news.title);
                Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_news_show_detaile_tie://跟帖
                Intent intent = new Intent(this,CommentActivity.class);
                intent.putExtra("nid",nid);
                startActivity(intent);
                break;
        }

    }


//    @Override
//    public void getCurrentNews(News news) {
//        //将信息传给收藏
//    }
}

