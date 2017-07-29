package com.shashwat.obdii.async;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import com.shashwat.obdii.interfaces.SocketConnectedListener;

import java.util.UUID;

/**
 * Created by Shashwat on 15/04/17.
 */

public class ConnectDeviceAsyncTask extends AsyncTask<String, String, BluetoothSocket> {

    SocketConnectedListener socketConnectedListener;
    String TAG = "ConnectDeviceAsyncTask";

    public ConnectDeviceAsyncTask(){

    }

    public void setOnSocketConnectedListener(SocketConnectedListener socketConnectedListener) {
        this.socketConnectedListener = socketConnectedListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected BluetoothSocket doInBackground(String... params) {


        try {

            String deviceAddress = params[0];
            BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = btAdapter.getRemoteDevice(deviceAddress);
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
            return socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(BluetoothSocket s) {
        if(socketConnectedListener!=null){
            Log.e("Socket", "Connected");
            socketConnectedListener.onSocketConnectionComplete(s);
        }
        super.onPostExecute(s);

    }


}
