package edu.feucui.everydaynews.entity;

import java.util.ArrayList;

/**
 * 用户评论的响应
 * Created by Administrator on 2016/10/24.
 */
public class SubmitCommentResponse {

    String message;
    int status;
    public ArrayList<Comments> data;
}
