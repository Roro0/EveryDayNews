package edu.feucui.everydaynews.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.activity.NewsDatileActivity;
import edu.feucui.everydaynews.adapter.NewsListAdapter;
import edu.feucui.everydaynews.entity.News;

/**
 * 新闻收藏
 * Created by Administrator on 2016/10/13.
 */
public class NewsCollectionFragment extends Fragment {
//ArrayList<News> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_activity_news,container,false);
        return mView;
}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View getView  = getView();
        ListView mLvCollect = (ListView) getView.findViewById(R.id.lv_news_show);
        FragmentActivity activity = getActivity();
//        mList = NewsDatileActivity.collecd;
//        NewsListAdapter adapter =new NewsListAdapter(activity,mList,R.layout.item_news_show);
//        mLvCollect.setAdapter(adapter);
    }
}
