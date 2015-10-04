package com.jazeit.jazeitapp;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.jazeit.jazeitapp.Server_connection_class.Public_Class_Connection;
import com.jazeit.jazeitapp.dd.CircularProgressButton;
import com.jazeit.jazeitapp.globalclass.JSONSharedPreferences;
import com.jazeit.jazeitapp.globalclass._glbFuncs;
import com.jazeit.jazeitapp.globalclass._glbServerConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.jazeit.jazeitapp.Server_connection_class.Public_Class_Connection;
import com.jazeit.jazeitapp.custom_class.ImageViewRounded;
import com.jazeit.jazeitapp.globalclass.JSONSharedPreferences;
import com.jazeit.jazeitapp.globalclass._glbFuncs;
import com.jazeit.jazeitapp.globalclass._glbServerConfig;

public class Select_country extends ActionBarActivity {
    Spinner sp,sp_country,sp_state,sp_city;
    InputStream is = null;
    ArrayList<String> Coutry_list,State_list,all_product_catagory_names;
    String result = null,line = null,Country_id,State_id,User_id,Country_Name;
    _glbServerConfig glbconf;
    JSONSharedPreferences jshare;
    _glbFuncs glbfunc;
    private SharedPreferences prefs;
    private String prefName = "JazeIt";
    CircularProgressButton circularButton1;
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);        }
        glbconf = new _glbServerConfig();
        glbfunc = new _glbFuncs();
        User_id = getSharedPreferences(glbconf.app_name,MODE_PRIVATE).getString("user_id","0");

        sp_country= (Spinner) findViewById(R.id.spinner);
        sp_state = (Spinner) findViewById(R.id.spinner2);
        sp_city = (Spinner) findViewById(R.id.sp_city_sp);
        Boolean is_netConnetion = glbfunc.isConnectingToInternet(Select_country.this);
        if ( glbfunc.isInternetOn(getApplicationContext(), false) == false ) {
            new AlertDialog.Builder(Select_country.this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.Server_not_reachable)
                    .setNeutralButton(R.string.OK_btn, null)
                    .setInverseBackgroundForced(true).show();

        } else {
            String country_url = glbconf.Country_;
            new Country_Async().execute(country_url);
        }
        Coutry_list=new ArrayList<String>();
        State_list = new ArrayList<String>();
        sp_country
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int position, long arg3) {
                        // TODO Auto-generated method stub
                        sp_state.setVisibility(View.VISIBLE);
                        int no = position+1;
                        Country_id = String.valueOf(no);
                        Country_Name = sp_country.getSelectedItem().toString();
                        //Toast.makeText(Search_catagory.this,""+Country_Name,Toast.LENGTH_LONG).show();
                        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("COuntry_name", Country_Name);
                        editor.commit();
                        String  cname = getSharedPreferences("JazeIt", MODE_PRIVATE)
                                .getString("COuntry_name", "Dubai");
                        //txt_country.setText(cname);
                        Boolean is_netConnetion = glbfunc.isConnectingToInternet(Select_country.this);
                        if (!is_netConnetion) {
                            new AlertDialog.Builder(Select_country.this)
                                    .setTitle(R.string.app_name)
                                    .setMessage(R.string.Server_not_reachable)
                                    .setNeutralButton(R.string.OK_btn, null)
                                    .setInverseBackgroundForced(true).show();

                        } else {
                             String country_url = glbconf.Country_;
                             new State_Async().execute(country_url);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                        sp_state.setAdapter(null);
                    }
                });
        circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (circularButton1.getProgress() == 0) {
                     simulateSuccessProgress(circularButton1);

                } else {
                     circularButton1.setProgress(0);
                }
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent_ = new Intent(Select_country.this,App_Setting.class);
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
                Intent intent = new Intent(Select_country.this,App_Setting.class);
                startActivity(intent);
            }
        });
        widthAnimation.start();
    }

    private class Country_Async extends AsyncTask<String, Void, Void> {
        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        protected void onPreExecute() {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("user_id", User_id));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        glbconf.Country_);
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
                //Log.d("result : ", "" + result);
                Log.e("pass 2", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }
            // result = _pub_class.do_in_back_connection();
            return null;

/************ Make Post Call To Web Server ***********/
        }

        protected void onPostExecute(Void unused) {
// NOTE: You can call UI Element here.

// Close progress dialog
            //  Dialog.dismiss();
            try {
                String Country_name = null;
                JSONArray json_data = new JSONArray(result);
                //  Log.d("json_data : ", "" + json_data);

                jshare.saveJSONArray(Select_country.this,prefName,"_Country",json_data);
                for (int i = 0; i < json_data.length(); i++) {
                    Country_id = json_data.getJSONObject(i).getString("id");
                    String Country_ = json_data.getJSONObject(i).getString("country_name");

                    Coutry_list.add(Country_);

                }

                sp_country
                        .setAdapter(new ArrayAdapter<String>(Select_country.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                Coutry_list));
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }
    private class State_Async extends AsyncTask<String, Void, Void> {
        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        protected void onPreExecute() {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("country_id", Country_id));
            Log.d("Country_id",""+Country_id);
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        glbconf.Country_);
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
                Log.d("state result : ", "" + result);
                Log.e("pass 2", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }
            return null;
/************ Make Post Call To Web Server ***********/
        }
        protected void onPostExecute(Void unused) {
// NOTE: You can call UI Element here.
            try {
                String Country_name = null;
                JSONArray json_data = new JSONArray(result);
                Log.d("json_data for state : ", "" + json_data);
                jshare.saveJSONArray(Select_country.this,prefName,"_state",json_data);
                for (int i = 0; i < json_data.length(); i++) {
                    State_id = json_data.getJSONObject(i).getString("id");
                    String state = json_data.getJSONObject(i).getString("state_name");
                    State_list.add(state);
                }
                ArrayAdapter<String> adpater =  new ArrayAdapter<String>(Select_country.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        State_list);
                sp_state.setAdapter(adpater);
                adpater.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }
}
