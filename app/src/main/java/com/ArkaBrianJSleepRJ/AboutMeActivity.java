package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ArkaBrianJSleepRJ.model.Account;
import com.ArkaBrianJSleepRJ.model.Renter;
import com.ArkaBrianJSleepRJ.request.BaseApiService;
import com.ArkaBrianJSleepRJ.request.UtilsApi;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText balanceTopUp;
    Context mContext;
    TextView name, username, balance;

    Button topUp;

    TextView renterNameRegistered, renterAddressRegistered, renterPhoneRegistered;
    EditText renterName, renterAddress, renterPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mApiService = UtilsApi.getAPIService();
        mContext = this;

        Button jadiRenter = findViewById(R.id.jadiRegisterRenter);
        Button gaJadi = findViewById(R.id.cancelRegister);
        Button OrderList = findViewById(R.id.OrderListButton);

        MotionLayout motionLayout = findViewById(R.id.motionLayout);
        balance = findViewById(R.id.BalanceData);
        name = findViewById(R.id.NameData);
        username = findViewById(R.id.EmailData);

        balanceTopUp = findViewById(R.id.BalanceShower);
        topUp = findViewById(R.id.topUpButton);

        renterNameRegistered = findViewById(R.id.renterNameData);
        renterAddressRegistered = findViewById(R.id.renterAddressData);
        renterPhoneRegistered = findViewById(R.id.renterPhoneData);

        name.setText(MainActivity.accountGas.name);
        username.setText(MainActivity.accountGas.email);
        balance.setText(String.valueOf(MainActivity.accountGas.balance));

        renterName = findViewById(R.id.renterNameInput);
        renterAddress = findViewById(R.id.renterAddressInput);
        renterPhone = findViewById(R.id.renterPhoneInput);

        if(MainActivity.accountGas.renter != null){
            ((MotionLayout)findViewById(R.id.motionLayout)).transitionToState(R.id.end_c);
            renterNameRegistered.setVisibility(View.VISIBLE);
            renterAddressRegistered.setVisibility(View.VISIBLE);
            renterPhoneRegistered.setVisibility(View.VISIBLE);
            renterNameRegistered.setText(MainActivity.accountGas.renter.username);
            renterAddressRegistered.setText(MainActivity.accountGas.renter.address);
            renterPhoneRegistered.setText(MainActivity.accountGas.renter.phoneNumber);
        }
        else{
            jadiRenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(renterName.getText().toString().equals("") || renterAddress.getText().toString().equals("") || renterPhone.getText().toString().equals("")) {
                        Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Renter renter = requestRenter(renterName.getText().toString(), renterAddress.getText().toString(), renterPhone.getText().toString());
                        Intent intent = new Intent(AboutMeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                }
            });

            gaJadi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MotionLayout)findViewById(R.id.motionLayout)).transitionToStart();
                }
            });
        }

        topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(balanceTopUp.getText().toString().equals("")){
                    Toast.makeText(mContext, "Please fill the balance", Toast.LENGTH_SHORT).show();
                }
                else{
                    Double balanceTopUpDouble = Double.parseDouble(balanceTopUp.getText().toString());
                    Boolean isTopUp = requestTopUp(MainActivity.accountGas.id, balanceTopUpDouble);
                }
            }
        });

        OrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutMeActivity.this, OrderListActivity.class);
                startActivity(intent);
            }
        });

    }

    protected Renter requestRenter(String name, String address, String phone){

        mApiService.registerRenter(MainActivity.accountGas.id, name, address, phone).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if (response.isSuccessful()){
                    MainActivity.accountGas.renter = response.body();
                    Toast.makeText(mContext, "Register Renter Success", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(AboutMeActivity.this, MainActivity.class);
                    startActivity(move);
                } else {
                    Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }



    protected Boolean requestTopUp(int id, double balance ){
        System.out.println("Id: " + id);
        System.out.println("TopUp: " + balance);
        mApiService.topUpBalance(id,balance).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Boolean res = response.body();
                    System.out.println("Top Up Successful") ;
                    //MainActivity.savedAccount.balance += balance;
                    MainActivity.reloadAccount(MainActivity.accountGas.id);
                    Toast.makeText(mContext, "Top Up Successful", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(AboutMeActivity.this, AboutMeActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("TOPUP ERROR!!");
                System.out.println(t.toString());
                Toast.makeText(mContext,"Top Up Failed",Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}