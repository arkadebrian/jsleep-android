package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


public class AboutMeActivity extends AppCompatActivity {
    TextView name, username, balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        name = findViewById(R.id.NameData);
        username = findViewById(R.id.EmailData);
        balance = findViewById(R.id.BalanceData);

        name.setText(MainActivity.accountGas.name);
        username.setText(MainActivity.accountGas.email);
        balance.setText(String.valueOf(MainActivity.accountGas.balance));
    }

}