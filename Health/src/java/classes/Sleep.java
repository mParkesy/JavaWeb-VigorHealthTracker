/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author xze15agu
 */
public class Sleep {
    private int sleepID;
    private int userID;
    private Date bedTime;
    private Date wakeTime;
    private int sleepGrade;
    
    private final Connection con;

    /**
     * Construct a standard sleep instance
     * @param sleepID
     * @param userID
     * @param bedTime
     * @param wakeTime
     * @param sleepGrade 
     */
    public Sleep(int sleepID, int userID, Date bedTime, Date wakeTime, int sleepGrade) {
        this.sleepID = sleepID;
        this.userID = userID;
        this.bedTime = bedTime;
        this.wakeTime = wakeTime;
        this.sleepGrade = sleepGrade;
        this.con = null;
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

    public int getUserID() {
        return userID;
    }
    
    
}