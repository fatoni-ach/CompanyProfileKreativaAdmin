package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.bumptech.glide.module.AppGlideModule;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.ViewHolder> {

    RequestQueue requestQueue;
    private Context context;
    private List<Berita> mData;
    LinkDatabase linkDatabase;
    public static AdapterBerita ma;
    public AdapterBerita(Context context, List list ){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

//    public void add(){
//        notifyItemInserted(mData.size());
//        notify();
//        notifyDataSetChanged();
//    }

    @Override
    public AdapterBerita.ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_berita, parent, false);
        ViewHolder  vh  =   new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterBerita.ViewHolder holder, final int position) {
        final String id = mData.get(position).getID_BERITA();
        final String judul = mData.get(position).getJUDUL_BERITA();
        final String deskripsi = mData.get(position).getDESK_BERITA();
        final String tanggal = mData.get(position).getTANGGAL();
        final String picture = mData.get(position).getPICTURE_BERITA();
        final String datetime = mData.get(position).getDATETIME_TGL();
        holder.judul_berita.setText(judul);
        holder.desk_berita.setText(deskripsi);
        holder.tanggal_berita.setText(tanggal);
        Glide.with(context).load(linkDatabase.linkurl()+picture).override(80, 80).placeholder(R.drawable.thumbnail).into(holder.gambar);
        holder.gambar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.option);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button edit = dialog.findViewById(R.id.BT_berita_edit);
                final Button delete = dialog.findViewById(R.id.BT_berita_delete);

                //apabila tombol edit diklik
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, EditBeritaActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("judul", judul);
                        intent.putExtra("deskripsi", deskripsi);
                        intent.putExtra("tanggal", tanggal);
                        intent.putExtra("picture", picture);
                        intent.putExtra("datetime", datetime);

                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });

                //apabila tombol delete diklik
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        String upload_url = linkDatabase.linkurl()+"berita.php?operasi=delete_berita";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                                if(response.toString().equals("data berhasil di hapus")){
                                    delete(position);
                                }
//                                dialog.dismiss();
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("path", picture);
                                params.put("DATETIME_TGL", datetime);
                                return params;
                            }
                        };
                        requestQueue    =   Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);

                        dialog.dismiss();
                    }
                });

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void delete(int i){
        mData.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, mData.size());
    }

    public void tambah(Berita e){
        mData.add(e);
        notifyItemInserted(mData.size()-1);
        notifyItemRangeChanged(0, mData.size());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView gambar;
        public TextView judul_berita, desk_berita, tanggal_berita;
        public ViewHolder( View v) {
            super(v);
            gambar = v.findViewById(R.id.IV_berita_list);
            judul_berita = v.findViewById(R.id.TV_berita_judul);
            desk_berita = v.findViewById(R.id.TV_berita_desk);
            tanggal_berita = v.findViewById(R.id.TV_berita_tgl);
        }
    }
}
