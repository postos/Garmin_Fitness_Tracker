import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GarminFitnessTracker {

    public static void main(String[] args) {

        GarminFitnessTracker obj = new GarminFitnessTracker(); 
        obj.handleActivities();
        
    }

    public void handleActivities(){

        String csvFile = "Activities.csv"; // path to CSV file
        String line;
        String csvSplitBy = ","; // defines the delimiter

        // create map to store total distance and activity count per year
        Map<Integer, YearData> yearDataMap = new HashMap<>(); 
        double bikeMilage = 0; // tracker for total miles ridden on Caad13 bicycle
        System.out.println("----------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // read the header line to skip over it (columns tab)
            String header = br.readLine();

            // loop through each line in the CSV
            while ((line = br.readLine()) != null) {
                // Split each line by the comma delimiter
                String[] data = line.split(csvSplitBy);

                String date = data[1]; 
                String activity = data[3]; 
                double distance = Double.parseDouble(data[4].replace("\"","".trim())); 

                // extract the year from the date
                int year = Integer.parseInt(date.substring(0,4)); // date ex. 2020-11-13 17:41:02

                // update the map with the distance and count
                YearData yearData = yearDataMap.getOrDefault(year, new YearData());
                yearData.addDistance(distance);
                yearData.incrementActivityCount(); 

                yearDataMap.put(year, yearData);

            }

            // output the results for each year
            for (Map.Entry<Integer, YearData> entry: yearDataMap.entrySet()){
                int year = entry.getKey(); 
                YearData yearData = entry.getValue(); 
                double totalDistance = yearData.getTotalDistance(); 
                int activityCount = yearData.getActivityCount(); 
                double averageDistance = yearData.getAverageDistance(); 
                bikeMilage += totalDistance; // track the total miles ridden on the Caad13 bike

                System.out.println("Year: " + year);
                System.out.printf("Total Distance: %.2f miles\n", totalDistance);
                System.out.println("Average Ride Distance: " + String.format("%.2f", averageDistance) + " miles");
                System.out.println("----------------------------");
            }
            System.out.println("\n-------------------------------------------");
            System.out.println("Total miles ridden on Caad13: " + bikeMilage );
            System.out.println("-------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
