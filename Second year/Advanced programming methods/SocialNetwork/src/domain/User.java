package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * define a User that extends Entity
 * firstName-String
 * lastName-String
 */
public class User extends Entity<Long> {

    /**
     * First and last name of a user
     */
    private String firstName, lastName;



    /**
     * constructor
     * @param firstName of the user
     * @param lastName of the user
     */
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }




    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    @Override
    public String toString() {
        String s;
        s = "User{ id='" + this.getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + "' }" ;
        return s;
    }
}