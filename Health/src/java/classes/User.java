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
    private String postcode;
    private String nationality;
    private String email;
    private double height;
    private Date dob;   
    private double exerciseLevel;

    public User(int id, String username, String firstname, String lastname, String gender, String postcode, String nationality, String email, double height, Date dob, double exerciseLevel) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.postcode = postcode;
        this.nationality = nationality;
        this.email = email;
        this.height = height;
        this.dob = dob;
        this.exerciseLevel = exerciseLevel;
    }

    
   
    /**
     *
     * @param username
     * @param password
     * @param firstname
     * @param lastname
     * @param gender
     * @param dob
     * @param postcode
     * @param nationality
     * @param email
     * @param height
     * @param weight
     * @param exercise
     * @throws java.lang.Exception
     */
    public User(String username, String password, String firstname,
            String lastname, String gender, Date dob, String postcode, 
            String nationality, String email, double height, double weight,
            double exercise)
            throws Exception {
        
        
    }
    
    public int getAge(){ 
        LocalDate date = Instant.ofEpochMilli(this.dob.getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(date, 
                LocalDate.now()).getYears();
    }
    
    public double getBMI() throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(this.getWeight()/Math.pow((this.height/100), 2)));
    }
    
    public int getCalories() throws Exception{
        return (int) Math.rint(this.getBMR() * this.exerciseLevel);
    }

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

    public String getGender() {
        if(gender.equals("m")){
            return "Male";
        } else {
            return "Female";
        }
    }

    public String getPostcode() {
        return postcode;
    }

    public String getNationality() {
        return nationality;
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

    public Double getWeight() throws Exception{
        return new Database().currentWeight(id).getWeight();
    }
}
