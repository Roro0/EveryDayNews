package edu.feucui.everydaynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 适配器的基类
 * Created by Administrator on 2016/9/29.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
 public  ArrayList<T> mList;
    Context mContext;
    LayoutInflater mLayoutInflate;
    int layoutId;
    public BaseAdapter(Context context,ArrayList<T> mList,int layoutId) {
        this.mList=mList;
        mContext=context;
        mLayoutInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId=layoutId;

        if (mList==null){
            this.mList= new ArrayList<T>();
        }
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      MyHolder holder;
        if (convertView==null){
            holder=new MyHolder();
            convertView=mLayoutInflate.inflate(layoutId,null);//需要Layout,通过构造方法获得
            convertView.setTag(holder);
        }else{
           holder = (MyHolder) convertView.getTag();
        }
        //渲染----不知道具体控件，怎么办？---通过抽象方法，在这里调用，具体细节交给子类去处理
      putView(holder,convertView,position,mList.get(position));
        return convertView;
    }
    public abstract  void putView(MyHolder holder, View convertView,int position,T t);
    class MyHolder{

    }
}
