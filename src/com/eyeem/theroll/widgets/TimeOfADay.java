package com.eyeem.theroll.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.eyeem.theroll.App;
import com.eyeem.theroll.R;
import org.achartengine.GraphicalView;
import org.achartengine.chart.CubicLineChart;
import org.achartengine.chart.PointStyle;
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

   public void setValues(int values[]) {
      setup.currentSeries.setTitle(null);
      setup.currentSeries.add(1, values[0]);
      setup.currentSeries.add(2, values[1]);
      setup.currentSeries.add(3, values[2]);
      setup.currentSeries.add(4, values[3]);
      setup.currentRenderer.setLineWidth(4);
      setup.currentRenderer.setPointStyle(PointStyle.CIRCLE);
      setup.currentRenderer.setColor(0xffe5b13e);
      setup.renderer.setLabelsTextSize(getResources().getDimension(R.dimen.label_text_size)*0.66f);
      setup.renderer.addXTextLabel(1, "morning");
      setup.renderer.addXTextLabel(2, "afternoon");
      setup.renderer.addXTextLabel(3, "evening");
      setup.renderer.addXTextLabel(4, "night");
      setup.renderer.setXLabelsColor(0xff2cddd4);
      setup.renderer.setXLabels(0);
      setup.renderer.setYLabels(0);
      setup.renderer.setXAxisMin(0.5);
      setup.renderer.setXAxisMax(4.5);
      setup.renderer.setYAxisMin(0);
      setup.renderer.setInScroll(true);
      int max = 0;
      for (int i = 0; i<values.length; i++) {
         if (values[i] > max)
            max = values[i];
      }
      setup.renderer.setYAxisMax(max+max/2);
      repaint();
   }

   private static class ChartSetup {
      XYSeries currentSeries;
      XYSeriesRenderer currentRenderer;
      XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
      XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
      CubicLineChart chart;
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
      s.renderer.setMarginsColor(App.the.getResources().getColor(R.color.bg));
      s.chart = new CubicLineChart(s.dataset, s.renderer, 0.2f) {
         @Override
         protected int drawLegend(Canvas canvas, DefaultRenderer renderer, String[] titles, int left, int right, int y, int width, int height, int legendSize, Paint paint, boolean calculate) {
            return 0;
            //return super.drawLegend(canvas, renderer, titles, left, right, y, width, height, legendSize, paint, calculate);    //To change body of overridden methods use File | Settings | File Templates.
         }
      };
      return s;
   }
}
