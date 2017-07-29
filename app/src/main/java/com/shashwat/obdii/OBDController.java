package com.shashwat.obdii;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Shashwat on 28/07/17.
 */

public class OBDController {


    static ArrayList<Integer> buffer = new ArrayList<>();
    static StringBuffer sb = new StringBuffer();
    static BluetoothSocket socket = null;

    public static void setSocket(BluetoothSocket sock){
        socket = sock;
    }


    public static int sendCommand(String cmd) {
        try {
            socket.getOutputStream().write((cmd + "\r").getBytes());
            socket.getOutputStream().flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveResult(socket);
    }

    public static int receiveResult(BluetoothSocket socket){
        int responseInt = 0, temp = 0;
        String rawData = "";

        try{
            InputStream in = socket.getInputStream();
            byte b = 0;
            StringBuilder res = new StringBuilder();

            // read until '>' arrives OR end of stream reached
            char c;
            // -1 if the end of the stream is reached
            while (((b = (byte) in.read()) > -1)) {
                c = (char) b;
                if (c == '>') // read until '>' arrives
                {
                    break;
                }
                res.append(c);
            }
            rawData = res.toString().replaceAll("SEARCHING", "");
            rawData = rawData.replaceAll("\\s", "");//removes all [ \t\n\x0B\f\r]


            rawData = rawData.replaceAll("(BUS INIT)|(BUSINIT)|(\\.)", "");



            // read string each two chars



            buffer.clear();
            sb.delete(0,sb.length());
            int begin = 8;
            int end = rawData.length();
            /*while (end <= rawData.length()) {
                temp = Integer.decode("0x" + rawData.substring(begin, end));
                if(begin>7){
                    responseInt*=100;
                    responseInt+=temp;
                    sb.append(rawData.substring(begin, end));
                    Log.e("---", "added: " + temp);
                }
                buffer.add(temp);
                begin = end;
                end += 2;
            }*/
            responseInt = Integer.decode("0x"+rawData.substring(begin, end));

            Log.e("Output ", responseInt+" , " + responseInt/4);






        }catch (Exception e){
            e.printStackTrace();
        }
        return  responseInt/4;
    }


}
