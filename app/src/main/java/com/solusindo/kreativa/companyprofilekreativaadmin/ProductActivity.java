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
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView rv_product;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Product> lstData;
    private AdapterProduct myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;

    public static ProductActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        rv_product = findViewById(R.id.RV_product_list);
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
        String URL      =   linkDatabase.linkurl()+"product.php?operasi=view_product";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Product model = new Product();
                        model.setID_PRODUCT(jsonObject.getString("ID_PRODUCT"));
                        model.setDESK_PRODUCT(jsonObject.getString("DESK_PRODUCT"));
                        model.setJUDUL_PRODUCT(jsonObject.getString("JUDUL_PRODUCT"));
                        model.setFOTO_PRODUCT(jsonObject.getString("FOTO_PRODUCT"));
                        model.setTANGGAL_PRODUCT(jsonObject.getString("TANGGAL_PRODUCT"));

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
        requestQueue    =   Volley.newRequestQueue(ProductActivity.this);
        ArrayRequest.setShouldCache(false);
        requestQueue.add(ArrayRequest);
    }

    public void tambah(Product b){
        lstData.add(b);
        myAdapter       =   new AdapterProduct(ProductActivity.this, lstData);
        rv_product.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
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

    public void setRvadapter(List<Product> lst) {
        myAdapter       =   new AdapterProduct(ProductActivity.this, lst);
        layoutManager   =   new GridLayoutManager(this, 2);
        rv_product.setLayoutManager(layoutManager);
        rv_product.setAdapter(myAdapter);
    }

    public void onBack(View view) {finish();
    }

    public void onTambah(View view) {
        Intent intent = new Intent(getApplicationContext(), TambahProductActivity.class);
        startActivity(intent);
    }
}
