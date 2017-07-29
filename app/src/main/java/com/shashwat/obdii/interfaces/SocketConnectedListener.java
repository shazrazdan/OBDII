package com.shashwat.obdii.interfaces;

import android.bluetooth.BluetoothSocket;

/**
 * Created by Shashwat on 15/04/17.
 */

public interface SocketConnectedListener {

    void onSocketConnectionComplete(BluetoothSocket socket);

}
