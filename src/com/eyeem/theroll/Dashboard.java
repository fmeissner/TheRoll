package com.eyeem.theroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.eyeem.theroll.widgets.Cities;
import com.eyeem.theroll.widgets.ColorPie;
import com.eyeem.theroll.widgets.TimeOfADay;

import java.util.HashMap;

public class Dashboard extends Activity {

   ScrollView scrollView;
   LinearLayout ll;
   TimeOfADay timeOfADay;
   Cities cities;

   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      scrollView = new ScrollView(this);
      ll = new LinearLayout(this);
      ll.setOrientation(LinearLayout.VERTICAL);
      scrollView.addView(ll, -1, -1);
      scrollView.setBackgroundColor(getResources().getColor(R.color.bg));

      timeOfADay = new TimeOfADay(this);
      timeOfADay.setValues(new int[]{23, 80, 55, 35});

      cities = new Cities(this);
      HashMap<String, Integer> testCities = new HashMap<String, Integer>();
      testCities.put("San Francisco", 124);
      testCities.put("Berlin", 44);
      testCities.put("London", 214);
      testCities.put("Copenhagen", 34);
      testCities.put("Łódź", 23);
      testCities.put("Babylos", 27);
      cities.setupValues(testCities);

      ColorPie colorPie = new ColorPie(this);
      HashMap<String, Integer> testColors = new HashMap<String, Integer>();
      testColors.put("red", 33);
      testColors.put("green", 33);
      testColors.put("blue", 33);
      testColors.put("magenta", 12);
      colorPie.setupValues(testColors);


      int h = getResources().getDimensionPixelSize(R.dimen.graph_height);
      ll.addView(timeOfADay, -1, h);
      ll.addView(cities, -1, h);
      ll.addView(colorPie, -1, h);

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
}
