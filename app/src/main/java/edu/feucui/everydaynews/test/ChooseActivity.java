package edu.feucui.everydaynews.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import edu.feucui.everydaynews.R;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ChooseActivity extends Activity implements ChooseAdapter.OnItemClickListener, View.OnClickListener {

    RecyclerView recyclerView;
    public static boolean[] mChooseArray;
    ChooseAdapter adapter;
    Button mBtnConfirm;
    LinearLayout  mLinearLayout;
    ArrayList<String> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("choose","进");
        super.onCreate(savedInstanceState);
        mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        recyclerView =new RecyclerView(this);
        mBtnConfirm= new Button(this);
        mBtnConfirm.setText("确认");
        mBtnConfirm.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mBtnConfirm.setGravity(Gravity.CENTER);
        mLinearLayout.addView(recyclerView);
        mLinearLayout.addView(mBtnConfirm);

        setContentView(mLinearLayout);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.e("data","进");
        mList = new ArrayList<>();
        mList.add("张三1");
        mList.add("张三2");
        mList.add("张三3");
        mList.add("张三4");
        mList.add("张三5");
        mList.add("张三6");
        mList.add("张三7");
        mList.add("张三8");
        mChooseArray= new boolean[mList.size()];
        //布局管理器
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
         adapter = new ChooseAdapter(this,mList);
        recyclerView.setAdapter(adapter);
       //自条目的点击
        adapter.setOnItemClickListener(this);
        mBtnConfirm.setOnClickListener(this);

    }

    @Override
    public void onItemClick(ChooseAdapter.MyHolder holder, int position) {
        Toast.makeText(this,"   "+position,Toast.LENGTH_SHORT).show();
        mChooseArray[position]=!mChooseArray[position];
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        //结束选择---------需要将选中的数据传递给上一个界面
        ArrayList<String>  choosed = new ArrayList<>(); //存放选中的条目
        for (int i=0;i<mChooseArray.length;i++){
            if (mChooseArray[i]){
                choosed.add(mList.get(i));
            }

        }

        Intent intent = new Intent();
        intent.putExtra("data1",choosed);
        /**
         * 返回数据给之前的activity
         * param1：int ResultCode :结果码   区分此次跳转工作是否成功
         * param2:Intent data:数据
         */
        setResult(RESULT_OK,intent);//数据返回结束
        finish();
    }
}
