package com.eyeem.theroll.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/6/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Photo {
   public String city;
   public String country;
   public ArrayList<String> colors = new ArrayList<String>();
   public String day;
   public String timeOfDay;
   public long timestamp;
   public int width;
   public int height;
   public String filePath;
   public double lat;
   public double lon;
   public String id;
}