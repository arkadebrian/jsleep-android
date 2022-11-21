package com.ArkaBrianJSleepRJ.request;

import com.ArkaBrianJSleepRJ.model.Account;
import com.ArkaBrianJSleepRJ.model.Room;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

    @GET("/account/{id}")
    Call<Account> getAccount(@Path("id") int id);

}
