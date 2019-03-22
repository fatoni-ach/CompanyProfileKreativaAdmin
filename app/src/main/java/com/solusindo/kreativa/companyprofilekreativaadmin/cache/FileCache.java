package com.solusindo.kreativa.companyprofilekreativaadmin.cache;

import android.content.Context;
import android.os.Environment;

import com.android.volley.toolbox.StringRequest;

import java.io.File;

public class FileCache {
    private File cachedir;
    public FileCache(Context context){
        if(Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            cachedir = new File(Environment.getExternalStorageDirectory(), "fcImages");
        }else{
            cachedir = context.getCacheDir();
        }
        if(!cachedir.mkdirs()){
            cachedir.mkdirs();
        }
    }

    public File getFile(String url){
        String filename = String.valueOf(url.hashCode());

        File f = new File(cachedir, filename);
        return f;
    }

    public void clear(){
        File[] files = cachedir.listFiles();
        if(files == null){
            return;
        }
        for(File f : files){
            f.delete();
        }
    }
}
