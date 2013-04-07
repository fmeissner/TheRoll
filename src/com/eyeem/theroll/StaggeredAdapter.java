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
import android.widget.ImageView;

import com.eyeem.theroll.model.Photo;
import staggeredgridviewdemo.loader.ImageLoader;
import staggeredgridviewdemo.views.ScaleImageView;

public class StaggeredAdapter extends ArrayAdapter<Photo> {

   private ImageLoader mLoader;

   public StaggeredAdapter(Context context, int textViewResourceId,
                           Photo[] objects, ImageLoader loader) {
      super(context, textViewResourceId, objects);
      mLoader = loader;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {

      ViewHolder holder;
      final Photo p = getItem(position);
      if (convertView == null) {
         LayoutInflater layoutInflator = LayoutInflater.from(getContext());
         convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
                 null);
         holder = new ViewHolder();
         holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
         holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getContext(), PhotoActivity.class);
               Bundle b = new Bundle();
               intent.putExtra("filePath",p.filePath);
               if(p.lon!=0.0){
                  intent.putExtra("lat",p.lat);
               }
               if(p.lat!=0.0){
                  intent.putExtra("lon",p.lon);
               }
               getContext().startActivity(intent);
            }
         });
         convertView.setTag(holder);
      }

      holder = (ViewHolder) convertView.getTag();

      mLoader.DisplayImage(getItem(position).filePath, holder.imageView);

      return convertView;
   }

   static class ViewHolder {
      ScaleImageView imageView;
   }
}
