public class YearData{
    private double totalDistance;
    private int activityCount; 

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
        return  activityCount == 0 ? 0 : totalDistance/activityCount; 
    }
}