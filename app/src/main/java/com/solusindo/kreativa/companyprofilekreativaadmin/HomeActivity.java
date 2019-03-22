package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void onBeritaTerbaru(View view) {
        Intent intent = new Intent(this, BeritaActivity.class);
        startActivity(intent);
    }

    public void onCompanyProfile(View view) {
        Intent intent = new Intent(this, CompanyProfileActivity.class);
        startActivity(intent);
    }

    public void onFasilitas(View view) {
        Intent intent = new Intent(this, FasilitasActivity.class);
        startActivity(intent);
    }

    public void onGallery(View view) {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    public void onProduct(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    public void onPortofolio(View view) {
        Intent intent = new Intent(this, PortofolioActivity.class);
        startActivity(intent);
    }
}
