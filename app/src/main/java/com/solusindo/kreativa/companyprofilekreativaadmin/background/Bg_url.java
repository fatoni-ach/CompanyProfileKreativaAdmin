package com.solusindo.kreativa.companyprofilekreativaadmin.background;

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

public class Bg_url extends AsyncTask<String, Void,String> {
    LinkDatabase linkDatabase;
    Context context;
    public AsyncResponse delegate = null;

    public Bg_url(Context context){
        this.context = context;
        linkDatabase = new LinkDatabase();
    }
    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String type = voids[0];
        linkDatabase = new LinkDatabase();

        String login = linkDatabase.linkurl()+"url.php?operasi=update_video";
        if (type.equals("update_video")) {
            try {
                String link_video = voids[1];
                URL url = new URL(login);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("URL_VIDEO_PROFIL", "UTF-8") + "=" + URLEncoder.encode(link_video, "UTF-8");
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

    @Override
    protected void onPostExecute(String s) {
        delegate.processfinish(s);
    }
}
