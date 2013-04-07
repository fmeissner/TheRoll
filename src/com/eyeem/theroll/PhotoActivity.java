package com.eyeem.theroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created with IntelliJ IDEA.
 * User: ramz
 * Date: 6/4/13
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoActivity extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);

      //todo: display largish photo + link to see photos taken near this one
   }
}
