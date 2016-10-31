package edu.feucui.everydaynews.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.activity.fragment.NewsCollectionFragment;
import edu.feucui.everydaynews.activity.fragment.NewsFragment;
import edu.feucui.everydaynews.adapter.SlideLeftAdapter;
import edu.feucui.everydaynews.entity.SlideLeftEntity;

/**
 * 主界面
 * Created by Administrator on 2016/9/29.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView mSlideLeftListView;//左边抽屉
    LinearLayout mSlideRightLlView;//右边抽屉
    DrawerLayout mDrawer;//主界面DrawerLayout
    ImageView mIvPhoto;//右抽屉的头像
    TextView mAccount;//右边抽屉的帐号
    NewsFragment newsFragment = new NewsFragment();
    NewsCollectionFragment collectFragment = new NewsCollectionFragment();
    SlideLeftAdapter adapter;
    String account,token,photoPath;//从个人中心传来的帐号，用户令牌和头像链接
    Receiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);
        //第三方分享：初始化sdk
        ShareSDK.initSDK(this,"181f1484a7c84");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("进进进", "onStart: " );
        receiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter("send");
        registerReceiver(receiver,intentFilter);
}

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    protected void init() {;

        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);//抽屉
        mSlideLeftListView = (ListView) findViewById(R.id.lv_slide_left);//左边抽屉
        mSlideRightLlView = (LinearLayout) findViewById(R.id.ll_slide_right);//右边抽屉
      ImageView mQQ = (ImageView) findViewById(R.id.iv_home_drawer_right_share_qq);
        getData();
        setOnClickListeners(this,mBaseLeft,mBaseRight,mIvPhoto,mQQ);
    }
    /**
     * 加载抽屉内的信息
     * //数据源------左边抽屉+右边抽屉
     */
    public void getData() {
//左边抽屉
        ArrayList<SlideLeftEntity> mListData = new ArrayList<>();
        SlideLeftEntity info1 = new SlideLeftEntity("新闻", "NEWS", R.mipmap.biz_navigation_tab_news);
        SlideLeftEntity info2 = new SlideLeftEntity("收藏", "FAVORITE", R.mipmap.biz_navigation_tab_read);
        SlideLeftEntity info3 = new SlideLeftEntity("本地", "LOCAL", R.mipmap.biz_navigation_tab_local_news);
        SlideLeftEntity info4 = new SlideLeftEntity("跟帖", "COMMENT", R.mipmap.biz_navigation_tab_ties);
        SlideLeftEntity info5 = new SlideLeftEntity("图片", "PHOTO", R.mipmap.biz_navigation_tab_pics);
        mListData.add(info1);
        mListData.add(info2);
        mListData.add(info3);
        mListData.add(info4);
        mListData.add(info5);
        //拿到左边抽屉的适配器
        adapter = new SlideLeftAdapter(mListData, this);
        //设置适配器
        mSlideLeftListView.setAdapter(adapter);
        mSlideLeftListView.setOnItemClickListener(this);
        //右边抽屉
        mIvPhoto = (ImageView) findViewById(R.id.iv_home_drawer_photo);
        mAccount = (TextView) findViewById(R.id.tv_home_right_drawer_login_account);
    }
    /**
     * 加载新闻数据
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        FragmentTransaction trs = fragmentTransation();
        trs.add(R.id.ll_content_home,newsFragment);
        trs.add(R.id.ll_content_home,collectFragment);
        trs.commit();
    }
    /**
     * 点击事件
     *  @param v
     */
    boolean flag=true;
    @Override
    public void onClick(View v) {
    switch (v.getId()){
    case R.id.iv_base_left://左边抽屉的缩放
            if (flag==true){
            mDrawer.openDrawer(mSlideLeftListView);
                flag=false;
            } else {
                mDrawer.closeDrawer(mSlideLeftListView);
                flag=true;
            }
            break;
        case R.id.iv_base_right://右边抽屉的缩放
            if (flag==true){
                mDrawer.openDrawer(mSlideRightLlView);
                flag=false;
            }else{
                mDrawer.closeDrawer(mSlideRightLlView);
                flag=true;
            }
            break;
        case R.id.iv_home_drawer_photo://头像
            Intent intent = new Intent();
         if (mAccount.getText()==account ){
             //说明已经是登录状态--->进入个人中心
             intent.setClass(this,PersonalWebActivity.class);
         }else {
             //说明没有登录-->进入登录界面
             intent.setClass(this,LoginActivity.class);
         }
            startActivity(intent);
            mDrawer.closeDrawer(mSlideRightLlView); //转到Login界面后，右边抽屉消失
        break;
        case R.id.iv_home_drawer_right_share_qq:
            showShare();
            break;
       }
    }
    /**
     *碎片事物的封装方法
     * @return
     */
    public  FragmentTransaction fragmentTransation(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        return ts;
    }
    /**
     * 左边抽屉的子条目点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //碎片的替换
        FragmentTransaction trs = fragmentTransation();
        switch (position){
            case 0://新闻列表
                 mBaseTitle.setText("新闻资讯");
                 trs.hide(collectFragment);
                 trs.show(newsFragment);
                 break;
            case 1://新闻收藏
                mBaseTitle.setText("新闻收藏");
                trs.hide(newsFragment);
                trs.show(collectFragment);
                break;
    }
        trs.commit();
        mDrawer.closeDrawer(mSlideLeftListView);
    }

    /**
     *分享内容
     */

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    /**
     * 广播接收器：接收个人中心返回的信息
     */
    public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        account =  intent.getStringExtra("account");
        token = intent.getStringExtra("token");
        photoPath = intent.getStringExtra("photoPath");
        Log.e("Receiver", "onReceive:进----------------- account=="+account+"");
        mAccount.setText(account);
        Glide.with(HomeActivity.this).load(photoPath).into(mIvPhoto);
    }
}
}
