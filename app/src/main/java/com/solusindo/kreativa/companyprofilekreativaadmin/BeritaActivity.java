package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BeritaActivity extends AppCompatActivity {

    private RecyclerView rv_berita;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Berita> lstData;
    private AdapterBerita myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;

    public static BeritaActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita2);
        rv_berita = findViewById(R.id.RV_berita);
        lstData =   new ArrayList<>();
        linkDatabase = new LinkDatabase();
        ma = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        initDataset();
    }

    private void initDataset(){
        String URL      =   linkDatabase.linkurl()+"berita.php?operasi=view_berita";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Berita model = new Berita();
                        model.setID_BERITA(jsonObject.getString("ID_BERITA"));
                        model.setJUDUL_BERITA(jsonObject.getString("JUDUL_BERITA"));
                        model.setDESK_BERITA(jsonObject.getString("DESK_BERITA"));
                        model.setTANGGAL(jsonObject.getString("TANGGAL"));
                        model.setPICTURE_BERITA(jsonObject.getString("PICTURE_BERITA"));
                        model.setDATETIME_TGL(jsonObject.getString("DATETIME_TGL"));

                        lstData.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setRvadapter(lstData);
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());

                progressDialog.dismiss();
            }
        });
        requestQueue    =   Volley.newRequestQueue(this);
//        ArrayRequest.setShouldCache(false);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(List<Berita> lst) {
        myAdapter       =   new AdapterBerita(this, lst);
        layoutManager   =   new LinearLayoutManager(this);
        rv_berita.setLayoutManager(layoutManager);
        rv_berita.setAdapter(myAdapter);
    }

    public void tambah(Berita b){
        lstData.add(b);
        myAdapter       =   new AdapterBerita(this, lstData);
        rv_berita.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        rv_berita.scrollToPosition(lstData.size()-1);
    }

    public void reload(){
//        for(int i=0; i< lstData.size(); i++){
//            myAdapter.delete(i);
//            lstData.remove(i);
//        }
////        lstData.clear();
//        myAdapter.notifyItemRangeChanged(0, lstData.size());
        lstData.clear();
        myAdapter.notifyDataSetChanged();

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        initDataset();
//        Toast.makeText(getBaseContext(), String.valueOf(lstData.size()), Toast.LENGTH_LONG).show();
    }

    public void update(){
        myAdapter.notifyDataSetChanged();
        rv_berita.setAdapter(myAdapter);
    }


    public void onBack(View view) {finish();
    }

//    public void onTambah(View view) {
//        Intent intent = new Intent(this, TambahBerita.class);
//        startActivity(intent);
//    }

    public void onFAtambah(View view) {
        Intent intent = new Intent(this, TambahBerita.class);
        startActivity(intent);
    }
}
