package controller;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import database.RcmDBDAO;

import java.awt.GridLayout;
import java.util.ArrayList;

/**Method to create a sample dataset
 * @author:Kavan
 * @return JFreeChart
 **/
public class StatisticsGraph extends JFrame {
  private int rcmID;
  private static final long serialVersionUID = 1L;

  /**Parameterized Constructor for Statistics Graph
   * @param applicationTitle:String, chartTitle:String, rcmID:int
   **/
  public StatisticsGraph(String applicationTitle, String chartTitle,int rcmID) {
	  	super(applicationTitle);
	  	this.rcmID=rcmID;  
	
        // This will create the dataset 
        PieDataset dataset = createDataset();
        // Based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, chartTitle+" "+rcmID);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
        setContentPane(chartPanel);
        chartPanel.setLayout(new GridLayout(0, 2, 0, 0));

    }
    
    
  	/**Method to create a sample dataset
  	 * @param dataset:PieDataset , title:String
  	 * @return JFreeChart
  	 **/
    private  PieDataset createDataset() {
    	
    	ArrayList<String> transactionList = RcmDBDAO.RetrieveTransaction(rcmID);
        	
        DefaultPieDataset result = new DefaultPieDataset();
        for(int i=0;i<transactionList.size();i++) {
        	
        	 double weight =Double.parseDouble(transactionList.get(i));
        	 int numItems=Integer.parseInt(transactionList.get(i+1));
        	 double amount=Double.parseDouble(transactionList.get(i+2));
        	 int numEmptied=Integer.parseInt(transactionList.get(i+3));
        	
        	 result.setValue("Total Weight :"+weight, new Double(weight));
             result.setValue("Number of Items :"+numItems, new Integer(numItems));
             result.setValue("Total Amount :"+amount, new Double(amount));
             result.setValue("Number of times emptied :"+numEmptied,new Integer(numEmptied));
             i=i+3;
        }
       
        return result;
        
    }
    
    
    /**Method to create a chart
     * @param dataset:PieDataset , title:String
     * @return JFreeChart
     **/
    private JFreeChart createChart(PieDataset dataset, String title) {
        
        JFreeChart chart = ChartFactory.createPieChart3D(title,dataset,true,true,false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setForegroundAlpha(0.5f);
        return chart;
        
    }
    
 } 
