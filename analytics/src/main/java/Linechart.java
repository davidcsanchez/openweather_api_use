import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


class LineChart extends JFrame {

    private String directoryDB;

    public LineChart(String directoryBD) throws IOException {
        this.directoryDB = directoryBD;
        exploitData();
    }


    public void exploitData() throws IOException {
        XYDataset dataset = getXYDataset(new WeatherDB(this.directoryDB).selectAll());
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = getChartPanel(chart);
        saveChart(chartPanel);
    }

    private ChartPanel getChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);
        pack();
        setTitle("Line chart");
        return chartPanel;
    }

    private void saveChart(ChartPanel chartPanel) throws IOException {
        File lineChart = new File( "linechart.jpg");
        ChartUtils.saveChartAsJPEG(lineChart,
                chartPanel.getChart(),
                chartPanel.getWidth(),
                chartPanel.getHeight());
    }


    private XYDataset getXYDataset(ArrayList<Weather> events) {
        var temperature = new XYSeries("Temperature");
        var pressure = new XYSeries("Pressure");
        var humidity = new XYSeries("Humidity");

        for (int i = 0; i < events.size(); i++) {
            double date = events.get(i).getTs().getEpochSecond();
            temperature.add(date , events.get(i).getTemp());
            pressure.add(date , events.get(i).getPressure());
            humidity.add(date , events.get(i).getHumidity());
        }
        var dataset = new XYSeriesCollection();
        dataset.addSeries(temperature);
        dataset.addSeries(pressure);
        dataset.addSeries(humidity);
        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "The evolution of temperature, pressure, and humidity",
                "Time format in epoch seconds",
                "Evolutions",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        XYPlot plot = chart.getXYPlot();
        var renderer = new XYLineAndShapeRenderer();
        colourLinechart(renderer);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.setTitle(new TextTitle("The evolution of temperature, pressure, and humidity",
                        new Font("Serif", Font.BOLD, 18)
                )
        );
        return chart;
    }

    private void colourLinechart(XYLineAndShapeRenderer renderer) {
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
    }

}