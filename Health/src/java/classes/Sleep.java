/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xze15agu
 */
public class Sleep {
    private int sleepID;
    private User user;
    private Date bedTime;
    private Date wakeTime;
    private int sleepGrade;

    /**
     * Construct a standard sleep instance
     * @param sleepID
     * @param userID
     * @param bedTime
     * @param wakeTime
     * @param sleepGrade 
     */
    public Sleep(int sleepID, int userID, Date bedTime, Date wakeTime, int sleepGrade) {
        try {
            this.sleepID = sleepID;
            this.user = new Database().getUser(userID);
            this.bedTime = bedTime;
            this.wakeTime = wakeTime;
            this.sleepGrade = sleepGrade;
        } catch (Exception ex) {
            Logger.getLogger(Sleep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    /**
     * @return the total amount of sleep
     */
    public double getTotalSleep(){
        double time = getWakeTime().getTime() - getBedTime().getTime();
        return time / (1000*60*60);
    }
    
    
    
    /**
     * @return the date the sleep started
     */
    public String getDateOfSleep(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(this.bedTime);
    }

    /**
     * @return the bedTime
     */
    public Date getBedTime() {
        return bedTime;
    }

    /**
     * @return the wakeTime
     */
    public Date getWakeTime() {
        return wakeTime;
    }

    /**
     * @return the sleepGrade
     */
    public int getSleepGrade() {
        return sleepGrade;
    }

    public int getSleepID() {
        return sleepID;
    }

    public User getUser() {
        return user;
    }
    
    
}