package com.eyeem.theroll;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.eyeem.storage.Storage;
import com.eyeem.theroll.storage.PhotoStorage;
import com.eyeem.theroll.widgets.Cities;
import com.eyeem.theroll.widgets.ColorPie;
import com.eyeem.theroll.widgets.TimeOfADay;

public class Dashboard extends Activity implements Storage.Subscription {

   ScrollView scrollView;
   LinearLayout ll;
   Handler handler;

   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      handler = new Handler();
      scrollView = new ScrollView(this);
      ll = new LinearLayout(this);
      ll.setOrientation(LinearLayout.VERTICAL);
      scrollView.addView(ll, -1, -1);
      scrollView.setBackgroundColor(getResources().getColor(R.color.bg));
      setContentView(scrollView);
      /*mChartView.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            // handle the click event on the chart
            SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
            if (seriesSelection == null) {
               Toast.makeText(Dashboard.this, "No chart element", Toast.LENGTH_SHORT).show();
            } else {
               // display information of the clicked point
               Toast.makeText(
                  Dashboard.this,
                  "Chart element in series index " + seriesSelection.getSeriesIndex()
                     + " data point index " + seriesSelection.getPointIndex() + " was clicked"
                     + " closest point value X=" + seriesSelection.getXValue() + ", Y="
                     + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
            }
         }
      });*/
   }

   @Override
   protected void onResume() {
      super.onResume();
      PhotoStorage.all.subscribe(this);
      onUpdate(null);
   }

   @Override
   protected void onPause() {
      super.onPause();
      PhotoStorage.all.unsubscribe(this);
   }

   @Override
   public void onUpdate(Action action) {
      handler.post(new Runnable() {
         @Override
         public void run() {;
            ll.removeAllViews();
            TimeOfADay timeOfADay = new TimeOfADay(getBaseContext());
            Cities cities = new Cities(getBaseContext());
            ColorPie colorPie = new ColorPie(getBaseContext());

            int h = getResources().getDimensionPixelSize(R.dimen.graph_height);
            ll.addView(timeOfADay, -1, h);
            ll.addView(cities, -1, h);
            ll.addView(colorPie, -1, h);

            timeOfADay.setValues(PhotoStorage.daysStats);
            timeOfADay.repaint();
            timeOfADay.invalidate();

            cities.setupValues(PhotoStorage.cityStats);
            cities.repaint();
            cities.invalidate();

            colorPie.setupValues(PhotoStorage.colorStats);
            colorPie.repaint();
            colorPie.invalidate();
         }
      });
   }
}
