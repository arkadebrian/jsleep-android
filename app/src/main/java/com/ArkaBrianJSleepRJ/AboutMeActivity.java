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

import com.ArkaBrianJSleepRJ.model.Renter;
import com.ArkaBrianJSleepRJ.request.BaseApiService;
import com.ArkaBrianJSleepRJ.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText balanceTopUp;
    Context mContext;
    TextView name, username, balance;

    TextView renterNameRegistered, renterAddressRegistered, renterPhoneRegistered;
    EditText renterName, renterAddress, renterPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        Button jadiRenter = findViewById(R.id.jadiRegisterRenter);
        Button gaJadi = findViewById(R.id.cancelRegister);
        MotionLayout motionLayout = findViewById(R.id.motionLayout);
        name = findViewById(R.id.NameData);
        username = findViewById(R.id.EmailData);
        balance = findViewById(R.id.BalanceData);

        renterNameRegistered = findViewById(R.id.renterNameData);
        renterAddressRegistered = findViewById(R.id.renterAddressData);
        renterPhoneRegistered = findViewById(R.id.renterPhoneData);

        name.setText(MainActivity.accountGas.name);
        username.setText(MainActivity.accountGas.email);
        balance.setText(String.valueOf(MainActivity.accountGas.balance));

        renterNameRegistered.setText(MainActivity.accountGas.renter.username);
        renterAddressRegistered.setText(MainActivity.accountGas.renter.address);
        renterPhoneRegistered.setText(MainActivity.accountGas.renter.phoneNumber);

        renterName = findViewById(R.id.renterNameInput);
        renterAddress = findViewById(R.id.renterAddressInput);
        renterPhone = findViewById(R.id.renterPhoneInput);

        jadiRenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(renterName.getText().toString().equals("") || renterAddress.getText().toString().equals("") || renterPhone.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Renter renter = requestRenter(MainActivity.accountGas.id, renterName.getText().toString(), renterAddress.getText().toString(), renterPhone.getText().toString());
                }

            }
        });

        gaJadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MotionLayout)findViewById(R.id.motionLayout)).transitionToStart();
            }
        });

        if(MainActivity.accountGas.renter != null){
            gaJadi.setVisibility(View.GONE);
            jadiRenter.setVisibility(View.GONE);
            renterName.setVisibility(View.GONE);
            renterAddress.setVisibility(View.GONE);
            renterPhone.setVisibility(View.GONE);
            ((MotionLayout)findViewById(R.id.motionLayout)).transitionToEnd();
            findViewById(R.id.renterName).setVisibility(View.VISIBLE);
            findViewById(R.id.renterAddress).setVisibility(View.VISIBLE);
            findViewById(R.id.renterPhone).setVisibility(View.VISIBLE);
            renterNameRegistered.setVisibility(View.VISIBLE);
            renterAddressRegistered.setVisibility(View.VISIBLE);
            renterPhoneRegistered.setVisibility(View.VISIBLE);
        }

    }

    protected Renter requestRenter(int id, String name, String address, String phone){
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

}