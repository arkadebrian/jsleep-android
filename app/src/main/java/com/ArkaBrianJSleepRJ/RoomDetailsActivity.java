package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ArkaBrianJSleepRJ.model.Facility;
import com.ArkaBrianJSleepRJ.model.Room;
import com.ArkaBrianJSleepRJ.request.BaseApiService;
import com.ArkaBrianJSleepRJ.request.UtilsApi;

public class RoomDetailsActivity extends AppCompatActivity {
    TextView roomName, cityFromSpinner, bedTypeFromSpinner;
    EditText priceField, addressField, sizeField;
    CheckBox ac, fridge, wifi, bathtub, balcony, resto, pool, gym;
    Button order;

    BaseApiService mApiService;
    Context mContext;

    public static Room selectedRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        mApiService = UtilsApi.getAPIService();
        mContext = this;

        roomName = findViewById(R.id.RoomName);
        priceField = findViewById(R.id.PriceField);
        addressField = findViewById(R.id.AddressField);
        sizeField = findViewById(R.id.SizeField);

        ac = findViewById(R.id.IncludeAC);
        fridge = findViewById(R.id.includeFridge);
        wifi = findViewById(R.id.includeWiFi);
        bathtub = findViewById(R.id.includeBathtub);
        balcony = findViewById(R.id.includeBalcony);
        resto = findViewById(R.id.includeResto);
        pool = findViewById(R.id.includePool);
        gym = findViewById(R.id.includeGym);

        order = findViewById(R.id.OrderButton);

        roomName.setText(selectedRoom.name);
        roomName.setEnabled(false);
        priceField.setText(String.valueOf(selectedRoom.price.price));
        priceField.setEnabled(false);
        addressField.setText(selectedRoom.address);
        addressField.setEnabled(false);
        sizeField.setText(String.valueOf(selectedRoom.size));
        sizeField.setEnabled(false);

        cityFromSpinner = findViewById(R.id.CityField);
        bedTypeFromSpinner = findViewById(R.id.BedField);

        for (int i = 0; i < selectedRoom.facility.size(); i++) {
            if(selectedRoom.facility.get(i).equals(Facility.AC)){
                ac.setChecked(true);
            }
            if(selectedRoom.facility.get(i).equals(Facility.Balcony)){
                balcony.setChecked(true);
            }
            if(selectedRoom.facility.get(i).equals(Facility.Bathtub)){
                bathtub.setChecked(true);
            }
            if(selectedRoom.facility.get(i).equals(Facility.Refrigerator)){
                fridge.setChecked(true);
            }
            if(selectedRoom.facility.get(i).equals(Facility.FitnessCenter)){
                gym.setChecked(true);
            }
            if(selectedRoom.facility.get(i).equals(Facility.SwimmingPool)){
                pool.setChecked(true);
            }
            if(selectedRoom.facility.get(i).equals(Facility.Restaurant)){
                resto.setChecked(true);
            }
            if(selectedRoom.facility.get(i).equals(Facility.WiFi)) {
                wifi.setChecked(true);
            }
            ac.setClickable(false);
            balcony.setClickable(false);
            fridge.setClickable(false);
            wifi.setClickable(false);
            bathtub.setClickable(false);
            resto.setClickable(false);
            pool.setClickable(false);
            gym.setClickable(false);
        }

        cityFromSpinner.setText(selectedRoom.city.toString());
        bedTypeFromSpinner.setText(selectedRoom.bedType.toString());

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}