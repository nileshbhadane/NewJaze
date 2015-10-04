package com.jazeit.jazeitapp.Server_connection_class;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jazeit.jazeitapp.LoginActivity;
import com.jazeit.jazeitapp.R;
import com.jazeit.jazeitapp.globalclass._glbServerConfig;

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
 * Created by root on 18/8/15.
 */
public class Server_Connection_signupEmail extends AsyncTask<String, Void, Void> {
    _glbServerConfig glbconf = new _glbServerConfig();
    InputStream is = null;
    String result = null;
    String line = null;
    Context oContext;
    int code;
    @Override
    protected void onPreExecute() {


    }
    @Override
    protected Void doInBackground(String... params) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
       String to = params[0];
        String username  = params[1];
        nameValuePairs.add(new BasicNameValuePair("to",to));
        nameValuePairs.add(new BasicNameValuePair("username" +
                "",username));
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(glbconf.Server_Registration_send_email);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
        }
        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("Result", ""+result);

        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try
        {



        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }

}