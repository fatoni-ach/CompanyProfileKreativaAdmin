package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.solusindo.kreativa.companyprofilekreativaadmin.background.Bg_login;
import com.solusindo.kreativa.companyprofilekreativaadmin.interfaces.AsyncResponse;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText et_username, et_password;
    LinkDatabase linkDatabase;
    ProgressDialog progressDialog;
    ProgressBar progressBar;private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = findViewById(R.id.ET_login_username);
        et_password = findViewById(R.id.ET_login_password);
        progressBar = findViewById(R.id.PB_login);
        linkDatabase = new LinkDatabase();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.INTERNET}, 1);
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_WIFI_STATE}, 1);
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        progressBar.setVisibility(View.GONE);
    }

    public void onLogin(View v){
        final String user = et_username.getText().toString();
        String pass = et_password.getText().toString();
        if(user.equals("")||pass.equals("")){
            Toast.makeText(getBaseContext(), "Masukkan username dan password terlebih dahulu",Toast.LENGTH_LONG).show();
        }else{
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setMessage("Loading...");
//            progressDialog.show();
            progressBar.setVisibility(View.VISIBLE);

//        String type = "login";
//        Bg_login bg = new Bg_login(this);
//        bg.delegate = this;
//        bg.execute(type,user,pass);}
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);

        String upload_url = linkDatabase.linkurl()+"login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if(response.toLowerCase().toString().equals("login sukses")){
                    Intent intent = new Intent(LoginActivity.this ,HomeActivity.class);
                    startActivity(intent);
                }else Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
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

                params.put("username", et_username.getText().toString());
                params.put("password", et_password.getText().toString());

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }
    }

//    @Override
//    public void processfinish(String output) {
//        progressBar.setVisibility(View.GONE);
//        if(output.equals("login sukses")){
////            progressDialog.dismiss();
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
//        } else Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
//    }
}
