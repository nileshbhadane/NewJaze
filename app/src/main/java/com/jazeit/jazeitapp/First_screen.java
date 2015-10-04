package com.jazeit.jazeitapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class First_screen extends AppCompatActivity {
    Button btn_login,btn_Registered;

    private SharedPreferences prefs;
    private String prefName = "JazeIt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        getSupportActionBar().hide();
        btn_login = (Button)findViewById(R.id.btn_Login);
        btn_Registered = (Button)findViewById(R.id.btn_Registered);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login = new Intent(First_screen.this,LoginActivity.class);
                startActivity(intent_login);
                prefs = getSharedPreferences(prefName, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                String appid = "000_000";
                //---save the values in the EditText view to preferences---
                editor.putString("APP_ID", appid);

                //---saves the values---
                editor.commit();

            }
        });
        btn_Registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_registered = new Intent(First_screen.this,MainActivity.class);
                startActivity(intent_registered);

            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            /*AlertDialog ExitAlert = new AlertDialog.Builder(this).create();
            ExitAlert.setTitle("Exit Jazeit");
            ExitAlert.setIcon(R.drawable.logo);
            ExitAlert.setMessage("Are you sure you want to exit the APP?");

            ExitAlert.setButton2("Continue",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            ExitAlert.setButton("Exit App",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            return;
                        }
                    });
            ExitAlert.show();*/
            Intent intent_first = new Intent(First_screen.this,First_screen.class);
            startActivity(intent_first);
        }
        return false;
    }

}
