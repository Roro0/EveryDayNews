package edu.feucui.everydaynews.net;

/**
 *
 * Created by Administrator on 2016/9/23.
 */
public interface SetResultFinishListener {
    void success(Response response);
    void failure(Response response);
}
