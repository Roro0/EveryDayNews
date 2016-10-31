package edu.feucui.everydaynews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.adapter.CommentListAdapter;
import edu.feucui.everydaynews.adapter.NewsListAdapter;
import edu.feucui.everydaynews.entity.CommentArray;
import edu.feucui.everydaynews.entity.Comments;
import edu.feucui.everydaynews.entity.News;
import edu.feucui.everydaynews.net.Constant;
import edu.feucui.everydaynews.net.MyHttp;
import edu.feucui.everydaynews.net.Response;
import edu.feucui.everydaynews.net.SetResultFinishListener;
import edu.feucui.everydaynews.xlistview.XListView;

/**
 * 发表评论
 * Created by Administrator on 2016/10/13.
 */
public class CommentActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {
    News currentNews;//当前新闻
    XListView mCommentList;
    EditText mCommentWrite;
    ImageView mSend;
    CommentListAdapter adapter;
    ArrayList<Comments> comments;
    int dir=1;
    int cid;//当前列表中最后一条评论的id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_comment);
    }

    @Override
    void init() {

          mCommentList = (XListView) findViewById(R.id.lv_activity_comment);
          mCommentWrite = (EditText) findViewById(R.id.et_activity_comment_write);
          mSend = (ImageView) findViewById(R.id.iv_activity_comment_send);
         mBaseTitle.setText("评论");
         mBaseLeft.setImageResource(R.mipmap.back);
         mBaseRight.setVisibility(View.GONE);
        //设置适配器
        adapter =  new CommentListAdapter(this,null,R.layout.item_comment_show);
        mCommentList.setAdapter(adapter);
        getHttpCommentShowData();//至此，已经拿到了当前评论信息
        setOnClickListeners(this,mBaseLeft,mSend);
    }
    /**
     * 显示评论列表的信息
     */
    String stamp;//       时间
    public void getHttpCommentShowData(){
        //拿到当前新闻
//        Intent intent =getIntent();
//        int nid =  intent.getIntExtra("nid",0);
        int nid = NewsListAdapter.news.nid;
//cmt_list ? ver=版本号& nid=新闻id & type=1 & stamp=yyyyMMdd & cid=评论id & dir=0 & cnt=20
        Map<String,String> param = new HashMap<>();
        param.put("ver","0000000");
        param.put("nid",""+nid);
        param.put("type","1");
        param.put("stamp",stamp);//yyyyMMdd
        param.put("cid",""+cid);//评论id
        param.put("dir",""+dir);//1刷新，2加载
        param.put("cnt","");//更新的最大条目
        MyHttp.get(this, Constant.COMMENT_SHOW, param, new SetResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson = new Gson();
                CommentArray commentArray = gson.fromJson(response.result.toString(), CommentArray.class);
                //拿到了评论信息 将评论信息显示在条目上
                 comments= commentArray.data;
             if (comments!=null&&comments.size()>0){
                 adapter.mList.addAll(comments);
                 adapter.notifyDataSetChanged();
             }
                mCommentList.stopRefresh();
                mCommentList.stopLoadMore();
            }
            @Override
            public void failure(Response response) {
                mCommentList.stopRefresh();
                mCommentList.stopLoadMore();
            }
        });
    }

    /**
     *  发表评论的 网络请求
     */
    public void getHttpSendComment(){
//cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
    Map<String ,String > param = new HashMap<>();
        param.put("ver","0000000");
        param.put("nid","");
        param.put("token","");
        param.put("imei","");
        param.put("ctx","");

    }

    /**
     * 1刷新
     */
    @Override
    public void onRefresh() {
        dir=1;
        comments.clear();
        getHttpCommentShowData();
    }

    /**
     * 2加载
     */
    @Override
    public void onLoadMore() {
       dir=2;
        if (comments.size()>0){
            Comments comment = comments.get(comments.size()-1);//加载前，列表的最后一条评论
              cid = comment.getCid();
        }
        getHttpCommentShowData();
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.iv_activity_comment_send://发表评论
              //发送评论     ----判断是否是登录状态，不是的话没有权限发表评论
              break;
          case R.id.iv_base_left://返回主界面
              Intent intent = new Intent(this,HomeActivity.class);
              startActivity(intent);
              break;
      }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param binder
     */
    private void hideKeyboard(IBinder binder) {
        if (binder != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
