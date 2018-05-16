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
    public Goal(int userID, String type) throws Exception {
        this.user = new Database().getUser(userID);
        this.start = 0;
        this.target = 0;
        this.type = type;
        
    }
    
    public Goal(int goalID, int userID, String type) throws Exception {
        this.user = new Database().getUser(userID);
        this.start = 0;
        this.target = 0;
        this.type = type;
        this.goalID = goalID;
        
    }
    

    public Goal(double start, double target, int userID, String type) throws Exception {
        this.target = target;
        try {
            this.user = new Database().getUser(userID);
        } catch (Exception ex) {
            System.out.println("Failed to construct user in goal construction");
        }
        this.type = type;
        this.start = start;
    }

    public Goal(int goalID,double start, double target, int userID, String type) throws Exception {
        this.target = target;
        try {
            this.user = new Database().getUser(userID);
        } catch (Exception ex) {
            System.out.println("Failed to construct user in goal construction");
        }
        this.type = type;
        this.start = start;
        this.goalID = goalID;
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


    public String toJSON() {
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\"start\": " + "\"" + this.start + "\"" + ",");
        str.append("\"target\": " + "\"" + this.target + "\"" + ",");
        str.append("\"type\": " + "\"" + this.type + "\"" + ",");
        str.append("\"user\": " + this.user.getID());
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
