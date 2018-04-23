/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author 100116544
 */
public class Activity {

    private int activityID;
    private String activity;
    private double MET;

    private final Connection con;

    public Activity(int activityID, String activity, double MET) {
        this.activityID = activityID;
        this.activity = activity;
        this.MET = MET;
        this.con = null;
    }

    public int getActivityID() {
        return activityID;
    }

    public String getActivity() {
        return activity;
    }

    public double getMET() {
        return MET;
    }

}
