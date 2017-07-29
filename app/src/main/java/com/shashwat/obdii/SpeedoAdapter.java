package com.shashwat.obdii;

/**
 * Created by Shashwat on 27/07/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.DecoDrawEffect;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.HashMap;

/**
 * Created by Shashwat on 18/07/2016.
 */
public class SpeedoAdapter extends PagerAdapter {
    Handler handler;
    int count;
    SeriesItem seriesItem1;
    int secondaryArc;
    SeriesItem primaryArc;
    int series3Index;
    DecoView arcView;
    SparseArray<View> views = new SparseArray<View>();

    // Common
    Context context;
    ViewGroup layout;
    Integer[] initialValues;




    Integer[] colorArrayTotal = {0xFFC8E6C9, 0xFFFFF9C4, 0xFFFFCCBC};
    Integer[] colorArrayCorrect = {0xFF1B5E20, 0xFFFDD835, 0xFFBF360C};


    public SpeedoAdapter(Context ctx, int count, Integer[] initialValues) {
        context = ctx;
        this.count=count;
        this.initialValues = initialValues;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (position<4) {
            layout = (ViewGroup) inflater.inflate(R.layout.layout_deco,
                    container, false);
            CreateDecoView(position);
        } else {

        }
        container.addView(layout);
        views.put(position,layout);

        return layout;

    }


    private void CreateDecoView(int position) {

            arcView = (DecoView) layout.findViewById(R.id.dynamicArcView);


            arcView.configureAngles(270, 0);

            seriesItem1 = new SeriesItem.Builder(colorArrayTotal[0])
                    .setRange(0, 360, 360)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setLineWidth(32f)
                    .build();

            secondaryArc = arcView.addSeries(seriesItem1);


            primaryArc = new SeriesItem.Builder(colorArrayCorrect[0])
                    .setRange(0, 360, 0)
                    .setLineWidth(60f)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .build();

            series3Index = arcView.addSeries(primaryArc);

            arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                    .setDelay(00)
                    .setDuration(50)
                    .build());

            int i = (int) (Math.random()*3.0);

            arcView.addEvent(new DecoEvent.Builder(initialValues[position]).setIndex(series3Index).setColor(colorArrayCorrect[i]).setDelay(000).setDuration(200).build());
            //arcView.addEvent(new DecoEvent.Builder(360).setIndex(secondaryArc).setColor(0xFF9E9E9E).setDelay(000).setDuration(500).build());


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
                return "Revs";
            case 1:
                return "Speed";
            case 2:
                return "Throttle";
        }
        return super.getPageTitle(position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void notifyPrimaryChanged(int pos, int value) {

        View layout = views.get(pos);
        DecoView arcView = (DecoView) layout.findViewById(R.id.dynamicArcView);
        arcView.addEvent(new DecoEvent.Builder(value*360/8000).setIndex(series3Index).setColor(colorArrayCorrect[(value/3000)]).setDelay(000).setDuration(500).build());



        notifyDataSetChanged();
    }
}

