package com.jazeit.jazeitapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jazeit.jazeitapp.custom_class.CustomAdapter;
import com.jazeit.jazeitapp.globalclass.JSONSharedPreferences;
import com.jazeit.jazeitapp.globalclass._glbFuncs;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class SubCatagories extends ActionBarActivity {
    JSONSharedPreferences jshare;

    String Parent_cat_name_0;
    JSONArray jsonArray_subcatagory;
    String Main_catagory=null,name;
    ArrayList<String> list;
    ListView listView;
    _glbFuncs glbfunc = new _glbFuncs();
    InputStream is = null;
    String result = null,status,line = null,item = "",id,parentIid;
    JSONObject jsonObject;
    JSONArray Jarray;
    com.jazeit.jazeitapp.globalclass._glbServerConfig glbconf = new com.jazeit.jazeitapp.globalclass._glbServerConfig();
    private SharedPreferences prefs;
    private String prefName = "JazeIt";

    public static int [] prgmImages={R.drawable.indian,
            R.drawable.indian,R.drawable.indian,R.drawable.indian,
            R.drawable.indian,R.drawable.indian,R.drawable.indian,
            R.drawable.indian,R.drawable.indian,R.drawable.indian,
            R.drawable.indian,R.drawable.indian,R.drawable.indian,
            R.drawable.indian,R.drawable.indian};
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_catagories);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        TextView tv = (TextView) findViewById(R.id.catagories);
        parentIid = getIntent().getStringExtra("Parent_id");

        tv.setText(getIntent().getStringExtra("subcatagory"));
        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("subcatagory", getIntent().getStringExtra("subcatagory"));

        //---saves the values---
        editor.commit();
        try {
            Boolean is_netConnetion = glbfunc.isConnectingToInternet(SubCatagories.this);
            if (!is_netConnetion) {
                new AlertDialog.Builder(SubCatagories.this)
                        .setTitle(R.string.app_name)
                        .setMessage(R.string.Server_not_reachable)
                        .setNeutralButton(R.string.OK_btn, null)
                        .setInverseBackgroundForced(true).show();

            } else {
                String Search_Cat_prod = glbconf.get_all_Subcatagories;
                new ListingSub_cat().execute(Search_Cat_prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<String>();
        //list.add("Auto");
       // listView.setAdapter(new CustomAdapter(this, list,prgmImages));


      /*  try {
            JSONArray allCatagory_Json = jshare.loadJSONArray(SubCatagories.this, prefName, "allcategories");
            Log.d("jsonArra second: ", "" + allCatagory_Json);

            for(int i=0;i<allCatagory_Json.length();i++)
            {
                Integer c_info = Integer.parseInt(allCatagory_Json.getJSONObject(i).getString("c_info"));
                Log.d("c_info second: ", "" + c_info);

                if(c_info == 1) {
                    String Parent_cat_name_1  = allCatagory_Json.getJSONObject(i).getString("cat_name");
                }else {
                    Parent_cat_name_0 = allCatagory_Json.getJSONObject(i).getString("cat_name");
                    Log.d("Parent_cat_name_0: ", "" + Parent_cat_name_0);

                    HashMap<String,String> cat_list_hash=new HashMap<String, String>();
                    cat_list_hash.put("VTITLE",Parent_cat_name_0);
                    Log.d("cat_list_hash: ", "" + cat_list_hash);
                    Main_catagory = tv.getText().toString();
                    if(Parent_cat_name_0.equals(Main_catagory))
                    {
                        jsonArray_subcatagory   = allCatagory_Json.getJSONObject(i).getJSONArray("subcat");
                        Log.d("jsonArray_subcatagory: ", "" + jsonArray_subcatagory);
                        //Toast.makeText(SubCatagories.this, "" + jsonArray_subcatagory, Toast.LENGTH_LONG).show();
                        for(int k=0;k<jsonArray_subcatagory.length();k++)
                        {
                            name = jsonArray_subcatagory.getJSONObject(k).getString("sub_cat_name");
                            Log.d("name: ", "" + name);
                            list.add(name);
                            *//*ArrayAdapter<String> adp=new ArrayAdapter<String> (getBaseContext(),
                                    android.R.layout.simple_dropdown_item_1line,list);
                            adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
*//*
                            listView.setAdapter(new CustomAdapter(this, list,prgmImages));
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                   // Intent intent = new Intent(SubCatagories.this,Product_listing.class);
                                    //startActivity(intent);

                                }
                            });
                        }
                    }else
                    {
                        jsonArray_subcatagory   = allCatagory_Json.getJSONObject(i).getJSONArray("subcat");
                    }
                }
            }
        }catch(Exception e){}*/

    }


    private class ListingSub_cat extends AsyncTask<String, Void, Void> {
        // Required initialization
        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        protected void onPreExecute() {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("parent_id", "17"));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        glbconf.get_all_Subcatagories);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httppost.addHeader("access_key","kHuljasImsiM");
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
                Log.d("SDub result : ", "" + result);
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray json_data = jsonObject.getJSONArray("services");
                Log.d("category : ", "" + json_data);

                    for(int i=0;i<json_data.length();i++) {
                        String cat_name = json_data.getJSONObject(i).getString("cate_name");
                        Log.d("cate_name_original", "" + cat_name);
                        list.add(cat_name);
                        listView.setAdapter(new CustomAdapter(SubCatagories.this, list, prgmImages));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                // Intent intent = new Intent(SubCatagories.this,Product_listing.class);
                                //startActivity(intent);

                            }
                        });
                    }



            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }

}
