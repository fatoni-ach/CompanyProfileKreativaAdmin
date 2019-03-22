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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VisiMisiActivity extends AppCompatActivity {
    TextView visi, misi;
    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    String str_visi, str_misi;
    public static VisiMisiActivity vm;ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_misi);
        visi = (TextView)findViewById(R.id.TV_vm_visi);
        misi = (TextView)findViewById(R.id.TV_vm_misi);
        vm = new VisiMisiActivity();
        vm = this;
        str_misi = new String(); str_visi = new String();
        linkDatabase = new LinkDatabase();
        getvisimisi();
    }

    public void getvisimisi() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url      =   linkDatabase.linkurl()+"visimisi.php?operasi=view";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_visi = jsonObject.getString("ISI_VISI");
                    str_misi = jsonObject.getString("ISI_MISI");

                    visi.setText(str_visi);
                    misi.setText(str_misi);
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
        Intent intent = new Intent(this, EditVisiActivity.class);
        intent.putExtra("visi", str_visi);
        intent.putExtra("misi", str_misi);
        startActivity(intent);
    }

    public void onBack(View view) {
        finish();
    }

    public void refresh() {
        String url      =   linkDatabase.linkurl()+"visimisi.php?operasi=view";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_visi = jsonObject.getString("ISI_VISI");
                    str_misi = jsonObject.getString("ISI_MISI");

                    visi.setText(str_visi);
                    misi.setText(str_misi);
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

    public void onBatal(View view) {
        finish();
    }
}
