package com.eyeem.theroll.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/6/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeOfADay extends GraphicalView {

   ChartSetup setup;

   public TimeOfADay(Context context, ChartSetup setup) {
      super(context, setup.chart);
      this.setup = setup;
   }

   public TimeOfADay(Context context) {
      this(context, initChart());
   }

   public final static String[] LABELS = {"Morning", "Afternoon", "Evening", "Night"};

   public void testSetup() {
      setup.currentSeries.setTitle(null);
      setup.currentSeries.add(1, 2);
      setup.currentSeries.add(2, 3);
      setup.currentSeries.add(3, 2);
      setup.currentSeries.add(4, 5);
      setup.currentSeries.add(5, 4);
      repaint();
   }

   private static class ChartSetup {
      XYSeries currentSeries;
      XYSeriesRenderer currentRenderer;
      XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
      XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
      BarChart chart;
   }

   private static ChartSetup initChart() {
      ChartSetup s = new ChartSetup();
      s.currentSeries = new XYSeries("Data");
      s.dataset.addSeries(s.currentSeries);
      s.currentRenderer = new XYSeriesRenderer();
      s.renderer.addSeriesRenderer(s.currentRenderer);
      s.renderer.setClickEnabled(true);
      s.renderer.setZoomEnabled(false, false);
      s.renderer.setPanEnabled(false, false);
      s.renderer.setXAxisMin(1 - 0.5);
      s.renderer.setXAxisMax(5 + 0.5);
      s.renderer.setYAxisMin(0);
      s.renderer.setYAxisMax(5);
      s.renderer.setBarSpacing(0.7);
      s.chart = new BarChart(s.dataset, s.renderer, BarChart.Type.DEFAULT) {
         @Override
         protected int drawLegend(Canvas canvas, DefaultRenderer renderer, String[] titles, int left, int right, int y, int width, int height, int legendSize, Paint paint, boolean calculate) {
            return 0;
            //return super.drawLegend(canvas, renderer, titles, left, right, y, width, height, legendSize, paint, calculate);    //To change body of overridden methods use File | Settings | File Templates.
         }
      };
      return s;
   }
}
