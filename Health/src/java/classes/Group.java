package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Group {

    private int groupID;
    private String groupName;
    private int userID;
    //private Goal groupGoal

    public Group(int groupID, String groupName, int userID) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.userID = userID;
    }

   

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
