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
public class WeightGoal extends Goal{
    private Weight weightGoal;

    public WeightGoal(Weight weightGoal, int goalID, String goalName, int userID, boolean complete) {
        super(goalID, goalName, userID, complete);
        this.weightGoal = weightGoal;
    }

    public Weight getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(Weight weightGoal) {
        this.weightGoal = weightGoal;
    }
    
    
}
