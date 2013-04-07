package com.eyeem.theroll;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: ramz
 * Date: 6/4/13
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoActivity extends Activity implements View.OnClickListener {
   String nearbyImagesLink= "";
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.photo_main);
      ImageView iv = (ImageView) findViewById(R.id.singlePhoto);
      if(getIntent().hasExtra("filePath")) {
         File imgFile = new File(getIntent().getStringExtra("filePath"));
         if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv.setImageBitmap(myBitmap);
         }
      }
      if(getIntent().hasExtra("lat") && getIntent().hasExtra("lon")){
         nearbyImagesLink = "http://www.eyeem.com/a/radius:"+getIntent().getDoubleExtra("lat",0.0)+":"+getIntent().getDoubleExtra("lon",0.0);
      } else{
         findViewById(R.id.imagelink).setVisibility(View.GONE);
      }
   }

   @Override
   public void onClick(View v) {
      switch (v.getId()) {
         case R.id.imagelink:
            //TODO: open city album instead of radius
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(nearbyImagesLink));
            startActivity(i);
            break;
         default:
            break;
      }
   }

}
