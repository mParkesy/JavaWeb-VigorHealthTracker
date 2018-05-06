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
    private int id;
    private String message;
    private User sender;
    private User recipent;
    //private DateTime

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
     * @param senderID the senderID to set
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
        this.recipent = recipent;
    }
}
