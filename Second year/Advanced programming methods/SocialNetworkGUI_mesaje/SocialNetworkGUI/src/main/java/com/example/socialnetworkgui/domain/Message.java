package com.example.socialnetworkgui.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Message class which extends an Entity
 */
public class Message extends Entity<Long> {

    private User from;
    private User to;
    private String message;

    private String user1Firstname;
    private String user1Lastname;
    private String user2Firstname;
    private String user2Lastname;



    /**
     * Constructor for a normal message
     * @param from - User who sends the message
     * @param to - List of users where the message goes
     * @param message - String that contains the message
     */
    public Message(User from, User to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public String getUser1Firstname() {
        return user1Firstname;
    }

    public void setUser1Firstname(String user1Firstname) {
        this.user1Firstname = user1Firstname;
    }

    public String getUser1Lastname() {
        return user1Lastname;
    }

    public void setUser1Lastname(String user1Lastname) {
        this.user1Lastname = user1Lastname;
    }

    public String getUser2Firstname() {
        return user2Firstname;
    }

    public void setUser2Firstname(String user2Firstname) {
        this.user2Firstname = user2Firstname;
    }

    public String getUser2Lastname() {
        return user2Lastname;
    }

    public void setUser2Lastname(String user2Lastname) {
        this.user2Lastname = user2Lastname;
    }

    /**
     * Getter function for User from
     * @return the user who sends the message
     */
    public User getFrom() {
        return from;
    }

    /**
     * Setter function for User from
     * @param from - new User for the message
     */
    public void setFrom(User from) {
        this.from = from;
    }

    /**
     * Getter function for destination of the message
     * @return - List of users that get the message
     */
    public User getTo() {
        return to;
    }



    /**
     * Setter function for List of Users
     * @param to - new List of Users
     */
    public void setTo(User to) {
        this.to = to;
    }

    /**
     * Getter function for message
     * @return the string containing the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter function for message
     * @param message - new string message
     */
    public void setMessage(String message) {
        this.message = message;
    }




    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return from.equals(message1.from) && to.equals(message1.to) && message.equals(message1.message) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, message);
    }



}