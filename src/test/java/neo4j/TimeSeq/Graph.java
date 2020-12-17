package neo4j.TimeSeq;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
//import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Graph extends JFrame {
	
	SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");;
	
    public Graph(int[] data, String title, String yLabel, String seriesLabel, String fileName) throws IOException {
    	 XYDataset dataset = createDataset(data);
         JFreeChart chart = createChart(dataset, title, yLabel, seriesLabel, fileName);

         ChartPanel chartPanel = new ChartPanel(chart);
         chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
         chartPanel.setBackground(Color.white);
         add(chartPanel);

         pack();
         setTitle("Line chart");
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
//    public Graph(int[] data, String title, String yLabel, String seriesLabel, String series2Lable) throws IOException {
//    	XYDataset dataset = createDataset(data);
//    	XYDataset dataset2 = createDataset(data);
//
//        JFreeChart chart = createChart(dataset);
//
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
//        chartPanel.setBackground(Color.white);
//        add(chartPanel);
//
//        pack();
//        setTitle("Line chart");
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }

    private XYDataset createDataset(int[] data) {
        TimeSeries series = new TimeSeries("Daily");
        TimeSeries series2 = new TimeSeries("7-Day Average");

        int len = data.length;
        double rollingSum = 0;
        for(int i = 0; i < 7; i++) {
            series.add(new Day(Date.from(LocalDate.of(2020,1,1).plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant())), data[i]);
            rollingSum = rollingSum + data[i];
            series2.add(new Day(Date.from(LocalDate.of(2020,1,1).plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant())), rollingSum / (i+1));
        }
        for(int i = 7; i < len; i++) {
            series.add(new Day(Date.from(LocalDate.of(2020,1,1).plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant())), data[i]);
            rollingSum = rollingSum + data[i] - data[i-7];
            series2.add(new Day(Date.from(LocalDate.of(2020,1,1).plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant())), (rollingSum / 7));
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series2);
        
        return dataset;
    }
    
    private JFreeChart createChart(XYDataset dataset, String title, String yLabel, String seriesLabel, String fileName) throws IOException {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title,
                yLabel,
                "Number" + seriesLabel,
                dataset,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(title,
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );
        DateAxis domainAxis = new DateAxis("Date");
        domainAxis.setDateFormatOverride(format);
        plot.setDomainAxis(domainAxis);
        
        File file = new File(fileName);
        ChartUtils.saveChartAsPNG(file, chart,1920, 1080);
        return chart;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
			public void run() {

			   Graph ex = null;
			   int[] data = {1,2,3,4,5,6,7,8,9,10};
				try {
					ex = new Graph(data, 
							"Deaths as a Result of Covid-19 in 2020",
			                "Day",
			                "of Deaths",
			                ".\\Charts\\chart.png");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    ex.setVisible(true);
			}
		});
    }
}