package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogoActivity extends AppCompatActivity {
    private final int IMG_REQUEST =1;
    public Bitmap bitmap;
    ImageView logo;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    private JsonArrayRequest arrayRequest;
//    String url_img = "http://192.168.137.1/server/images/logo.jpg";
    String url_img;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        logo = (ImageView)findViewById(R.id.IV_logo);
        linkDatabase = new LinkDatabase();
        url_img = new String();
//        imageLoader = new ImageLoader(this);
//
//        imageLoader.displayImage(url_img, logo);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        geturl();
//        url_img = linkDatabase.linkurl()+"images/logo.jpg";
//        Toast.makeText(getApplicationContext(), url_img, Toast.LENGTH_LONG).show();
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void onPilih(View view) {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, IMG_REQUEST);
        com.solusindo.kreativa.companyprofilekreativaadmin.
                ImagePicker.pickImage(this, "Select Your image : ");
    }

    private void geturl() {
        String url      =   linkDatabase.linkurl()+"url.php?operasi=view_logo";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    url_img = linkDatabase.linkurl()+jsonObject.getString("URL_LOGO");
//                    Glide.with(LogoActivity.this).load(linkDatabase.linkurl()+url_img).placeholder(R.drawable.thumbnail).into(logo);
                    Glide.with(getBaseContext()).load(url_img).placeholder(R.drawable.thumbnail).into(logo);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }
        );
        requestQueue    =   Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        bitmap = com.mvc.imagepicker.ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if(bitmap != null){
//            Uri path = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                logo.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            logo.setImageBitmap(bitmap);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onUpload(View view) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"url.php?operasi=update_logo";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Logo berhasil di ganti", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "ada masalah pada koneksi internet", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss");
                        String time = sdf.format(new Date());
                        params.put("URL_LOGO", ImagetoString(bitmap));
                        params.put("path", "images/"+time+"_logo.jpg");

                        return params;
                    }
                };
        requestQueue    =   Volley.newRequestQueue(LogoActivity.this);
        requestQueue.add(stringRequest);
//        Picasso.with(this).invalidate(url_img);
//        Picasso.with(this).load(url_img).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
    }

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }

    public void onBatal(View view) {
        finish();

    }

    public void onBack(View view) {
        finish();
    }
}
