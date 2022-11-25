package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ArkaBrianJSleepRJ.model.BedType;
import com.ArkaBrianJSleepRJ.model.Facility;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Spinner spinnerCity = (Spinner) findViewById(R.id.dropdownCity);
        spinnerCity.setAdapter(new ArrayAdapter<Facility>(this, android.R.layout.simple_spinner_item, Facility.values()));
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Spinner spinnerBedType = (Spinner) findViewById(R.id.dropdownBed);
        spinnerBedType.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
    }
}