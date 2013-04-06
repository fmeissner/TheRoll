package com.eyeem.theroll.storage;

import android.content.Context;
import com.eyeem.storage.Storage;
import com.eyeem.theroll.model.Photo;

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
      }
   }

   public static PhotoStorage getInstance() {
      return sInstance;
   }
}
