package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.solusindo.kreativa.companyprofilekreativaadmin.background.Bg_profil;
import com.solusindo.kreativa.companyprofilekreativaadmin.background.Bg_visimisi;
import com.solusindo.kreativa.companyprofilekreativaadmin.interfaces.AsyncResponse;

public class EditVisiActivity extends AppCompatActivity implements AsyncResponse {
    TextInputEditText et_visi, et_misi;
    String str_visi, str_misi;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_visi);
        et_visi = findViewById(R.id.TIE_vm_visi);
        et_misi = findViewById(R.id.TIET_vm_misi);
        str_visi = new String();str_misi = new String();

        str_visi = getIntent().getStringExtra("visi");
        str_misi = getIntent().getStringExtra("misi");

        et_visi.setText(str_visi);
        et_misi.setText(str_misi);
    }

    public void onSimpan(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String type = "update_visi";
        Bg_visimisi bg = new Bg_visimisi(this);
        bg.delegate = this;
        bg.execute(type, et_visi.getText().toString(), et_misi.getText().toString());
    }

    @Override
    public void processfinish(String output) {
        if(output.equals("update visi berhasil")){
            Toast.makeText(getApplicationContext(), "Data Berhasil di Update", Toast.LENGTH_LONG).show();
            VisiMisiActivity visiMisiActivity = new VisiMisiActivity();
            visiMisiActivity.vm.getvisimisi();
            progressDialog.dismiss();
            finish();
        } else {
            Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
        }
    }

    public void onBatal(View view) {
        finish();
    }
}
