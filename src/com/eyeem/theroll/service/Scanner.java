package com.eyeem.theroll.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.eyeem.theroll.utils.Log;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/6/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Scanner extends Service {

   public final static String ACTION_SCAN = "scan_files";

   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }

   // This is the old onStart method that will be called on the pre-2.0
   // platform.  On 2.0 or later we override onStartCommand() so this
   // method will not be called.
   @Override
   public void onStart(Intent intent, int startId) {
      handleCommand(intent);
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      handleCommand(intent);
      // We want this service to continue running until it is explicitly
      // stopped, so return sticky.
      return START_STICKY;
   }

   public void handleCommand(Intent intent) {
      if (intent != null && ACTION_SCAN.equals(intent.getAction())) {
         scanPhotos();
      }
   }

   public void scanPhotos() {
      // TODO scan photos here
      Log.i(this, "scanPhotos");
   }
}
