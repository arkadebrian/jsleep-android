package com.ArkaBrianJSleepRJ.model;

public class Account extends Serializable{
    public String name;
    public String password;
    public Renter renter;
    public String email;
    public double balance;

public Account(int id, String name, String password, Renter renter, String email, double balance) {
        super(id);
        this.name = name;
        this.password = password;
        this.renter = renter;
        this.email = email;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "\nAccount{\n" +
                "\tbalance ='" + this.balance + '\n' +
                "\temail ='" + this.email + '\n' +
                "\tname ='" + this.name + '\n' +
                "\tpassword ='" + this.password + '\n' +
                "\trenter ='" + this.renter + '\n' +
                '}';
    }

}
