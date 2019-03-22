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
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Galery;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterGalery extends RecyclerView.Adapter<AdapterGalery.ViewHolder> {

    RequestQueue requestQueue;
    private Context context;
    private List<Galery> mData;
    LinkDatabase linkDatabase;
    public static AdapterGalery ma;

    public AdapterGalery(Context context, List list){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public AdapterGalery.ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_galery, parent, false);
        ViewHolder vh  =   new ViewHolder(v);
        return vh;
    }

    public void delete(int i){
        mData.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, mData.size());
    }

    public void tambah(Galery e){
        mData.add(e);
        notifyItemInserted(mData.size()-1);
        notifyItemRangeChanged(0, mData.size());
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(AdapterGalery.ViewHolder holder, final int position) {
        final String id = mData.get(position).getID_GALLERY();
        final String desk = mData.get(position).getDESK_GALLERY();
        final String foto = mData.get(position).getFOTO_GALLERY();
        final String datetime = mData.get(position).getTANGGAL_GALERY();
        holder.tv_desk.setText(desk);
        Picasso.get().load(linkDatabase.linkurl()+foto).placeholder(R.drawable.thumbnail).into(holder.imageView);
//        Picasso.with(context).load(linkDatabase.linkurl()+foto).placeholder(R.drawable.thumbnail).into(holder.imageView);
//        Picasso.with(context).invalidate(linkDatabase.linkurl()+foto);
//        Picasso.with(context).load(linkDatabase.linkurl()+foto).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
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
                        Intent intent = new Intent(context, EditGaleryActivity.class);
                        intent.putExtra("ID_GALLERY", id);
                        intent.putExtra("DESK_GALLERY", desk);
                        intent.putExtra("FOTO_GALLERY", foto);
                        intent.putExtra("TANGGAL_GALERY", datetime);

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
                        String upload_url = linkDatabase.linkurl()+"galery.php?operasi=delete_galery";
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
                                params.put("path", foto);
                                params.put("ID_GALLERY", id);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; TextView tv_desk;
        public ViewHolder( View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.IV_galery_list);
            tv_desk = (TextView)itemView.findViewById(R.id.TV_lg_desk);
        }
    }
}
