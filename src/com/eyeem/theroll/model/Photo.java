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
   public ArrayList<Integer> colors = new ArrayList<Integer>();
   public int day;
   public String filePath;
   public long time;
   public double lat;
   public double lon;
   public String id;
}
