package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class CompanyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
    }

    public void onBack(View view) {
        finish();
    }

    public void onProfile(View view) {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }

    public void onVideo(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

    public void onLogo(View view) {
        Intent intent = new Intent(this, LogoActivity.class);
        startActivity(intent);
    }

    public void onVisiMisi(View view) {
        Intent intent = new Intent(this, VisiMisiActivity.class);
        startActivity(intent);
    }

    public void onOrganisasi(View view) {
        Intent intent = new Intent(this, OrganisasiActivity.class);
        startActivity(intent);
    }

}
