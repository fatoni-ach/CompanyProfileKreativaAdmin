package com.solusindo.kreativa.companyprofilekreativaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.solusindo.kreativa.companyprofilekreativaadmin.background.Bg_profil;
import com.solusindo.kreativa.companyprofilekreativaadmin.background.Bg_url;
import com.solusindo.kreativa.companyprofilekreativaadmin.interfaces.AsyncResponse;
import com.solusindo.kreativa.companyprofilekreativaadmin.table.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoActivity extends YouTubeBaseActivity {
    public static final String API_KEY = "AIzaSyDnzZHwUEcMg9ET0W2B47eO4-iMNNClT94";
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
//    public String VIDEO_ID = "https://www.youtube.com/watch?v=GdTLMQKuOVI";
    public String VIDEO_ID;
    String[] str_split;
    String video_url;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
//    private List<Profil> lstData;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayerFragment youTubePlayerFragment;
    YouTubePlayer myouTubePlayer;
    LinkDatabase linkDatabase;
    TextInputEditText video;
    VideoView playVideo;
    MediaController mediaC;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video = (TextInputEditText)findViewById(R.id.ET_video_link);
        youTubePlayerFragment = YouTubePlayerFragment.newInstance();
        VIDEO_ID = new String();
        str_split = new String[10];
        video_url = new String();
        linkDatabase = new LinkDatabase();
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.YV_video);
        geturl();
        youTubePlayersetup();
//        youTubePlayerView.initialize(API_KEY, this);
//        youTubePlayerFragment.initialize(API_KEY, this);

    }

    private void geturl() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url      =   linkDatabase.linkurl()+"url.php?operasi=view_video";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    video_url = jsonObject.getString("URL_VIDEO_PROFIL");
                    VIDEO_ID = video_url;
                    progressDialog.dismiss();
//                    Toast.makeText(getBaseContext(), video_url.toString(), Toast.LENGTH_LONG).show();
                    video.setText(video_url);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }
        );
        requestQueue    =   Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);
    }

    public void onPreview(View view) {
        try {
            String str_link = video.getText().toString();
            if(str_link.length()==11){
                VIDEO_ID = str_link;
            }else {
                str_split = str_link.split("/");
                if (str_split[0].length()<11){
                    if(str_split[2].length()>9){
                        String split = str_split[3];
                        VIDEO_ID = split.substring(8, 19);
                    }else if(str_split[2].toLowerCase().equals("youtu.be")){
                        String split = str_split[3];
                        VIDEO_ID = split.substring(0, 11);
                    }
                }
            }

//            Toast.makeText(getApplicationContext(), VIDEO_ID.toString(), Toast.LENGTH_LONG).show();
//            youTubePlayerView.initialize(API_KEY, this);
            myouTubePlayer.cueVideo(VIDEO_ID);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
//        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(link)));
//        Log.i("Video", "Video Playing....");


    }

    public void onDelete(View view) {
        video.setText("");
    }

    public void onBack(View view) {
        finish();
    }

    private void youTubePlayersetup(){
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
//            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
//            youTubePlayer.cuePlaylist(VIDEO_ID);
                    myouTubePlayer = youTubePlayer;
                    myouTubePlayer.loadVideo(VIDEO_ID);
                    myouTubePlayer.cueVideo(VIDEO_ID);
//            player.cueVideo(VIDEO_ID);
//            youTubePlayer = player;
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), "Failured to Initialize!", Toast.LENGTH_LONG).show();
            }
        };
        youTubePlayerView.initialize(API_KEY, mOnInitializedListener);
    }

//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
//        player.setPlayerStateChangeListener(playerStateChangeListener);
//        player.setPlaybackEventListener(playbackEventListener);
//
//        /** Start buffering **/
//        if (!b) {
////            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
////            youTubePlayer.cuePlaylist(VIDEO_ID);
//            player.loadVideo(VIDEO_ID);
////            player.cueVideo(VIDEO_ID);
////            youTubePlayer = player;
//        }
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
//    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };

//    @Override
//    public void processfinish(String output) {
//        if(output.equals("Update Video Berhasil")){
//            Toast.makeText(getApplicationContext(), "Link Berhasil Di Simpan", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
//        }
//    }

    public void onYoutube(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v="+VIDEO_ID)));
        Log.i("Video", "Video Playing....");
    }

    public void onSimpann(View view) {
//        String type = "update_video";
//        Bg_url bg = new Bg_url(this);
//        bg.delegate= this;
//        bg.execute(type, VIDEO_ID);

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"url.php?operasi=update_video";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if(response.toLowerCase().toString().equals("update video berhasil")){
                    Toast.makeText(getApplicationContext(), "Link Berhasil Di Simpan", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getBaseContext(), response.toString(), Toast.LENGTH_LONG).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("URL_VIDEO_PROFIL", VIDEO_ID);
                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
