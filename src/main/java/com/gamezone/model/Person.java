package com.gamezone.model;

public abstract class Person {
    private String name;
    private String identification;
    private String phoneNumber;

    public Person(String name,String identification,String phoneNumber) {
        this.name = name;
        this.identification = identification ;
        this.phoneNumber = phoneNumber ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public abstract String getDescription();
}

