package com.jazeit.jazeitapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jazeit.jazeitapp.globalclass.JSONSharedPreferences;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Product_listing extends ActionBarActivity {
    _glbServerConfig glbconf = new _glbServerConfig();
    String result = null,line = null;
    InputStream is = null;
    String company_name;
    JSONSharedPreferences jshare;
    private SharedPreferences prefs;
    private String prefName = "JazeIt",parent_category,child_category,parentIid;
    TextView Title;
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        prefs = getSharedPreferences(prefName, MODE_PRIVATE);

        Title = (TextView) findViewById(R.id.product_listing_title);
        parent_category = prefs.getString("subcatagory", "Catagory");
        child_category = getIntent().getStringExtra("SubCategory");

        Title.setText(parent_category+" > "+child_category);
        GridView gridView = (GridView)findViewById(R.id.gridview);
        try{
            String get_all_Subcatagories = glbconf.Listing_cat;
            new Listing_cat().execute(get_all_Subcatagories);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        gridView.setAdapter(new MyAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               TextView tv = (TextView) view.findViewById(R.id.text);
String name = tv.getText().toString();
                Intent intet =new Intent(Product_listing.this,Profile_Activity.class);
                intet.putExtra("title_profiledetails",name);
                startActivity(intet);

            }
        });

    }
    private class Listing_cat extends AsyncTask<String, Void, Void> {
        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        protected void onPreExecute() {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("user_id", "a"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        glbconf.Listing_cat);
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

                //jsonObject = new JSONObject(item);

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
                JSONArray json_data = jsonObject.getJSONArray("allservices");
                Log.d("All subCat : ", "" + json_data);
                jshare.saveJSONArray(Product_listing.this,prefName,"allservices",json_data);


            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }
    private class MyAdapter extends BaseAdapter
    {
        Context ocontext;
        private LayoutInflater inflater;
        private List<Item> items = new ArrayList<Item>();

        public MyAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);
            try {
                JSONArray json_data = jshare.loadJSONArray(Product_listing.this, prefName, "allservices");
                for (int i=0;i<json_data.length();i++)
                {
                    JSONObject jsonObj = json_data.getJSONObject(i);
                    company_name  = jsonObj.getString("company_name");
                    Log.d("company_name", "" + company_name);
                    items.add(new Item(company_name, R.drawable.quard));

                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }


        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i)
        {
            return items.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup)
        {
            View v = view;
            ImageView picture;
            final TextView name;
            final Context context=null;
            if(v == null)
            {
                v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView)v.getTag(R.id.picture);
            name = (TextView)v.getTag(R.id.text);

            Log.d("sixe od f item :",""+items.size());
           final  Item item = (Item)getItem(i);
            int imgSize = 0;
          // picture.setImageResource(item.drawableId);
            picture.setImageResource(R.drawable.quard);
            name.setText(item.name);
           /* v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Toast.makeText(ocontext, "You Clicked " + name.getText().toString(), Toast.LENGTH_LONG).show();
                    *//*Intent intent = new Intent(ocontext, Profile_Activity.class);
                    intent.putExtra("SubCategory",item.name);
                    ocontext.startActivity(intent);*//*

                }
            });*/

           return v;
        }

        private class Item
        {
            final String name;
            final int drawableId;

            Item(String name, int drawableId)
            {
                this.name = name;
                this.drawableId = drawableId;
            }

        }
    }
}
