/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 100116544
 */
public class Exercise {
    private int exerciseID;
    private int minutes;
    private Date date;
    private Activity activity;
    private User user;
    private double distance;  
    
    public Exercise(int exerciseID, int userID, Activity activity, Date date, int minutes, double distance) {
        try {
            this.exerciseID = exerciseID;
            this.minutes = minutes;
            this.date = date;
            this.activity = activity;
            this.user = new Database().getUser(userID);
            this.distance = distance;
        } catch (Exception ex) {
            Logger.getLogger(Exercise.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
   
    
    public double getHours(){
        return this.minutes / 60.0;
    }
    
    public double getCaloriesBurnt() throws Exception{
        
        return this.user.getWeight() * this.activity.getMET() * this.getHours();
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public Date getDate() {
        return date;
    }

    public Activity getActivity() {
        return activity;
    }
    
    public String getActivityName(){
        return this.activity.getActivity();
    }
        
    public User getUser() {
        return user;
    }

    public double getDistance() {
        return distance;
    }

    public int getMinutes() {
        return minutes;
    }
    
}
