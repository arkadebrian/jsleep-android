package com.ArkaBrianJSleepRJ.model;

public class Renter extends Serializable{
    public String username;
    public String address;
    public String phoneNumber;

    public Renter(int id, String username, String address, String phoneNumber) {
        super(id);
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }



}
