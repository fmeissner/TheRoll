package com.eyeem.theroll.storage;

import android.content.Context;
import com.eyeem.storage.Storage;
import com.eyeem.theroll.model.Photo;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/6/13
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoStorage extends Storage<Photo> {
   private static PhotoStorage sInstance = null;

   public PhotoStorage(Context context) {
      super(context);
   }

   @Override
   public String id(Photo object) {
      return object.id;
   }

   @Override
   public Class<Photo> classname() {
      return Photo.class;
   }

   public static void initialize(Context ctx) {
      if (sInstance == null) {
         sInstance = new PhotoStorage(ctx);
         sInstance.init(1000);
         all().setTrimSize(1000);
      }
   }

   public static PhotoStorage getInstance() {
      return sInstance;
   }

   public static List all() {
      return sInstance.obtainList("all");
   }

   public int[] daysStats = {0, 0, 0, 0};
   public HashMap<String, Integer> cityStats = new HashMap<String, Integer>();
   public HashMap<String, Integer> colorStats = new HashMap<String, Integer>();

   @Override
   protected void addOrUpdate(String id, Photo object) {
      // daysStats
      if ("Morning".equals(object.timeOfDay)) {
         daysStats[0] = daysStats[0]+1;
      } else if ("Afternoon".equals(object.timeOfDay)) {
         daysStats[1] = daysStats[1]+1;
      } else if ("Evening".equals(object.timeOfDay)) {
         daysStats[2] = daysStats[2]+1;
      } else if ("Night".equals(object.timeOfDay)) {
         daysStats[3] = daysStats[3]+1;
      }

      // cityStats
      String cityName = object.city;
      if (cityName != null) {
         Integer count = cityStats.get(cityName);
         if (count == null) {
            count = 0;
         } else {
            count++;
         }
         cityStats.put(cityName, count);
      }

      // colorStats
      for (String colorName : object.colors) {
         if (colorName != null) {
            Integer count = colorStats.get(colorName);
            if (count == null) {
               count = 0;
            } else {
               count++;
            }
            colorStats.put(colorName, count);
         }
      }
      super.addOrUpdate(id, object);
   }
}
