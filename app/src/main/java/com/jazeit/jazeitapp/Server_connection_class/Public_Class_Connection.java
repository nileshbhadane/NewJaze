package com.jazeit.jazeitapp.Server_connection_class;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jazeit.jazeitapp.globalclass._glbServerConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by root on 27/8/15.
 */
public class Public_Class_Connection extends AsyncTask<String, Void, Void> {

    InputStream is = null;
    String result = null;
    String line = null;


    @Override
    protected Void doInBackground(String... params) {



        return null;
    }


    public void Pre_Exec_httpConn(String path,Context Ocontext,ArrayList<NameValuePair> nameValuePairs)
    {
        nameValuePairs = new ArrayList<NameValuePair>();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(
                    path);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.d("namevalue",""+nameValuePairs);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
           Toast.makeText(Ocontext, "Invalid Ip", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }
    public String  do_in_back_connection()
    {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.d("result : ", "" + result);
            Log.e("pass 2", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());
        }
        return result;
    }
}
