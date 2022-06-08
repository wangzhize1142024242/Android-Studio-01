package com.example.androidstudio01.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ImageUtil {
    public static String imageToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] buffer =byteArrayOutputStream.toByteArray();
        String baseStr = android.util.Base64.encodeToString(buffer, android.util.Base64.DEFAULT);
        return baseStr;
    }
    public static Bitmap base64ToImage(String bitmap64){
        byte[] bytes = android.util.Base64.decode(bitmap64, android.util.Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap;
    }
}