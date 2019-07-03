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
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Product;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    RequestQueue requestQueue;
    private Context context;
    private List<Product> mData;
    LinkDatabase linkDatabase;
    public static AdapterProduct ma;

    public AdapterProduct(Context context, List list){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder( ViewGroup parent, int position) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        ViewHolder vh  = new ViewHolder(v);
        return vh;
    }

    public void delete(int i){
        mData.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, mData.size());
    }

    public void tambah(Product e){
        mData.add(e);
        notifyItemInserted(mData.size()-1);
        notifyItemRangeChanged(0, mData.size());
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(AdapterProduct.ViewHolder holder, final int position) {
        final String id = mData.get(position).getID_PRODUCT();
        final String judul = mData.get(position).getJUDUL_PRODUCT();
        final String desk = mData.get(position).getDESK_PRODUCT();
        final String foto = mData.get(position).getFOTO_PRODUCT();
        final String datetime = mData.get(position).getTANGGAL_PRODUCT();
        holder.tv_judul.setText(judul);
        holder.tv_desk.setText(desk);
        Glide.with(context).load(linkDatabase.linkurl()+foto)
                .override(150, 150).placeholder(R.drawable.thumbnail).into(holder.imageView);
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
                        Intent intent = new Intent(context, EditProductActivity.class);
                        intent.putExtra("ID_PRODUCT", id);
                        intent.putExtra("JUDUL_PRODUCT", judul);
                        intent.putExtra("DESK_PRODUCT", desk);
                        intent.putExtra("FOTO_PRODUCT", foto);
                        intent.putExtra("TANGGAL_PRODUCT", datetime);

                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });

                //apabila tombol delete diklik
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final Dialog dialogconfirm = new Dialog(context);
                        dialogconfirm.setContentView(R.layout.confirm);
                        dialogconfirm.setTitle("Konfirmasi");
                        dialogconfirm.show();

                        Button yes = dialogconfirm.findViewById(R.id.BT_confirm_yes);
                        Button no = dialogconfirm.findViewById(R.id.BT_confirm_no);
                        TextView confirm = dialogconfirm.findViewById(R.id.TV_confirm);
                        confirm.setText("Apakah anda yakin ingin menghapus produk ?");
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final ProgressDialog progressDialog = new ProgressDialog(context);
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.setMessage("Loading...");
                                progressDialog.show();
                                String upload_url = linkDatabase.linkurl()+"product.php?operasi=delete_product";
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
                                        params.put("ID_PRODUCT", id);
                                        return params;
                                    }
                                };
                                requestQueue    =   Volley.newRequestQueue(context);
                                requestQueue.add(stringRequest);

                                dialogconfirm.dismiss();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogconfirm.dismiss();
                            }
                        });
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
        ImageView imageView; TextView tv_desk, tv_judul;
        LinearLayout linearLayout;
        public ViewHolder( View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.IV_product_list);
            tv_judul=(TextView)itemView.findViewById(R.id.TV_lp_judul);
            tv_desk=(TextView)itemView.findViewById(R.id.TV_lp_desk);
            linearLayout = itemView.findViewById(R.id.LL_product);
        }
    }
}
