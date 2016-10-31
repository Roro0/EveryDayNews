package edu.feucui.everydaynews.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 *  裁剪圆形 图片
 * Created by lenovo on 2016/10/6.
 */
public class CutPhotoUtil {

    /**
     * 裁剪圆形 图片
     * @return 返回裁剪过后的图片
     */
    public static Bitmap getCicleBitmap(Bitmap resBit){
        int height=resBit.getHeight();
        int width=resBit.getWidth();

        //拿到宽和高的最小值
        int min=height>width?width:height;
        Bitmap bitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);

        Paint paint=new Paint();
        paint.setAntiAlias(true); //抗锯齿

        Canvas canvas=new Canvas(bitmap);
        canvas.drawBitmap(bitmap,0,0,paint);

        canvas.drawCircle(min/2,min/2,min/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(resBit,0,0,paint);

        return  bitmap;
    }
}
