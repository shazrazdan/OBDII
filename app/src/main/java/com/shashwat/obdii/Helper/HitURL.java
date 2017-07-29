package com.shashwat.obdii.Helper;

import android.os.AsyncTask;
import android.util.Log;

import com.shashwat.obdii.interfaces.OnURLDataFetchCompleteListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Shashwat on 15/04/17.
 */

public class HitURL extends AsyncTask<String, String, String> {

    OnURLDataFetchCompleteListener onURLDataFetchCompleteListener;
    String TAG = "HitURL";

    String param0;

    HitURL(String s){
        param0 = s;
    }

    public void setOnURLDataFetchCompleteListener(OnURLDataFetchCompleteListener onURLDataFetchCompleteListener) {
        this.onURLDataFetchCompleteListener = onURLDataFetchCompleteListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String uri = param0;


        BufferedReader bufferedReader;
        HttpURLConnection con = null;
        try {
            URL url = new URL(uri);
            this.publishProgress(TAG,url.toString());

            con = (HttpURLConnection) url.openConnection();
            con.connect();
            if (con.getResponseCode() == 201 || con.getResponseCode() == 200) {
                Log.d(TAG, "true");
            }
            StringBuilder sb = new StringBuilder("");
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            }
            con.disconnect();
            bufferedReader.close();
            return sb.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "exception";
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(onURLDataFetchCompleteListener!=null){
            onURLDataFetchCompleteListener.onURLDataFetchComplete(s);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

}
