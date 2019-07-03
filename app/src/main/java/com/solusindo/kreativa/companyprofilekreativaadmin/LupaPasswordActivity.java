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
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LupaPasswordActivity extends AppCompatActivity {
    LinkDatabase linkDatabase;
    EditText et_email;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);
        linkDatabase = new LinkDatabase();
        et_email = findViewById(R.id.ET_lp_email);
    }

    public void onLupaPass(View view) {
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
                    Intent intent = new Intent(LupaPasswordActivity.this, KonfirmasiActivity.class);
                    intent.putExtra("EMAIL", et_email.getText().toString());
                    startActivity(intent);
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
}
