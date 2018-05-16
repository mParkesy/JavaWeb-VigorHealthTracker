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
 * @author Daniel
 */
public class FoodLog {

    private int id;
    private Food food;
    private User user;
    private String meal;
    private Date date;

    public FoodLog(int id, Food food, int userID, String meal, Date date) {
        try {
            this.id = id;
            this.food = food;
            this.user = new Database().getUser(userID);
            this.meal = meal;
            this.date = date;
        } catch (Exception ex) {
            Logger.getLogger(FoodLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the foodID
     */
    public Food getFood() {
        return food;
    }

    /**
     * @param foodID the foodID to set
     */
    public void setFoodID(Food food) {
        this.food = food;
    }

    /**
     * @return the userID
     */
    public User getUser() {
        return user;
    }

    /**
     * @param userID the userID to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the meal
     */
    public String getMeal() {
        return meal;
    }

    /**
     * @param meal the meal to set
     */
    public void setMeal(String meal) {
        this.meal = meal;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

     public String toJSON(){
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\"id\": "  + this.id + ",");
        str.append("\"name\": " + "\"" +  this.food.getName() + "\",");
        str.append("\"carbs\": "  + this.food.getCarbs() + ",");
        str.append("\"protein\": "  + this.food.getProtein() + ",");
        str.append("\"sugar\": "  + this.food.getSugar() + ",");
        str.append("\"fat\": "  + this.food.getFat()+ ",");
        str.append("\"energy\": "  + this.food.getEnergy());
        str.append("}");
        return str.toString();
    }
}
