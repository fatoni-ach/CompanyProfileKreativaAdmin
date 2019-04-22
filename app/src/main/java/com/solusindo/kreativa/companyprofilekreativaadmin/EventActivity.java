package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
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
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private RecyclerView rv_event;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Event> lstData;
    private AdapterEvent myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;

    public static EventActivity ma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        rv_event = findViewById(R.id.RV_event);
        lstData =   new ArrayList<>();
        linkDatabase = new LinkDatabase();
        ma = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        initDataset();
    }

    private void initDataset() {
        String URL      =   linkDatabase.linkurl()+"event.php?operasi=view_event";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Event model = new Event();
                        model.setID_EVENT(jsonObject.getString("ID_EVENT"));
                        model.setNAMA_EVENT(jsonObject.getString("NAMA_EVENT"));
                        model.setTGL_EVENT(jsonObject.getString("TGL_EVENT"));
                        model.setTEMPAT(jsonObject.getString("TEMPAT"));
                        model.setKAPISITAS(jsonObject.getString("KAPISITAS"));
                        model.setHTM(jsonObject.getString("HTM"));
                        model.setFOTO_EVENT(jsonObject.getString("FOTO_EVENT"));
                        model.setSTATUS(jsonObject.getString("STATUS"));

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

    private void setRvadapter(List<Event> lst) {
        myAdapter       =   new AdapterEvent(this, lst);
        layoutManager   =   new LinearLayoutManager(this);
        rv_event.setLayoutManager(layoutManager);
        rv_event.setAdapter(myAdapter);
    }

    public void onBack(View view) {
        finish();
    }

    public void onFAtambah(View view) {
        Intent intent = new Intent(this, TambahEventActivity.class);
        startActivity(intent);
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
}
