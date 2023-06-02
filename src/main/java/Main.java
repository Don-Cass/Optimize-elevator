import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Building building = new Building();
        double threshold1 = 10;
        double threshold2 = 0.3;

        // Create XY series for difference and totalAllocation
        XYSeries goodCaseSeries = new XYSeries("Good Cases");

        double minDifference = Double.MAX_VALUE;
        double maxTotalAllocation = 0;
        int goodCaseCount = 0;

        while (true) {
            Elevator elevator1 = new Elevator(building);
            Elevator elevator2 = new Elevator(building);
            Elevator elevator3 = new Elevator(building);

            building.resetResourceDistribution();
            elevator1.reset();
            elevator2.reset();
            elevator3.reset();

            elevator1.setOperating_floor(new int[]{2, 3, 4, 5, 6, 7, 8});
            elevator1.setResource();

            elevator2.setOperating_floor_Random();
            elevator2.setResource();

            elevator3.setOperating_floor_Random();
            elevator3.setResource();

            building.distributeResources(elevator1, elevator2, elevator3);

            double difference = building.calculateDistributionDifference();
            float totalAllocation = building.calculateTotalResourceAllocation();

            if (difference < threshold1 && totalAllocation > threshold2) {
                if(difference > 0.02){
                    // Add good case to the series
                    goodCaseSeries.add(difference, totalAllocation);
                    System.out.println("Iteration: " + goodCaseCount);
                    System.out.println("Difference: " + difference);
                    System.out.println("TotalAllocation: " + totalAllocation);
                    System.out.println("Elevator 1 operating floors: " + elevator1.getOperating_floor());
                    System.out.println("Elevator 2 operating floors: " + elevator2.getOperating_floor());
                    System.out.println("Elevator 3 operating floors: " + elevator3.getOperating_floor());
                    System.out.println("-----------------------------------");

                    // Update maxTotalAllocation
                    maxTotalAllocation = totalAllocation;
                    goodCaseCount++;
                }
            }

            // Update minDifference
            if (difference < minDifference) {
                minDifference = difference;
            }
            if (totalAllocation > maxTotalAllocation) {
                maxTotalAllocation = totalAllocation;
            }
            if (goodCaseCount >= 100000) {
                break;
            }
        }

        // Create the chart
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(goodCaseSeries);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Good Cases: Difference vs Total Allocation", "Difference", "Total Allocation",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        // Display the chart in a JFrame
        JFrame frame = new JFrame("Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.getContentPane().add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);

        System.out.println("Minimum difference during the iterations: " + minDifference);
        System.out.println("Maximum total resource allocation during the iterations: " + maxTotalAllocation);
        System.out.println("Number of good cases: " + goodCaseCount);
    }
}