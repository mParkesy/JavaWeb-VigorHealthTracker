/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Date;

/**
 * A class that stores an exercise carried out by a user, it stores the 
 * user as an object, an activity as an object along with the date, duration
 * of exercise and distance
 */
public class Exercise {
    private int exerciseID;
    private int minutes;
    private Date date;
    private Activity activity;
    private User user;
    private double distance;  
    
    /**
     * A exercise constructor that takes all fields as a parameter and 
     * instantiates the exercise
     * @param exerciseID
     * @param userID
     * @param activity
     * @param date
     * @param minutes
     * @param distance 
     */
    public Exercise(int exerciseID, int userID, Activity activity, 
            Date date, int minutes, double distance) {
        try {
            this.exerciseID = exerciseID;
            this.minutes = minutes;
            this.date = date;
            this.activity = activity;
            this.user = new Database().getUser(userID);
            this.distance = distance;
        } catch (Exception ex) {
            System.out.println("Failed to construct user in "
                    + "Exercise constructer");
        }
    }

    /**
     * Getter for exercise ID
     * @return The exercise ID as an integer
     */
    public int getExerciseID() {
        return exerciseID;
    }

    /**
     * Getter for exercise date
     * @return The exercise date as a Date object
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for an activity object for the current exercise
     * @return The activity for the exercise as a activity object
     */
    public Activity getActivity() {
        return activity;
    }
    
    /**
     * Getter for activity name/type 
     * @return The activity name as a string
     */
    public String getActivityName(){
        return this.activity.getActivity();
    }
    
    /**
     * Getter for a user object for the current exercise
     * @return The user as a User object
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for the exercise distance for the current exercise
     * @return The distance as a double 
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Getter for the number of minutes exercised for the current exercise
     * @return The number of minutes exercised as a integer
     */
    public int getMinutes() {
        return minutes;
    }  
              
    /**
     * Calculates number of hours exercised from the minutes field
     * @return The number of hours as a double
     */
    public double getHours(){
        return this.minutes / 60.0;
    }
    
    /**
     * Calculates the number of calories burnt for the current exercise 
     * @return The number of calories burnt as an integer
     * @throws Exception If the double fails to round to the nearest integer
     */
    public int getCaloriesBurnt() throws Exception{
        double result = this.user.getWeight() 
                * this.activity.getMET() * this.getHours();
        return (int) Math.round(result);
    }
}
