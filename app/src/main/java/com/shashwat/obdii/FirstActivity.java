package com.shashwat.obdii;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

public class FirstActivity extends AppCompatActivity {

    Integer[] initialValues = {1,1,1};
    SpeedoAdapter speedoAdapter;
    PagerBullet viewPager;

    ArrayList deviceStrs = new ArrayList();
    final ArrayList devices = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                Set pairedDevices = btAdapter.getBondedDevices();
                if (pairedDevices.size() > 0)
                {
                    for (Iterator iterator = pairedDevices.iterator(); iterator.hasNext(); ) {
                        BluetoothDevice device = (BluetoothDevice) iterator.next();
                        deviceStrs.add(device.getName() + "\n" + device.getAddress());
                        devices.add(device.getAddress());
                    }
                }

                // show list
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(FirstActivity.this);

                ArrayAdapter adapter = new ArrayAdapter(FirstActivity.this, android.R.layout.select_dialog_singlechoice,
                        deviceStrs.toArray(new String[deviceStrs.size()]));

                alertDialog.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        String deviceAddress = devices.get(position).toString();
                        Log.e("Chose", deviceAddress);
                        // Calling an AsyncTask here.
                    }
                });

                alertDialog.setTitle("Choose Bluetooth device");
                alertDialog.show();
                //speedoAdapter.notifyPrimaryChanged(viewPager.getViewPager().getCurrentItem(),value);
            }
        });
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

        viewPager = (PagerBullet) findViewById(R.id.pagerBullet);


        speedoAdapter = new SpeedoAdapter(this, 3, initialValues);
        viewPager.setAdapter(speedoAdapter);
        viewPager.getViewPager().setOffscreenPageLimit(3);
        viewPagerTab.setDistributeEvenly(true);

        viewPagerTab.setViewPager(viewPager.getViewPager());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
