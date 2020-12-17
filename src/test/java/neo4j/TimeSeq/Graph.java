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

	public Graph(int[] data, String title, String xLabel, String seriesLabel, String fileName, int type) throws IOException {
		XYDataset dataset = createDataset(data, type);
		JFreeChart chart = createChart(dataset, title, xLabel, seriesLabel, fileName, type);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		add(chartPanel);

		pack();
		setTitle("Line chart");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


	private XYDataset createDataset(int[] data, int type) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		if(type == 0) {
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

			dataset.addSeries(series);
			dataset.addSeries(series2);
		}
		else {
			TimeSeries series = new TimeSeries("Daily");

			int len = data.length;
			double sum = 0;
			for(int i = 0; i < len; i++) {
				sum += data[i];
				series.add(new Day(Date.from(LocalDate.of(2020,1,1).plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant())), sum);
			}
			dataset.addSeries(series);
		}

		return dataset;
	}


	private JFreeChart createChart(XYDataset dataset, String title, String xLabel, String seriesLabel, String fileName, int type) throws IOException {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				title,
				xLabel,
				"Number" + seriesLabel,
				dataset,
				true,
				true,
				false
				);

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		if(type == 0) {
			renderer.setSeriesPaint(1, Color.BLUE);
		}
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

//	public static void main(String[] args) {
//
//		int[] data = {1,2,3,4,5,6,7,8,9,10};
//		int[] infected = {12,46,3,68,33,687,33,646,235,75,234,43};
//		int[] dead = {34,46,7,4,3,1,435,32,34,54,6,32,5,7,2,23};
//		try {
//			Graph infect = new Graph(infected, 
//					"Covid-19 Infections per day in 2020",
//					"Day",
//					"of Cases",
//					".\\Charts\\infectDaily.png", 0);
//
//			Graph death = new Graph(dead, 
//					"Deaths per Day as a Result of Covid-19 in 2020",
//					"Day",
//					"of Deaths",
//					".\\Charts\\deadDaily.png", 0);
//
//			Graph infectCumm = new Graph(infected, 
//					"Total Covid-19 Infections in 2020",
//					"Day",
//					"of Cases",
//					".\\Charts\\infectCummulative.png", 1);
//
//			Graph deathCumm = new Graph(dead, 
//					"Deaths as a Result of Covid-19 in 2020",
//					"Day",
//					"of Deaths",
//					".\\Charts\\deadCummulative.png", 1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}