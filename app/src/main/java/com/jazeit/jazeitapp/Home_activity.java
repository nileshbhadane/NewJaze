package com.jazeit.jazeitapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jazeit.jazeitapp.Server_connection_class.Public_Class_Connection;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Home_activity extends ActionBarActivity {
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    NestedScrollView nested_scroll;
    android.support.design.widget.CoordinatorLayout coordinatorLayout;


    CoordinatorLayout rootLayout;

    TableLayout Layout_Tabels;
    Spinner sp,sp_country,sp_state,sp_city;
    List<String> li;

    ArrayList<String> Coutry_list,State_list,all_product_catagory_names;
    String Country_id,State_id,product_cat;
    String fname,Email,User_id,Country_code,statCode="0",Country_Name;
    TextView txt_date,txt_username,txt_day,txt_country,txt_cat_name;
    Button btn_my_page,btn_company_page,btn_account_setting,btn_logout,btn_time;
    DisplayMetrics metrics;
    LinearLayout layout_for_setting;


    _glbFuncs glbfunc;
    ImageView imag;
    String Parent_cat_name_0;
    boolean imageOn = false;
    RelativeLayout relativeLayout;
    ImageView v1;
    int  rowCnt,i;
    int screenSize,density;
    JSONArray jsonArray_subcatagory;
    //ArrayList<HashMap<String,String>> itemNames=new ArrayList<HashMap<String, String>>();
    ArrayList<Integer> itemNames=new ArrayList<Integer>();
    // RelativeLayout tv;
    RelativeLayout rl;
    TextView tv,tv1,tv2,tv3;
    Public_Class_Connection _pub_class;

    AutoCompleteTextView Search_anything;
    TableRow rows;
    _glbServerConfig glbconf = new _glbServerConfig();
    String result = null,line = null;
    InputStream is = null;
    String company_name;
    JSONSharedPreferences jshare;
    private SharedPreferences prefs;
    private String prefName = "JazeIt",parent_category,child_category;
    TextView Title;
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        findviewByid();
        initInstances();


        GridView gridView = (GridView)findViewById(R.id.GridView);
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
                Toast.makeText(Home_activity.this,name,Toast.LENGTH_LONG).show();
               // Intent intet =new Intent(Home_activity.this,SubCatagories.class);
               // intet.putExtra("title_profiledetails",name);
               // startActivity(intet);

            }
        });
        ImageView link= (ImageView) findViewById(R.id.in);
        ImageView fb= (ImageView) findViewById(R.id.fb);
        ImageView twit= (ImageView) findViewById(R.id.twit);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://you"));
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=you"));
                }
                startActivity(intent);
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String urlFb = "fb://page/";
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(urlFb));

                final PackageManager packageManager = getPackageManager();
                List<ResolveInfo> list = packageManager.queryIntentActivities(
                        intent1, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.size() == 0) {
                    final String urlBrowser = "https://www.facebook.com/pages/";
                    intent1.setData(Uri.parse(urlBrowser));
                }
                overridePendingTransition(0, 0);
                startActivity(intent1);


            }
        });
        twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetUrl =
                        String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                                urlEncode("Tweet text"), urlEncode("https://www.google.fi/"));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

// Narrow down to official Twitter app, if available:
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                        intent.setPackage(info.activityInfo.packageName);
                    }
                }

                startActivity(intent);
            }
        });
        Search_anything.setTextColor(Color.parseColor("#FFFFFF"));
        Search_anything.setHintTextColor(Color.parseColor("#FFFFFF"));
        Search_anything.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Search_anything.setFilters(new InputFilter[] {
                        new InputFilter() {
                            public CharSequence filter(CharSequence src, int start,int end, Spanned dst, int dstart, int dend) {
                                if(src.equals("")){
                                    return src;
                                }
                                if(src.toString().matches("[a-zA-Z ]+")){
                                    return src;
                                }
                                return "";
                            }
                        }
                });
                try {
                    if(Search_anything.getText().toString().length()==2)
                    {
                        product_cat=Search_anything.getText().toString();
                        all_product_catagory_names=new ArrayList<String>();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Boolean is_netConnetion = glbfunc.isConnectingToInternet(Home_activity.this);
                                    if (!is_netConnetion) {
                                        new AlertDialog.Builder(Home_activity.this)
                                                .setTitle(R.string.app_name)
                                                .setMessage(R.string.Server_not_reachable)
                                                .setNeutralButton(R.string.OK_btn, null)
                                                .setInverseBackgroundForced(true).show();

                                    } else {
                                        String Search_Cat_prod = glbconf.Search_product_cat;
                                        new Product_Search_Async().execute(Search_Cat_prod);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();;

                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        Search_anything.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                Intent intent = new Intent(Home_activity.this,Main_second.class);
                startActivity(intent);
            }
        });

    }
    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            // Log.wtf("twitter", "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        String status = getSharedPreferences("JazeIt",MODE_PRIVATE).getString("status","false");
        if(!status.equals("1"))
        {
            Intent intent = new Intent(Home_activity.this,LoginActivity.class);
            startActivity(intent);

        }
    }
    private void initInstances() {
        fname = getSharedPreferences(glbconf.app_name,MODE_PRIVATE).getString("firstname","Name");
        Email = getSharedPreferences(glbconf.app_name,MODE_PRIVATE).getString("Email", "sample@sample.com");
        User_id = getSharedPreferences(glbconf.app_name,MODE_PRIVATE).getString("user_id","0");
        Log.d("User_id",""+User_id);
//        layout_for_setting.bringToFront();
        txt_username.setText(fname);
        txt_username.bringToFront();
        txt_username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(layout_for_setting.getVisibility() == View.VISIBLE)
                {
                    layout_for_setting.setVisibility(View.GONE);
                }else
                {
                    layout_for_setting.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }
    private void findviewByid()
    {
        Search_anything= (AutoCompleteTextView) findViewById(R.id.Search_anything);
        layout_for_setting = (LinearLayout) findViewById(R.id.layout_for_setting);
        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp_country= (Spinner) findViewById(R.id.sp_country);
        sp_state = (Spinner) findViewById(R.id.sp_state);
        sp_city = (Spinner) findViewById(R.id.sp_city);
        btn_time = (Button) findViewById(R.id.btn_time);
        btn_my_page = (Button) findViewById(R.id.btn_my_page);
        btn_company_page = (Button) findViewById(R.id.btn_company_page);
        btn_account_setting = (Button) findViewById(R.id.btn_account_setting);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_country = (TextView) findViewById(R.id.txt_country);
        // Layout_Tabels = (TableLayout) findViewById(R.id.Layout_Tabels);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);


    }
    private class Listing_cat extends AsyncTask<String, Void, Void> {
        // Required initialization
        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        protected void onPreExecute() {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String User_id = getSharedPreferences(glbconf.app_name,MODE_PRIVATE).getString("user_id","0");
            nameValuePairs.add(new BasicNameValuePair("user_id", "47"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        glbconf.get_all_catagories);
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
                JSONArray json_data = jsonObject.getJSONArray("allcategories");
                Log.d("allcategories : ", "" + json_data);
                jshare.saveJSONArray(Home_activity.this,prefName,"allcategories",json_data);


            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }
    private class Product_Search_Async extends AsyncTask<String, Void, Void> {
        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        protected void onPreExecute() {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("keyword", product_cat));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        glbconf.Search_product_cat);
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
                JSONArray json_data = jsonObject.getJSONArray("companies");
                Log.d("json_data for search : ", "" + json_data);

                for (int i = 0; i < json_data.length(); i++) {
                    String state = json_data.getJSONObject(i).getString("company_name");

                    all_product_catagory_names.add(state);
                }
                ArrayAdapter adapter = new ArrayAdapter(Home_activity.this,android.R.layout.simple_list_item_1,all_product_catagory_names);
                Search_anything.setAdapter(adapter);
                Search_anything.setThreshold(1);

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
                JSONArray json_data = jshare.loadJSONArray(Home_activity.this, prefName, "allcategories");
                for (int i=0;i<json_data.length();i++)
                {
                    JSONObject jsonObj = json_data.getJSONObject(i);
                    company_name  = jsonObj.getString("cat_name");
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
