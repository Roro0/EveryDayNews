package edu.feucui.everydaynews.test;

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

/**
 * Created by Administrator on 2016/10/11.
 */
public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.MyHolder> {
     Context mContext;
    ArrayList<String> mList;
    LayoutInflater layoutInflater;
    View mView;
    public ChooseAdapter(Context mContext,ArrayList<String> mList) {
        this.mContext=mContext;
        this.mList=mList;
       layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.e("mList",mList.get(0));
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化自条目view,将其与当前RecyleView绑定
          mView  = layoutInflater.inflate(R.layout.item_recycle_view,parent,false);
        return new MyHolder(mView);
    }

    /**
     * 渲染
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.name.setText(mList.get(position));
        //选择状态
        holder.img.setSelected(ChooseActivity.mChooseArray[position]);
        holder.itemView.setOnClickListener(new MyClickClass(holder,position));//5种点击方法之一
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }



    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name;
        View itemView;

        public MyHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
              img = (ImageView) itemView.findViewById(R.id.iv_recycle_check);
              name= (TextView) itemView.findViewById(R.id.tv_recycle_text);
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
