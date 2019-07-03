package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Event;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Galery;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Join_event;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Peserta_event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LihatPesertaActivity extends AppCompatActivity {
    String ID_EVENT, ID_PESERTA, NAMA_PESERTA, NO_HP, FOTO_BUKTI_PEMBAYARAN, PENDIDIKAN_TERAKHIR, JENIS_KELAMIN, ALAMAT_PESERTA,
            EMAIL_PESERTA, NAMA_EVENT;
    LinkDatabase linkDatabase;
    ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private JsonArrayRequest ArrayRequest;
    private List<Join_event> lstData;
    private AdapterPeserta myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rv_peserta;
    TextView judul;
    public static LihatPesertaActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_peserta);
        judul = findViewById(R.id.TV_judul_event);
        rv_peserta = findViewById(R.id.RV_peserta);
        ID_EVENT = new String(); ID_PESERTA = new String();NAMA_PESERTA = new String();
        NO_HP = new String();FOTO_BUKTI_PEMBAYARAN=new String();PENDIDIKAN_TERAKHIR = new String();
        JENIS_KELAMIN= new String();ALAMAT_PESERTA=new String();EMAIL_PESERTA=new String();NAMA_EVENT = new String();

        ma = this;

        ID_EVENT = getIntent().getStringExtra("ID_EVENT");
        NAMA_EVENT = getIntent().getStringExtra("NAMA_EVENT");
        lstData = new ArrayList<>();
        linkDatabase = new LinkDatabase();
        judul.setText(NAMA_EVENT);
        initDataset();
//        Toast.makeText(getBaseContext(), ID_EVENT.toString(),Toast.LENGTH_LONG).show();
    }

    public void onFAtambah(View view) {
    }

    public void onBack(View view) {finish();
    }

    private void initDataset() {
        progressDialog = new ProgressDialog(LihatPesertaActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String URL      =   linkDatabase.linkurl()+"peserta_event.php?operasi=peserta_event&id_event="+String.valueOf(ID_EVENT);
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Join_event model = new Join_event();
                        model.setNAMA_PESERTA(jsonObject.getString("NAMA_PESERTA"));
                        model.setID_PESERTA(jsonObject.getString("ID_PESERTA"));
                        model.setSTATUS_JOIN(jsonObject.getString("STATUS_JOIN"));
                        model.setID_EVENT(ID_EVENT);
//                        model.setID_PESERTA(jsonObject.getString("ID_PESERTA"));
//                        Toast.makeText(getApplicationContext(), jsonObject.getString("ID_PESERTA"), Toast.LENGTH_LONG).show();

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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });//{
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("ID_EVENT", String.valueOf(ID_EVENT));
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new HashMap<>();
//                header.put("Content-Type", "application/x-www-form-urlencoded");
//                return header;
//            }
       // };
        requestQueue    =   Volley.newRequestQueue(this);
        requestQueue.add(ArrayRequest);
    }
    public void setRvadapter(List<Join_event> lst) {
        myAdapter       =   new AdapterPeserta(this, lst);
        layoutManager   =   new LinearLayoutManager(this);
        rv_peserta.setLayoutManager(layoutManager);
        rv_peserta.setAdapter(myAdapter);
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

//        progressDialog.setMessage("Loading...");
//        progressDialog.show();

        initDataset();
//        Toast.makeText(getBaseContext(), String.valueOf(lstData.size()), Toast.LENGTH_LONG).show();
    }
}
