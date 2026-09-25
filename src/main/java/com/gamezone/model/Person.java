package com.gamezone.model;
import java.io.Serializable;

/**
 * represents a generic person that interacts with the store.
 * this class is abstract because a person must always be specialized
 * as either a customer or a seller
 */

public abstract class Person  implements Serializable {
    private String name;
    private String identification;
    private String phoneNumber;

    /**
     * creates a new person with the given basic information
     *
     * @param name the person`s full name
     * @param identification the person`s identification number
     * @param phoneNumber the person`s contact phone number
     *
     */
    public Person(String name,String identification,String phoneNumber) {
        this.name = name;
        this.identification = identification ;
        this.phoneNumber = phoneNumber ;
    }

    /**
     * *returns the person`s name
     *
     * @return the name of the person
     */

    public String getName() {
        return name;
    }

    /**
     * sets or updates the person's name
     *  @param name the new full name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the person's identification number
     *
     * @return
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * sets or updates the person's identification number
     *
     * @param identification
     */
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    /**
     * returns the person's contact phone number
     *
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets or updates the person's contact phone number
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * returns a description of the person, defined by each subclass
     *
     * @return a textual description specific to the person's role
     */

    public abstract String getDescription();
}

