package classes;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Group {

    private int groupID;
    private String groupName;
    private User user;
    private String description;
    private String imagePath;
    //private Goal groupGoal

    public Group(int groupID, String groupName, int userID, String description) {
        try {
            this.groupID = groupID;
            this.groupName = groupName;
            this.user = new Database().getUser(userID);
            this.description = description;
        } catch (Exception ex) {
            Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public User getUser() {
        return user;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public String toJSON(){
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\"description\": " + "\"" + this.description + "\"" + ",");
        str.append("\"groupID\": " + "\"" + this.groupID + "\"" + ",");
        str.append("\"groupName\": " + "\"" + this.groupName + "\"" + ",");
        str.append("\"user\": " +  this.user.getID());
        str.append("}");
        return str.toString();
    }
    
}
