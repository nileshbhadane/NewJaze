package com.jazeit.jazeitapp;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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

/**
 * Created by root on 28/9/15.
 */
public class Forgot_Password_Activity extends Activity {
    EditText forgot_pass;
    CircularProgressButton circularButton1;
    com.jazeit.jazeitapp.globalclass._glbFuncs glbFunc = new com.jazeit.jazeitapp.globalclass._glbFuncs();
    com.jazeit.jazeitapp.globalclass._glbServerConfig glbconf = new com.jazeit.jazeitapp.globalclass._glbServerConfig();
    private SharedPreferences prefs;
    private String prefName = "JazeIt";
    InputStream is = null;
    String result = null, status, line = null, item = "", id;

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password_);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        forgot_pass = (EditText) findViewById(R.id.et_pass);

        circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {

                    if (glbFunc.isInternetOn(getApplicationContext(), false) == false) {
                        new AlertDialog.Builder(Forgot_Password_Activity.this)
                                .setTitle(R.string.app_name)
                                .setMessage(R.string.Server_not_reachable)
                                .setNeutralButton(R.string.OK_btn, null)
                                .setInverseBackgroundForced(true).show();

                    } else {

                        simulateSuccessProgress(circularButton1);
                        String Server_url = glbconf.forgot_password;
                        new forgot_pwd().execute(Server_url);

                    }
                } else {
                    circularButton1.setProgress(0);
                }
            }
        });
        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forgot_Password_Activity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
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

    private class forgot_pwd extends AsyncTask<String, Void, Void> {

        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Forgot_Password_Activity.this);
        ProgressDialog progressDialog;

        protected void onPreExecute() {
// NOTE: You can call UI Element here.

// Start Progress Dialog (Message)

            progressDialog = new ProgressDialog(Forgot_Password_Activity.this,
                    R.style.AppTheme_Dark_Dialog);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String email_id = forgot_pass.getText().toString();
            nameValuePairs.add(new BasicNameValuePair("email", email_id));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        glbconf.forgot_password);
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
                String statuss = json_data.getString("status");
                String msg = json_data.getString("message");
                if (statuss.equals("false")) {
                    new AlertDialog.Builder(Forgot_Password_Activity.this).setTitle(R.string.app_name).setMessage(msg).setNeutralButton(R.string.OK_btn, null).setInverseBackgroundForced(true).show();
                } else {
                    new AlertDialog.Builder(Forgot_Password_Activity.this).setTitle(R.string.app_name).setMessage(R.string._forgot__password_Message).setNeutralButton(R.string.OK_btn, null).setInverseBackgroundForced(true).show();
                }

            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }

}
