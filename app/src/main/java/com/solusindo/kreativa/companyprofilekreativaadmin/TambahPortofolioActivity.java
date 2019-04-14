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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Portofolio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TambahPortofolioActivity extends AppCompatActivity {

    private final int IMG_REQUEST =1;
    ImageView imageView; TextInputEditText et_nama, et_tgl, et_tempat, et_desk;
    public Bitmap bitmap;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    private JsonArrayRequest arrayRequest;
    String time2, time, nama,tgl, tempat, desk, picture;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_portofolio);

        imageView = (ImageView)findViewById(R.id.IV_tpor_gambar);
        et_nama = (TextInputEditText)findViewById(R.id.TIET_tpor_judul);
        et_tgl = (TextInputEditText)findViewById(R.id.TIET_tpor_tgl);
        et_tempat =(TextInputEditText)findViewById(R.id.TIET_tpor_tempat);
        et_desk = (TextInputEditText)findViewById(R.id.TIET_tpor_desk);
        linkDatabase = new LinkDatabase();
        time2 = new String(); time = new String();nama = new String();desk = new String();tgl = new String();
        picture = new String();tempat=new String();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        imageView.setVisibility(View.GONE);
    }

    public void onBatal(View view) {finish();
    }

    public void onSimpan(View view) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"portofolio.php?operasi=insert_portofolio";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("upload data berhasil")){
//                    Portofolio m = new Portofolio();
//                    m.setNAMA_PROJECT(et_nama.getText().toString());
//                    m.setTANGGAL_PROJECT(time2);
//                    m.setTEMPAT_PROJECT(et_tempat.getText().toString());
//                    m.setDESKRIPSI_PROJECT(et_desk.getText().toString());
//                    m.setFOTO_PROJECT("images/"+time+"_portofolio.jpg");
                    PortofolioActivity.ma.reload();

//                    AdapterBerita.ma.tambah(m);
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss");
                time = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                time2 = sdf2.format(new Date());

                params.put("NAMA_PROJECT", et_nama.getText().toString());
                params.put("TANGGAL_PROJECT", et_tgl.getText().toString());
                params.put("TEMPAT_PROJECT", et_tempat.getText().toString());
                params.put("DESKRIPSI_PROJECT", et_desk.getText().toString());
                params.put("FOTO_PROJECT", ImagetoString(bitmap));
                params.put("path", "images/"+time+"_portofolio.jpg");

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(TambahPortofolioActivity.this);
        requestQueue.add(stringRequest);
    }

    public void onPilih(View view) {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, IMG_REQUEST);
        com.solusindo.kreativa.companyprofilekreativaadmin.ImagePicker.pickImage(this, "Select Your image : ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null){
//            Uri path = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                imageView.setVisibility(View.VISIBLE);
//                imageView.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            bitmap = com.mvc.imagepicker.ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }
}
