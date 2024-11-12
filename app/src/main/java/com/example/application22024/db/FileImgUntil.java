package com.example.application22024.db;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 图片管理 保存和加载
 */
public class FileImgUntil {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 异步，防止闪退
     * @param bitmap
     * @param path
     * @return
     */
    public static Future<Void> saveBitmapAsync(final Bitmap bitmap, final String path){
        return executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                saveImageBitmapToFileImg(bitmap,path);
                return null;
            }
        });
    }


    public static void saveImageBitmapToFileImg(Bitmap bitmap, String path){
        //实现保存图片
        File file = new File(path);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,fos);
            fos.flush();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * 将bitmap类型保存为文件，并返回路径
     * @param url
     * @param context
     * @param path
     */
    public static void saveImageBitmapToFileImg(Uri url, Context context, String path){
        CustomTarget<Bitmap> target = new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                //实现保存图片
                File file = new File(path);
                try{
                    FileOutputStream fos = new FileOutputStream(file);
                    resource.compress(Bitmap.CompressFormat.PNG, 100,fos);
                    fos.flush();
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        };
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(target);

    }

    /**
     * 获取路径
     * @return
     */
    public static String getImgName(){
        String pigName = "/" + UUID.randomUUID().toString().replace("-","") + ".png";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + pigName;
    }

}
