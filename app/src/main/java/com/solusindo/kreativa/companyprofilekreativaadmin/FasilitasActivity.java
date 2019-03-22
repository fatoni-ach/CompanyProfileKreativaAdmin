package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Fasilitas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FasilitasActivity extends AppCompatActivity {
    private RecyclerView rv_fasilitas;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Fasilitas> lstData;
    private AdapterFasilitas myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;

    public static FasilitasActivity ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas);

        rv_fasilitas = findViewById(R.id.RV_fasilitas);
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

    private void initDataset() {
        String URL      =   linkDatabase.linkurl()+"fasilitas.php?operasi=view_fasilitas";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Fasilitas model = new Fasilitas();
                        model.setID_FASILITAS(jsonObject.getString("ID_FASILITAS"));
                        model.setDESK_FASILITAS(jsonObject.getString("DESK_FASILITAS"));
                        model.setFOTO_FASILITAS(jsonObject.getString("FOTO_FASILITAS"));
                        model.setTANGGAL_FASILITAS(jsonObject.getString("TANGGAL_FASILITAS"));

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
        requestQueue    =   Volley.newRequestQueue(this);
        ArrayRequest.setShouldCache(false);
        requestQueue.add(ArrayRequest);
    }

    public void tambah(Fasilitas b){
        lstData.add(b);
        myAdapter       =   new AdapterFasilitas(getApplicationContext(), lstData);
        rv_fasilitas.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        rv_fasilitas.scrollToPosition(lstData.size()-1);
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

    public void setRvadapter(List<Fasilitas> lst) {
        myAdapter       =   new AdapterFasilitas(this, lst);
        layoutManager   =   new GridLayoutManager(this, 2);
//        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        rv_fasilitas.setLayoutManager(layoutManager);
        rv_fasilitas.setAdapter(myAdapter);
//        rv_fasilitas.scrollToPosition(lst.size()-1);
    }

    public void onBack(View view) {finish();
    }

    public void onTambah(View view) {
        Intent intent = new Intent(this, TambahFasillitasActivity.class);
        startActivity(intent);
    }
}
