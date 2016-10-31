package edu.feucui.everydaynews.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;

import edu.feucui.everydaynews.R;

/**
 * SlidingPaneLayour练习
 * Created by Administrator on 2016/10/14.
 */
public class SlidingPaneLayoutTest extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_pane_layout_test);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
       SlidingPaneLayout slidingPaneLayout =  new SlidingPaneLayout(this);
    }
}
