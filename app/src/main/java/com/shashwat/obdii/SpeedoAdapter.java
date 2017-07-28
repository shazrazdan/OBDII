package com.shashwat.obdii;

/**
 * Created by Shashwat on 27/07/17.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shashwat on 18/07/2016.
 */
public class SpeedoAdapter extends PagerAdapter {
    Handler handler;
    int count;



    // Common
    Context context;
    ViewGroup layout;




    Integer[] colorArrayTotal = {0xFFC8E6C9, 0xFFFFF9C4, 0xFFFFCCBC};
    Integer[] colorArrayAttempted = {0xFF66BB6A, 0xFFFFEE58, 0xFFFF7043};
    Integer[] colorArrayCorrect = {0xFF1B5E20, 0xFFFDD835, 0xFFBF360C};

    public static HashMap<String, Bundle> graphData;

    public SpeedoAdapter(Context ctx, int count) {
        context = ctx;
        this.count=count;

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        layout = (ViewGroup) inflater.inflate(R.layout.layout_deco,
                container, false);

        handler = new Handler();
        if (position == 0) {

            alpha a = new alpha();
            a.CreateDecoView(position);


        }else if (position == 1) {

            alpha b = new alpha();
            b.CreateDecoView(position);


        } else if (position == 2) {
            alpha b = new alpha();
            b.CreateDecoView(position);
        }
        container.addView(layout);
        return layout;

    }


    class alpha{
        SeriesItem seriesItem1;
        int series1Index;
        SeriesItem seriesItem3;
        int series3Index;
        DecoView arcView;


        private void CreateDecoView(int position) {

            arcView = (DecoView) layout.findViewById(R.id.dynamicArcView);


// Create background track
            /*arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                    .setRange(0, 360, 360)
                    .setInitialVisibility(false)
                    .setLineWidth(0f)
                    .build());*/

//Create data series track
            seriesItem1 = new SeriesItem.Builder(colorArrayTotal[0])
                    .setRange(0, 360, 0)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setLineWidth(32f)
                    .build();

            series1Index = arcView.addSeries(seriesItem1);
/*
            seriesItem2 = new SeriesItem.Builder(colorArrayAttempted[0])
                    .setRange(0, 360, 0)
                    .setLineWidth(0f)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setCapRounded(true)
                    .build();

            series2Index = arcView.addSeries(seriesItem2);
*/

            seriesItem3 = new SeriesItem.Builder(colorArrayCorrect[0])
                    .setRange(0, 360, 0)
                    .setLineWidth(60f)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .build();

            series3Index = arcView.addSeries(seriesItem3);

            arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                    .setDelay(00)
                    .setDuration(50)
                    .build());

            int i = (int) (Math.random()*3.0);
            if (position==0) {

                arcView.addEvent(new DecoEvent.Builder(30*360/100).setIndex(series3Index).setColor(colorArrayCorrect[i]).setDelay(000).setDuration(500).build());
                arcView.addEvent(new DecoEvent.Builder(100*360/100).setIndex(series1Index).setColor(0xFF9E9E9E).setDelay(000).setDuration(500).build());
            } else if(position==1){

                arcView.addEvent(new DecoEvent.Builder(40*360/100).setIndex(series3Index).setColor(colorArrayCorrect[i]).setDelay(000).setDuration(50).build());
                arcView.addEvent(new DecoEvent.Builder(100*360/100).setIndex(series1Index).setColor(0xFF9E9E9E).setDelay(000).setDuration(50).build());
            } else if(position==2){

                arcView.addEvent(new DecoEvent.Builder(80*360/100).setIndex(series3Index).setColor(colorArrayCorrect[i]).setDelay(000).setDuration(50).build());
                arcView.addEvent(new DecoEvent.Builder(100*360/100).setIndex(series1Index).setColor(0xFF9E9E9E).setDelay(000).setDuration(50).build());
            }


            arcView.configureAngles(270, 0);


        }


    }

    @Override
    public int getCount() {
        return count;
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "  Revs  ";
            case 1:
                return "  Speed  ";
            case 2:
                return "  Throttle  ";
        }
        return super.getPageTitle(position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}

