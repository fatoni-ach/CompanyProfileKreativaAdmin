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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.github.tntkhang.gmailsenderlibrary.GMailSender;
import com.github.tntkhang.gmailsenderlibrary.GmailListener;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LupaPasswordActivity extends AppCompatActivity {
    LinkDatabase linkDatabase;
    EditText et_email;
    private RequestQueue requestQueue;
    int kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);
        linkDatabase = new LinkDatabase();
        et_email = findViewById(R.id.ET_lp_email);
    }

    public void onLupaPass(View view) {
        int min = 100, max=999;
        kode = new Random().nextInt(max-min+1)+min;
        request();
    }

    public void close(){
        ((LupaPasswordActivity) this).finish();
    }

    void request(){
        String upload_url = linkDatabase.linkurl()+"lupa_password.php?operasi=lupa_password_admin";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "sukses"+response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("email benar")){
//                    Intent intent = new Intent(LupaPasswordActivity.this, KonfirmasiActivity.class);
//                    intent.putExtra("EMAIL", et_email.getText().toString());
//                    startActivity(intent);
                    sendCode();
//                    sendEmail();
                } else {
                    Toast.makeText(getApplicationContext(), "Email yang anda masukkan tidak valid !!", Toast.LENGTH_LONG).show();
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

                params.put("EMAIL", et_email.getText().toString().toLowerCase());

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(LupaPasswordActivity.this);
        requestQueue.add(stringRequest);
    }

    void sendCode(){
        String upload_url = linkDatabase.linkurl()+"lupa_password.php?operasi=send_code";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toLowerCase().toString().equals("code di kirim")){
                    sendEmail();
//                    Toast.makeText(getApplicationContext(), "database berhasil di update", Toast.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(), "Kode verifikasi sudah di kirim ke email anda", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(LupaPasswordActivity.this, KonfirmasiActivity.class);
//                    intent.putExtra("EMAIL", et_email.getText().toString());
//                    startActivity(intent);
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

                params.put("code", String.valueOf(kode));

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(LupaPasswordActivity.this);
        requestQueue.add(stringRequest);
    }

    void sendEmail(){
        GMailSender.withAccount("no.replyCreativa@gmail.com", "kreativakreativa")
                .withTitle("Kode Verifikasi Company Profile")
                .withBody("Masukkan kode "+ String.valueOf(kode)+" ke dalam aplikasi company profile")
                .withSender("no.replyCreativa@gmail.com")
                .toEmailAddress(et_email.getText().toString()) // one or multiple addresses separated by a comma
                .withListenner(new GmailListener() {
                    @Override
                    public void sendSuccess() {
                        Toast.makeText(getApplicationContext(), "Kode verifikasi sudah di kirim ke email anda", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LupaPasswordActivity.this, KonfirmasiActivity.class);
                        intent.putExtra("EMAIL", et_email.getText().toString());
                        startActivity(intent);
                    }

                    @Override
                    public void sendFail(String err) {
                        Toast.makeText(getApplicationContext(), "Fail: " + err, Toast.LENGTH_LONG).show();
                    }
                })
                .send();
    }
}
