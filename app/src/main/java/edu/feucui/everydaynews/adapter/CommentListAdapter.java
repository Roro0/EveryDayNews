package edu.feucui.everydaynews.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.feucui.everydaynews.R;
import edu.feucui.everydaynews.entity.Comments;

/**
 * 显示评论的适配器
 * Created by Administrator on 2016/10/13.
 */
public class CommentListAdapter extends BaseAdapter<Comments> {

    public CommentListAdapter(Context context, ArrayList<Comments> mList, int layoutId) {
        super(context, mList, layoutId);
    }

    @Override
    public void putView(MyHolder holder, View convertView, int position, Comments comments) {
        ImageView mPhoto = (ImageView) convertView.findViewById(R.id.iv_item_comment_photo);
        Glide.with(mContext).load(comments.getPortrait()).into(mPhoto);
        TextView mAccount = (TextView) convertView.findViewById(R.id.tv_item_comment_account);
        mAccount.setText(comments.getUid());
        TextView mStamp = (TextView) convertView.findViewById(R.id.tv_item_comment_time);
        mStamp.setText(comments.getStamp());
        TextView mContent = (TextView) convertView.findViewById(R.id.tv_item_comment_comment);
        mContent.setText(comments.getContent());
    }
}
