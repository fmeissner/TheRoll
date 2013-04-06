package com.eyeem.theroll.utils;

public class Log {
   public final static String TAG = "TheRoll";
   public static boolean log = true;

   public static void w(Object object, String str) {
      if (log) android.util.Log.w(TAG, msg(object, str));
   }

   public static void e(Object object, String str) {
      if (log) android.util.Log.e(TAG, msg(object, str));
   }

   public static void e(Object object, String str, Throwable e) {
      if (log) android.util.Log.e(TAG, msg(object, str), e);
   }

   public static void i(Object object, String str) {
      if (log) android.util.Log.i(TAG, msg(object, str));
   }

   public static void d(Object object, String str) {
      if (log) android.util.Log.d(TAG, msg(object, str));
   }

   public static void v(Object object, String str) {
      if (log) android.util.Log.v(TAG, msg(object, str));
   }

   public static void d(Class klass, String str) {
      if (log) android.util.Log.d(TAG, msg(klass, str));
   }

   public static void v(Class klass, String str) {
      if (log) android.util.Log.v(TAG, msg(klass, str));
   }

   private static String msg(Class klass, String msg) {
      return klass.getSimpleName() + ": " + msg;
   }

   private static String msg(Object object, String msg) {
      return object.getClass().getSimpleName() + ": " + msg;
   }

}
