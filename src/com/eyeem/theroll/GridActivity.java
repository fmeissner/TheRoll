package com.eyeem.theroll;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import com.eyeem.storage.Storage;
import com.eyeem.theroll.activity.MenuActivity;
import com.eyeem.theroll.model.Photo;
import com.eyeem.theroll.storage.PhotoStorage;
import com.origamilabs.library.views.StaggeredGridView;
import staggeredgridviewdemo.loader.ImageLoader;

import java.lang.ref.WeakReference;


public class GridActivity extends MenuActivity implements View.OnClickListener {

   static WeakReference<GridActivity> _this;
   StaggeredGridView gridView;
   String gridheaderlink = "http://www.eyeem.com";
   private ImageLoader loader;

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

      StaggeredAdapter adapter = new StaggeredAdapter(GridActivity.this, R.id.imageView1, getUrls(), loader);
      gridView.setAdapter(adapter);
      adapter.notifyDataSetChanged();

   }

   Storage.Query query;

   public static void setQuery(final Storage.Query query) {
      final GridActivity a = _this.get();
      if (a == null)
         return;
      a.query = query;
      a.reloadAdapter();

   }


   public void reloadAdapter() {
      Thread t = new Thread(new Runnable() {
         @Override
         public void run() {
            final StaggeredAdapter adapter = new StaggeredAdapter(GridActivity.this, R.id.imageView1, getUrls(), loader);
            gridView.post(new Runnable() {
               @Override
               public void run() {
                  gridView.setAdapter(adapter);
                  adapter.notifyDataSetChanged();
               }
            });
         }
      });
      t.setPriority(Thread.MIN_PRIORITY);
      t.start();

/*
      TextView gridLabel = (TextView)this.findViewById(R.id.gridheader);
      TextView gridLink = (TextView)this.findViewById(R.id.gridheaderlink);
      if(!TextUtils.isEmpty(city)){
         //link to city album - set label to city name accordingly
         gridLabel.setText("Photos taken in " + city);
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
      } else if (!TextUtils.isEmpty(timeOfDay)){
         gridLabel.setText("Photos taken during the " + timeOfDay);
         //set label to time of day
      } else if (!TextUtils.isEmpty(color)){
         //set label to color
         gridLabel.setText("Photos with the color " + color);
      }
*/
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

   private Photo[] getUrls() {

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

      Photo[] urls = new Photo[list.size()];
      try {
         for (int i = 0; i < urls.length; i++) {
            urls[i] = list.get(i);
         }
      } catch (Throwable w) {/*best coding ever!*/}
      return urls;
   }
}
