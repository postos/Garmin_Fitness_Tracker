import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GarminFitnessTracker {

	// ***************************************************************
	//
	// Method: main()
	//
	// Description: The main method of the program
	//
	// Parameters: String array
	//
	// Returns: N/A
	//
	// **************************************************************
    public static void main(String[] args) {
        GarminFitnessTracker obj = new GarminFitnessTracker();
        obj.handleActivities("Activities.csv");
    }

    // ***************************************************************
	//
	// Method: 
	//
	// Description: 
	//
	// Parameters: 
	//
	// Returns: 
	//
	// **************************************************************

    private void handleActivities(String csvFile) {
        Map<Integer, YearData> yearDataMap = parseCSV(csvFile);
        YearlyStats(yearDataMap);
    }

    // ***************************************************************
	//
	// Method: 
	//
	// Description: 
	//
	// Parameters: 
	//
	// Returns: 
	//
	// **************************************************************

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
	// Method: 
	//
	// Description: 
	//
	// Parameters: 
	//
	// Returns: 
	//
	// **************************************************************

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
	// Method: 
	//
	// Description: 
	//
	// Parameters: 
	//
	// Returns: 
	//
	// **************************************************************

    private void YearlyStats(Map<Integer, YearData> yearDataMap) {
        double bikeMilage = 0;

        for (Map.Entry<Integer, YearData> entry : yearDataMap.entrySet()) {
            int year = entry.getKey();
            YearData yearData = entry.getValue();
            double totalDistance = yearData.getTotalDistance();
            int activityCount = yearData.getActivityCount();
            double averageDistance = yearData.getAverageDistance();

            bikeMilage += totalDistance;
            displayYearlyStats(year, totalDistance, averageDistance);
        }
        displayTotalMiles(bikeMilage);
    }

    // ***************************************************************
	//
	// Method: 
	//
	// Description: 
	//
	// Parameters: 
	//
	// Returns: 
	//
	// **************************************************************

    public void displayYearlyStats(int year, double totalDistance, double averageDistance){
        System.out.println("----------------------------");
        System.out.println("Year: " + year);
        System.out.printf("Total Distance: %.2f miles\n", totalDistance);
        System.out.println("Average Ride Distance: " + String.format("%.2f", averageDistance) + " miles");
        System.out.println("----------------------------");
    }

    // ***************************************************************
	//
	// Method: 
	//
	// Description: 
	//
	// Parameters: 
	//
	// Returns: 
	//
	// **************************************************************

    public void displayTotalMiles(double bikeMilage){
        System.out.println("\n-------------------------------------------");
        System.out.println("Total miles ridden on Caad13: " + bikeMilage);
        System.out.println("-------------------------------------------");
    }
}
