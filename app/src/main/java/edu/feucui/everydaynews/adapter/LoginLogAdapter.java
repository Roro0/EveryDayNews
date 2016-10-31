package edu.feucui.everydaynews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.entity.LoginLog;
import edu.feucui.everydaynews.test.ChooseActivity;

/**
 * Created by Administrator on 2016/10/20.
 */
public class LoginLogAdapter extends RecyclerView.Adapter<LoginLogAdapter.MyHolder> {
    Context mContext;
    public ArrayList<LoginLog> mLoginLogList;
    LayoutInflater layoutInflater;
    View mView;
    public LoginLogAdapter(Context mContext,ArrayList<LoginLog> mLoginLogList) {
        this.mContext=mContext;
        this.mLoginLogList=mLoginLogList;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (mLoginLogList==null){
            this.mLoginLogList = new ArrayList<LoginLog>();
        }

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化自条目view,将其与当前RecyleView绑定
        mView  = layoutInflater.inflate(R.layout.item_personal_web_loginlog,parent,false);
        return new MyHolder(mView);
    }

    /**
     * 渲染
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.time.setText(mLoginLogList.get(position).time);
        holder.address.setText(mLoginLogList.get(position).address);
        holder.device.setText(mLoginLogList.get(position).device+"");
        holder.itemView.setOnClickListener(new MyClickClass(holder,position));//5种点击方法之一
    }

    @Override
    public int getItemCount() {
        return mLoginLogList==null?0:mLoginLogList.size();
    }



    public class MyHolder extends RecyclerView.ViewHolder{

        TextView time;
        TextView address;
        TextView device;
        View itemView;

        public MyHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            time = (TextView) itemView.findViewById(R.id.tv_loginlog_time);
            address= (TextView) itemView.findViewById(R.id.tv_loginlog_address);
            device= (TextView) itemView.findViewById(R.id.tv_loginlog_device);
        }
    }
    public class MyClickClass implements View.OnClickListener{

        MyHolder holder;
        int position;

        public MyClickClass(MyHolder holder,int position) {
            this.holder=holder;
            this.position=position;
        }
        @Override
        public void onClick(View v) {
            //响应
            onItemClickListener.onItemClick(holder, position);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    OnItemClickListener onItemClickListener;

    /**
     * 回调接口
     */
    public interface OnItemClickListener{
        void onItemClick(MyHolder holder,int position);
    }
}


