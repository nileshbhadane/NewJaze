package com.jazeit.jazeitapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class App_Setting extends ActionBarActivity {

    ListView listView;
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[] {
            R.drawable.ic_action_action_settings,
            R.drawable.ic_action_communication_vpn_key,
            R.drawable.ic_action_social_person,
            R.drawable.ic_action_maps_pin_drop };
    // Defined Array values to show in ListView
    String[] values = new String[] { "Select", "Change Password", "About us",
            "Contact us" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__setting);
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list_account_setting);

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i <= 3; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("txt", "" + values[i]);
            hm.put("flag", Integer.toString(flags[i]));
            aList.add(hm);
        }
        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent app_Intent = new Intent(App_Setting.this,Search_catagory.class);
                startActivity(app_Intent);
            }
        });
        // Keys used in Hashmap
        String[] from = { "flag", "txt" };

        // Ids of views in listview_layout
        int[] to = { R.id.flag, R.id.txt };

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList,
                R.layout.activitysettingicons, from, to);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                switch (itemPosition) {
                    case 0:
                        Intent select_intent = new Intent(App_Setting.this,Select_country.class);
                        startActivity(select_intent);
                        break;
                    case 1:
                        Intent change_pwd_intent = new Intent(App_Setting.this,Change_Password.class);
                        startActivity(change_pwd_intent);
                        break;
                    case 2:

                        break;
                    case 3:

                        break;

                    default:
                        break;

                }
            }

        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent_ = new Intent(App_Setting.this,Search_catagory.class);
            startActivity(intent_);
        }
        return false;
    }


}
