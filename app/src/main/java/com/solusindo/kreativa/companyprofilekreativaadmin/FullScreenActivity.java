package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

//import com.github.piasy.biv.BigImageViewer;
//import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
//import com.github.piasy.biv.loader.glide.GlideImageLoader;
//import com.github.piasy.biv.view.BigImageView;

public class FullScreenActivity extends AppCompatActivity {

    LinkDatabase linkDatabase; ImageView photoView;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        photoView = (ImageView) findViewById(R.id.photo_view);
        linkDatabase = new LinkDatabase();
        link = new String();
        link = getIntent().getStringExtra("link");
        Glide.with(this).load(linkDatabase.linkurl()+link).placeholder(R.drawable.thumbnail).into(photoView);
    }

}
