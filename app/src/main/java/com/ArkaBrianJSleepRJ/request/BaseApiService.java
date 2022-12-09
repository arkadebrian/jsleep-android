package com.ArkaBrianJSleepRJ.request;

import com.ArkaBrianJSleepRJ.model.Account;
import com.ArkaBrianJSleepRJ.model.BedType;
import com.ArkaBrianJSleepRJ.model.City;
import com.ArkaBrianJSleepRJ.model.Facility;
import com.ArkaBrianJSleepRJ.model.Price;
import com.ArkaBrianJSleepRJ.model.Renter;
import com.ArkaBrianJSleepRJ.model.Room;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.*;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);

    @POST("account/login")
    Call<Account> login(
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("account/register")
    Call<Account> register(
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(
            @Path("id") int id,
            @Query("username") String username,
            @Query("address") String address,
            @Query("phoneNumber") String phoneNumber
    );

    @POST("account/{id}/topUp")
    Call<Boolean> topUpBalance(
            @Path("id") int id,
            @Query("balance") double balance
    );

    @GET("room/getAllRoom")
    Call<ArrayList<Room>> getAllRoom(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    @POST("room/createRoom")
    Call<Room> createRoom(
            @Query("accountId") int accountId,
            @Query("name") String name,
            @Query("size") int size,
            @Query("price") double price,
            @Query("facility") ArrayList<Facility> facility,
            @Query("city") City city,
            @Query("address") String address,
            @Query("bedType")BedType bedType
    );

}
