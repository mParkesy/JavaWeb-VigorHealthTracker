/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author 100116544
 */
public class Activity {

    private int activityID;
    private String activity;
    private double MET;

    public Activity(int activityID, String activity, double MET) {
        this.activityID = activityID;
        this.activity = activity;
        this.MET = MET;
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
