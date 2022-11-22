package com.ArkaBrianJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ArkaBrianJSleepRJ.model.Account;
import com.ArkaBrianJSleepRJ.model.Room;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static Account accountGas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();
        InputStream filepath = null;
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<Room> roomList  = new ArrayList<Room>();
        try {
            filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            Room[] acc = gson.fromJson(reader, Room[].class);
            Collections.addAll(roomList, acc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Room room : roomList) {
            name.add(room.name);
        }
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
        ListView view = (ListView) findViewById(R.id.listViewer);
        view.setAdapter(roomAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent aboutMeIntent = new Intent(MainActivity.this,AboutMeActivity.class);
        if (item.getItemId() == R.id.account_button) {
            Toast.makeText(this, "About me", Toast.LENGTH_SHORT).show();
            startActivity(aboutMeIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }
}