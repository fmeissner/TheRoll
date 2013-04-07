package com.eyeem.theroll.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.eyeem.storage.Storage;
import com.eyeem.theroll.GridActivity;
import com.eyeem.theroll.R;
import com.eyeem.theroll.storage.PhotoStorage;
import com.eyeem.theroll.widgets.Cities;
import com.eyeem.theroll.widgets.ColorPie;
import com.eyeem.theroll.widgets.TimeOfADay;
import org.achartengine.model.SeriesSelection;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 3/17/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MenuFragment extends SherlockFragment implements Storage.Subscription {

   ScrollView scrollView;
   LinearLayout ll;
   Handler handler;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle bundle) {
      handler = new Handler();
      scrollView = new ScrollView(getSherlockActivity());
      ll = new LinearLayout(getSherlockActivity());
      ll.setOrientation(LinearLayout.VERTICAL);
      scrollView.addView(ll, -1, -1);
      scrollView.setBackgroundColor(getResources().getColor(R.color.bg));
      return scrollView;
   }

   @Override
   public void onUpdate(Action action) {
      handler.post(new Runnable() {
         @Override
         public void run() {
            final Context ctx = getSherlockActivity();
            ll.removeAllViews();
            final TimeOfADay timeOfADay = new TimeOfADay(ctx);
            final Cities cities = new Cities(ctx);
            final ColorPie colorPie = new ColorPie(ctx);

            int h = getResources().getDimensionPixelSize(R.dimen.graph_height);
            ll.addView(prepareText("- LATEST", 0xff2cddd4));
            separator(ll);
            ll.addView(prepareText("- AROUND YOU", 0xff2cddd4));
            separator(ll);
            ll.addView(prepareText("TIME OF A DAY", 0xff555555));
            ll.addView(timeOfADay, -1, h);
            ll.addView(prepareText("CITIES", 0xff555555));
            ll.addView(cities, -1, h);
            ll.addView(prepareText("COLORS", 0xff555555));
            ll.addView(colorPie, -1, h);
            int p = (int)getSherlockActivity().getResources().getDimension(R.dimen.label_text_size);
            ll.setPadding(p, 0, p, 0);

            timeOfADay.setValues(PhotoStorage.daysStats);
            timeOfADay.repaint();
            timeOfADay.invalidate();
            timeOfADay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  final SeriesSelection seriesSelection = timeOfADay.getCurrentSeriesAndPoint();
                  if (seriesSelection == null) {
                     //Toast.makeText(Dashboard.this, "No chart element", Toast.LENGTH_SHORT).show();
                  } else {
                     // display information of the clicked point
                     PhotoStorage.TimeQuery q = new PhotoStorage.TimeQuery();
                     q.timeOfDay = new String[]{"Morning", "Afternoon", "Evening", "Night"}[(int) seriesSelection.getXValue() - 1];
                     GridActivity.setQuery(q);
                  }
               }
            });

            cities.setupValues(PhotoStorage.cityStats);
            cities.repaint();
            cities.invalidate();
            cities.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  final SeriesSelection seriesSelection = cities.getCurrentSeriesAndPoint();
                  if (seriesSelection == null) {
                     //Toast.makeText(Dashboard.this, "No chart element", Toast.LENGTH_SHORT).show();
                  } else {
                     // display information of the clicked point
                     PhotoStorage.CityQuery q = new PhotoStorage.CityQuery();
                     q.city = cities.inOrder.get((int)seriesSelection.getXValue());
                     GridActivity.setQuery(q);
                  }
               }
            });

            colorPie.setupValues(PhotoStorage.colorStats);
            colorPie.repaint();
            colorPie.invalidate();
            colorPie.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  SeriesSelection seriesSelection = colorPie.getCurrentSeriesAndPoint();
                  int index = seriesSelection.getPointIndex();
                  PhotoStorage.ColorQuery q = new PhotoStorage.ColorQuery();
                  q.color = colorPie.inOrder.get(index);
                  GridActivity.setQuery(q);
               }
            });
         }
      });
   }

   private TextView prepareText(String text, int color) {
      TextView tv = new TextView(getSherlockActivity());
      tv.setText(text);
      tv.setTypeface(Typeface.DEFAULT_BOLD);
      tv.setTextColor(color);
      //tv.setPadding((int)getSherlockActivity().getResources().getDimensionPixelSize(R.dimen.paddin_left_text), 0, 0, 0);
      return tv;
   }

   private void separator(LinearLayout ll) {
      View view  = new View(getSherlockActivity());
      view.setBackgroundColor(0xffc19c33);
      ll.addView(view, -1, 3);
   }

   @Override
   public void onResume() {
      super.onResume();
      PhotoStorage.all.subscribe(this);
      onUpdate(null);
   }

   @Override
   public void onPause() {
      super.onPause();
      PhotoStorage.all.unsubscribe(this);
   }
}
