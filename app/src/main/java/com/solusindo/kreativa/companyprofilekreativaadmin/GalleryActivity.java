package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Galery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView rv_galery;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Galery> lstData;
    private AdapterGalery myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    LinkDatabase linkDatabase;

    public static GalleryActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        rv_galery = findViewById(R.id.RV_galery_list);
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
        String URL      =   linkDatabase.linkurl()+"galery.php?operasi=view_galery";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Galery model = new Galery();
                        model.setID_GALLERY(jsonObject.getString("ID_GALLERY"));
                        model.setDESK_GALLERY(jsonObject.getString("DESK_GALLERY"));
                        model.setFOTO_GALLERY(jsonObject.getString("FOTO_GALLERY"));
                        model.setTANGGAL_GALERY(jsonObject.getString("TANGGAL_GALERY"));

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
        requestQueue    =   Volley.newRequestQueue(GalleryActivity.this);
        ArrayRequest.setShouldCache(false);
        requestQueue.add(ArrayRequest);
    }

    public void tambah(Galery b){
        lstData.add(b);
        myAdapter       =   new AdapterGalery(getApplicationContext(), lstData);
        rv_galery.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        rv_galery.scrollToPosition(lstData.size()-1);
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

    public void setRvadapter(List<Galery> lst) {
        myAdapter       =   new AdapterGalery(this, lst);
        layoutManager   =   new GridLayoutManager(this, 2);
//        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        rv_galery.setLayoutManager(layoutManager);
        rv_galery.setAdapter(myAdapter);
//        rv_fasilitas.scrollToPosition(lst.size()-1);
    }

    public void onBack(View view) {finish();
    }


    public void onTambah(View view) {
        Intent intent = new Intent(getApplicationContext(), TambahGalleryActivity.class);
        startActivity(intent);
    }
}
