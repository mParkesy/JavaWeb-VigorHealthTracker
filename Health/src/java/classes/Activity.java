package classes;

/**
 * A java class that holds a possible activity for a user to carry out
 */
public class Activity {

    private int activityID;
    private String activity;
    private double MET;

    /**
     * Constructor for an Activity object
     * @param activityID The ID of the activity from the database
     * @param activity The name of the Activity
     * @param MET The MET value for the activity, used in calorie calculations
     */
    public Activity(int activityID, String activity, double MET) {
        this.activityID = activityID;
        this.activity = activity;
        this.MET = MET;
    }

    /**
     * A getter for the activity ID
     * @return The activity ID as an integer
     */
    public int getActivityID() {
        return activityID;
    }

    /**
     * A getter for the activity name
     * @return The name as a string
     */
    public String getActivity() {
        return activity;
    }

    /**
     * A getter for the MET value
     * @return The MET value as a double
     */
    public double getMET() {
        return MET;
    }

    /**
     * Set activityID to new integer
     * @param activityID The new activity ID
     */
    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    /**
     * Set activity name to new string
     * @param activity The new activity name
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * Set MET value to new double
     * @param MET The new MET value
     */
    public void setMET(double MET) {
        this.MET = MET;
    }

    
}
