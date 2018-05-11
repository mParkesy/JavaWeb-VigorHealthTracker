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
 * @author Parkesy
 */

//BASIC WEIGHT BASED GOAL IMPLEMENTATION
public class Goal {
    private int goalID;
    //private String goalName;
    
    private User user;
    private double start;
    private double target;
    private String type;
    

    //blank goal
    public Goal(int userID,String type) throws Exception{
        this.user = new Database().getUser(userID);
        this.start = 0;
        this.target = 0;
        this.type = type;
       
    }
    
    public Goal(double start, double target, int userID,String type) throws Exception {
       
        this.target = target;
        try {
            this.user = new Database().getUser(userID);
        } catch (Exception ex) {
            Logger.getLogger(Goal.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        this.start = start;
    }

  
    
    
    /*
    public String getGoalName() {
        return goalName;
    }
    */
    public User getUser() {
        return user;
    }


    public void setGoalID(int goalID) {
        this.goalID = goalID;
    }
    
    
    public void setUser(User user) {
        this.user = user;
    }

    public double getDifference(){
        return this.start - this.getTarget();
    }
    
     public double left(){
        double left = 0;
        try {
            left  = getUser().getWeight() - this.getTarget();
        } catch (Exception ex) {
            Logger.getLogger(Goal.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(left < 0){
            left = 0;
           
        }
        
        return left;
     }
     
     public String toJSON(){
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\"start\": " + "\"" + this.start + "\"" + ",");
        str.append("\"target\": " + "\"" + this.getTarget()+ "\"" + ",");
        str.append("\"type\": " + "\"" + this.getType() + "\"" + ",");
        str.append("\"user\": " +  this.getUser().getID());
        str.append("}");
        return str.toString();
     }

    /**
     * @return the goalID
     */
    public int getGoalID() {
        return goalID;
    }

    /**
     * @return the target
     */
    public double getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(double target) {
        this.target = target;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
}
