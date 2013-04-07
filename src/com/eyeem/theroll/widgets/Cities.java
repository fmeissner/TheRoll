package com.eyeem.theroll.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.eyeem.theroll.App;
import com.eyeem.theroll.R;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/6/13
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class Cities extends GraphicalView {

   ChartSetup setup;
   HashMap<String, Integer> values;

   public Cities(Context context, ChartSetup setup) {
      super(context, setup.chart);
      this.setup = setup;
   }

   public Cities(Context context) {
      this(context, initChart());
   }

   public ArrayList<String> inOrder = new ArrayList<String>();

   public void setupValues(HashMap<String, Integer> values) {
      inOrder.clear();
      this.values = values;
      setup.currentSeries.setTitle(null);
      int count = values.keySet().size();
      int max = 0;
      int i = 0;
      for (String cityName : values.keySet()) {
         if (max < values.get(cityName))
            max = values.get(cityName);
         inOrder.add(cityName);
         setup.currentSeries.add(i, values.get(cityName));
         setup.renderer.addXTextLabel(i, cityName);
         i++;
      }
      setup.renderer.setXLabels(0);
      setup.renderer.setYLabels(0);
      setup.renderer.setXAxisMin(-0.5);
      setup.renderer.setXAxisMax(count-0.5);
      setup.renderer.setYAxisMin(0);
      setup.renderer.setYAxisMax(max * 1.5);
      setup.currentRenderer.setColor(getResources().getColor(R.color.blueish));
      setup.renderer.setLabelsTextSize(getResources().getDimension(R.dimen.label_text_size)*0.66f);
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
      s.renderer.setMarginsColor(App.the.getResources().getColor(R.color.bg));
      s.renderer.setInScroll(true);
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
