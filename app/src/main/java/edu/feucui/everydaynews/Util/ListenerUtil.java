package edu.feucui.everydaynews.Util;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by Administrator on 2016/10/10.
 */
public class ListenerUtil  {


    /**
     *通过View进行绑定点击事件
     */
    public static void setOnClickListeners(View.OnClickListener listener, View... view){
        if (listener==null){
            return;
        }else {
            for (View v:view){
                v.setOnClickListener(listener);
            }
        }
    }


}
