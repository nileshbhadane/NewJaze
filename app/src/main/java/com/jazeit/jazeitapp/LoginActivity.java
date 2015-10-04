package com.jazeit.jazeitapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
//import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
//import android.support.v4.app.DialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.Fragment;

/*
import com.facebook.login.LoginClient;
*/
import com.facebook.internal.CollectionMapper;
import com.google.android.gms.appdatasearch.GetRecentContextCall;
/*import com.google.android.gms.fitness.data.Session;*/
import com.google.android.gms.fitness.data.Session;
import com.jazeit.jazeitapp.custom_class.ProgressHUD;
import com.jazeit.jazeitapp.globalclass._glbAsyncHandler;
import com.jazeit.jazeitapp.globalclass._glbFileHandler;
import com.jazeit.jazeitapp.globalclass._glbFuncs;
import com.jazeit.jazeitapp.globalclass._glbServerConfig;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
public class LoginActivity extends FragmentActivity {
    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;
	com.jazeit.jazeitapp.globalclass._glbFuncs glbFunc = new com.jazeit.jazeitapp.globalclass._glbFuncs();
	EditText username, pass;

	int ImageLength,ImageCtr,code;
    Button btn_forgotpassword,btnfb,btntwit;
    ImageView slidingImage;
	InputStream is = null;
	String result = null,status,line = null,item = "",id;
	JSONObject jsonObject;
	JSONArray Jarray;
    com.jazeit.jazeitapp.globalclass._glbServerConfig glbconf = new com.jazeit.jazeitapp.globalclass._glbServerConfig();
	private SharedPreferences prefs;
	private String prefName = "JazeIt";
    ProgressDialog progressDialog;
    String Server_url = "http://www.thumpi.com/jazeit_api/api_user/login";
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //You need this method to be used only once to configure
        //your key hash in your App Console at
        // developers.facebook.com/apps

        getFbKeyHash("com.jaze.hello_world");

        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);



        }

        init();

		Button log = (Button) findViewById(R.id.btn_log);
        log.bringToFront();
		log.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
              Boolean is_netConnetion = glbFunc.isInternetOn(LoginActivity.this,true);
                        //.isConnectingToInternet(LoginActivity.this);
                if ( glbFunc.isInternetOn(getApplicationContext(), false) == false ) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle(R.string.app_name)
                            .setMessage(R.string.Server_not_reachable)
                            .setNeutralButton(R.string.OK_btn, null)
                            .setInverseBackgroundForced(true).show();

                } else {
                    if (username.getText().toString().matches("")
                            && pass.getText().toString().matches("")) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle(R.string.app_name)
                                .setMessage(R.string.All_field_blank)
                                .setNeutralButton(R.string.OK_btn, null)
                                .setInverseBackgroundForced(true).show();


                    } else {
                        progressDialog = new ProgressDialog(LoginActivity.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Authenticating...");
                        progressDialog.show();
                            String Server_url = "http://www.thumpi.com/jazeit_api/api_user/login";
                            new LongOperation().execute(Server_url);

                        //http://www.thumpi.com/jazeit_api/api_user/login?email=jairangwani@gmail.com&password=q1w2e3r4
                       /* if ( glbFunc.isInternetOn(getApplicationContext(), false) == true ) {
                            String names = username.getText().toString();
                            String appide = pass.getText().toString();
                            String _url = Server_url + "?email=" + names + "&password=" + appide  ;
                            _glbAsyncHandler glbAsyn = new _glbAsyncHandler();
                            glbAsyn.PARAM_CallingContext = LoginActivity.this;
                            glbAsyn.PARAM_DailogMessage = "Updating Server ..";
                            glbAsyn.PARAM_ShowDowloadDialog = false ;
                            glbAsyn.PARAM_PrefKey = "storedAddress";
                            glbAsyn.PARAM_PostHandler =	"SAVE_PREF";
                            glbAsyn.execute(_url);
                            glbAsyn = null;
                        }*/
                   }
                }





            }
		});
        fbLoginButton = (LoginButton)findViewById(R.id.fb_login_button);

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(LoginActivity.this,Search_catagory.class);
                startActivity(intent);
                System.out.println("Facebook Login Successful!");
                System.out.println("Logged in user Details : ");
                System.out.println("--------------------------");
                System.out.println("User ID  : " + loginResult.getAccessToken().getUserId());
                Log.d("User Id ",""+loginResult.getAccessToken().getUserId());
                Log.d("User name",""+loginResult.getAccessToken().getApplicationId());
                prefs = getSharedPreferences(prefName, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                //---save the values in the EditText view to preferences---
                editor.putString("FBUser_id", loginResult.getAccessToken().getUserId());
                editor.putString("FBAccessToken", loginResult.getAccessToken().getToken());

                editor.commit();
                System.out.println("Authentication Token : " + loginResult.getAccessToken().getToken());
               Log.d("Authentication : ", "" + loginResult.getAccessToken().getToken());
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");
            }
        });


		btnfb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
        btntwit.setOnClickListener(new OnClickListener() {

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
        findViewById(R.id.btn_signup).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_forgotpass).setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Forgot_Password_Activity.class);
                 startActivity(intent);
             }
          }
        );

	}

    public void init()
    {
        username = (EditText) findViewById(R.id.et_email);
        pass = (EditText) findViewById(R.id.et_pass);
        btnfb = (Button) findViewById(R.id.btn_fb);
        btntwit = (Button) findViewById(R.id.btn_twit);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.header);
        rl.bringToFront();

    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            Log.wtf("twitter", "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

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
                           // finish();
                            finish();
                            return;
                        }
                    });
            ExitAlert.show();

        }

        return false;
    }
    private class LongOperation extends AsyncTask<String, Void, Void> implements DialogInterface.OnCancelListener {
        ProgressHUD mProgressHUD;
        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        String data = "";
        int sizeData = 0;
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);

        protected void onPreExecute() {
// NOTE: You can call UI Element here.

// Start Progress Dialog (Message)

            mProgressHUD = ProgressHUD.show(LoginActivity.this,"Connecting", true,true,this);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String names = username.getText().toString();
            String appide = pass.getText().toString();
/*
            nameValuePairs.add(new BasicNameValuePair("user", names));
            nameValuePairs.add(new BasicNameValuePair("pass", appide));  */
            nameValuePairs.add(new BasicNameValuePair("email", names));
            nameValuePairs.add(new BasicNameValuePair("password", appide));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        "http://www.thumpi.com/jazeit_api/api_user/login");
// "http://thumpi.com/webservices/code/User/authenticate");
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
// NOTE: You can call UI Element here.

// Close progress dialog
            mProgressHUD.dismiss();

            try {

                JSONObject json_data = new JSONObject(result);
                String status = json_data.getString("status");
                if(status.equals("false")) {
                    new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.app_name).setMessage(R.string.Password_is_Match).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();

                }else
                {
                    JSONObject json_obj1 = json_data.getJSONObject("userinfo");
                    String status1 = json_obj1.getString("status").toString();
                    String Email = json_obj1.getString("email").toString();
                    String user_id = json_obj1.getString("userid").toString();
                    String firstname = json_obj1.getString("fname").toString();
                    prefs = getSharedPreferences(prefName, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    //---save the values in the EditText view to preferences---
                    editor.putString("firstname", firstname);
                    editor.putString("status", status1);
                    editor.putString("Email", Email);
                    editor.putString("user_id", user_id);

                    //---saves the values---
                    editor.commit();

                    progressDialog.dismiss();
                    Log.d("Original data : ",""+status1+""+Email);
                    Intent intent = new Intent(LoginActivity.this,Search_catagory.class);
                   // Intent intent = new Intent(LoginActivity.this,Home_activity.class);
                    startActivity(intent);
                }
              /*  if(json_data==null)
                {
                    new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.app_name).setMessage(R.string.Password_is_Match).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();

                }else {
                    Log.d("json_data : ", "" + json_data);
                    String flag = json_data.getString("code").toString();
                    if (flag.equals("5"))
                    {
                        new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.app_name).setMessage(R.string.Password_is_Match).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();

                    }else if (flag.equals("4"))
                    {
                        new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.app_name).setMessage(R.string.User_not_Creted).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();

                    }else
                    {

                    }


                }*/
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
        @Override

        public void onCancel(DialogInterface dialog) {
            this.cancel(true);
            mProgressHUD.dismiss();
        }
    }
    static public class MyDialogFragment extends DialogFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d!=null){
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.mydia, container, false);
            return root;
        }

    }
    public void getFbKeyHash(String packageName) {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("YourKeyHash: "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent i) {

        callbackManager.onActivityResult(reqCode, resCode, i);

    }

}
