/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author xze15agu
 */
public class Sleep {
    private int sleepID;
    private User user;
    private DateTime bedTime;
    private DateTime wakeTime;
    private int sleepGrade;

    /**
     * Construct a standard sleep instance
     * @param sleepID
     * @param userID
     * @param bedTime
     * @param wakeTime
     * @param sleepGrade 
     */
    public Sleep(int sleepID, int userID, DateTime bedTime, 
            DateTime wakeTime, int sleepGrade) {
        try {
            this.sleepID = sleepID;
            this.user = new Database().getUser(userID);
            this.bedTime = bedTime;
            this.wakeTime = wakeTime;
            this.sleepGrade = sleepGrade;
        } catch (Exception ex) {
            System.out.println("Failed to construct user in sleep "
                    + "constructor");
        }
    }
    
    /**
     * @return the total amount of sleep
     */
    public double getTotalSleep(){
        Minutes mins = Minutes.minutesBetween(this.bedTime, this.wakeTime);
        //Hours hours = Hours.hoursBetween(this.bedTime, this.wakeTime);
        //int numHours = hours.getHours();
        int numMins = mins.getMinutes();
        return (double) numMins /60;
    }
    
    /**
     * @return the date the sleep started
     */
    public String getDateOfSleep(){
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        return this.bedTime.toString(dtf);
    }

    public DateTime getBedTime() {
        return bedTime;
    }

    public DateTime getWakeTime() {
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
    
    public int getDay() {
        return this.bedTime.getDayOfMonth();
    }

    public int getMonth() {
        return this.bedTime.getMonthOfYear();
    }

    public int getYear() {
        return this.bedTime.getYear();
    }
    
}