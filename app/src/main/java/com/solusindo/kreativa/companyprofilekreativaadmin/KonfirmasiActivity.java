package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class KonfirmasiActivity extends AppCompatActivity {
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);
        email = new String();
        email = getIntent().getStringExtra("EMAIL");
        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();

    }

    public void onOk(View view) {
        Intent intent = new Intent(KonfirmasiActivity.this, GantiPasswordActivity.class);
        startActivity(intent);
    }

    public void close(){
        ((KonfirmasiActivity) this).finish();
    }
}
