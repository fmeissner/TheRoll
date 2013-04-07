package com.eyeem.theroll;

import android.app.Application;
import android.content.Intent;
import android.graphics.BitmapFactory;
import com.eyeem.storage.Storage;
import com.eyeem.theroll.service.Scanner;
import com.eyeem.theroll.storage.PhotoStorage;
import org.achartengine.chart.ScatterChart;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/6/13
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class App extends Application {
   public static App the;

   @Override
   public void onCreate() {
      super.onCreate();
      the = this;

      // initialize storage
      PhotoStorage.initialize(this);

      // first load storage, then start scanning
      PhotoStorage.all.subscribe(new Storage.Subscription() {
         @Override
         public void onUpdate(Action action) {
            if (action.name.equals(Storage.Subscription.LOADED)) {
               Intent scanIntent = new Intent(the, Scanner.class);
               scanIntent.setAction(Scanner.ACTION_SCAN);
               startService(scanIntent);
            }
         }
      });
      PhotoStorage.all.load();

      ScatterChart.circle = BitmapFactory.decodeResource(getResources(), R.drawable.point);
   }
}
