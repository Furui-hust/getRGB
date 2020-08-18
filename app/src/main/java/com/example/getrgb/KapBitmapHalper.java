package com.example.getrgb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//传递函数的工具类
public class KapBitmapHalper {
    // 获取imageview的图片，并且转化为可传递的string
    public static String GetStringByImageView(ImageView imageView) {
        // 从ImageView得到Bitmap对象
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        // 把Bitmap转码成字符串
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        String imageBase64 = new String(Base64.encode(baos.toByteArray(), 0));
        return imageBase64;
    }

    // 将字符串转化为图片
    public static Bitmap GetBitmapByImageString(String imageBase64) {
        byte[] byte64 = Base64.decode(imageBase64, 0);
        ByteArrayInputStream bais = new ByteArrayInputStream(byte64);
        Bitmap bitmap = BitmapFactory.decodeStream(bais);
        return bitmap;
    }

    /**
     * 提供一个简单的传递(全局公用 仅仅可以暂存一张图片)
     * saveImageView
     * getImageBitMap
     */
    private static String imageString = null;

    public static void SaveImageView(ImageView imageView) {
        imageString = GetStringByImageView(imageView);
    }

    public static Bitmap GetImageBitMap() {
        return GetBitmapByImageString(imageString);
    }
}
