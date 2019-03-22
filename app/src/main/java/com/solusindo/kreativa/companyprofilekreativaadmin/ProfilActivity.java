package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfilActivity extends AppCompatActivity {
    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    private List<Profil> lstData;
    public static ProfilActivity PA;
    TextView nama_perusahaan, alamat, no_telp, email,instagram, desk, wa;
    String str_nama_perusahaan, str_alamat, str_no_telp, str_email,str_instagram,str_wa,
            str_desk, id;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        linkDatabase = new LinkDatabase();
        lstData = new ArrayList<>();
        str_nama_perusahaan = new String();
        str_alamat = new String();
        str_no_telp = new String();
        str_email = new String();
        str_instagram = new String();
        str_desk = new String();
        id = new String(); str_wa = new String();
        nama_perusahaan = (TextView)findViewById(R.id.TV_pp_namap);
        alamat = (TextView)findViewById(R.id.TV_pp_alamatp);
        no_telp = (TextView)findViewById(R.id.TV_pp_notelp);
        email = (TextView)findViewById(R.id.TV_pp_email);
        instagram = (TextView)findViewById(R.id.TV_pp_instagram);
        desk = (TextView)findViewById(R.id.TV_pp_desk);
        wa = (TextView)findViewById(R.id.TV_pp_wa);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        PA = this;

        String url      =   linkDatabase.linkurl()+"profil.php?operasi=view";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_desk = jsonObject.getString("DESK_PERUSAHAAN");
                    str_email = jsonObject.getString("EMAIL");
                    str_no_telp = jsonObject.getString("TELP");
                    str_alamat = jsonObject.getString("ALAMAT");
                    str_instagram = jsonObject.getString("INSTAGRAM");
                    str_wa = jsonObject.getString("WHATSAPP");

                    nama_perusahaan.setText(str_nama_perusahaan);
                    alamat.setText(str_alamat);
                    no_telp.setText(str_no_telp);
                    email.setText(str_email);
                    instagram.setText(str_instagram);
                    desk.setText(str_desk);
                    wa.setText(str_wa);
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

    public void onEdit(View view) {
        Intent intent = new Intent(this, EditProfilActivity.class);
        intent.putExtra("NAMA_PERUSAHAAN", str_nama_perusahaan);
        intent.putExtra("DESK_PERUSAHAAN", str_desk);
        intent.putExtra("EMAIL", str_email);
        intent.putExtra("TELP", str_no_telp);
        intent.putExtra("ALAMAT", str_alamat);
        intent.putExtra("INSTAGRAM", str_instagram);
        intent.putExtra("WHATSAPP", str_wa);
        startActivity(intent);
    }

    public void refresh(){
        String url      =   linkDatabase.linkurl()+"profil.php?operasi=view";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_desk = jsonObject.getString("DESK_PERUSAHAAN");
                    str_email = jsonObject.getString("EMAIL");
                    str_no_telp = jsonObject.getString("TELP");
                    str_alamat = jsonObject.getString("ALAMAT");
                    str_instagram = jsonObject.getString("INSTAGRAM");
                    str_wa = jsonObject.getString("WHATSAPP");


                    nama_perusahaan.setText(str_nama_perusahaan);
                    alamat.setText(str_alamat);
                    no_telp.setText(str_no_telp);
                    email.setText(str_email);
                    instagram.setText(str_instagram);
                    desk.setText(str_desk);
                    wa.setText(str_wa);
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

    public void onBack(View view) {
        finish();
    }

    public void onFAedit(View view) {
        Intent intent = new Intent(this, EditProfilActivity.class);
        intent.putExtra("NAMA_PERUSAHAAN", str_nama_perusahaan);
        intent.putExtra("DESK_PERUSAHAAN", str_desk);
        intent.putExtra("EMAIL", str_email);
        intent.putExtra("TELP", str_no_telp);
        intent.putExtra("ALAMAT", str_alamat);
        intent.putExtra("INSTAGRAM", str_instagram);
        intent.putExtra("WHATSAPP", str_wa);
        startActivity(intent);
    }
}
