package com.eyeem.theroll.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
      final String[] columns = {
              //MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DATE_TAKEN,
              //MediaStore.Images.Media.SIZE, MediaStore.Images.Media.LATITUDE, MediaStore.Images.Media.LONGITUDE, "width", "height"
      };
      Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
      cursor.moveToPosition(-1);
      int processedCount = 0;
      while (cursor.moveToNext()) {
         String id = String.valueOf(cursor.getInt((cursor.getColumnIndex(MediaStore.Images.Media._ID))));
         String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
         Photo photo = process(filePath, id);
         Log.i(this,"filePath: "+photo.filePath);
         /*String width = cursor.getString(cursor.getColumnIndex("width"));
         String height = cursor.getString(cursor.getColumnIndex("height"));
         if(width!=null)
            photo.width = Integer.parseInt(width);
         if(height!=null)
            photo.height = Integer.parseInt(height);*/
         if (photo != null) {
            storage.push(photo);
         }
         processedCount++;
         if (processedCount > 150) {
            // TMP
            break;
         }
      }
      cursor.close();
   }

   private Photo process(String filePath, String id) {
      Log.i(this, "scanPhotos process("+filePath+")");
      Photo photo = new Photo();
      photo.id = id;
      photo.filePath = filePath;
      try {
         //GET basic metadata (lat, lng, width, height, timestamp)
         ExifInterface newExif=null;
         newExif = new ExifInterface(filePath);
         if(newExif.getAttribute(ExifInterface.TAG_GPS_LATITUDE) != null && newExif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) != null){
            try{
               photo.lat = ExifLatLong(newExif.getAttribute(ExifInterface.TAG_GPS_LATITUDE),newExif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
               photo.lon = ExifLatLong(newExif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE),newExif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
               Geocoder gcd = new Geocoder(this, Locale.getDefault());
               List<Address> addresses = gcd.getFromLocation(photo.lat, photo.lon, 1);
               if (addresses.size() > 0){
                  photo.city = addresses.get(0).getLocality();
                  photo.country = addresses.get(0).getCountryName();
               }
               getTimeOfDay(newExif.getAttribute(ExifInterface.TAG_DATETIME), photo);
               if(photo.width==0){
                  photo.width = Integer.parseInt(newExif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
               }
               if(photo.height==0){
                  photo.height = Integer.parseInt(newExif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
               }
            }catch (Exception e) {
               // TODO: handle exception
            }
         }
         //GET inferred data (day, time of day, city, country)
      } catch (Throwable e){

      }
      try {
         photo.colors = colorQuery(filePath);
      } catch (Throwable e) {
         Log.e(this, "scanPhotos colorQuery("+filePath+") error", e);
      }
      printPhoto(photo);
      return photo;
   }

   APIClientConfig IMAGA_CONFIG = new APIClientConfig("acc_b6c25fc6", "7c0765b6ce790023f06cfa18a7b47790", "78.128.78.162");

   private ArrayList<String> colorQuery(String filePath) throws IOException {
      ColorAPIClient client = new ColorAPIClient(IMAGA_CONFIG);

      UploadClient uploadClient = new UploadClient(IMAGA_CONFIG);

      List<ColorResult> colorResults = client.colorsByUploadCode(uploadClient.uploadForProcessing(new File(filePath)));
      ArrayList<String> colorsInt = new ArrayList<String>();
      for (ColorResult res : colorResults) {
         for (ExtendedColor color : res.getInfo().getImageColors()) {
            Log.i(this, "scanPhotos closest parent: "+color.getClosestPaletteColorParent()+" w/ percent:"+color.getPercent());
            if(color.getPercent()>20.0){
               //TODO: this might actually be adding the same parent twice based on the child.
               colorsInt.add(color.getClosestPaletteColorParent());
               Log.i(this, "scanPhotos adding color: "+color.getClosestPaletteColorParent());
            }
         }
      }
      return colorsInt;
   }

   public float ExifLatLong(String value, String ref) throws Exception {
      Float result = null;
      String[] DMS = value.split(",", 3);

      String[] stringD = DMS[0].split("/", 2);
      Double D0 = Double.valueOf(stringD[0]);
      Double D1 = Double.valueOf(stringD[1]);
      Double FloatD = D0 / D1;

      String[] stringM = DMS[1].split("/", 2);
      Double M0 = Double.valueOf(stringM[0]);

      Double M1 = Double.valueOf(stringM[1]);
      Double FloatM = M0 / M1;

      String[] stringS = DMS[2].split("/", 2);
      Double S0 = Double.valueOf(stringS[0]);
      Double S1 = Double.valueOf(stringS[1]);
      Double FloatS = S0 / S1;

      result = new Float(FloatD + (FloatM / 60) + (FloatS / 3600));

      if (ref.equals("N") || ref.equals("E")) {
         return result;
      } else {
         return 0 - result;
      }
   }

   public void getTimeOfDay(String dateTime,Photo photo){
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
      Date convertedDate = new Date();
      try {
         convertedDate = dateFormat.parse(dateTime);
      } catch (ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      dateFormat.applyPattern("EEEE");
      photo.day = dateFormat.format(convertedDate);
      dateFormat.applyPattern("HH");
      int hour = Integer.parseInt(dateFormat.format(convertedDate));
      if(hour >=0 && hour <= 6)
         photo.timeOfDay = "Night";
      else if(hour >6 && hour < 12)
         photo.timeOfDay = "Morning";
      else if(hour >=12 && hour < 18)
         photo.timeOfDay = "Afternoon";
      else
         photo.timeOfDay = "Evening";
   }

   public void printPhoto(Photo p){
      Log.i(this,"scanPhoto width:"+p.width);
      Log.i(this,"scanPhoto height:"+p.height);
      Log.i(this,"scanPhoto lat:"+p.lat);
      Log.i(this,"scanPhoto lon:"+p.lon);
      Log.i(this,"scanPhoto city:"+p.city);
      Log.i(this,"scanPhoto country:"+p.country);
      Log.i(this,"scanPhoto time:"+p.timeOfDay);
      Log.i(this,"scanPhoto day:"+p.day);
   }
}
