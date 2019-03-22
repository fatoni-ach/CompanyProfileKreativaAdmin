package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;

import com.solusindo.kreativa.companyprofilekreativaadmin.cache.FileCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {
    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;

    private Map<ImageView, String> imageViews =
            Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    ExecutorService executorService;
    public ImageLoader(Context context){
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    final int stub_id = R.drawable.ic_launcher_background;

    public void displayImage(String url, ImageView imageView){
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }else {
            queuePhoto(url, imageView);
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView){
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new Photosloader(p));
    }

    private Bitmap getBitmap(String url){
        File f = fileCache.getFile(url);
        Bitmap b = decodeFile(f);
        if(b!=null){
            return b;
        }
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);

            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.copyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;

        }catch (Throwable ex){
            ex.printStackTrace();
            return null;
        }
    }

    private Bitmap decodeFile(File f) {
        try{
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            final int REQUIERED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while (true){
                if (width_tmp/2 <REQUIERED_SIZE || height_tmp /2 <REQUIERED_SIZE ){
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    private class PhotoToLoad{
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url = u;
            imageView = i;
        }
    }

    private class Photosloader implements Runnable{

        PhotoToLoad photoToLoad;
        Photosloader(PhotoToLoad photoToLoad){
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            if(imageViewRefused(photoToLoad)){
                return;
            }
            Bitmap bmp = getBitmap(photoToLoad.url);
            memoryCache.put(photoToLoad.url, bmp);
            if(imageViewRefused(photoToLoad)){
                return;
            }

            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }
    boolean imageViewRefused(PhotoToLoad photoToLoad){
        String tag = imageViews.get(photoToLoad.imageView);
        if(tag == null || !tag.equals(photoToLoad.url)){
            return true;
        } else return false;
    }

    class BitmapDisplayer implements Runnable{

        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p){
            bitmap = b;
            photoToLoad = p;
        }

        @Override
        public void run() {
            if(imageViewRefused(photoToLoad)){
                return;
            }
            if (bitmap!= null){
                photoToLoad.imageView.setImageBitmap(bitmap);
            }else {
                photoToLoad.imageView.setImageResource(stub_id);
            }
        }
    }

    public void clearCache(){
        memoryCache.clear();
        fileCache.clear();
    }
}