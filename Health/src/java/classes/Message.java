/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author owner
 */
public class Message {
    private String message;
    private User sender;
    private User recipent;

    /**
     * Message constructor that passes all possible parameters
     * @param message
     * @param sender
     * @param recipent 
     */
    public Message( String message, User sender, User recipent) {
        this.message = message;
        this.sender = sender;
        this.recipent = recipent;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the senderID
     */
    public User getSender() {
        return sender;
    }

    /**
     * @param sender as user object
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * @return the recipentID
     */
    public User getRecipent() {
        return recipent;
    }

    /**
     * @param recipentID the recipentID to set
     */
    public void setRecipent(User recipentID) {
        this.recipent = recipentID;
    }
}
