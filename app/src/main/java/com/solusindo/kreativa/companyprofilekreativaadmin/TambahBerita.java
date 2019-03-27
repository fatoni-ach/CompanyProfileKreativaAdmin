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
import com.mvc.imagepicker.ImagePicker;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TambahBerita extends AppCompatActivity {
    private final int IMG_REQUEST =1;
    ImageView imageView; TextInputEditText et_judul, et_desk;
    public Bitmap bitmap;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    private JsonArrayRequest arrayRequest;
    String time2, time, judul, desk, tgl, picture;
    ProgressDialog progressDialog;

//    String url_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_berita2);
        imageView = (ImageView)findViewById(R.id.IV_tb_gambar);
        et_judul = (TextInputEditText)findViewById(R.id.TIET_tb_judul);
        et_desk = (TextInputEditText)findViewById(R.id.TIET_tb_desk);
        linkDatabase = new LinkDatabase();
        time2 = new String(); time = new String();judul = new String();desk = new String();tgl = new String();
        picture = new String();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        imageView.setVisibility(View.GONE);
        ImagePicker.setMinQuality(600, 600);
    }

    public void onBatal(View view) {finish();
    }

    public void onSimpan(View view) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"berita.php?operasi=insert_berita";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("upload data berhasil")){
//                    Berita m = new Berita();
//                    m.setJUDUL_BERITA(et_judul.getText().toString());
//                    m.setDESK_BERITA(et_desk.getText().toString());
//                    m.setTANGGAL(time2);
//                    m.setPICTURE_BERITA("images/"+time+"_berita.jpg");
                    BeritaActivity.ma.reload();

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

                params.put("JUDUL_BERITA", et_judul.getText().toString());
                params.put("DESK_BERITA", et_desk.getText().toString());
                params.put("TANGGAL", time2);
                params.put("PICTURE_BERITA", ImagetoString(bitmap));
                params.put("path", "images/"+time+"_berita.jpg");
                params.put("DATETIME_TGL", time);

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(TambahBerita.this);
        requestQueue.add(stringRequest);
    }

    public void onPilih(View view) {
        com.solusindo.kreativa.companyprofilekreativaadmin.ImagePicker.pickImage(this, "Select Your image : ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

//        if(requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null){
//            Uri path = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                imageView.setVisibility(View.VISIBLE);
//                imageView.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(bitmap);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }
}
