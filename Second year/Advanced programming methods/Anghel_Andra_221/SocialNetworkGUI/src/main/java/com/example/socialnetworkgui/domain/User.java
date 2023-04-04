package com.example.socialnetworkgui.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * class for a user that extends Entity
 */
public class User extends Entity<Long>{
    private String firstName;
    private String lastName;
    private List<User> friends;

    private String email;
    private String password;

    private List<User> requests = new ArrayList<>();


    /**
     * Constructor for user
     * @param firstName string
     * @param lastName string
     * @param friends list of users
     */
    public User(String firstName, String lastName, List<User> friends, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new ArrayList<>();
        this.email=email;
        this.password=password;
    }
    public User(String firstName, String lastName, List<User> friends, String email, String password, List<User> request) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new ArrayList<>();
        this.email=email;
        this.password=password;
        this.requests=new ArrayList<>();
    }

    public User (){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void addFriend(User user){
        friends.add(user);
    }
    public void deleteFriend(User user) {
        friends.remove(user);
    }

    public List<User> getRequests() {
        return requests;
    }

    public void addRequests(User user){
        requests.add(user);
    }
    public void deleteRequest(User user) {
        requests.remove(user);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' + "}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return firstName.equals(user.firstName) && lastName.equals(user.lastName) && Objects.equals(friends, user.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, friends);
    }

}