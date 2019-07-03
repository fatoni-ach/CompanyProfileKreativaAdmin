package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GantiPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);
    }

    public void onOk(View view) {
        KonfirmasiActivity konfirmasiActivity = new KonfirmasiActivity();
        konfirmasiActivity.close();
        LupaPasswordActivity lupaPasswordActivity = new LupaPasswordActivity();
        lupaPasswordActivity.close();
        Intent intent = new Intent(GantiPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        ((GantiPasswordActivity) this).finish();
    }
}
