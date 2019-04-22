package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TambahEventActivity extends AppCompatActivity {
    TextInputEditText et_event, et_tgl, et_tempat, et_kapasitas, et_htm;
    ImageView imageView; Spinner SP_status;
    private final int IMG_REQUEST =1; public Bitmap bitmap;
    ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    LinkDatabase linkDatabase;
    String time2, time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_event);
        et_event = findViewById(R.id.ET_Tevent_nama);
        et_tgl = findViewById(R.id.ET_Tevent_tgl);
        et_tempat = findViewById(R.id.ET_Tevent_tempat);
        et_kapasitas = findViewById(R.id.ET_Tevent_kapasitas);
        et_htm = findViewById(R.id.ET_Tevent_htm);
        SP_status = findViewById(R.id.SP_Tevent_status);
        imageView = findViewById(R.id.IV_Tevent);
        time = new String();time2 = new String();
        linkDatabase = new LinkDatabase();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void onBatal(View view) {finish();
    }

    public void onSimpan(View view) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"event.php?operasi=insert_event";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("upload data berhasil")){
                    EventActivity.ma.reload();
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

                params.put("NAMA_EVENT", et_event.getText().toString());
                params.put("TGL_EVENT", et_tgl.getText().toString());
                params.put("TEMPAT", et_tempat.getText().toString());
                params.put("KAPISITAS", et_kapasitas.getText().toString());
                params.put("path", "images/"+time+"_event.jpg");
                params.put("HTM", et_htm.getText().toString());
                params.put("STATUS", SP_status.getSelectedItem().toString());
                params.put("FOTO_EVENT", ImagetoString(bitmap));

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(TambahEventActivity.this);
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
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = com.mvc.imagepicker.ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if(bitmap != null){
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
        }
    }

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }
}
