package com.ArkaBrianJSleepRJ.model;

public class Price {
    public double discount;
    public double price;

    public Price(double discount, double price) {
        this.discount = discount;
        this.price = price;
    }

    public Price(double price) {
        this.discount = 0;
        this.price = price;
    }
}
