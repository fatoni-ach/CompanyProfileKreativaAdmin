package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.solusindo.kreativa.companyprofilekreativaadmin.table.Berita;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Join_event;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Peserta_event;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterPeserta extends RecyclerView.Adapter<AdapterPeserta.ViewHolder>{
    private List<Join_event> mData;
    Context context;
    public AdapterPeserta(Context context, List list){
        this.context = context;
        this.mData = list;
    }
    @Override
    public AdapterPeserta.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_peserta, parent, false);
        ViewHolder vh  =   new ViewHolder(v);
        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(AdapterPeserta.ViewHolder holder, final int i) {
        holder.no.setText(String.valueOf(i+1));
        holder.nama.setText(mData.get(i).getNAMA_PESERTA());
        if(mData.get(i).getSTATUS_JOIN().equals("LUNAS")) {
            holder.nama.setBackgroundColor(Color.GREEN);
        } else holder.nama.setBackgroundColor(Color.RED);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPesertaActivity.class);
                intent.putExtra("ID_PESERTA", mData.get(i).getID_PESERTA());
                intent.putExtra("ID_EVENT", mData.get(i).getID_EVENT());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView no, nama;
        Button detail;
        public ViewHolder( View itemView) {
            super(itemView);
            no = (TextView) itemView.findViewById(R.id.TV_no_peserta_list);
            nama = (TextView) itemView.findViewById(R.id.TV_nama_list);
            detail = (Button) itemView.findViewById(R.id.BT_detail_list);
        }
    }
}
