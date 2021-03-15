package com.example.assignment3;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class timezoneservice  extends Service {

    String line;
    JSONArray jsonarr;
    IDAO dao;

    public void onCreate() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this,"Service starting",Toast.LENGTH_SHORT).show();
        Log.println(Log.DEBUG,"service****","in service start");
        dao = new DbDAO(this);
        Thread thread = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                gettimezoneinfo();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            parseJsonData(jsonarr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void parseJsonData(JSONArray arr) throws JSONException {
        for (int i = 0; i < arr.length(); ++i) {

            JSONObject jsn = arr.getJSONObject(i);

            String keyVal = jsn.getString("zoneName");

            Cites c = new Cites(keyVal);
            Cites d = new Cites();
            d.connect(c,dao);
            d.save1();

            Log.println(Log.DEBUG,"service",keyVal);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void gettimezoneinfo() {
        try{
            URL url = new URL("https://api.timezonedb.com/v2.1/list-time-zone?key=OSL2DR7N24HS&format=json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            Log.println(Log.DEBUG,"service","Looking for Connection*****");
            connection.connect();
            Log.println(Log.DEBUG,"service","Connection Established");
            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream() ) );
            while( (line = reader.readLine()) != null ){
                content.append(line);
            }
            line = content.toString();
            JSONObject obj = new JSONObject(line);
            jsonarr = obj.getJSONArray("zones");
            Log.println(Log.DEBUG,"service", "data downloaded");

        } catch(Exception ex) {
            ex.printStackTrace();
            Log.println(Log.DEBUG, "service", "Error");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}