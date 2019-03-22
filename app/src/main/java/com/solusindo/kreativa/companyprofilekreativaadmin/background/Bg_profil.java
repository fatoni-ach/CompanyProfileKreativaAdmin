package com.solusindo.kreativa.companyprofilekreativaadmin.background;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.solusindo.kreativa.companyprofilekreativaadmin.LinkDatabase;
import com.solusindo.kreativa.companyprofilekreativaadmin.interfaces.AsyncResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Bg_profil extends AsyncTask<String, Void,String> {
    LinkDatabase linkDatabase;
    Context context;
    public AsyncResponse delegate = null;

    public Bg_profil(Context context){
        this.context = context;
        linkDatabase = new LinkDatabase();
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processfinish(s);
    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String type = voids[0];
        linkDatabase = new LinkDatabase();

        String login = linkDatabase.linkurl()+"profil.php?operasi=update_profil";
        if (type.equals("update")) {
            try {
                String nama_p = voids[1];
                String alamat = voids[2];
                String no_telp = voids[3];
                String email = voids[4];
                String instagram = voids[5];
                String desk = voids[6];
                String wa = voids[7];
                URL url = new URL(login);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("NAMA_PERUSAHAAN", "UTF-8") + "=" + URLEncoder.encode(nama_p, "UTF-8")
                        + "&&" + URLEncoder.encode("DESK_PERUSAHAAN", "UTF-8") + "=" + URLEncoder.encode(desk, "UTF-8")
                        + "&&" + URLEncoder.encode("EMAIL", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&&" + URLEncoder.encode("TELP", "UTF-8") + "=" + URLEncoder.encode(no_telp, "UTF-8")
                        + "&&" + URLEncoder.encode("ALAMAT", "UTF-8") + "=" + URLEncoder.encode(alamat, "UTF-8")
                        + "&&" + URLEncoder.encode("INSTAGRAM", "UTF-8") + "=" + URLEncoder.encode(instagram, "UTF-8")
                        + "&&" + URLEncoder.encode("WHATSAPP", "UTF-8") + "=" + URLEncoder.encode(wa, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
        }
        return result;
    }
}
