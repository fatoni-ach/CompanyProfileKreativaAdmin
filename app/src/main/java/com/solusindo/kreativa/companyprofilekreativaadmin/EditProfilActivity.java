package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.solusindo.kreativa.companyprofilekreativaadmin.background.Bg_login;
import com.solusindo.kreativa.companyprofilekreativaadmin.background.Bg_profil;
import com.solusindo.kreativa.companyprofilekreativaadmin.interfaces.AsyncResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditProfilActivity extends AppCompatActivity implements AsyncResponse {
    EditText nama_perusahaan, alamat, no_telp, email,instagram, desk,wa;
    ProgressDialog progressDialog; LinkDatabase linkDatabase;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        nama_perusahaan = (EditText)findViewById(R.id.ET_pp_namap);
        alamat = (EditText)findViewById(R.id.ET_pp_alamatp);
        no_telp = (EditText)findViewById(R.id.ET_pp_notelp);
        email = (EditText)findViewById(R.id.ET_pp_email);
        instagram = (EditText)findViewById(R.id.ET_pp_instagram);
        desk = (EditText)findViewById(R.id.ET_pp_deskripsi);
        wa = (EditText)findViewById(R.id.ET_pp_wa);
        linkDatabase = new LinkDatabase();

        String str_nama_p = getIntent().getStringExtra("NAMA_PERUSAHAAN"),
                str_desk = getIntent().getStringExtra("DESK_PERUSAHAAN"),
                str_email = getIntent().getStringExtra("EMAIL"),
                str_telp = getIntent().getStringExtra("TELP"),
                str_alamat = getIntent().getStringExtra("ALAMAT"),
                str_instagram = getIntent().getStringExtra("INSTAGRAM"),
                str_wa = getIntent().getStringExtra("WHATSAPP");

        nama_perusahaan.setText(str_nama_p);
        alamat.setText(str_alamat);
        no_telp.setText(str_telp);
        email.setText(str_email);
        instagram.setText(str_instagram);
        desk.setText(str_desk);
        wa.setText(str_wa);


    }

    public void onEdit(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
//        String str_nama_p = getIntent().getStringExtra("NAMA_PERUSAHAAN"),
//                str_desk = getIntent().getStringExtra("DESK_PERUSAHAAN"),
//                str_email = getIntent().getStringExtra("EMAIL"),
//                str_telp = getIntent().getStringExtra("TELP"),
//                str_alamat = getIntent().getStringExtra("ALAMAT"),
//                str_instagram = getIntent().getStringExtra("INSTAGRAM");
//        String type = "update";
//        Bg_profil bg = new Bg_profil(this);
//        bg.delegate = this;
//        bg.execute(type,nama_perusahaan.getText().toString(),alamat.getText().toString(),no_telp.getText().toString(),
//                email.getText().toString(), instagram.getText().toString(), desk.getText().toString(), wa.getText().toString());
//
//
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"profil.php?operasi=update_profil";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("update data berhasil")){
                    ProfilActivity.PA.refresh();
                    finish();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("NAMA_PERUSAHAAN", nama_perusahaan.getText().toString());
                params.put("DESK_PERUSAHAAN", desk.getText().toString());
                params.put("EMAIL", email.getText().toString());
                params.put("TELP", no_telp.getText().toString());
                params.put("ALAMAT", alamat.getText().toString());
                params.put("INSTAGRAM", instagram.getText().toString());
                params.put("WHATSAPP", wa.getText().toString());


                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void onBatal(View view) {
        finish();
    }

    @Override
    public void processfinish(String output) {
        if(output.equals("Update Data Berhasil")){
            Toast.makeText(getApplicationContext(), "Data Berhasil di Update", Toast.LENGTH_LONG).show();
            ProfilActivity profilActivity = new ProfilActivity();
            profilActivity.PA.refresh();
            finish();
        } else {
            Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
    }

    public void onBack(View view) { finish();
    }
}
