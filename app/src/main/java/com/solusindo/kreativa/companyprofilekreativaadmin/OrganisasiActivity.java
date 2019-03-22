package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrganisasiActivity extends AppCompatActivity {
    private final int IMG_REQUEST =1;
    public Bitmap bitmap;
    ImageView organisasi;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    private JsonArrayRequest arrayRequest;
    String url_organisasi; ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisasi);
        organisasi = findViewById(R.id.IV_organisasi);
        url_organisasi = new String();
        linkDatabase = new LinkDatabase();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        geturl();
    }
    private void geturl() {
        String url      =   linkDatabase.linkurl()+"url.php?operasi=view_organisasi";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    url_organisasi = linkDatabase.linkurl()+jsonObject.getString("URL_STRUKTUR_ORGANISASI");
//                    Toast.makeText(getBaseContext(), url_img.toString(), Toast.LENGTH_LONG).show();
//                    Picasso.with(getBaseContext()).load(url_organisasi).placeholder(R.drawable.thumbnail).into(organisasi);
                    Picasso.get().load(url_organisasi).placeholder(R.drawable.thumbnail).error(R.drawable.thumbnail).into(organisasi);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                progressDialog.dismiss();
            }
        }
        );
        requestQueue    =   Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);
    }

    public void onBack(View view) {finish();
    }

    public void onUpload(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    public void onSimpan(View view) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"url.php?operasi=update_organisasi";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss");
                String time = sdf.format(new Date());
                params.put("URL_STRUKTUR_ORGANISASI", ImagetoString(bitmap));
                params.put("path", "images/"+time+"_organisasi.jpg");

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(OrganisasiActivity.this);
        requestQueue.add(stringRequest);
//        Picasso.with(this).invalidate(url_organisasi);
//        Picasso.with(this).load(url_organisasi).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
    }

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                organisasi.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
