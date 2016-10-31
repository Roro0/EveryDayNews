package edu.feucui.everydaynews.net;

/**
 * Created by Administrator on 2016/9/23.
 */
public class Response {
    public int code;
    public Object result;

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", result=" + result +
                '}';
    }
}
