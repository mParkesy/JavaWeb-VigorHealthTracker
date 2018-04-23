package classes;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Weight {

    private int weightID;
    private int userID;
    private double weight;
    private Date date;

    public Weight(int weightID, int userID, double weight, Date date) {
        this.weightID = weightID;
        this.userID = userID;
        this.weight = weight;
        this.date = date;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return the date
     */
    public String getDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(this.date);
    }

    public int getDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public int getYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

}
