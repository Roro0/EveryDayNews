package edu.feucui.everydaynews.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import edu.feucui.everydaynews.R;
import edu.zx.slidingmenu.SlidingMenu;

/**
 * Created by Administrator on 2016/10/14.
 */
public class SlidingMenuTest extends Activity {
    SlidingMenu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.sliding_menu_content_test);

//        View mView = getLayoutInflater().inflate(R.layout.sliding_menu_left_test,null);
        menu = new SlidingMenu(this);
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.sliding_menu_left_test);
        menu.setSecondaryMenu(R.layout.sliding_menu_right_test);
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setBehindOffset(100);//cuo
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()||menu.isSecondaryMenuShowing()){
            //点击返回键 收起菜单
            menu.showContent();
        }
        super.onBackPressed();

    }
}
