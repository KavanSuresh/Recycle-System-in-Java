package controller;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import database.RcmDBDAO;

/**
 * Bar chart giving a comparison of RCM Data
 * @author Kavan
 **/

public class BarGraph extends JFrame {

    /**
     * Creates a new Frame.
     * @param title  the frame title.
     */
    public BarGraph(final String title) {

        super(title);

        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);

    }

    /**
     * Creates and returns a dataset of total weight and total amount.
     * 
     * @return The dataset.
     */
    private CategoryDataset createDataset() {
        
    	 final String category1 = "Total weight";
    	 final String category2 = "Total Amount";
       
         String series1 = null,series2= null,series3= null;
        
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
    	int rcmID1 = 0,rcmID2=0,rcmID3=0;
    	ArrayList <Integer> rcmIdList = new ArrayList<Integer>();
    	rcmIdList = RcmDBDAO.getRcmID();
    	int count =rcmIdList.size();
    	for(int i=0;i<count;i++) {
    		 series1 = "RCM ID:"+rcmIdList.get(i);
    		 rcmID1=rcmIdList.get(i);
    		 i++;
    		 if(i<count) {
    			 series2 = "RCM ID:"+rcmIdList.get(i);
    			 rcmID2=rcmIdList.get(i);
    			 i++;
    		 }
    		 if(i<count) {
    			 series3 = "RCM ID:"+rcmIdList.get(i);
    			 rcmID3=rcmIdList.get(i);
    			 i++;
    		 }

    	}
       
    	ArrayList<String> transactionList1 = RcmDBDAO.RetrieveTransaction(rcmID1);
    
        for(int i=0;i<transactionList1.size();i++) {
        	
        	 double weight1 =Double.parseDouble(transactionList1.get(i));
        	 int numItems1=Integer.parseInt(transactionList1.get(i+1));
        	 double amount1=Double.parseDouble(transactionList1.get(i+2));
        	 int numEmptied1=Integer.parseInt(transactionList1.get(i+3));
        	 System.out.println(weight1+"      "+amount1);
        	 dataset.addValue(weight1, series1, category1);
        	 dataset.addValue(amount1, series1, category2);
        	
             i=i+3;
        }
       
        if(rcmID2!=0) {
        	 ArrayList<String> transactionList2 = RcmDBDAO.RetrieveTransaction(rcmID2);
         	
             for(int i=0;i<transactionList2.size();i++) {
             	
             	 double weight2 =Double.parseDouble(transactionList2.get(i));
             	 int numItems2=Integer.parseInt(transactionList2.get(i+1));
             	 double amount2=Double.parseDouble(transactionList2.get(i+2));
             	 int numEmptied2=Integer.parseInt(transactionList2.get(i+3));
             	 System.out.println(weight2+"      "+amount2);
             	
             	 dataset.addValue(weight2, series2, category1);
             	 dataset.addValue(amount2, series2, category2);
                  i=i+3;
             }
        }
       
        if(rcmID3!=0) {
       	 ArrayList<String> transactionList3 = RcmDBDAO.RetrieveTransaction(rcmID3);
        	
            for(int i=0;i<transactionList3.size();i++) {
            	
            	 double weight3 =Double.parseDouble(transactionList3.get(i));
            	 int numItems3=Integer.parseInt(transactionList3.get(i+1));
            	 double amount3=Double.parseDouble(transactionList3.get(i+2));
            	 int numEmptied3=Integer.parseInt(transactionList3.get(i+3));
            	 System.out.println(weight3+"      "+amount3);
            	
            	 dataset.addValue(weight3, series3, category1);
            	 dataset.addValue(amount3, series3, category2);
            	
                 i=i+3;
            }
       }
       
        return dataset;
        
    }
    
    /** Creates a bar Graph
     * @param dataset  
     * @return  chart
     */
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createBarChart(
            "Comparison of Recycling stations",         // chart title
            "Recycling stations",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

       

        // set the background color for the chart
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
         
        return chart;
        
    }

}
