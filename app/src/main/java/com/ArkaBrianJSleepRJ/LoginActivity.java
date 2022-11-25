package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ArkaBrianJSleepRJ.model.Account;
import com.ArkaBrianJSleepRJ.request.BaseApiService;
import com.ArkaBrianJSleepRJ.request.UtilsApi;
import com.google.gson.internal.bind.util.ISO8601Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username, password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getAPIService();
        mContext = this;
        Button login = findViewById(R.id.loginButton);
        TextView register = findViewById(R.id.registerNow);
        username = findViewById(R.id.emailInputLogin);
        password = findViewById(R.id.passwordInputLogin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestLogin(username.getText().toString(), password.getText().toString());
//                Intent move = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(move);
            }
        });

    }

    protected Account requestLogin(String email, String password){
        mApiService.login(email, password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()){
                    MainActivity.accountGas = response.body();
                    System.out.println(MainActivity.accountGas.toString());
                    Toast.makeText(mContext, "Hai Ganteng", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Email atau Password salah", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}