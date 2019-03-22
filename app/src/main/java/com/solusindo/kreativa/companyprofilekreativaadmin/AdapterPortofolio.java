package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Portofolio;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterPortofolio extends RecyclerView.Adapter<AdapterPortofolio.ViewHolder> {

    RequestQueue requestQueue;
    private Context context;
    private List<Portofolio> mData;
    LinkDatabase linkDatabase;
    public static AdapterPortofolio ma;
    public AdapterPortofolio(Context context, List list ){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public AdapterPortofolio.ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_portofolio, parent, false);
        ViewHolder vh  =   new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterPortofolio.ViewHolder holder, final int position) {
        final String id = mData.get(position).getID_PORTOFOLIO();
        final String judul = mData.get(position).getNAMA_PROJECT();
        final String tanggal = mData.get(position).getTANGGAL_PROJECT();
        final String tempat = mData.get(position).getTEMPAT_PROJECT();
        final String desk = mData.get(position).getDESKRIPSI_PROJECT();
        final String picture = mData.get(position).getFOTO_PROJECT();
        holder.tv_judul.setText(judul);
        holder.tv_desk.setText(desk);
        holder.tv_tgl.setText(tanggal);
        Picasso.get().load(linkDatabase.linkurl()+picture).placeholder(R.drawable.thumbnail).into(holder.imageView);
//        Picasso.with(context).load(linkDatabase.linkurl()+picture).placeholder(R.drawable.thumbnail).into(holder.imageView);
//        Picasso.with(context).invalidate(linkDatabase.linkurl()+picture);
//        Picasso.with(context).load(linkDatabase.linkurl()+picture).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
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
                        Intent intent = new Intent(context, EditPortofolioActivity.class);
                        intent.putExtra("ID_PORTOFOLIO", id);
                        intent.putExtra("NAMA_PROJECT", judul);
                        intent.putExtra("TANGGAL_PROJECT", tanggal);
                        intent.putExtra("TEMPAT_PROJECT", tempat);
                        intent.putExtra("DESKRIPSI_PROJECT", desk);
                        intent.putExtra("FOTO_PROJECT", picture);

                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });

                //apabila tombol delete diklik
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                        String upload_url = linkDatabase.linkurl()+"portofolio.php?operasi=delete_portofolio";
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
                                params.put("ID_PORTOFOLIO", id);
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

    public void delete(int i){
        mData.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, mData.size());
    }

    public void tambah(Portofolio e){
        mData.add(e);
        notifyItemInserted(mData.size()-1);
        notifyItemRangeChanged(0, mData.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; TextView tv_judul, tv_desk, tv_tgl;
        public ViewHolder( View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.IV_lpor_gambar);
            tv_judul=(TextView)itemView.findViewById(R.id.TV_lpor_judul);
            tv_desk=(TextView)itemView.findViewById(R.id.TV_lpor_desk);
            tv_tgl=(TextView)itemView.findViewById(R.id.TV_lpor_tgl);
        }
    }
}
