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

public class GantiPasswordActivity extends AppCompatActivity {
    EditText et_ganti_password,et_ganti_password2;
    private RequestQueue requestQueue;
    LinkDatabase linkDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);

        et_ganti_password = findViewById(R.id.ET_gp_1);
        et_ganti_password2 = findViewById(R.id.ET_gp_2);
        linkDatabase = new LinkDatabase();


    }

    public void onOk(View view) {
        if(et_ganti_password.getText().toString().equals(et_ganti_password2.getText().toString())){
            request();
        }else {
            Toast.makeText(getApplicationContext(), "Password yang di inputkan tidak sama", Toast.LENGTH_LONG).show();
        }




        KonfirmasiActivity konfirmasiActivity = new KonfirmasiActivity();
        konfirmasiActivity.close();
        LupaPasswordActivity lupaPasswordActivity = new LupaPasswordActivity();
        lupaPasswordActivity.close();
        Intent intent = new Intent(GantiPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        ((GantiPasswordActivity) this).finish();
    }

    void request(){
        String upload_url = linkDatabase.linkurl()+"lupa_password.php?operasi=ganti_password";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toLowerCase().toString().equals("sukses")){
                    Toast.makeText(getApplicationContext(), "password berhasil di ganti", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(GantiPasswordActivity.this, LoginActivity.class);
//                    startActivity(intent);
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

                params.put("password", et_ganti_password.getText().toString());

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(GantiPasswordActivity.this);
        requestQueue.add(stringRequest);
    }

}
