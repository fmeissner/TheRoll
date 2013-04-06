package com.eyeem.theroll;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.color.bean.ColorResult;
import net.jakobnielsen.imagga.color.bean.ExtendedColor;
import net.jakobnielsen.imagga.color.bean.IndexableImage;
import net.jakobnielsen.imagga.color.client.ColorAPIClient;
import net.jakobnielsen.imagga.color.client.ColorsByUrlsRequest;
import net.jakobnielsen.imagga.crop_slice.bean.Resolution;
import net.jakobnielsen.imagga.crop_slice.bean.SmartCropping;
import net.jakobnielsen.imagga.crop_slice.client.CropSliceAPIClient;
import net.jakobnielsen.imagga.upload.client.UploadClient;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.XYChart;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
   private GraphicalView mChartView;

   private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

   private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

   private XYSeries mCurrentSeries;

   private XYSeriesRenderer mCurrentRenderer;

   private void initChart() {
      mCurrentSeries = new XYSeries("Sample Data");
      mDataset.addSeries(mCurrentSeries);
      mCurrentRenderer = new XYSeriesRenderer();
      mRenderer.addSeriesRenderer(mCurrentRenderer);
      mRenderer.setClickEnabled(true);
      mRenderer.setZoomEnabled(false, false);
      mRenderer.setPanEnabled(false, false);
      mRenderer.setXAxisMin(1 - 0.5);
      mRenderer.setXAxisMax(5 + 0.5);
      mRenderer.setYAxisMin(0);
      mRenderer.setYAxisMax(5);
      mRenderer.setBarSpacing(0.7);
   }

   private void addSampleData() {
      mCurrentSeries.setTitle(null);
      mCurrentSeries.add(1, 2);
      mCurrentSeries.add(2, 3);
      mCurrentSeries.add(3, 2);
      mCurrentSeries.add(4, 5);
      mCurrentSeries.add(5, 4);
   }

   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      initChart();
      addSampleData();
      mChartView = new GraphicalView(this, new BarChart(mDataset, mRenderer, BarChart.Type.DEFAULT) {
         @Override
         protected int drawLegend(Canvas canvas, DefaultRenderer renderer, String[] titles, int left, int right, int y, int width, int height, int legendSize, Paint paint, boolean calculate) {
            return 0;
            //return super.drawLegend(canvas, renderer, titles, left, right, y, width, height, legendSize, paint, calculate);    //To change body of overridden methods use File | Settings | File Templates.
         }
      });
      setContentView(mChartView);

      mChartView.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            // handle the click event on the chart
            SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
            if (seriesSelection == null) {
               Toast.makeText(MainActivity.this, "No chart element", Toast.LENGTH_SHORT).show();
            } else {
               // display information of the clicked point
               Toast.makeText(
                  MainActivity.this,
                  "Chart element in series index " + seriesSelection.getSeriesIndex()
                     + " data point index " + seriesSelection.getPointIndex() + " was clicked"
                     + " closest point value X=" + seriesSelection.getXValue() + ", Y="
                     + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
            }
         }
      });

      try {
         testImagga();
      } catch (Throwable e) {
         e.printStackTrace();
      }
   }

   APIClientConfig IMAGA_CONFIG = new APIClientConfig("acc_b6c25fc6", "7c0765b6ce790023f06cfa18a7b47790", "78.128.78.162");

   public void testImagga() throws IOException {
      // Initialize the upload client with the configuration you have received from Imagga.
      UploadClient uploadClient = new UploadClient(IMAGA_CONFIG);

      // Initialize the client with the configuration you have received from Imagga.
      //ColorAPIClient client = new ColorAPIClient(IMAGA_CONFIG);

      //ColorsByUrlsRequest request = new ColorsByUrlsRequest();

      //request.setUrlsToProcess(Arrays.asList(
      //   new IndexableImage("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png", 100),
      //   new IndexableImage("http://www.toondoo.com/public/l/a/z/lazee/toons/cool-cartoon-152229.png", 101)));

      //List<ColorResult> colorResults = client.colorsByUploadCode(uploadClient.uploadForProcessing(new File("/mnt/sdcard/test.jpg")));
      //for (ColorResult res : colorResults) {
      //   for (ExtendedColor color : res.getInfo().getImageColors()) {
      //      Log.i("COLOR", color.getHtmlCode());
      //   }
      //}

      // Initialize the client with the configuration you have received from Imagga.
      CropSliceAPIClient client = new CropSliceAPIClient(IMAGA_CONFIG);

      List<SmartCropping> lst = client.smartCroppingByUploadCode(
         uploadClient.uploadForProcessing(new File("/mnt/sdcard/test.jpg")),
            true,
            Arrays.asList(new Resolution(50, 50)),
            true);
      lst.size();
   }
}
