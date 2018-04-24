package classes;

public class Group {

    private int groupID;
    private String groupName;
    private int userID;
    //private Goal groupGoal

    public Group(int groupID, String groupName, int userID) {
        this.groupID = groupID;
        this.groupName = groupName;
        if(userID == 0){
            this.userID = new Database().getGroupAdmin(groupID);
        } else {
            this.userID = userID;
        }
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

}
