package classes;

public class Group {

    private int groupID;
    private String groupName;
    private int userID;
    private String description;
    private String imagePath;
    //private Goal groupGoal

    public Group(int groupID, String groupName, int userID, String description) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.userID = userID;
        this.description = description;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getUserID() {
        return userID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    
}
