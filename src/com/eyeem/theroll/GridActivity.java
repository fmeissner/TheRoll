package com.eyeem.theroll;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import com.eyeem.storage.Storage;
import com.eyeem.theroll.activity.MenuActivity;
import com.eyeem.theroll.model.Photo;
import com.eyeem.theroll.storage.PhotoStorage;
import com.origamilabs.library.views.StaggeredGridView;
import staggeredgridviewdemo.loader.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class GridActivity extends MenuActivity implements View.OnClickListener {

   static WeakReference<GridActivity> _this;
   StaggeredGridView gridView;
   String gridheaderlink = "http://www.eyeem.com";
   private ImageLoader loader;
   StaggeredAdapter adapter;

   /**
    * This will not work so great since the heights of the imageViews
    * are calculated on the iamgeLoader callback ruining the offsets. To fix this try to get
    * the (intrinsic) image width and height and set the views height manually. I will
    * look into a fix once I find extra time.
    */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      _this = new WeakReference<GridActivity>(this);
      setContentView(R.layout.activity_main);
      loader = new ImageLoader(this);
      gridView = (StaggeredGridView) this.findViewById(R.id.staggeredGridView1);

      int margin = getResources().getDimensionPixelSize(R.dimen.margin);

      gridView.setItemMargin(margin); // set the GridView margin

      gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well

      adapter = new StaggeredAdapter(GridActivity.this, loader);
      gridView.setAdapter(adapter);
      adapter.setPhotos(getUrls());

   }

   Storage.Query query;

   public static void setQuery(final Storage.Query query) {
      final GridActivity a = _this.get();
      if (a == null)
         return;
      a.reloadAdapter(query);
   }


   public void reloadAdapter(PhotoStorage.Query query) {
      this.query = query;
      gridView.post(new Runnable() {
         @Override
         public void run() {
            adapter.setPhotos(getUrls());
         }
      });


      TextView gridLabel = (TextView)this.findViewById(R.id.gridheader);
      TextView gridLink = (TextView)this.findViewById(R.id.gridheaderlink);
      gridLink.setVisibility(View.INVISIBLE);
      gridLabel.setVisibility(View.INVISIBLE);
      if(query instanceof PhotoStorage.CityQuery){
         //link to city album - set label to city name accordingly
         String city = ((PhotoStorage.CityQuery)query).city;
         gridLabel.setText("Photos taken in " + city);
         gridLabel.setVisibility(View.VISIBLE);
         gridLink.setVisibility(View.VISIBLE);
         if(city.equals("Berlin")){
            gridLink.setText("see more photos");
            gridheaderlink = "http://www.eyeem.com/a/17";
         } else if (city.equals("San Francisco")){
            gridLink.setText("see more photos");
            gridheaderlink = "http://www.eyeem.com/a/2257";
         } else if (city.equals("Barcelona")){
            gridheaderlink = "http://www.eyeem.com/a/744";
            gridLink.setText("see more photos");
         } else if(city.equals("Menlo Park")){
            gridheaderlink = "http://www.eyeem.com/a/35486";
            gridLink.setText("see more photos");
         }  else if(city.equals("Le Mesnil-Amelot")){
            gridheaderlink = "http://www.eyeem.com/a/68633";
            gridLink.setText("see more photos");
         }
      } else if (query instanceof PhotoStorage.TimeQuery) {
         gridLabel.setText("Photos taken during the " + ((PhotoStorage.TimeQuery)query).timeOfDay);
         gridLabel.setVisibility(View.VISIBLE);
         //set label to time of day
      } else if (query instanceof PhotoStorage.ColorQuery){
         //set label to color
         gridLabel.setText("Photos with the color " + ((PhotoStorage.ColorQuery)query).color);
         gridLabel.setVisibility(View.VISIBLE);
      } else {
        // blablablablablablablabalbalbalbalb
      }

   }

   @Override
   public void onClick(View v) {
      switch (v.getId()) {
         case R.id.gridheaderlink:
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(gridheaderlink));
            startActivity(i);
            break;
         default:
            break;
      }
   }

   private ArrayList<Photo> getUrls() {

      if (query == null) {
/*      Storage.Query<Photo> query = null;

      if (!TextUtils.isEmpty(city)) {
         Log.i(this,"photoquery city:"+city);
         query = new Storage.Query<Photo>() {
            @Override
            public boolean eval(Photo photo) {
               return !TextUtils.isEmpty(photo.city) && photo.city.equals(city);
            }
         };
      } else if (!TextUtils.isEmpty(timeOfDay)) {
         Log.i(this,"photoquery timeofday:"+timeOfDay);
         query = new Storage.Query<Photo>() {
            @Override
            public boolean eval(Photo photo) {
               return !TextUtils.isEmpty(photo.timeOfDay) && photo.timeOfDay.equals(timeOfDay);
            }
         };
      } else if (!TextUtils.isEmpty(color)) {
         Log.i(this,"photoquery color:"+color);
>>>>>>> 523b643e146968894ac56279e6ffe4f1757971b8*/
         query = new Storage.Query<Photo>() {
            @Override
            public boolean eval(Photo photo) {
               // return all the photos
               return true;
            }
         };
      }

      PhotoStorage.List list = PhotoStorage.getInstance().obtainList("grid");
      list.enableSort(PhotoStorage.comparator);
      list.setQuery(query);
      list.reloadQuery();

      return list.toArrayList(list.size());
   }
}
