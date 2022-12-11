package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ArkaBrianJSleepRJ.model.Facility;
import com.ArkaBrianJSleepRJ.model.Payment;
import com.ArkaBrianJSleepRJ.model.Room;
import com.ArkaBrianJSleepRJ.request.BaseApiService;
import com.ArkaBrianJSleepRJ.request.UtilsApi;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDetailsAndPaymentActivity extends AppCompatActivity {
    TextView roomName, cityFromSpinner, bedTypeFromSpinner;
    EditText priceField, addressField, sizeField;
    CheckBox ac, fridge, wifi, bathtub, balcony, resto, pool, gym;
    Button order;
    MotionLayout motionLayout;

    TextView PriceView, BalanceView, CurrentBalance, FromDateText, ToDateText, VoucherText;
    EditText FromDateField, ToDateField, VoucherField;
    DatePickerDialog InputFromDate, InputToDate;
    Button PayButton, CancelButton;

    public static String fromDate, toDate;

    BaseApiService mApiService;
    Context mContext;

    public static Room selectedRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details_and_payment);
        mApiService = UtilsApi.getAPIService();
        mContext = this;
        motionLayout = findViewById(R.id.RoomDetails);
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

        //End State
        PriceView = findViewById(R.id.PriceView);
        BalanceView = findViewById(R.id.BalanceView);
        CurrentBalance = findViewById(R.id.CurrentBalance);
        FromDateText = findViewById(R.id.FromDateText);
        ToDateText = findViewById(R.id.ToDateText);
        VoucherText = findViewById(R.id.VoucherText);

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        FromDateField = findViewById(R.id.FromDateField);
        FromDateField.setOnClickListener(v ->{
            InputFromDate.show();
        });
        InputFromDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                FromDateField.setText(year + "-" + (month + 1) + "-" + day);
                fromDate = year + "-" + (month + 1) + "-" + day;
            }
        }, day, month, year);

        ToDateField = findViewById(R.id.ToDateField);
        ToDateField.setOnClickListener(v ->{
            InputToDate.show();
        });
        InputToDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                ToDateField.setText(year + "-" + (month + 1) + "-" + day);
                toDate = year + "-" + (month + 1) + "-" + day;
            }
        }, day, month, year);

        VoucherField = findViewById(R.id.VoucherField);

        PayButton = findViewById(R.id.PayButton);
        CancelButton = findViewById(R.id.CancelButton);

        PriceView.setText(String.valueOf(selectedRoom.price.price));
        BalanceView.setText(String.valueOf(MainActivity.accountGas.balance));

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                motionLayout.transitionToEnd();
            }
        });
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                motionLayout.transitionToStart();
            }
        });
        PayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Payment payment = createPayment(MainActivity.accountGas.id, MainActivity.accountGas.renter.id, selectedRoom.id, FromDateField.getText().toString(), ToDateField.getText().toString());
            }
        });
    }

    protected Payment createPayment(int buyerId, int renterId, int roomId, String fromDate, String toDate) {
        System.out.println(buyerId);
        System.out.println(renterId);
        System.out.println(roomId);
        System.out.println(fromDate);
        System.out.println(toDate);
        mApiService.create(buyerId, renterId, roomId, fromDate, toDate).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful()) {
                    Payment payment = response.body();
                    Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}