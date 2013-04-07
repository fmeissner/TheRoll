package com.eyeem.theroll;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.eyeem.theroll.model.Photo;
import staggeredgridviewdemo.loader.ImageLoader;
import staggeredgridviewdemo.views.ScaleImageView;

import java.util.ArrayList;

public class StaggeredAdapter extends BaseAdapter {

   private ImageLoader mLoader;
   ArrayList<Photo> photos = new ArrayList<Photo>();
   Context context;

   public StaggeredAdapter(Context context, ImageLoader loader) {
      mLoader = loader;
      this.context = context;
   }

   @Override
   public int getCount() {
      return photos.size();
   }

   @Override
   public Object getItem(int position) {
      return photos.get(position);
   }

   @Override
   public long getItemId(int position) {
      return position;
   }

   public void setPhotos(ArrayList<Photo> photos) {
      this.photos = photos;
      notifyDataSetChanged();
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {

      ViewHolder holder;
      final Photo p = photos.get(position);
      if (convertView == null) {
         LayoutInflater layoutInflator = LayoutInflater.from(context);
         convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
                 null);
         holder = new ViewHolder();
         holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
         convertView.setTag(holder);
      }

      holder = (ViewHolder) convertView.getTag();
      holder.imageView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(context, PhotoActivity.class);
            Bundle b = new Bundle();
            intent.putExtra("filePath",p.filePath);
            if(p.lon!=0.0){
               intent.putExtra("lat",p.lat);
            }
            if(p.lat!=0.0){
               intent.putExtra("lon",p.lon);
            }
            context.startActivity(intent);
         }
      });

      mLoader.DisplayImage(p.filePath, holder.imageView);

      return convertView;
   }

   static class ViewHolder {
      ScaleImageView imageView;
   }
}
