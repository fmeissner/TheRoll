package com.eyeem.theroll.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.MediaStore;
import com.eyeem.theroll.model.Photo;
import com.eyeem.theroll.storage.PhotoStorage;
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
   public PhotoStorage storage;

   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }

   @Override
   public void onCreate() {
      super.onCreate();
      storage = PhotoStorage.getInstance();
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
         Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               scanPhotos();
            }
         });
         t.setPriority(Thread.MIN_PRIORITY);
         t.run();
      }
   }

   private void scanPhotos() {
      // TODO scan photos here
      Log.i(this, "scanPhotos");
      ContentResolver cr = getContentResolver();
      Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
      cursor.moveToPosition(-1);
      while (cursor.moveToNext()) {
         Photo photo = process(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
         if (photo != null) {
            storage.push(photo);
         }
      }
      cursor.close();
   }

   private Photo process(String filePath) {
      Log.i(this, "process("+filePath+")");
      return null;
   }
}
