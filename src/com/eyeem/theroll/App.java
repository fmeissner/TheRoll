package com.eyeem.theroll;

import android.app.Application;
import android.content.Intent;
import com.eyeem.theroll.service.Scanner;

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

      // start scanning
      Intent scanIntent = new Intent(this, Scanner.class);
      scanIntent.setAction(Scanner.ACTION_SCAN);
      startService(scanIntent);
   }
}
