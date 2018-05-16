/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;

public class Food {

    private int id;
    private String name;
    private double protein;
    private double fat;
    private double carbs;
    private double energy;
    private double sugar;

    public Food() {
        this.id = 0;
        this.name = "";
        this.protein = 0;
        this.fat = 0;
        this.carbs = 0;
        this.energy = 0;
        this.sugar = 0;
    }    
    
    public Food(int id, String name, double protein, double fat, double carbs, double energy, double sugar) {
        this.id = id;
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.energy = energy;
        this.sugar = sugar;
    }

    public static ArrayList<Food> filterFood(String in) throws Exception {
        int count = 0;
        ArrayList<Food> out = new ArrayList<>();

        for (Food food : new Database().allFood()) {
            if (food.name.toLowerCase().startsWith(in.toLowerCase())) {
                out.add(food);
                count++;
            }
            if (count == 10) {
                break;
            }
        }

        return out;
    }

    /**
     * @return the id
     */
    public int getID() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the protein
     */
    public double getProtein() {
        return protein;
    }

    /**
     * @param protein the protein to set
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * @return the fat
     */
    public double getFat() {
        return fat;
    }

    /**
     * @param fat the fat to set
     */
    public void setFat(double fat) {
        this.fat = fat;
    }

    /**
     * @return the carbs
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * @param carbs the carbs to set
     */
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    /**
     * @return the energy
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * @param energy the energy to set
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /**
     * @return the sugar
     */
    public double getSugar() {
        return sugar;
    }

    /**
     * @param sugar the sugar to set
     */
    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String toJSON(){
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\"id\": "  + this.id + ",");
        str.append("\"name\": " + "\"" +  this.name + "\",");
        str.append("\"carbs\": "  + this.carbs + ",");
        str.append("\"protein\": "  + this.protein + ",");
        str.append("\"sugar\": "  + this.sugar + ",");
        str.append("\"fat\": "  + this.fat + ",");
        str.append("\"energy\": "  + this.energy);
        str.append("}");
        return str.toString();
    }

}
