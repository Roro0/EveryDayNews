package edu.feucui.everydaynews.test;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.entity.Registe;

/**
 * 下载
 * Created by Administrator on 2016/10/14.
 */
public class Download extends Activity implements View.OnClickListener {
    Receiver receiver;
    DownloadManager downloadManager;
    long downId;//下载id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_load_test);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Button btn = (Button) findViewById(R.id.btn_down_load);
        btn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        //获取下载管理器
          downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        String uri = "http://192.168.199.239:8080/a.avi";
        //需要一个下载请求
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
        //设置
        //下载方式
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI| DownloadManager.Request.NETWORK_MOBILE);
        //显示下载通知
        // request.setShowRunningNotification(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //设置下载到本应用的内部文件目录
        request.setDestinationInExternalFilesDir(this, null,"down_load.avi");
       Toast.makeText(this,"加入到下载队列",Toast.LENGTH_SHORT).show();
        downId =  downloadManager.enqueue(request);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        receiver = new Receiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver,filter);

    }

    public class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(Download.this,"下载完成",Toast.LENGTH_SHORT).show();
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            Log.e("zzzzzzzz",id+"     "+downId);
            if (downId==id){
               Uri uri = downloadManager.getUriForDownloadedFile(id);//拿到下载文件的路径
//                String uriPath = uri.getPath();
//                Log.e("uri1==",uriPath);  // /storage/emulated/0/Android/data/edu.feucui.everydaynews/files/down_load-2.avi
                //如果没有拿到文件，需要那文件，这里已经拿到了
//                String[] filePathColumn={MediaStore.Images.Media.DATA};
//                Cursor curs=getContentResolver().query(uri,filePathColumn,null,null,null);
//                curs.moveToFirst();
//                int columIndex=curs.getColumnIndex(filePathColumn[0]);
//                String path=curs.getString(columIndex);
                //打开APK
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setDataAndType(uri,downloadManager.getMimeTypeForDownloadedFile(id));
                startActivity(intent1);
            }

        }
    }
}
