package com.eyeem.theroll;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.eyeem.theroll.widgets.TimeOfADay;

public class Dashboard extends Activity {

   ScrollView scrollView;
   LinearLayout ll;
   TimeOfADay timeOfADay;

   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      scrollView = new ScrollView(this);
      ll = new LinearLayout(this);
      ll.setOrientation(LinearLayout.VERTICAL);
      scrollView.addView(ll, -1, -1);

      timeOfADay = new TimeOfADay(this);
      timeOfADay.testSetup();

      TextView tv = new TextView(this);
      tv.setText("TEST");

      ll.addView(timeOfADay, -1, getResources().getDimensionPixelSize(R.dimen.graph_height));
      ll.addView(tv, -1, -2);

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
