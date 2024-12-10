//********************************************************************
//
//  Developer:     Paul Ostos
//
//  File Name:     GarminFitnessTracker.java
//
//  Java Version:  "17.0.1" 2021-10-19 LTS
//
//  Description:   This class processes activity data from a CSV file, 
//                 calculates yearly statistics (total distance, average 
//                 distance), and displays the results.
//
//********************************************************************

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GarminFitnessTracker {

	// ***************************************************************
	//
	// Method:      main()
	//
	// Description: The main method of the program
	//
	// Parameters:  String array
	//
	// Returns:     N/A
	//
	// **************************************************************
    public static void main(String[] args) {
        GarminFitnessTracker obj = new GarminFitnessTracker();
        obj.handleActivities("Activities.csv");
    }

    // ***************************************************************
    //
    // Method:      handleActivities(String csvFile)
    //
    // Description: Handles the overall process of parsing the CSV file and 
    //              calculating yearly statistics.
    //
    // Parameters:  String csvFile - The path to the CSV file containing activity data.
    //
    // Returns:     N/A
    //
    // ***************************************************************

    private void handleActivities(String csvFile) {
        Map<Integer, YearData> yearDataMap = parseCSV(csvFile);
        YearlyStats(yearDataMap);
    }

    // ***************************************************************
    //
    // Method:      parseCSV(String csvFile)
    //
    // Description: Reads the CSV file, parses each line, and updates the map 
    //              with activity data for each year.
    //
    // Parameters:  String csvFile - The path to the CSV file.
    //
    // Returns:     Map<Integer, YearData> - A map with the year as the key and YearData as the value.
    //
    // ***************************************************************

    private Map<Integer, YearData> parseCSV(String csvFile) {
        Map<Integer, YearData> yearDataMap = new HashMap<>();
        String csvSplitBy = ",";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                processActivityData(data, yearDataMap);
            }
        } catch (IOException e) {
            System.err.println("Error Parsing CSV file.");;
            e.printStackTrace();
        }
        return yearDataMap;
    }

    // ***************************************************************
    //
    // Method:      processActivityData(String[] data, Map<Integer, YearData> yearDataMap)
    //
    // Description: Processes a single activity entry, extracts the year, 
    //              and updates the yearDataMap with the total distance and activity count.
    //
    // Parameters:  String[] data - The data from a single CSV line.
    //              Map<Integer, YearData> yearDataMap - The map storing activity data by year.
    //
    // Returns:     N/A
    //
    // ***************************************************************

    private void processActivityData(String[] data, Map<Integer, YearData> yearDataMap) {
        String date = data[1];
        String activity = data[3];
        double distance = Double.parseDouble(data[4].replace("\"", "").trim());

        // Extract the year from the date
        int year = Integer.parseInt(date.substring(0, 4));

        // Update the map with the distance and activity count
        YearData yearData = yearDataMap.getOrDefault(year, new YearData());
        yearData.addDistance(distance);
        yearData.incrementActivityCount();

        yearDataMap.put(year, yearData);
    }

    // ***************************************************************
    //
    // Method:      YearlyStats(Map<Integer, YearData> yearDataMap)
    //
    // Description: Processes and displays the yearly statistics for total 
    //              distance, average distance, and overall bike mileage.
    //
    // Parameters:  Map<Integer, YearData> yearDataMap - The map containing data for each year.
    //
    // Returns:     N/A
    //
    // ***************************************************************

    private void YearlyStats(Map<Integer, YearData> yearDataMap) {
        double bikeMilage = 0;

        for (Map.Entry<Integer, YearData> entry : yearDataMap.entrySet()) {
            int year = entry.getKey();
            YearData yearData = entry.getValue();
            double totalDistance = yearData.getTotalDistance();
            int activityCount = yearData.getActivityCount();
            double averageDistance = yearData.getAverageDistance();

            bikeMilage += totalDistance;
            displayYearlyStats(year, totalDistance, averageDistance, activityCount);
        }
        displayTotalMiles(bikeMilage);
    }

    // ***************************************************************
    //
    // Method:      displayYearlyStats(int year, double totalDistance, double averageDistance)
    //
    // Description: Displays the statistics for a given year, including the 
    //              total distance and average distance per activity.
    //
    // Parameters:  int year - The year for which stats are displayed.
    //              double totalDistance - The total distance for the year.
    //              double averageDistance - The average distance per activity for the year.
    //              int activityCount - The total count of activities for the year.
    //
    // Returns:     N/A
    //
    // ***************************************************************

    public void displayYearlyStats(int year, double totalDistance, double averageDistance, int activityCount){
        System.out.println("----------------------------");
        System.out.println("Year: " + year);
        System.out.printf("Total Distance: %.2f miles\n", totalDistance);
        System.out.println("Total Activities: " + activityCount);
        System.out.println("Average Ride Distance: " + String.format("%.2f", averageDistance) + " miles");
        System.out.println("----------------------------");
    }

    // ***************************************************************
    //
    // Method:      displayTotalMiles(double bikeMilage)
    //
    // Description: Displays the total miles ridden on the Caad13 bicycle.
    //
    // Parameters:  double bikeMilage - The total miles ridden on the bicycle.
    //
    // Returns:     N/A
    //
    // ***************************************************************

    public void displayTotalMiles(double bikeMilage){
        System.out.println("\n-------------------------------------------");
        System.out.println("Total miles ridden on Caad13: " + bikeMilage);
        System.out.println("-------------------------------------------");
    }
}
