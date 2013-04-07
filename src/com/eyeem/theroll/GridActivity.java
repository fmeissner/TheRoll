package com.eyeem.theroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import android.view.Window;
import com.origamilabs.library.views.StaggeredGridView;

/**
 *
 * This will not work so great since the heights of the imageViews 
 * are calculated on the iamgeLoader callback ruining the offsets. To fix this try to get 
 * the (intrinsic) image width and height and set the views height manually. I will
 * look into a fix once I find extra time.
 *
 * @author Maurycy Wojtowicz
 *
 */
public class GridActivity extends Activity {

   /**
    * Images are taken by Romain Guy ! He's a great photographer as well as a
    * great programmer. http://www.flickr.com/photos/romainguy
    */

   private String urls[] = {
           "/storage/emulated/0/DCIM/Camera/IMG_20130403_145042.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130403_145048.jpg",
           "/storage/emulated/0/EYEEM/eyeem1364999929237.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1364999960677.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_035622.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_035715.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_055409.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365047679391.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_090918.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_090923.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_090944.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_091005.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_091251.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_091255.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_091343.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_091409.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365059842414.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365060077103.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_092202.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_092217.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_092240.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_092245.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_092812.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_093319.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_093332.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_093337.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_095334.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_100141.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_182313.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_182954.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_183623.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_183634.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_191548.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_191548_1.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_191550.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_215453.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130404_232642.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365110857800.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_020354.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_041447.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_061248.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_061256.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365138450139.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_071749.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365139163685.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365160043852.jpg",
           "/storage/emulated/0/DCIM/Camera/PANO_20130405_082207.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_082331.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_083143.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_090716.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_090724.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_090731.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_090736.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_090739.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_090744.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_090746.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_104358.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_104434.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_104528.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_132020.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_132144.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_132206.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_135150.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_135200.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_135210.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365196429030.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365216039481.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130405_204634.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365220467569.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365220842320.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130406_085527.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130406_085539.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130406_090654.jpg",
           "/storage/emulated/0/DCIM/Camera/IMG_20130406_092703.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365265696469.jpg",
           "/storage/emulated/0/EYEEM/eyeem1365274952333.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365274967060.jpg",
           "/storage/emulated/0/EYEEM/eyeem1365275402113.jpg",
           "/storage/emulated/0/EYEEM/eyeemfiltered1365275421812.jpg",
           "/storage/emulated/0/DCIM/Camera/2002-12-08 12.00.00.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-08-22 10.37.28.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-13 21.26.00.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-14 14.32.00.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-15 01.05.55.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-15 01.42.59.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-16 00.42.18.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-17 19.29.03.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-17 21.24.14.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-18 13.29.17.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-18 17.01.19.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-19 00.20.52.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-19 00.21.21.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-19 00.22.40.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-19 00.36.05.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-19 14.56.31.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-20 21.00.44.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-20 21.00.59.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-20 21.22.25.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 00.26.05.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 01.11.32.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 02.20.55.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 02.30.45.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 02.35.29.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 02.48.54.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 12.36.45.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 12.47.49.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 13.10.26.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 13.20.30.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 13.47.57.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 13.48.44.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 13.49.03.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 13.49.17.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 13.49.35.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 13.51.46.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 13.52.19.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 14.09.08.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 14.14.56.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 14.19.09.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 14.24.25.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 14.32.00.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 16.35.25.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-21 16.35.35.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 12.14.31.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 12.24.45.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 12.25.05.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 12.25.11.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 12.34.54.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 15.57.38.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 15.58.05.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 15.58.20.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 17.23.40.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 17.27.52.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 17.58.03.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 18.27.26.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 18.28.20.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 18.35.18.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 19.17.13.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 19.17.51.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 21.05.00.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 21.10.58.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 21.11.13.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-22 21.11.35.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-23 01.38.09.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-23 01.38.38.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-23 01.38.55.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-23 02.04.11.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-23 02.26.43.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-23 02.27.12.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-23 02.36.01.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-23 02.36.15.jpg",
           "/storage/emulated/0/DCIM/Camera/2012-09-23 02.36.30.jpg"
   };
   /**
    * This will not work so great since the heights of the imageViews
    * are calculated on the iamgeLoader callback ruining the offsets. To fix this try to get
    * the (intrinsic) image width and height and set the views height manually. I will
    * look into a fix once I find extra time.
    */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.activity_main);
      StaggeredGridView gridView = (StaggeredGridView) this.findViewById(R.id.staggeredGridView1);

      int margin = getResources().getDimensionPixelSize(R.dimen.margin);

      gridView.setItemMargin(margin); // set the GridView margin

      gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well

      StaggeredAdapter adapter = new StaggeredAdapter(GridActivity.this, R.id.imageView1, urls);

      gridView.setAdapter(adapter);
      adapter.notifyDataSetChanged();
   }
}
