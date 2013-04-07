package com.eyeem.theroll.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.eyeem.theroll.R;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/7/13
 * Time: 3:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Activity extends SlidingFragmentActivity {
   @Override
   public void onCreate(Bundle bundle) {
      super.onCreate(bundle);
      setBehindContentView(R.layout.menu_frame);
   }

   public Fragment getActiveFragment(ViewPager container, int position) {
      String name = makeFragmentName(container.getId(), position);
      return  getSupportFragmentManager().findFragmentByTag(name);
   }

   private static String makeFragmentName(int viewId, int index) {
      return "android:switcher:" + viewId + ":" + index;
   }
}
