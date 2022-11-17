package com.ArkaBrianJSleepRJ.model;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable{
    public int accountId;
    public String name;
    public ArrayList<Date> booked;
    public String address;
    public Price price;
    public City city;
    public int size;
    public BedType bedType;
    public Facility facility;

    public Room(
            int id,
            int accountId,
            String name,
            ArrayList<Date> booked,
            String address,
            Price price, City city,
            int size,
            BedType bedType,
            Facility facility
    ) {
        super(id);
        this.accountId = accountId;
        this.name = name;
        this.booked = booked;
        this.address = address;
        this.price = price;
        this.city = city;
        this.size = size;
        this.bedType = bedType;
        this.facility = facility;
    }
}