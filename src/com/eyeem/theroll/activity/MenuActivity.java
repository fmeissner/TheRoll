package com.eyeem.theroll.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.eyeem.theroll.R;
import com.eyeem.theroll.fragment.MenuFragment;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/7/13
 * Time: 3:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class MenuActivity extends Activity {
   @Override
   public void onCreate(Bundle bundle) {
      super.onCreate(bundle);

      // Add fragment that is slide menu
      if (getSupportFragmentManager().findFragmentById(R.id.menu_frame) != null) {
         menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_frame);
      } else {
         FragmentTransaction t = getSupportFragmentManager().beginTransaction();
         t.add(R.id.menu_frame, initMenuFragment());
         t.commit();
      }

      // customize the SlidingMenu
      this.setSlidingActionBarEnabled(true);

      getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
      getSlidingMenu().setShadowDrawable(R.drawable.shadow);
      getSlidingMenu().setBehindOffsetRes(R.dimen.actionbar_home_width);
      getSlidingMenu().setBehindScrollScale(0.25f);

      getSherlock().getActionBar().setIcon(R.drawable.sidebar);
      getSherlock().getActionBar().setHomeButtonEnabled(true);
      getSherlock().getActionBar().setTitle(null);
      getSupportActionBar().setDisplayShowCustomEnabled(true);
      RelativeLayout rl = new RelativeLayout(this);
      ImageView iv = new ImageView(this);
      iv.setImageResource(R.drawable.topbarlogo);
      rl.addView(iv, -1, -2);
      ((RelativeLayout.LayoutParams)iv.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT);
      getSherlock().getActionBar().setCustomView(rl);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home) {
         toggle();
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   protected MenuFragment menuFragment;
   protected SherlockFragment initMenuFragment() {
      return menuFragment = new MenuFragment();
   }
}
