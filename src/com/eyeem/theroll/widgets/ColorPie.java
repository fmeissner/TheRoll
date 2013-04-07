package com.eyeem.theroll.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.eyeem.theroll.App;
import com.eyeem.theroll.R;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PieChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/6/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ColorPie extends GraphicalView {

   ChartSetup setup;
   HashMap<String, Integer> values;

   public ColorPie(Context context, ChartSetup setup) {
      super(context, setup.chart);
      this.setup = setup;
   }

   public ColorPie(Context context) {
      this(context, initChart());
   }

   ArrayList<SimpleSeriesRenderer> currentRenderers = new ArrayList<SimpleSeriesRenderer>();

   public void setupValues(HashMap<String, Integer> values) {
      for (SimpleSeriesRenderer ssr : currentRenderers) {
         setup.renderer.removeSeriesRenderer(ssr);
      }
      currentRenderers.clear();
      // color --> count
      this.values = values;
      //setup.dataset.setTitle(null);
      int count = values.keySet().size();
      for (String colorName : values.keySet()) {
         setup.dataset.add(colorName, 33);
         SimpleSeriesRenderer r = new SimpleSeriesRenderer();
         currentRenderers.add(r);
         r.setColor(values.get(colorName));
         setup.renderer.addSeriesRenderer(r);
      }
      setup.renderer.setLabelsTextSize(getResources().getDimension(R.dimen.label_text_size)*0.66f);
      setup.renderer.setInScroll(true);
      repaint();
   }

   private static class ChartSetup {
      CategorySeries dataset = new CategorySeries("Colors");
      DefaultRenderer renderer = new DefaultRenderer();
      PieChart chart;
   }

   private static ChartSetup initChart() {
      ChartSetup s = new ChartSetup();
      s.renderer.setClickEnabled(true);
      s.renderer.setZoomEnabled(false);
      s.renderer.setPanEnabled(false);
      // s.renderer.setMarginsColor(App.the.getResources().getColor(R.color.bg));
      s.chart = new PieChart(s.dataset, s.renderer) {
         @Override
         protected int drawLegend(Canvas canvas, DefaultRenderer renderer, String[] titles, int left, int right, int y, int width, int height, int legendSize, Paint paint, boolean calculate) {
            return 0;
            //return super.drawLegend(canvas, renderer, titles, left, right, y, width, height, legendSize, paint, calculate);    //To change body of overridden methods use File | Settings | File Templates.
         }
      };
      return s;
   }
}
