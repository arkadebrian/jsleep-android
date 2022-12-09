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
    public ArrayList<Facility> facility = new ArrayList<>();

    public Room(
            int id,
            int accountId,
            String name,
            int size,
            Price price,
            ArrayList<Facility> facility,
            City city,
            String address,
            BedType bedType
    ) {
        super(id);
        this.accountId = accountId;
        this.name = name;
        this.booked = new ArrayList<>();
        this.address = address;
        this.price = price;
        this.city = city;
        this.size = size;
        this.bedType = bedType;
        this.facility.addAll(facility);
    }
}
