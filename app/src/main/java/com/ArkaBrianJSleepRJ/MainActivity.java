package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ArkaBrianJSleepRJ.model.Account;
import com.ArkaBrianJSleepRJ.model.Room;
import com.ArkaBrianJSleepRJ.request.BaseApiService;
import com.ArkaBrianJSleepRJ.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static Account accountGas;
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<String> RoomsName = new ArrayList<>();

    static BaseApiService mApiService;
    Context mContext;
    EditText page;
    ListView listView;
    Button prev, next;
    int currentPage = 0;

    ArrayList<Room> acc;
    ArrayList<Room> temp;
    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = UtilsApi.getAPIService();
        mContext = this;
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);

        page = findViewById(R.id.pageInput);

        listView = findViewById(R.id.listViewer);
        listView.setOnItemClickListener(this::onItemClick);
        acc = getAllRoom(currentPage, 10);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage <= 1) {
                    currentPage = 1;
                    Toast.makeText(mContext, "Page 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPage--;
                try {
                    acc = getAllRoom(currentPage - 1, 10);
                    Toast.makeText(mContext, "Page " + currentPage, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(temp.size()>currentPage){
                    currentPage=1;
                    return;
                }
                currentPage++;
                try {
                    acc = getAllRoom(currentPage-1, 1);  //return null
                    Toast.makeText(mContext, "Page "+currentPage, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.account_button:
                Intent intent = new Intent(this, AboutMeActivity.class);
                startActivity(intent);
                return true;
            case R.id.add_button:
                if(accountGas.renter != null) {
                    Intent intent1 = new Intent(this, CreateRoomActivity.class);
                    startActivity(intent1);
                    return true;
                }
                else {
                    Toast.makeText(this, "You are not allowed to create room", Toast.LENGTH_SHORT).show();
                    return true;
                }

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    protected static ArrayList<String> getRoomName(ArrayList<Room> rooms){
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            names.add(rooms.get(i).name);
        }
        return names;
    }

    protected ArrayList<Room> getAllRoom(int page, int pageSize){
        mApiService.getAllRoom(page, pageSize).enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
                if(response.isSuccessful()){
                    temp = response.body();
                    names = getRoomName(temp);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, names);
                    listView.setAdapter(adapter);
                    Toast.makeText(mContext, "Got All Room", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Room>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "List View could not be displayed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, RoomDetailsAndPaymentActivity.class);
        RoomDetailsAndPaymentActivity.selectedRoom = temp.get(position);
        startActivity(intent);
    }

    protected static Account reloadAccount(int id){
        mApiService.getAccount(id).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful())
                    accountGas = response.body();
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return null;
    }

}