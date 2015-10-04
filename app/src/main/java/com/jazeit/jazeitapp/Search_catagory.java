package com.jazeit.jazeitapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.res.Configuration;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jazeit.jazeitapp.Server_connection_class.Public_Class_Connection;
import com.jazeit.jazeitapp.custom_class.ImageViewRounded;
import com.jazeit.jazeitapp.custom_class.ProgressHUD;
import com.jazeit.jazeitapp.custom_class.SquareImageView;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Search_catagory extends Activity {
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    NestedScrollView nested_scroll;
    android.support.design.widget.CoordinatorLayout coordinatorLayout;
    RelativeLayout layout;
    CoordinatorLayout rootLayout;
    TableLayout Layout_Tabels;
    Spinner sp,sp_country,sp_state,sp_city;
    List<String> li;
    InputStream is = null;
    ArrayList<String> Coutry_list,State_list,all_product_catagory_names;
    String result = null,line = null,Country_id,State_id,product_cat;
    String fname,Email,User_id,Country_code,statCode="0",Country_Name;
    TextView txt_date,txt_username,txt_day,txt_country,txt_cat_name;
    Button btn_my_page,btn_company_page,btn_account_setting,btn_logout,btn_time;
    DisplayMetrics metrics;
    LinearLayout layout_for_setting;
    _glbServerConfig glbconf;
    JSONSharedPreferences jshare;
    _glbFuncs glbfunc;
    ImageView imag;
    String Parent_cat_name_0;
    boolean imageOn = false;
    RelativeLayout relativeLayout;
    ImageView v1;
    String company_name;
    JSONArray json_data;
    int  rowCnt,i;
    int screenSize,density;
    JSONArray jsonArray_subcatagory;
    //ArrayList<HashMap<String,String>> itemNames=new ArrayList<HashMap<String, String>>();
    ArrayList<Integer> itemNames=new ArrayList<Integer>();
    // RelativeLayout tv;
    RelativeLayout rl;
    TextView tv,tv1,tv2,tv3;
    Public_Class_Connection _pub_class;
    private SharedPreferences prefs;
    private String prefName = "JazeIt";
    AutoCompleteTextView Search_anything;
    ImageView userpicture;
    TableRow rows;
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_catagory);
        String status = getSharedPreferences("JazeIt",MODE_PRIVATE).getString("status","false");
        String FBUser_id = getSharedPreferences("JazeIt",MODE_PRIVATE).getString("FBUser_id","0");
        if((!status.equals("1"))||(FBUser_id.equals(0)))
        {
            Intent intent = new Intent(Search_catagory.this,LoginActivity.class);
            startActivity(intent);finish();
        }
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);        }


        nested_scroll = (NestedScrollView) findViewById(R.id.nested_scroll);
        nested_scroll.bringToFront();
        initToolbar();
        initInstances();
        /*String Access_token = getSharedPreferences("JazeIt",MODE_PRIVATE).getString("FBAccessToken","0");
        Bitmap MyprofPicBitMap = null;
        try {
            URL MyProfilePicURL = new URL("https://graph.facebook.com/me/picture?type=normal&method=GET&access_token="+ Access_token );

            MyprofPicBitMap = BitmapFactory.decodeStream(MyProfilePicURL.openConnection().getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
       // userpicture.setImageBitmap(MyprofPicBitMap);
        GridView gridView = (GridView)findViewById(R.id.GridView);
        if ( glbfunc.isInternetOn(getApplicationContext(), false) == false ) {
            new AlertDialog.Builder(Search_catagory.this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.Server_not_reachable)
                    .setNeutralButton(R.string.OK_btn, null)
                    .setInverseBackgroundForced(true).show();

        } else {
            try {
                String get_all_Subcatagories = glbconf.Listing_cat;
                new Listing_cat().execute(get_all_Subcatagories);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        gridView.setAdapter(new MyAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.text);
                String name = tv.getText().toString();
            TextView Parent_id = (TextView) view.findViewById(R.id.Parent_id);
                String Parent_id_text = Parent_id.getText().toString();
                Intent intet =new Intent(Search_catagory.this,SubCatagories.class);
                intet.putExtra("subcatagory",name);
                intet.putExtra("Parent_id",Parent_id_text);
                 startActivity(intet);

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
            Intent intent = new Intent(Search_catagory.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initToolbar() {
        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/    }

    private void initInstances() {
        findviewByid();
        glbconf = new _glbServerConfig();
        glbfunc = new _glbFuncs();
        _pub_class = new Public_Class_Connection();
//        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final android.support.design.widget.AppBarLayout appbarlayout = (AppBarLayout) findViewById(R.id.Appbarlayuot);
        imag = new ImageView(this);
        relativeLayout = new RelativeLayout(Search_catagory.this);
       /* BM if(rowcount<numRows)
            table.addView(tablerowLayout[rowcount]);
        if (toolbar != null) {
            // setActionBar(toolbar);
            //      getSupportActionBar().setHomeButtonEnabled(true);
            //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        fname = getSharedPreferences(glbconf.app_name,MODE_PRIVATE).getString("firstname","Name");
        Email = getSharedPreferences(glbconf.app_name,MODE_PRIVATE).getString("Email", "sample@sample.com");
        User_id = getSharedPreferences(glbconf.app_name,MODE_PRIVATE).getString("user_id","0");
        Log.d("User_id",""+User_id);
        layout_for_setting.bringToFront();
        txt_username.setText(fname);
        txt_username.bringToFront();
        txt_username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               /* if(layout_for_setting.getVisibility() == View.VISIBLE)
                {
                    layout_for_setting.setVisibility(View.GONE);
                }else
                {
                    layout_for_setting.setVisibility(View.VISIBLE);
                }*/
                customDialog();
                return false;
            }
        });
        Calendar cal=Calendar.getInstance();
        String dayNumberSuffix = getDayNumberSuffix(cal.get(Calendar.DAY_OF_MONTH));
        DateFormat dateFormat = new SimpleDateFormat(" d'" + dayNumberSuffix + "' MMMM");
        String dates = dateFormat.format(cal.getTime());
        SpannableString text2 = new SpannableString(dates);
        text2.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 5, 0);
        text2.setSpan(new ForegroundColorSpan(Color.parseColor("#5F3a48")), 6, text2.length(), 0);
//        text2.setSpan(new ForegroundColorSpan(Color.BLUE), 15, text2.length(), 0);
        txt_date.setText(text2, TextView.BufferType.SPANNABLE);
        // txt_date.setText(dates);
        btn_my_page.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(Search_catagory.this,Profile_Activity.class);
                startActivity(intent);
                return false;
            }
        });
        btn_company_page.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(Search_catagory.this,Profile_Activity.class);
                startActivity(intent);
                return false;
            }
        });
        btn_account_setting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(Search_catagory.this,Profile_Activity.class);
                startActivity(intent);
                return false;
            }
        });
        btn_logout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               /* SharedPreferences settings = Search_catagory.this.getSharedPreferences("JazeIt", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent intent = new Intent(Search_catagory.this,LoginActivity.class);
                startActivity(intent);*/
                return false;
            }
        });
        txt_day = (TextView) findViewById(R.id.txt_day);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);//ff5c89
        txt_day.setTextColor(Color.parseColor("#ff5c89"));
        txt_day.setText(dayOfTheWeek);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());
        btn_time.setText(formattedDate);
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
                        txt_country.setText(cname);
                        Boolean is_netConnetion = glbfunc.isConnectingToInternet(Search_catagory.this);
                        if (!is_netConnetion) {
                            new AlertDialog.Builder(Search_catagory.this)
                                    .setTitle(R.string.app_name)
                                    .setMessage(R.string.Server_not_reachable)
                                    .setNeutralButton(R.string.OK_btn, null)
                                    .setInverseBackgroundForced(true).show();

                        } else {
                           // String country_url = glbconf.Country_;
                           // new State_Async().execute(country_url);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                        sp_state.setAdapter(null);
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
                                    Boolean is_netConnetion = glbfunc.isConnectingToInternet(Search_catagory.this);
                                    if (!is_netConnetion) {
                                        new AlertDialog.Builder(Search_catagory.this)
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
                        }).start();

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
                Intent intent = new Intent(Search_catagory.this,Main_second.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("on start method calling","");
    }
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
    public String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
    private void findviewByid()
    {
        Search_anything= (AutoCompleteTextView) findViewById(R.id.Search_anything);
        layout_for_setting = (LinearLayout) findViewById(R.id.layout_for_setting);
        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp_country= (Spinner) findViewById(R.id.sp_country);
        userpicture = (ImageView) findViewById(R.id.userpicture);
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
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);
        layout= (RelativeLayout)findViewById(R.id.custom_layout);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog ExitAlert = new AlertDialog.Builder(this).create();
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
            ExitAlert.show();
        }
        return false;
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

                jshare.saveJSONArray(Search_catagory.this,prefName,"_Country",json_data);
                for (int i = 0; i < json_data.length(); i++) {
                    Country_id = json_data.getJSONObject(i).getString("id");
                    String Country_ = json_data.getJSONObject(i).getString("country_name");

                    Coutry_list.add(Country_);

                }

                sp_country
                        .setAdapter(new ArrayAdapter<String>(Search_catagory.this,
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
                jshare.saveJSONArray(Search_catagory.this,prefName,"_state",json_data);
                for (int i = 0; i < json_data.length(); i++) {
                    State_id = json_data.getJSONObject(i).getString("id");
                    String state = json_data.getJSONObject(i).getString("state_name");
                    State_list.add(state);
                }
                ArrayAdapter<String> adpater =  new ArrayAdapter<String>(Search_catagory.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        State_list);
                sp_state.setAdapter(adpater);
                adpater.notifyDataSetChanged();
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
                ArrayAdapter adapter = new ArrayAdapter(Search_catagory.this,android.R.layout.simple_list_item_1,all_product_catagory_names);
                Search_anything.setAdapter(adapter);
                Search_anything.setThreshold(1);

            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }
    private class get_all_catagories_Async extends AsyncTask<String, Void, Void> {
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
                jshare.saveJSONArray(Search_catagory.this,prefName,"allcategories",json_data);


            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }
    private class Listing_cat extends AsyncTask<String, Void, Void> implements DialogInterface.OnCancelListener {
       // ProgressHUD mProgressHUD;
        // Required initialization
        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        protected void onPreExecute() {
           // mProgressHUD = ProgressHUD.show(Search_catagory.this,"Connecting", true,true,this);

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
           // mProgressHUD.dismiss();
// NOTE: You can call UI Element here.
            try {
                String Country_name = null;
                JSONObject jsonObject = new JSONObject(result);
                 json_data = jsonObject.getJSONArray("allcategories");
                Log.d("allcategories : ", "" + json_data);

                jshare.saveJSONArray(Search_catagory.this,prefName,"allcategories",json_data);


            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
        @Override

        public void onCancel(DialogInterface dialog) {
            this.cancel(true);
            //mProgressHUD.dismiss();
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
                JSONArray json_datas = jshare.loadJSONArray(Search_catagory.this, prefName, "allcategories");
                for (int i=0;i<json_datas.length();i++)
                {
                    JSONObject jsonObj = json_datas.getJSONObject(i);
                    company_name  = jsonObj.getString("cat_name");
                    int cat_id = jsonObj.getInt("cat_id");
                    Log.d("company_name", "" + company_name);

                    items.add(new Item(company_name,cat_id, R.drawable.quard));

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
            final TextView ids;
            final Context context=null;
            if(v == null)
            {
                v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
                v.setTag(R.id.Parent_id, v.findViewById(R.id.Parent_id));

            }

            picture = (ImageView)v.getTag(R.id.picture);
            name = (TextView)v.getTag(R.id.text);
            ids = (TextView)v.getTag(R.id.Parent_id);

            Log.d("sixe od f item :",""+items.size());
            final  Item item = (Item)getItem(i);
            int imgSize = 0;
            // picture.setImageResource(item.drawableId);
            picture.setImageResource(R.drawable.acco);
            name.setText(item.name);
            ids.setText(""+item.id);
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
            final int id;
            final int drawableId;

            Item(String name,int id, int drawableId)
            {
                this.name = name;
                this.id = id;
                this.drawableId = drawableId;
            }

        }
    }
    public void customDialog()
    {
        Context context=null;
        AlertDialog.Builder builder = new AlertDialog.Builder(Search_catagory.this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Search_catagory.this);
        final FrameLayout frameView = new FrameLayout(Search_catagory.this);
        builder.setView(frameView);
        final AlertDialog alertDialog = builder.create();
        final AlertDialog alertDialog1=builder1.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.personal_page, frameView);
        // alertDialog.show();
        Animation bottomUp = AnimationUtils.loadAnimation(Search_catagory.this,
                R.anim.anim1);
        layout.bringToFront();
        layout.startAnimation(bottomUp);

        layout.setVisibility(View.VISIBLE);
        Button btn_photo_library=(Button)layout.findViewById(R.id.button_photo_library);
        btn_photo_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                layout.setVisibility(View.GONE);
                Intent intent_my_profile = new Intent(Search_catagory.this,User_profile.class);
                startActivity(intent_my_profile);

            }
        });
        Button btn_camera=(Button)layout.findViewById(R.id.button2_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                layout.setVisibility(View.GONE);

            }
        });
        Button btn_cancel=(Button)layout.findViewById(R.id.button3_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                layout.setVisibility(View.GONE);

            }
        });
        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.GONE);
                Intent intent_setting = new Intent(Search_catagory.this,App_Setting.class);
                startActivity(intent_setting);
            }
        });
        findViewById(R.id.button_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.GONE);
                SharedPreferences settings = Search_catagory.this.getSharedPreferences("JazeIt", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent intent = new Intent(Search_catagory.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
