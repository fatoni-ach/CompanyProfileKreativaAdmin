package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.solusindo.kreativa.companyprofilekreativaadmin.background.Bg_profil;
//import com.solusindo.kreativa.companyprofilekreativaadmin.background.Bg_visimisi;
//import com.solusindo.kreativa.companyprofilekreativaadmin.interfaces.AsyncResponse;

import java.util.HashMap;
import java.util.Map;

public class EditVisiActivity extends AppCompatActivity {
    TextInputEditText et_visi, et_misi;
    String str_visi, str_misi;
    ProgressDialog progressDialog;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_visi);
        et_visi = findViewById(R.id.TIE_vm_visi);
        et_misi = findViewById(R.id.TIET_vm_misi);
        str_visi = new String();str_misi = new String();
        linkDatabase = new LinkDatabase();

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
//        Bg_visimisi bg = new Bg_visimisi(this);
//        bg.delegate = this;
//        bg.execute(type, et_visi.getText().toString(), et_misi.getText().toString());

        String upload_url = linkDatabase.linkurl()+"visimisi.php?operasi=update_visi";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("update visi berhasil")){
                    Toast.makeText(getApplicationContext(), "Data Berhasil di Update", Toast.LENGTH_LONG).show();
                    VisiMisiActivity visiMisiActivity = new VisiMisiActivity();
                    visiMisiActivity.vm.getvisimisi();
                    progressDialog.dismiss();
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

                params.put("ISI_VISI", et_visi.getText().toString());
                params.put("ISI_MISI", et_misi.getText().toString());
                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

//    @Override
//    public void processfinish(String output) {
//        if(output.equals("update visi berhasil")){
//            Toast.makeText(getApplicationContext(), "Data Berhasil di Update", Toast.LENGTH_LONG).show();
//            VisiMisiActivity visiMisiActivity = new VisiMisiActivity();
//            visiMisiActivity.vm.getvisimisi();
//            progressDialog.dismiss();
//            finish();
//        } else {
//            Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
//        }
//    }

    public void onBatal(View view) {
        finish();
    }
}
