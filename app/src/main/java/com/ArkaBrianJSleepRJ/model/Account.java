package com.ArkaBrianJSleepRJ.model;

public class Account extends Serializable{
    public String username;
    public String password;
    public Renter renter;
    public String email;
    public double balance;

public Account(int id, String username, String password, Renter renter, String email, double balance) {
        super(id);
        this.username = username;
        this.password = password;
        this.renter = renter;
        this.email = email;
        this.balance = balance;
    }

}
