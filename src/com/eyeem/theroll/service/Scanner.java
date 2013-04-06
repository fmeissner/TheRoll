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
import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.color.bean.ColorResult;
import net.jakobnielsen.imagga.color.bean.ExtendedColor;
import net.jakobnielsen.imagga.color.client.ColorAPIClient;
import net.jakobnielsen.imagga.upload.client.UploadClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
         t.start();
      }
   }

   private void scanPhotos() {
      // TODO scan photos here
      Log.i(this, "scanPhotos");
      ContentResolver cr = getContentResolver();
      Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
      cursor.moveToPosition(-1);
      int processedCount = 0;
      while (cursor.moveToNext()) {
         String id = String.valueOf(cursor.getInt((cursor.getColumnIndex(MediaStore.Images.Media._ID))));
         Photo photo = process(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)), id);
         if (photo != null) {
            storage.push(photo);
         }
         processedCount++;
         if (processedCount > 2) {
            // TMP
            break;
         }
      }
      cursor.close();
   }

   private Photo process(String filePath, String id) {
      Log.i(this, "process("+filePath+")");
      Photo photo = new Photo();
      photo.id = id;
      photo.filePath = filePath;
      try {
         photo.colors = colorQuery(filePath);
      } catch (Throwable e) {
         Log.e(this, "colorQuery("+filePath+") error", e);
      }
      return photo;
   }

   APIClientConfig IMAGA_CONFIG = new APIClientConfig("acc_b6c25fc6", "7c0765b6ce790023f06cfa18a7b47790", "78.128.78.162");

   private ArrayList<Integer> colorQuery(String filePath) throws IOException {
      ColorAPIClient client = new ColorAPIClient(IMAGA_CONFIG);

      UploadClient uploadClient = new UploadClient(IMAGA_CONFIG);

      List<ColorResult> colorResults = client.colorsByUploadCode(uploadClient.uploadForProcessing(new File(filePath)));
      ArrayList<Integer> colorsInt = new ArrayList<Integer>();
      for (ColorResult res : colorResults) {
         for (ExtendedColor color : res.getInfo().getImageColors()) {
            Log.i(this, "colorQuery()"+color.getHtmlCode());
            colorsInt.add(Integer.parseInt(color.getHtmlCode().replaceAll("#", ""), 16));
         }
      }
      return colorsInt;
   }
}
