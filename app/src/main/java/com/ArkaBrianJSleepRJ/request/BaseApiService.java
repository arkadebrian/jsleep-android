package com.ArkaBrianJSleepRJ.request;

import com.ArkaBrianJSleepRJ.model.Account;
import com.ArkaBrianJSleepRJ.model.Room;

import retrofit2.Call;
import retrofit2.http.*;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);

    @POST("account/login")
    Call<Account> login(@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> register(@Query("name") String name, @Query("email") String email, @Query("password") String password);

}
