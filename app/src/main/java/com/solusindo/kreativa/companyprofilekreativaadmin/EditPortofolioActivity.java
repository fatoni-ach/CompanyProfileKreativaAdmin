package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditPortofolioActivity extends AppCompatActivity {
    private final int IMG_REQUEST =1;
    public Bitmap bitmap;
    TextInputEditText et_judul,et_tgl, et_tempat, et_desk; ImageView gambar;
    String str_id, str_nama, str_tempat, str_desk, str_picture, str_datetime, time2, time;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    boolean status=false;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_portofolio);

        gambar = (ImageView)findViewById(R.id.IV_epor_gambar);
        et_judul = (TextInputEditText)findViewById(R.id.TIET_epor_judul);
        et_tgl = (TextInputEditText)findViewById(R.id.TIET_epor_tgl);
        et_tempat =(TextInputEditText)findViewById(R.id.TIET_epor_tempat);
        et_desk = (TextInputEditText)findViewById(R.id.TIET_epor_desk);
        linkDatabase = new LinkDatabase();
        time2 = new String(); time = new String();str_id=new String();
        str_nama = new String();str_tempat = new String();str_desk = new String();str_picture=new String();str_datetime=new String();

        str_id = getIntent().getStringExtra("ID_PORTOFOLIO");
        str_nama = getIntent().getStringExtra("NAMA_PROJECT");
        str_tempat = getIntent().getStringExtra("TEMPAT_PROJECT");
        str_desk = getIntent().getStringExtra("DESKRIPSI_PROJECT");
        str_picture = getIntent().getStringExtra("FOTO_PROJECT");
        str_datetime = getIntent().getStringExtra("TANGGAL_PROJECT");

        et_judul.setText(str_nama);
        et_tgl.setText(str_datetime);
        et_tempat.setText(str_tempat);
        et_desk.setText(str_desk);
        Picasso.get().load(linkDatabase.linkurl()+str_picture).placeholder(R.drawable.thumbnail).into(gambar);
//        Picasso.with(this).invalidate(linkDatabase.linkurl()+str_picture);
//        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
//        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).into(gambar);
    }

    public void onBatal(View view) {finish();
    }

    public void onSimpan(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"portofolio.php?operasi=update_portofolio";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("update data berhasil")){
                    PortofolioActivity.ma.reload();
                    finish();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss");
                time = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                time2 = sdf2.format(new Date());

                params.put("ID_PORTOFOLIO", str_id);
                params.put("NAMA_PROJECT", et_judul.getText().toString());
                params.put("TEMPAT_PROJECT", et_tempat.getText().toString());
                params.put("DESKRIPSI_PROJECT", et_desk.getText().toString());
                params.put("TANGGAL_PROJECT", et_tgl.getText().toString());
                if (status){
                    params.put("FOTO_PROJECT", ImagetoString(bitmap));
                    params.put("path", "images/"+time+"_portofolio.jpg");
                }

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(EditPortofolioActivity.this);
        requestQueue.add(stringRequest);
    }

    public void onPilih(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                gambar.setImageBitmap(bitmap);
                status = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }
}
