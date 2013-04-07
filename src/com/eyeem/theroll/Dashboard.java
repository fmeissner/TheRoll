package com.eyeem.theroll;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.eyeem.storage.Storage;
import com.eyeem.theroll.storage.PhotoStorage;
import com.eyeem.theroll.widgets.Cities;
import com.eyeem.theroll.widgets.ColorPie;
import com.eyeem.theroll.widgets.TimeOfADay;
import org.achartengine.model.SeriesSelection;

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
            final TimeOfADay timeOfADay = new TimeOfADay(getBaseContext());
            final Cities cities = new Cities(getBaseContext());
            final ColorPie colorPie = new ColorPie(getBaseContext());

            int h = getResources().getDimensionPixelSize(R.dimen.graph_height);
            ll.addView(timeOfADay, -1, h);
            ll.addView(cities, -1, h);
            ll.addView(colorPie, -1, h);

            timeOfADay.setValues(PhotoStorage.daysStats);
            timeOfADay.repaint();
            timeOfADay.invalidate();
            timeOfADay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  SeriesSelection seriesSelection = timeOfADay.getCurrentSeriesAndPoint();
                  if (seriesSelection == null) {
                     //Toast.makeText(Dashboard.this, "No chart element", Toast.LENGTH_SHORT).show();
                  } else {
                     // display information of the clicked point
                     GridActivity.startWithQuery(null,
                        new String[] {"Morning", "Afternoon", "Evening", "Night"} [(int)seriesSelection.getXValue()-1],
                        null,
                        Dashboard.this);
                     /*Toast.makeText(
                        Dashboard.this,
                        "Chart element in series index " + seriesSelection.getSeriesIndex()
                           + " data point index " + seriesSelection.getPointIndex() + " was clicked"
                           + " closest point value X=" + seriesSelection.getXValue() + ", Y="
                           + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();*/
                  }
               }
            });

            cities.setupValues(PhotoStorage.cityStats);
            cities.repaint();
            cities.invalidate();
            cities.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  SeriesSelection seriesSelection = cities.getCurrentSeriesAndPoint();
                  if (seriesSelection == null) {
                     //Toast.makeText(Dashboard.this, "No chart element", Toast.LENGTH_SHORT).show();
                  } else {
                     // display information of the clicked point
                     GridActivity.startWithQuery(cities.inOrder.get((int)seriesSelection.getXValue()),
                        null,
                        null,
                        Dashboard.this);
                  }
               }
            });

            colorPie.setupValues(PhotoStorage.colorStats);
            colorPie.repaint();
            colorPie.invalidate();
         }
      });
   }
}
