package plotting;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;

import java.awt.*;
import java.util.Random;

/**
 * The HeatMapPainter is responsible for creating a heatmap of Germany based on values taken out of a MySQL database.
 * Relevant values for a specific location are (Longitude, Latitude, Temperature).
 * All locations are retrieved from the database and each location is plotted on the map as a small rectangle having a specific
 * color which represents the measured temperature at this location.
 * 
 * As a result a map is painted which displays the temperature of all measured locations in Germany.
 * 
 * Example for Heatmap creation using JFreeChart (http://www.jfree.org/jfreechart/) was taken and adapted from 
 * Stackoverflow question (https://stackoverflow.com/questions/10739422/heatmaps-with-jfreechart)
 * 
 * @author Marcel Verst
 * @version 10.06.2019
 */
public class HeatMapPainter extends ApplicationFrame
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creating a HeatMapDemo object results in automatically creating the dataset and a heatmap based on the dataset.
	 */
	public HeatMapPainter()
    {
        super("Heatmap of Germany");
        final JFreeChart chart = createChart(createDataset());
        setContentPane(new ChartPanel(chart));
    }

	/**
	 * Creates a heatmap where longitude and latitude are taken to plot points at the respective x- and y-coordinate.
	 * Knowing the current temperature at a specific location allows to color the point in the respective temperature color
	 * defined with the paintScale.
	 * 
	 * @param XYZDataset Dataset consisting of Longitude, Latitude, Temperature tupels.
	 * @return JFreeChart The created chart.
	 */
    public JFreeChart createChart(XYZDataset dataset)
    {
    	// Label Axes
    	NumberAxis xAxis = new NumberAxis("Longitude");
    	NumberAxis yAxis = new NumberAxis("Latitude");

    	// Create PaintScale for displaying temperature colors
        LookupPaintScale paintScale = getPaintScale();
        PaintScaleLegend psl = new PaintScaleLegend(paintScale, new NumberAxis());
        psl.setPosition(RectangleEdge.RIGHT);
        psl.setAxisLocation(AxisLocation.TOP_OR_RIGHT);
        psl.setMargin(20.0, 0.0, 50.0, 0.0);

        // Create Plot
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, new XYBlockRenderer());
        ((XYBlockRenderer)plot.getRenderer()).setPaintScale(paintScale);
       
        Font font = new Font("TimesRoman", Font.PLAIN, 20);
        JFreeChart chart = new JFreeChart("Heatmap of Germany", font, plot, false);
        chart.addSubtitle(psl);
        return chart;
    }
    
    /**
     * Creates the dataset for the heatmap. Uses Longitude and Latitude of a location for setting X- and Y-position.
     * The current temperature at the location is used as z-coordinate to convert it into a temperature color.
     * 
     * @return XYZDataset Dataset consisting of Longitude, Latitude, Temperature tupel
     */
    public XYZDataset createDataset() {
    	Random rand = new Random();
    	
    	double[] x = new double[100];
    	double[] y = new double[100];
    	double[] z = new double[100];
    	
    	for(int i = 0; i < 100; i++) {
    		x[i] = rand.nextInt(50);
    		y[i] = rand.nextInt(50);
    		z[i] = rand.nextInt(50);
    	}
    	
    	DefaultXYZDataset dataset = new DefaultXYZDataset();
    	dataset.addSeries("Series", new double[][] {x,y,z});
    	return dataset;
    }
    
    /**
     * Creates a paintScale which maps temperatures to the respective RGB color space
     * 
     * @return LookupPaintScale The scale which is used for displaying correct temperature colors
     */
    public LookupPaintScale getPaintScale() {
    	// create a paint-scale and a legend showing it
        LookupPaintScale paintScale = new LookupPaintScale(-30, 40, Color.black);
        paintScale.add(-30, new Color(255,0,255));
        paintScale.add(-26, new Color(235,0,255));
        paintScale.add(-25, new Color(215,0,255));
        paintScale.add(-24, new Color(195,0,255));
        paintScale.add(-23, new Color(175,0,255));
        paintScale.add(-22, new Color(155,0,255));
        paintScale.add(-21, new Color(135,0,255));
        paintScale.add(-20, new Color(115,0,255));
        paintScale.add(-19, new Color(95,0,255));
        paintScale.add(-18, new Color(75,0,255));
        paintScale.add(-17, new Color(55,0,255));
        paintScale.add(-16, new Color(35,0,255));
        paintScale.add(-15, new Color(15,0,255));
        paintScale.add(-14, new Color(0,0,255));
        
        paintScale.add(-13, new Color(0,15,255));
        paintScale.add(-12, new Color(0,35,255));
        paintScale.add(-11, new Color(0,55,255));
        paintScale.add(-10, new Color(0,75,255));
        paintScale.add(-9, new Color(0,95,255));
        paintScale.add(-8, new Color(0,115,255));
        paintScale.add(-7, new Color(0,135,255));
        paintScale.add(-6, new Color(0,155,255));
        paintScale.add(-5, new Color(0,175,255));
        paintScale.add(-4, new Color(0,195,255));
        paintScale.add(-3, new Color(0,215,255));
        paintScale.add(-2, new Color(0,235,255));
        paintScale.add(-1, new Color(0,255,255));
        
        paintScale.add(0, new Color(0,255,235));
        paintScale.add(1, new Color(0,255,215));
        paintScale.add(2, new Color(0,255,195));
        paintScale.add(3, new Color(0,255,175));
        paintScale.add(4, new Color(0,255,155));
        paintScale.add(5, new Color(0,255,135));
        paintScale.add(6, new Color(0,255,115));
        paintScale.add(7, new Color(0,255,95));
        paintScale.add(8, new Color(0,255,75));
        paintScale.add(9, new Color(0,255,55));
        paintScale.add(10, new Color(0,255,35));
        paintScale.add(11, new Color(0,255,15));
        paintScale.add(12, new Color(0,255,0));
        paintScale.add(13, new Color(0,255,0));
        
        paintScale.add(14, new Color(15,255,0));
        paintScale.add(15, new Color(35,255,0));
        paintScale.add(16, new Color(55,255,0));
        paintScale.add(17, new Color(75,255,0));
        paintScale.add(18, new Color(95,255,0));
        paintScale.add(19, new Color(115,255,0));
        paintScale.add(20, new Color(135,255,0));
        paintScale.add(21, new Color(155,255,0));
        paintScale.add(22, new Color(175,255,0));
        paintScale.add(23, new Color(195,255,0));
        paintScale.add(24, new Color(215,255,0));
        paintScale.add(25, new Color(235,255,0));
        paintScale.add(26, new Color(255,255,0));
        
        paintScale.add(27, new Color(255,255,0));
        paintScale.add(28, new Color(255,235,0));
        paintScale.add(29, new Color(255,215,0));
        paintScale.add(30, new Color(255,195,0));
        paintScale.add(31, new Color(255,175,0));
        paintScale.add(32, new Color(255,155,0));
        paintScale.add(33, new Color(255,135,0));
        paintScale.add(34, new Color(255,115,0));
        paintScale.add(35, new Color(255,95,0));
        paintScale.add(36, new Color(255,75,0));
        paintScale.add(37, new Color(255,55,0));
        paintScale.add(38, new Color(255,35,0));
        paintScale.add(39, new Color(255,15,0));
        paintScale.add(40, new Color(255,0,0));
        
        return paintScale;
    }
}