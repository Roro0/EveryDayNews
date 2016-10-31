package edu.feucui.everydaynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.entity.SlideLeftEntity;

/**
 * 左边抽屉的适配器：自定yi
 * Created by Administrator on 2016/9/29.
 */
public class SlideLeftAdapter extends BaseAdapter{

ArrayList<SlideLeftEntity> mlist;
    Context mContext;
    LayoutInflater mInflate;

    public SlideLeftAdapter(ArrayList<SlideLeftEntity> mlist,Context mContext){
        this.mContext=mContext;
        this.mlist=mlist;
        mInflate= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mlist==null?0:mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      MyHolder holder;
        if (convertView==null){
           convertView =  mInflate.inflate(R.layout.item_home_drawable,null);
            holder= new MyHolder();
            holder.mIcon = (ImageView) convertView.findViewById(R.id.iv_slide_icon);
            holder.mName= (TextView) convertView.findViewById(R.id.tv_slide_name);
            holder.mEname = (TextView) convertView.findViewById(R.id.tv_slide_eName);
            convertView.setTag(holder);
        }else {
             holder= (MyHolder) convertView.getTag();
        }
        //渲染
        SlideLeftEntity info = mlist.get(position);
        holder.mIcon.setImageResource(info.icon);
        holder.mName.setText(info.name);
        holder.mEname.setText(info.eName);
        return convertView;
    }
   public class MyHolder{
        ImageView mIcon;
        TextView mName;
        TextView mEname;
    }
}
