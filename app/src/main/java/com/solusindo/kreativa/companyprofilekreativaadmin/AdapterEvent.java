package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ViewHolder> {

    RequestQueue requestQueue;
    private Context context;
    private List<Event> mData;
    LinkDatabase linkDatabase;
    public static AdapterEvent ma;
    public AdapterEvent(Context context, List list ){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public AdapterEvent.ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_event, parent, false);
        ViewHolder vh  =   new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterEvent.ViewHolder holder, final int position) {
        final String ID_EVENT = mData.get(position).getID_EVENT();
        final String NAMA_EVENT = mData.get(position).getNAMA_EVENT();
        final String TGL_EVENT = mData.get(position).getTGL_EVENT();
        final String TEMPAT = mData.get(position).getTEMPAT();
        final String KAPISITAS = mData.get(position).getKAPISITAS();
        final String HTM = mData.get(position).getHTM();
        final String FOTO_EVENT = mData.get(position).getFOTO_EVENT();
        final String STATUS = mData.get(position).getSTATUS();
        holder.judul.setText(NAMA_EVENT);
        Glide.with(context).load(linkDatabase.linkurl()+FOTO_EVENT).override(350, 200).placeholder(R.drawable.thumbnail).into(holder.gambar);
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
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
                        Intent intent = new Intent(context, EditEventActivity.class);
                        intent.putExtra("ID_EVENT", ID_EVENT);
                        intent.putExtra("NAMA_EVENT", NAMA_EVENT);
                        intent.putExtra("TGL_EVENT", TGL_EVENT);
                        intent.putExtra("TEMPAT", TEMPAT);
                        intent.putExtra("KAPISITAS", KAPISITAS);
                        intent.putExtra("HTM", HTM);
                        intent.putExtra("FOTO_EVENT", FOTO_EVENT);
                        intent.putExtra("STATUS", STATUS);

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
                        String upload_url = linkDatabase.linkurl()+"event.php?operasi=delete_event";
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
                                params.put("path", FOTO_EVENT);
                                params.put("id", ID_EVENT);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView gambar;
        public TextView judul;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.IV_event_list);
            judul = itemView.findViewById(R.id.TV_event_judul);
            linearLayout = itemView.findViewById(R.id.ll_event);
        }
    }
}
