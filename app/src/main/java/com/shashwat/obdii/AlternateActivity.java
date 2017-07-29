package com.shashwat.obdii;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class AlternateActivity extends AppCompatActivity {

    private static TextView logBox;
    private static ScrollView scrollView;
    static boolean refresh = true;
    boolean execute = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                execute = true;
                try{
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                if(refresh)
                    logBox.setText("");
                EditText input = (EditText) findViewById(R.id.editText);
                String val = input.getText().toString();
                if(val.length()==0) {
                    AddLog("No input provided", "Using default as RPM");
                    val = "010C";
                }

                OBDControllerAsync as = new OBDControllerAsync();
                as.execute(val);


            }
        });


        logBox = (TextView) findViewById(R.id.logbox);
        scrollView = (ScrollView) findViewById(R.id.scrollview);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            logBox.setText("");
            return true;
        } else if (id == R.id.action_switch) {
            if(refresh)
                item.setTitle("Append Mode");
            else
                item.setTitle("Refresh Mode");
            refresh=!refresh;
            return true;
        } else if(id == R.id.action_stop){
            execute = false;
            Log.e("Pressed ", "Stop");
        }

        return super.onOptionsItemSelected(item);
    }

    public static void AddLog(final String title, final String Message){

        if (title.length()>0) {
            logBox.append(title + ": " + Message + "\n\n");
        } else {
            logBox.append(Message + "\n\n");

        }
        //scrollView.fullScroll(View.FOCUS_DOWN);

        scrollView.post(new Runnable() {
            @Override
            public void run() {

                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        Log.e(title,Message);
    }

}
