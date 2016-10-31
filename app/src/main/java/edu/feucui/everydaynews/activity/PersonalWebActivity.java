package edu.feucui.everydaynews.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.adapter.LoginLogAdapter;
import edu.feucui.everydaynews.entity.LoginLog;
import edu.feucui.everydaynews.entity.PersonalPesponse;
import edu.feucui.everydaynews.entity.UpLoadPhotoResponse;
import edu.feucui.everydaynews.net.Constant;
import edu.feucui.everydaynews.net.MyHttp;
import edu.feucui.everydaynews.net.Response;
import edu.feucui.everydaynews.net.SetResultFinishListener;

/**
 * 个人中心
 * Created by Administrator on 2016/10/11.
 */
public class PersonalWebActivity extends BaseActivity implements View.OnClickListener {
    ImageView mPhoto;
    View popView;
    PopupWindow pop;
    LinearLayout mPhotoTake;
    LinearLayout mPhotoSelect;
    RecyclerView mLoginLog;//登录日志列表
    Button mExit;//退出按钮
    LoginLogAdapter adapter;
    TextView mAccount,mIntegratione,mComnum;
    String token,account;
    String photoPath;//图片路径
    /**
     * 权限的请求码
     */
    static final int PERMISSION_CODE=200;
    /**
     * 照相的请码
     */
    static final int GOTO_CAMERA=201;
    /**
     * 图库的请求码
     */
    static final int GOTO_PICK=202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_personalweb);
        mBaseTitle.setText("个人中心");
    }

    @Override
    void init() {
        mPhoto = (ImageView) findViewById(R.id.iv_personal_photo);//头像
          mAccount = (TextView) findViewById(R.id.tv_activity_personal_web_account);//帐号
          mIntegratione = (TextView) findViewById(R.id.tv_activity_personal_web_integration);//积分
           mComnum = (TextView) findViewById(R.id.tv_activity_personal_web_comnum);//跟帖数量
          mLoginLog = (RecyclerView) findViewById(R.id.recyView_activity_personal_web);//登录日志
           mExit = (Button) findViewById(R.id.btn_activity_personal_web_exit);
          popView = LayoutInflater.from(this).inflate(R.layout.view_popwindow,null);
          mPhotoTake = (LinearLayout) popView.findViewById(R.id.ll_photo_take);
          mPhotoSelect = (LinearLayout) popView.findViewById(R.id.ll_photo_select);
            adapter = new LoginLogAdapter(this,null);
        //设置RecycleView的风格 --------重要
            mLoginLog.setLayoutManager(new LinearLayoutManager(this));
          mLoginLog.setAdapter(adapter);
        //设置POPWINDOW
        pop = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //外部可点击:setOutsideTouchable+setBackgroundDrawable要配合使用
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());

        setOnClickListeners(this,mBaseLeft,mPhoto,mPhotoTake,mPhotoSelect,mExit);
        getHttpData();

    }
    /**
     * 用户中心：请求个人信息
     */
    public void getHttpData(){

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        //user_home?ver=版本号&imei=手机标识符&token =用户令牌
        Map<String,String> param = new HashMap<>();
        param.put("ver","0000000");
        param.put("imei","000000000000000");
        param.put("token",token);

        MyHttp.get(PersonalWebActivity.this, Constant.PERSONAL_WEB, param, new SetResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson = new Gson();
                PersonalPesponse personalPesponse = gson.fromJson(response.result.toString(), PersonalPesponse.class);
                 Log.e("response",response.result.toString());
                 account = personalPesponse.data.uid;//帐号
                 photoPath = personalPesponse.data.portrait;//头像
                int integration =personalPesponse.data.integration;//积分
                int comnum = personalPesponse.data.comnum;//跟帖数量
                Log.e("loginLog",personalPesponse.data.loginlog.get(0).toString());
                ArrayList<LoginLog> loginLog = personalPesponse.data.loginlog;//登录日志
                /**
                 * 注意：  再次进入  如果已经是登陆状态，个人信息怎么设置最新的数据
                 */
                mAccount.setText(account);
                Glide.with(PersonalWebActivity.this).load(photoPath).into(mPhoto);
                mIntegratione.setText(integration+"");
                mComnum.setText(comnum+"");
                if (loginLog!=null &&loginLog.size()>0){
                adapter.mLoginLogList.addAll(loginLog);//给适配器添加数据源
                adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(Response response) {

            }
        });
    }

    /**
     * 上传头像     服务器端接口出错了
     */
    public void upHttpPhoto(){
        final File photoFile = new File(photoPath);
     //user_image?token=用户令牌& portrait =头像
        Map<String,String> param = new HashMap<>();
        param.put("token",token);
        param.put("portrait",""+photoFile);
        MyHttp.get(this, Constant.UPLOAD_PHOTO, param, new SetResultFinishListener() {
            @Override
            public void success(Response response) {
              Gson gson = new Gson();
                UpLoadPhotoResponse upLoadPhotoResponse = gson.fromJson(response.result.toString(), UpLoadPhotoResponse.class);
                int result = upLoadPhotoResponse.data.result;
                switch (result){
                    case 0://上传成功
                        //在此，将头像信息传给主界面
                        Glide.with(PersonalWebActivity.this).load(photoFile).into(mPhoto);
                        break;
                    case 1://上传失败
                        Toast.makeText(PersonalWebActivity.this,"头像上传失败",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void failure(Response response) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_base_left://能进入用户中心，说明登陆成功了
                Intent intent=new Intent();
                intent.putExtra("token",token);
                intent.putExtra("photoPath",photoPath);
                intent.putExtra("account",account);
                intent.setAction("send");
                sendBroadcast(intent);
                finish();
                break;
            case R.id.iv_personal_photo://个人头像
                //打开popwindow
                /**
                 * 基于整个窗口显示
                 * 2.相对于？现实的位置
                 */
                pop.showAtLocation(mPhoto, Gravity.BOTTOM,0,0);
                break;
            case R.id.ll_photo_take://拍照
                //需要申请权限
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//API版本需要大于23
                if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){//检查相机权限，---checkSelfPermission方法要求API版本大于23
                  //有权限，直接跳转
                  goToCamera();
                }else {//没有权限  --申请权限
                 requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_CODE);
                }
                }else{
                   goToCamera();
                }
                break;
            case R.id.ll_photo_select://选择照片
                Intent intent1 = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1,GOTO_PICK);
                break;
            case R.id.btn_activity_personal_web_exit://退出按钮
                //清除用户信息----跳转到主界面
                 Intent intent2 = new Intent(this,HomeActivity.class);
                startActivity(intent2);
                break;
        }
    }

    /**
     * 对请求的结果进行处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GOTO_CAMERA://进入系统相机
                //设置头像
                if (resultCode==RESULT_OK) {
                    photoPath = Constant.PHOTO_FILE_PATH;
                    //上传头像
//                    upHttpPhoto();
                    //上传头像到服务器 接口出错，只能在本地设置
                    Glide.with(this).load(photoPath).into(mPhoto);
                }
                break;
            case GOTO_PICK://进入图库
                if (resultCode==RESULT_OK){
                    //方法中的Intent即为 选择图片的结果
                    Uri uri = data.getData();
                    //需要进行查询
                    String[] filePathColumn={MediaStore.Images.Media.DATA};
                    Cursor curs=getContentResolver().query(uri,filePathColumn,null,null,null);
                    curs.moveToFirst();
                    int columIndex=curs.getColumnIndex(filePathColumn[0]);
                    photoPath=curs.getString(columIndex);
                    //上传头像
//                    upHttpPhoto();
                    //上传头像到服务器 接口出错，只能在本地设置
                    Glide.with(this).load(photoPath).into(mPhoto);
                }
                break;
        }

    }

    /**
     * 对请求权限的结果进行处理
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults  请求权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED &&
                        grantResults[1]==PackageManager.PERMISSION_GRANTED){//同意使用相机并且保存图片
                   goToCamera();
                }else{
                    Toast.makeText(this,"权限管理-->应用-->相应权限",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    /**
     * 跳转到系统相机
     */
    public void goToCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //传递此次拍摄照片的路径    -----流
        //创建存储照片的路径
        File file = new File(Constant.DIR_PATH);
        if (!file.exists()){
            file.mkdirs();
        }
        Log.e("filePath==",file.getAbsolutePath());
        //给相机传递路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constant.PHOTO_FILE_PATH)));
        startActivityForResult(intent,GOTO_CAMERA);
    }

}
