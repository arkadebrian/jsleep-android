package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ArkaBrianJSleepRJ.model.BedType;
import com.ArkaBrianJSleepRJ.model.City;
import com.ArkaBrianJSleepRJ.model.Facility;
import com.ArkaBrianJSleepRJ.model.Price;
import com.ArkaBrianJSleepRJ.model.Room;
import com.ArkaBrianJSleepRJ.request.BaseApiService;
import com.ArkaBrianJSleepRJ.request.UtilsApi;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText name, price, address, size;
    Context mContext;

    ArrayList<Facility> facilities = new ArrayList<>();
    BedType bed;
    City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mApiService = UtilsApi.getAPIService();
        mContext = this;

        name = findViewById(R.id.roomNameInput);
        price = findViewById(R.id.roomPriceInput);
        address = findViewById(R.id.roomAddressInput);
        size = findViewById(R.id.roomSizeInput);

        Spinner spinnerCity = (Spinner) findViewById(R.id.dropdownCity);
        spinnerCity.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));
        Spinner spinnerBedType = (Spinner) findViewById(R.id.dropdownBed);
        spinnerBedType.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        Button create = findViewById(R.id.createRoomButton);
        Button cancel = findViewById(R.id.cancelCreateRoomButton);

        CheckBox ac = findViewById(R.id.facilityACinclude);
        CheckBox fridge = findViewById(R.id.facilityFridgeInclude);
        CheckBox wifi = findViewById(R.id.facilityWifiInclude);
        CheckBox bathtub = findViewById(R.id.facilityBathtubInclude);
        CheckBox balcony = findViewById(R.id.facilityBalconyInclude);
        CheckBox restaurant = findViewById(R.id.facilityRestaurantInclude);
        CheckBox pool = findViewById(R.id.facilitySwimmingPoolInclude);
        CheckBox gym = findViewById(R.id.facilityGymInclude);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double priceObj = new Double(price.getText().toString());
                Integer sizeObj = new Integer(size.getText().toString());

                double priceRoom = priceObj.parseDouble(price.getText().toString());
                int roomSize = sizeObj.parseInt(size.getText().toString());

                if(ac.isChecked()){
                    facilities.add(Facility.AC);
                }
                if(fridge.isChecked()){
                    facilities.add(Facility.Refrigerator);
                }
                if(wifi.isChecked()){
                    facilities.add(Facility.WiFi);
                }
                if(bathtub.isChecked()){
                    facilities.add(Facility.Bathtub);
                }
                if(balcony.isChecked()){
                    facilities.add(Facility.Balcony);
                }
                if(restaurant.isChecked()){
                    facilities.add(Facility.Restaurant);
                }
                if(pool.isChecked()){
                    facilities.add(Facility.SwimmingPool);
                }
                if(gym.isChecked()){
                    facilities.add(Facility.FitnessCenter);
                }

                String cities = spinnerCity.getSelectedItem().toString();
                city = City.valueOf(cities);
                String bedType = spinnerBedType.getSelectedItem().toString();
                bed = BedType.valueOf(bedType);

                Room room = requestRoom(MainActivity.accountGas.id, name.getText().toString(), roomSize, priceRoom, city, bed, facilities, address.getText().toString());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    protected Room requestRoom(int accountId, String name, int size, double price, City city, BedType bedType, ArrayList<Facility> facility, String address){
        System.out.println("ID : " + accountId);
        System.out.println("Name : " + name);
        System.out.println("Bed : " + bedType.toString());
        mApiService.createRoom(accountId, name, size, price, facility, city, address, bedType).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.isSuccessful()){
                    Room room = response.body();
                    Intent intent = new Intent(CreateRoomActivity.this, MainActivity.class);
                    Toast.makeText(mContext, "Room Created", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Room Failed to Create", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}