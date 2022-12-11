package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ArkaBrianJSleepRJ.model.Payment;
import com.ArkaBrianJSleepRJ.model.Room;
import com.ArkaBrianJSleepRJ.request.BaseApiService;
import com.ArkaBrianJSleepRJ.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity {
    EditText page;
    ListView listView;
    Button prev, next;
    int currentPage = 0;

    ArrayList<Payment> acc;
    ArrayList<Payment> temp;
    ArrayList<String> names;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        mApiService = UtilsApi.getAPIService();
        mContext = this;
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);

        page = findViewById(R.id.pageInput);

        listView = findViewById(R.id.listViewer);
        listView.setOnItemClickListener(this::onItemClick);
        acc = getAllOrder(MainActivity.accountGas.id, currentPage, 7);

    }

    protected static ArrayList<String> getOrderName(ArrayList<Payment> payments){
        ArrayList<String> names = new ArrayList<>();
        String fromDate, toDate;

        for (int i = 0; i < payments.size(); i++) {
            names.add(MainActivity.RoomsName.get(payments.get(i).id));
        }
        return names;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, RoomDetailsAndPaymentActivity.class);
//        RoomDetailsAndPaymentActivity.selectedRoom = temp.get(position);
        startActivity(intent);
    }

    protected ArrayList<Payment> getAllOrder(int accountId, int page, int pageSize){
        mApiService.getAllOrder(accountId, page, pageSize).enqueue(new Callback<ArrayList<Payment>>() {
            @Override
            public void onResponse(Call<ArrayList<Payment>> call, Response<ArrayList<Payment>> response) {
                if(response.isSuccessful()){
                    temp = response.body();
                    names = getOrderName(temp);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, names);
                    listView.setAdapter(adapter);
                    Toast.makeText(mContext, "Got All Order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Payment>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "List View could not be displayed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}