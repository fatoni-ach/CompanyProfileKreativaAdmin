package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class LihatEventActivity extends AppCompatActivity {
    TextView tv_nama, tv_tgl, tv_tempat, tv_kapasitas, tv_htm, tv_status;
    ImageView imageView;
    String ID_EVENT,NAMA_EVENT, TGL_EVENT, TEMPAT, KAPISITAS, HTM, FOTO_EVENT, STATUS;
    LinkDatabase linkDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_event);
        imageView = findViewById(R.id.IV_lihatevent);
        tv_nama = findViewById(R.id.TV_lihatevent_namap);
        tv_tgl = findViewById(R.id.TV_lihatevent_tgl);
        tv_tempat = findViewById(R.id.TV_lihatevent_tempat);
        tv_kapasitas = findViewById(R.id.TV_lihatevent_kapasitas);
        tv_htm = findViewById(R.id.TV_lihatevent_htm);
        tv_status = findViewById(R.id.TV_lihatevent_status);
        ID_EVENT = new String(); NAMA_EVENT = new String(); TGL_EVENT = new String(); TEMPAT = new String(); KAPISITAS = new String();
        HTM = new String(); FOTO_EVENT = new String();STATUS = new String();

        linkDatabase= new LinkDatabase();

        ID_EVENT = getIntent().getStringExtra("ID_EVENT");
        NAMA_EVENT = getIntent().getStringExtra("NAMA_EVENT");
        TGL_EVENT = getIntent().getStringExtra("TGL_EVENT");
        TEMPAT = getIntent().getStringExtra("TEMPAT");
        KAPISITAS = getIntent().getStringExtra("KAPISITAS");
        HTM = getIntent().getStringExtra("HTM");
        FOTO_EVENT = getIntent().getStringExtra("FOTO_EVENT");
        STATUS = getIntent().getStringExtra("STATUS");

        tv_nama.setText(NAMA_EVENT);
        tv_tgl.setText(": "+ TGL_EVENT);
        tv_tempat.setText(": "+TEMPAT);
        tv_kapasitas.setText(": "+KAPISITAS);
        tv_htm.setText(": "+HTM);
        tv_status.setText(": "+STATUS);
        Glide.with(this).load(linkDatabase.linkurl()+FOTO_EVENT).placeholder(R.drawable.thumbnail).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FullScreenActivity.class);
                intent.putExtra("link", FOTO_EVENT);
                startActivity(intent);
            }
        });
    }

    public void onBack(View view) {finish();
    }

    public void onLihatPeserta(View view) {
        Intent intent = new Intent(LihatEventActivity.this , LihatPesertaActivity.class);
        intent.putExtra("ID_EVENT", ID_EVENT);
        intent.putExtra("NAMA_EVENT", NAMA_EVENT);
//        intent.putExtra("TGL_EVENT", TGL_EVENT);
//        intent.putExtra("TEMPAT", TEMPAT);
//        intent.putExtra("KAPISITAS", KAPISITAS);
//        intent.putExtra("HTM", HTM);
//        intent.putExtra("FOTO_EVENT", FOTO_EVENT);
//        intent.putExtra("STATUS", STATUS);

        startActivity(intent);
    }

    public void onEdit(View view) {
        Intent intent = new Intent(LihatEventActivity.this, EditEventActivity.class);
        intent.putExtra("ID_EVENT", ID_EVENT);
        intent.putExtra("NAMA_EVENT", NAMA_EVENT);
        intent.putExtra("TGL_EVENT", TGL_EVENT);
        intent.putExtra("TEMPAT", TEMPAT);
        intent.putExtra("KAPISITAS", KAPISITAS);
        intent.putExtra("HTM", HTM);
        intent.putExtra("FOTO_EVENT", FOTO_EVENT);
        intent.putExtra("STATUS", STATUS);

        startActivity(intent);
    }
}
