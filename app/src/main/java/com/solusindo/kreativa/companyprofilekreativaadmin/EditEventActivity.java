package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditEventActivity extends AppCompatActivity {
    String ID_EVENT,NAMA_EVENT, TGL_EVENT, TEMPAT, KAPISITAS, HTM, FOTO_EVENT, STATUS,time2, time;
    Spinner SP_status;
    TextInputEditText et_nama, et_tgl, et_tempat, et_kapasitas, et_htm;
    ImageView imageView;
    LinkDatabase linkDatabase;
    boolean status=false;
    ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        ID_EVENT = new String(); NAMA_EVENT = new String(); TGL_EVENT = new String(); TEMPAT = new String(); KAPISITAS = new String();
        HTM = new String(); FOTO_EVENT = new String();STATUS = new String(); time = new String(); time2 = new String();
        SP_status = findViewById(R.id.SP_Eevent_status);
        et_nama = findViewById(R.id.ET_Eevent_nama);
        et_tgl = findViewById(R.id.ET_Eevent_tgl);
        et_tempat = findViewById(R.id.ET_Eevent_tempat);
        et_kapasitas = findViewById(R.id.ET_Eevent_kapasitas);
        et_htm = findViewById(R.id.ET_Eevent_htm);
        imageView = findViewById(R.id.IV_Eevent);
        linkDatabase = new LinkDatabase();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_status.setAdapter(adapter);

        ID_EVENT = getIntent().getStringExtra("ID_EVENT");
        NAMA_EVENT = getIntent().getStringExtra("NAMA_EVENT");
        TGL_EVENT = getIntent().getStringExtra("TGL_EVENT");
        TEMPAT = getIntent().getStringExtra("TEMPAT");
        KAPISITAS = getIntent().getStringExtra("KAPISITAS");
        HTM = getIntent().getStringExtra("HTM");
        FOTO_EVENT = getIntent().getStringExtra("FOTO_EVENT");
        STATUS = getIntent().getStringExtra("STATUS");

        et_nama.setText(NAMA_EVENT);
        et_tgl.setText(TGL_EVENT);
        et_tempat.setText(TEMPAT);
        et_kapasitas.setText(KAPISITAS);
        et_htm.setText(HTM);
        Glide.with(this).load(linkDatabase.linkurl()+FOTO_EVENT).placeholder(R.drawable.thumbnail).into(imageView);
        if (STATUS != null) {
            int posisi = adapter.getPosition(STATUS);
            SP_status.setSelection(posisi);
        }
    }

    public void onBatal(View view) {finish();
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
                progressDialog = new ProgressDialog(EditEventActivity.this);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                String upload_url = linkDatabase.linkurl()+"event.php?operasi=update_event";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        if(response.toLowerCase().toString().equals("update data berhasil")){
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
//                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
//                time2 = sdf2.format(new Date());

                        params.put("ID_EVENT", ID_EVENT);
                        params.put("NAMA_EVENT", et_nama.getText().toString());
                        params.put("TGL_EVENT", et_tgl.getText().toString());
                        params.put("TEMPAT", et_tempat.getText().toString());
                        params.put("KAPISITAS", et_kapasitas.getText().toString());
                        params.put("HTM", et_htm.getText().toString());
                        params.put("STATUS", SP_status.getSelectedItem().toString());
                        if (status){
                            params.put("FOTO_EVENT", ImagetoString(bitmap));
                            params.put("path", "images/"+time+"_event.jpg");
                        }
                        return params;
                    }
                };
                requestQueue    =   Volley.newRequestQueue(EditEventActivity.this);
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

    public void onPilih(View view) {
        com.solusindo.kreativa.companyprofilekreativaadmin.ImagePicker.pickImage(this, "Select Your image : ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        bitmap = com.mvc.imagepicker.ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if(bitmap !=null){
            status =true;
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
        }
    }

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }
}
