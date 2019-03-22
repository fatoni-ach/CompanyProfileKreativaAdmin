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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Portofolio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PortofolioActivity extends AppCompatActivity {

    private RecyclerView rv_portofolio;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Portofolio> lstData;
    private AdapterPortofolio myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;

    public static PortofolioActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portofolio);

        rv_portofolio = findViewById(R.id.RV_portofolio);
        lstData =   new ArrayList<>();
        linkDatabase = new LinkDatabase();
        ma = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final Context context = this;

        initDataset();
    }

    private void initDataset(){
        String URL      =   linkDatabase.linkurl()+"portofolio.php?operasi=view_portofolio";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Portofolio model = new Portofolio();
                        model.setID_PORTOFOLIO(jsonObject.getString("ID_PORTOFOLIO"));
                        model.setNAMA_PROJECT(jsonObject.getString("NAMA_PROJECT"));
                        model.setTANGGAL_PROJECT(jsonObject.getString("TANGGAL_PROJECT"));
                        model.setTEMPAT_PROJECT(jsonObject.getString("TEMPAT_PROJECT"));
                        model.setDESKRIPSI_PROJECT(jsonObject.getString("DESKRIPSI_PROJECT"));
                        model.setFOTO_PROJECT(jsonObject.getString("FOTO_PROJECT"));

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
                Log.d("error", error.toString()); progressDialog.dismiss();
            }
        });
        requestQueue    =   Volley.newRequestQueue(PortofolioActivity.this);
        ArrayRequest.setShouldCache(false);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(List<Portofolio> lst) {
        myAdapter       =   new AdapterPortofolio(PortofolioActivity.this, lst);
        layoutManager   =   new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        rv_portofolio.setLayoutManager(layoutManager);
        rv_portofolio.setAdapter(myAdapter);
        rv_portofolio.scrollToPosition(lst.size()-1);
    }

    public void tambah(Portofolio b){
        lstData.add(b);
        myAdapter       =   new AdapterPortofolio(PortofolioActivity.this, lstData);
        rv_portofolio.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        rv_portofolio.scrollToPosition(lstData.size()-1);
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


    public void onBack(View view) {finish();
    }

    public void onTambah(View view) {
        Intent intent = new Intent(this, TambahPortofolioActivity.class);
        startActivity(intent);
    }
}
