package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditProductActivity extends AppCompatActivity {

    private final int IMG_REQUEST =1;
    public Bitmap bitmap;
    TextInputEditText et_desk, et_judul; ImageView gambar;
    String str_id,str_judul, str_desk, str_tgl, str_picture, time2, time;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    boolean status=false;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        et_desk = (TextInputEditText)findViewById(R.id.TIET_eprodut_desk);
        et_judul = (TextInputEditText)findViewById(R.id.TIET_eprodut_judul);
        gambar = (ImageView)findViewById(R.id.IV_eprodut_gambar);

        str_id = new String();str_judul=new String();str_desk= new String();str_tgl=new String();str_picture=new String();time=new String();
        time2=new String();
        linkDatabase = new LinkDatabase();

        str_id = getIntent().getStringExtra("ID_PRODUCT");
        str_judul = getIntent().getStringExtra("JUDUL_PRODUCT");
        str_desk = getIntent().getStringExtra("DESK_PRODUCT");
        str_picture = getIntent().getStringExtra("FOTO_PRODUCT");
        str_tgl = getIntent().getStringExtra("TANGGAL_PRODUCT");

        et_judul.setText(str_judul);
        et_desk.setText(str_desk);
        Glide.with(this).load(linkDatabase.linkurl()+str_picture).placeholder(R.drawable.thumbnail).into(gambar);
    }

    public void onBack(View view) {finish();
    }

    public void onSimpan(View view) {
        final Dialog dialogconfirm = new Dialog(this);
        dialogconfirm.setContentView(R.layout.confirm);
        dialogconfirm.setTitle("Konfirmasi");
        dialogconfirm.show();

        Button yes = dialogconfirm.findViewById(R.id.BT_confirm_yes);
        Button no = dialogconfirm.findViewById(R.id.BT_confirm_no);
        TextView confirm = dialogconfirm.findViewById(R.id.TV_confirm);
        confirm.setText("Simpan perubahan ?");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(EditProductActivity.this);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                String upload_url = linkDatabase.linkurl()+"product.php?operasi=update_product";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        if(response.toLowerCase().toString().equals("update data berhasil")){
//                    Berita m = new Berita();
//                    m.setJUDUL_BERITA(et_judul.getText().toString());
//                    m.setDESK_BERITA(et_desk.getText().toString());
//                    m.setPICTURE_BERITA("images/"+time+"_berita.jpg");
//                    BeritaActivity.ma.tambah(m);

//                    AdapterBerita.ma.tambah(m);
                            ProductActivity.ma.reload();
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

                        params.put("ID_PRODUCT", str_id);
                        params.put("JUDUL_PRODUCT", et_judul.getText().toString());
                        params.put("DESK_PRODUCT", et_desk.getText().toString());
                        if (status){
                            params.put("FOTO_PRODUCT", ImagetoString(bitmap));
                            params.put("path", "images/"+time+"_product.jpg");
                        }

                        return params;
                    }
                };
                requestQueue    =   Volley.newRequestQueue(EditProductActivity.this);
                requestQueue.add(stringRequest);
                dialogconfirm.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogconfirm.dismiss();
            }
        });



    }

    public void onUpload(View view) {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, IMG_REQUEST);
        com.solusindo.kreativa.companyprofilekreativaadmin
                .ImagePicker.pickImage(this, "Select Your image : ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        bitmap = com.mvc.imagepicker.ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if(bitmap != null){
//            Uri path = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                gambar.setImageBitmap(bitmap);
//                status = true;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            status=true;
            gambar.setVisibility(View.VISIBLE);
            gambar.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }
}
