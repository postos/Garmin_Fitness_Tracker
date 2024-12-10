//********************************************************************
//
//  Developer:     Paul Ostos
//
//  File Name:     YearData.java
//
//  Java Version:  "17.0.1" 2021-10-19 LTS
//
//********************************************************************

public class YearData{
    
    private double totalDistance;
    private int activityCount; 

    // ***************************************************************
    //
    // Helper Methods:  addDistance(double distance): Adds a distance to the total distance.
    //
    //                  incrementActivityCount(): Increments the activity count.
    //
    //                  getTotalDistance(): Returns the total distance.
    //
    //                  getActivityCount(): Returns the total number of activities.
    //
    //                  getAverageDistance(): Returns the average distance per activity, or 0 if no activities.
    //
    // ***************************************************************

    public void addDistance (double distance){
        totalDistance += distance; 
    }
    
    public void incrementActivityCount(){
        activityCount++; 
    }

    public double getTotalDistance(){
        return totalDistance; 
    }

    public int getActivityCount(){
        return activityCount;
    }

    public double getAverageDistance(){
        // if activity count is 0 return it, otherwise return the average distance per ride
        return  activityCount == 0 ? 0 : totalDistance/activityCount; // ternary operator 
    }
}