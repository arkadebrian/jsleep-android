package com.ArkaBrianJSleepRJ.model;

public class Invoice extends Serializable{
    public enum RoomRating{
        NONE,
        BAD,
        NEUTRAL,
        GOOD
    }

    public enum PaymentStatus{
        FAILED,
        WAITING,
        SUCCESS
    }

    public int buyerId;
    public int renterId;

    public RoomRating rating;
    public PaymentStatus status;

    public Invoice(int id){
        super(id);
    }
}
