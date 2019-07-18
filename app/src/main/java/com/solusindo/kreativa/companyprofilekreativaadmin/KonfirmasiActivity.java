package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class KonfirmasiActivity extends AppCompatActivity {
    String email;
    EditText code;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);

        code = findViewById(R.id.ET_confirm_kode);
        linkDatabase = new LinkDatabase();

        email = new String();
        email = getIntent().getStringExtra("EMAIL");
        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
    }

    public void onOk(View view) {
        request();
    }

    void request(){
        String upload_url = linkDatabase.linkurl()+"lupa_password.php?operasi=cek_code";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "sukses"+response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("code benar")){
//                    Toast.makeText(getApplicationContext(), "Code sudah benar", Toast.LENGTH_LONG).show();
//                    sendEmail();
                    Intent intent = new Intent(KonfirmasiActivity.this, GantiPasswordActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Code yang anda masukkan tidak valid !!", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.d("error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("code", code.getText().toString().toLowerCase());

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(KonfirmasiActivity.this);
        requestQueue.add(stringRequest);
    }

    public void close(){
        ((KonfirmasiActivity) this).finish();
    }
}
