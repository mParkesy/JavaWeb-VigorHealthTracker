package classes;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author 100116544
 */
public class User {

    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String gender;
    private String email;
    private double height;
    private Date dob;   
    private double exerciseLevel;

    /**
     * User constructor
     * @param id
     * @param username
     * @param firstname
     * @param lastname
     * @param gender
     * @param email
     * @param height
     * @param dob
     * @param exerciseLevel 
     */
    public User(int id, String username, String firstname, String lastname, String gender, String email, double height, Date dob, double exerciseLevel) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.height = height;
        this.dob = dob;
        this.exerciseLevel = exerciseLevel;
    }
    
    
    /**
     * Calculates age from DOB
     * @return Age as int
     */
    public int getAge(){ 
        LocalDate date = Instant.ofEpochMilli(this.dob.getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(date, 
                LocalDate.now()).getYears();
    }
    
    /**
     * Calculates BMI
     * @return BMI as double
     * @throws Exception 
     */
    public double getBMI() throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(this.getWeight()/Math.pow((this.height/100), 2)));
    }
    
    /**
     * Multiples BMI by activity level
     * @return Calories as int
     * @throws Exception 
     */
    public int getCalories() throws Exception{
        return (int) Math.rint(this.getBMR() * this.exerciseLevel);
    }

    /**
     * Calculates BMR
     * @return BMR as int
     * @throws Exception 
     */
    public int getBMR() throws Exception{
        double result = 10 * this.getWeight() + 6.25 * this.height - 5 
                * this.getAge();
        if(this.gender.equals("m")){
            result += 5;
        } else {
            result -= 161;
        }
        
        return (int) Math.rint(result);    
    }
    
    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    /**
     * Makes sure gender outputs correctly
     * @return 
     */
    public String getGender() {
        if(gender.equals("m")){
            return "Male";
        } else {
            return "Female";
        }
    }

    public String getEmail() {
        return email;
    }

    public double getHeight() {
        return height;
    }
    
    public String getDob() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(this.dob);
    }
    
    /**
     * Gets the most recent weight for a user
     * @return The weight as a double
     * @throws Exception 
     */
    public Double getWeight() throws Exception{
        return new Database().currentWeight(id).getWeight();
    }
    
    public int lose500() throws Exception{
        return this.getCalories()-500;
    }
    
    public int lose1000() throws Exception{
        return this.getCalories()-1000;
    }
    
    public int gain500() throws Exception{
        return this.getCalories()+500;
    }
    
    public int gain1000() throws Exception{
        return this.getCalories()+1000;
    }
    
    public String toJSON(){
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\"id\": "  + this.id + ",");
        str.append("\"username\": " + "\"" +  this.username + "\"");
        str.append("}");
        return str.toString();
    }
    
    /**
     * Get most recent exercise
     * @return
     * @throws Exception 
     */
    public Double getExercise() throws Exception{
        return new Database().getMaxExercise(id).getDistance();

    }
}
