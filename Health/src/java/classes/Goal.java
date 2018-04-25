/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Date;

/**
 *
 * @author Parkesy
 */
public abstract class Goal {
    private int goalID;
    private String goalName;
    private int userID;
    //private Date start;
    //private Date end;
    private boolean complete;

    public Goal(int goalID, String goalName, int userID, boolean complete) {
        this.goalID = goalID;
        this.goalName = goalName;
        this.userID = userID;
       // this.start = start;
        //this.end = end;
        this.complete = complete;
    }

    public int getGoalID() {
        return goalID;
    }

    public String getGoalName() {
        return goalName;
    }

    public int getUserID() {
        return userID;
    }

//    public Date getStart() {
//        return start;
//    }
//
//    public Date getEnd() {
//        return end;
//    }

    public boolean isComplete() {
        return complete;
    }

    public void setGoalID(int goalID) {
        this.goalID = goalID;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

//    public void setStart(Date start) {
//        this.start = start;
//    }
//
//    public void setEnd(Date end) {
//        this.end = end;
//    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    
    
}
