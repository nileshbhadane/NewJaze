package com.jazeit.jazeitapp;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jazeit.jazeitapp.dd.CircularProgressButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Change_Password extends ActionBarActivity {
    EditText old_pwd,new_pwd,conf_pwd;
    TextView txt_message;
    CircularProgressButton circularButton1;
    com.jazeit.jazeitapp.globalclass._glbFuncs glbFunc = new com.jazeit.jazeitapp.globalclass._glbFuncs();
    com.jazeit.jazeitapp.globalclass._glbServerConfig glbconf = new com.jazeit.jazeitapp.globalclass._glbServerConfig();
    private SharedPreferences prefs;
    private String prefName = "JazeIt";
    InputStream is = null;
    String result = null,status,line = null,item = "",id;
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        old_pwd = (EditText) findViewById(R.id.old_password);
        new_pwd = (EditText) findViewById(R.id.et_new_pass);
        conf_pwd = (EditText) findViewById(R.id.et_conf_pass);
        txt_message = (TextView) findViewById(R.id.txt_message);
        circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {

                     if (glbFunc.isInternetOn(getApplicationContext(), false) == false) {
                       /* new AlertDialog.Builder(Change_Password.this)
                                .setTitle(R.string.app_name)
                                .setMessage(R.string.Server_not_reachable)
                                .setNeutralButton(R.string.OK_btn, null)
                                .setInverseBackgroundForced(true).show();*/

                    } else {
                        if (old_pwd.getText().toString().matches("")
                                && new_pwd.getText().toString().matches("") && conf_pwd.getText().toString().matches("")) {
                            new AlertDialog.Builder(Change_Password.this)
                                    .setTitle(R.string.app_name)
                                    .setMessage(R.string.All_field_blank)
                                    .setNeutralButton(R.string.OK_btn, null)
                                    .setInverseBackgroundForced(true).show();


                        } else {
                            simulateSuccessProgress(circularButton1);
                            String Server_url = glbconf.change_password;
                            new Change_pwd().execute(Server_url);

                        }
                    }
                } else {
                    circularButton1.setProgress(0);
                }
                //.isConnectingToInternet(LoginActivity.this);
               /* int colorId = circularButton1.getSolidColor();
                if (colorId == R.color.voilet) {
                    circularButton1.setProgress(0);

                } else {*/

                   /* if (glbFunc.isInternetOn(getApplicationContext(), false) == false) {
                        new AlertDialog.Builder(Change_Password.this)
                                .setTitle(R.string.app_name)
                                .setMessage(R.string.Server_not_reachable)
                                .setNeutralButton(R.string.OK_btn, null)
                                .setInverseBackgroundForced(true).show();

                    } else {
                        if (old_pwd.getText().toString().matches("")
                                && new_pwd.getText().toString().matches("") && conf_pwd.getText().toString().matches("")) {
                            new AlertDialog.Builder(Change_Password.this)
                                    .setTitle(R.string.app_name)
                                    .setMessage(R.string.All_field_blank)
                                    .setNeutralButton(R.string.OK_btn, null)
                                    .setInverseBackgroundForced(true).show();


                        } else {

                            String Server_url = glbconf.change_password;
                            new Change_pwd().execute(Server_url);

                        }
                    }*/
               // }
            }
        });
        conf_pwd.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPass1 = new_pwd.getText().toString();
                String strPass2 = conf_pwd.getText().toString();
                if (strPass1.equals(strPass2)) {
                    txt_message.setVisibility(View.VISIBLE);
                    txt_message.setText("Password match");               } else {
                    txt_message.setVisibility(View.VISIBLE);
                    txt_message.setText("Password do not match");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        final CircularProgressButton circularButton2 = (CircularProgressButton) findViewById(R.id.circularButton2);
        circularButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (circularButton2.getProgress() == 0) {
                    simulateErrorProgress(circularButton2);
                } else {
                    circularButton2.setProgress(0);
                }*/
                Intent app_setting_intent = new Intent(Change_Password.this, App_Setting.class);
                startActivity(app_setting_intent);
            }
        });
        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent app_setting_intent = new Intent(Change_Password.this,App_Setting.class);
                startActivity(app_setting_intent);
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent_ = new Intent(Change_Password.this,App_Setting.class);
            startActivity(intent_);
        }
        return false;
    }
    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }
    private class Change_pwd extends AsyncTask<String, Void, Void> {

        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Change_Password.this);
        ProgressDialog progressDialog;
        protected void onPreExecute() {
// NOTE: You can call UI Element here.

// Start Progress Dialog (Message)

            progressDialog = new ProgressDialog(Change_Password.this,
                    R.style.AppTheme_Dark_Dialog);
           // progressDialog.setIndeterminate(true);
           // progressDialog.setMessage("Authenticating...");
           // progressDialog.show();
           /* if (circularButton1.getProgress() == 0) {
                simulateSuccessProgress(circularButton1);
            } else {
                circularButton1.setProgress(0);
            }*/
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String old_password = old_pwd.getText().toString();
            String new_password = new_pwd.getText().toString();
            String email_id =getSharedPreferences(glbconf.app_name,MODE_PRIVATE).getString("Email", "sample@sample.com");
            nameValuePairs.add(new BasicNameValuePair("email", email_id));
            nameValuePairs.add(new BasicNameValuePair("new_password", new_password));
            nameValuePairs.add(new BasicNameValuePair("old_password", old_password));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        glbconf.change_password);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Invalid Ip", Toast.LENGTH_LONG).show();

                e.printStackTrace();
            }
        }
        protected Void doInBackground(String... urls) {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();

                Log.d("result login: ", "" + result);

                Log.e("pass 2", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }
            return null;

/************ Make Post Call To Web Server ***********/
        }
        protected void onPostExecute(Void unused) {
            try {

                JSONObject json_data = new JSONObject(result);
                clear_all();
                String statuss = json_data.getString("status");
                String msg= json_data.getString("message");
                if(statuss.equals("false")) {
                    new AlertDialog.Builder(Change_Password.this).setTitle(R.string.app_name).setMessage(msg).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();
                }else
                {
                    new AlertDialog.Builder(Change_Password.this).setTitle(R.string.app_name).setMessage(R.string.Password_is_change).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();
                }

            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }
    public void clear_all()
    {
        old_pwd.setText("");
        new_pwd.setText("");
        conf_pwd.setText("");
    }
}