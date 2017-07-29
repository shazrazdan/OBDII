package com.shashwat.obdii;

import android.os.AsyncTask;

/**
 * Created by Shashwat on 29/07/17.
 */

public class OBDControllerAsync extends AsyncTask<String, String, Integer> {

    ProgressUpdateListener progressUpdateListener;

    @Override
    protected Integer doInBackground(String... params) {

        for (int i=0;i<40;i++) {
            this.publishProgress(params[0],String.valueOf(OBDController.sendCommand(params[0])));
        }


        return 0;

    }

    @Override
    protected void onProgressUpdate(String... values) {
        try {

            AlternateActivity.AddLog("Command ", values[0].toString());
            AlternateActivity.AddLog("Result  ", values[1].toString());

        }catch (Exception e){
            e.printStackTrace();
        }

        if (progressUpdateListener!=null) {
            progressUpdateListener.onProgressUpdate(Integer.parseInt(values[1]));
        }
        super.onProgressUpdate(values[0]);
    }

    public void setOnProgressUpdateListener(ProgressUpdateListener progressUpdateListener) {
        this.progressUpdateListener = progressUpdateListener;
    }
}
