package com.jazeit.jazeitapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.jazeit.jazeitapp.R;


import com.jazeit.jazeitapp.Server_connection_class.Server_Connection_signupEmail;
import com.jazeit.jazeitapp.custom_class.ProgressHUD;
import com.jazeit.jazeitapp.dd.CircularProgressButton;
import com.jazeit.jazeitapp.globalclass.CropOption;
import com.jazeit.jazeitapp.globalclass.CropOptionAdapter;
import com.jazeit.jazeitapp.globalclass._glbFuncs;
import com.jazeit.jazeitapp.globalclass._glbServerConfig;
import android.content.DialogInterface.OnCancelListener;
@SuppressWarnings("unused")


public class MainActivity extends Activity  {
    private static final int SELECT_PICTURE = 1;
    Context _oContext;
    private String selectedImagePath;
    Button btn_choose;
    CircularProgressButton log;
    AnimationDrawable frameAnimation;
    Bitmap bmp;
    String id;
    InputStream is = null;
    String result = null;
    String line = null;
    int code;
    Canvas canvas;
    int density;DisplayMetrics metrics;
    RelativeLayout layout;
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    EditText name, uname, email, password;
    _glbFuncs glbFuncs = new _glbFuncs();
    private ProgressDialog pDialog;

    private SharedPreferences prefs;
    private String prefName = "JazeIt";
    Bitmap bitmap;
    com.jazeit.jazeitapp.custom_class.ImageViewRounded mImageView;
    _glbServerConfig glbconf = new _glbServerConfig();
    Boolean is_intenet;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        init();


        // Choose Image from gallery set it to Imageview
        btn_choose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                customDialog();


            }
        });

        log = (CircularProgressButton)findViewById(R.id.btn_save);
        log.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if((glbFuncs.isEmptyET(name)==true) && (glbFuncs.isEmptyET(uname) == true)&&(glbFuncs.isEmptyET(password) == true) && (glbFuncs.isEmptyET(uname) == true ))
                {
                    new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name).setMessage(R.string.All_field_blank).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();
                }else if (glbFuncs.isEmptyET(name) == true) {
                    new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name).setMessage(R.string.Name_is_Empty).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();
                    return;
                } else if (glbFuncs.isEmptyET(uname) == true) {
                    new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name).setMessage(R.string.Username_is_Empty).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();
                    return;
                } else if (glbFuncs.isEmptyET(password) == true) {
                    new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name).setMessage(R.string.Password_is_Empty).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();
                    return;
                }
                String emailid = email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                //onClick of button perform this simplest code.
                if (emailid.matches(emailPattern)) {
                    Boolean is_internet  = glbFuncs.isConnectingToInternet(MainActivity.this);
                    if(!is_internet)
                    {
                        new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name).setMessage(R.string.Server_not_reachable).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();
                    }else
                    {
                        String Server_url = glbconf.Server_Registration;
                        new Registration_Async().execute(Server_url);
                        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        //---save the values in the EditText view to preferences---
                        editor.putString("name", name.getText().toString());
                        editor.commit();
                    }
                } else {
                    new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name).setMessage(R.string.Email_not_valid).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();
                }
            }


        });
        findViewById(R.id.imageButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(r);
                finish();

            }
        });
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });
    }
    //====================INITIALOZATION OF CONTROLS=======================
    public void init()
    {
        name = (EditText) findViewById(R.id.et_firstname);
        uname = (EditText) findViewById(R.id.et_lastname);
        email = (EditText) findViewById(R.id.et_Email);
        password = (EditText) findViewById(R.id.et_passwords);
        EditText confirm_pwd = (EditText) findViewById(R.id.et_conpasswords);
        btn_choose = (Button) findViewById(R.id.btn_choose);
        mImageView = (com.jazeit.jazeitapp.custom_class.ImageViewRounded) findViewById(R.id.imageView_select_profile_pic);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        layout= (RelativeLayout)findViewById(R.id.custom_layout);
        metrics = new DisplayMetrics();
        MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        density = metrics.densityDpi;
    }
    //====================AFTER SELECTING CAMERA OR MEDIA OPTION=======================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("Inside activity result in profile fragment: ", "");
        if (requestCode ==PICK_FROM_CAMERA && resultCode == Activity.RESULT_OK)
        {
            doCrop();
        }
        if (requestCode == PICK_FROM_FILE && resultCode == Activity.RESULT_OK && null != data)
        {
            mImageCaptureUri = data.getData();
            Log.d("URI OF AN IMAGE - ", "" + mImageCaptureUri);
            doCrop();
        }
        if(requestCode== CROP_FROM_CAMERA )
        {
            DisplayMetrics metrics = new DisplayMetrics();
            MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            density = metrics.densityDpi;
            switch (density)
            {
                case DisplayMetrics.DENSITY_TV:
                    com.jazeit.jazeitapp.custom_class.ImageViewRounded mImageViews=(com.jazeit.jazeitapp.custom_class.ImageViewRounded)MainActivity.this.findViewById(R.id.imageView_select_profile_pic);
                    mImageViews.setPadding(0,0,0,0);
                    mImageViews.setVisibility(View.VISIBLE);
                    break;
            }
            if(data==null)
            {
            }
            else
            {
                Bundle extras = data.getExtras();
                if (extras != null)
                {
                    int i = 1;
                    metrics = new DisplayMetrics();
                    MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    density = metrics.densityDpi;
                    //getActivity().getWindow().findViewById(R.id.imageView5).setVisibility(View.GONE);
                    Bitmap photo = extras.getParcelable("data");
                    String path  =  MediaStore.Images.Media.insertImage(MainActivity.this.getContentResolver(), photo, "Wella" + ".png", "+Drawing");
                    Log.d("Path is - ", "" + path);
                    Bitmap result = null;
                    try
                    {
                        Bitmap bitmapResized = Bitmap.createScaledBitmap(photo, 305, 305, false);
                        result = Bitmap.createBitmap(305, 305, Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(result);
                        int color = 0xff424242;
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        //canvas.drawCircle(135,100,155, paint);
                        canvas.drawCircle(150,150,153,paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmapResized, 0,0, paint);
                    }
                    catch (NullPointerException e)
                    {

                    }
                    catch (OutOfMemoryError o)
                    {

                    }
                    Cursor c = MainActivity.this.getContentResolver().query(
                            Uri.parse(path), null, null, null, null);
                    c.moveToNext();
                    String actual_path= c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                    c.close();
                    Log.d("Actual Path is - ", "" + actual_path);
                    mImageView.setImageBitmap(result);
                    try {
                        /*String api_token=globalvariable.getToken();
                        String image_path= new ServerConnectionForUploadingAnImageFileToServer().execute(user_id,actual_path,api_token).get();
                        String  image_path1=ConstantsForWella.urlimgUploaded+image_path;
                        new ServerConnectionForDisplayingImageInAccountPage().execute(imageView_select_profile_pic,image_path1);
                   */ }catch (Exception e){}
                }
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) f.delete();
            }
        }
    }
    public void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        Intent intent1 = new Intent("com.android.camera.action.CROP");
        intent1.setType("image/*");
        List<ResolveInfo> list=MainActivity.this.getPackageManager().queryIntentActivities( intent1, 0 );
        int size = list.size();
        if (size == 0)
        {
            Toast.makeText(MainActivity.this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            Toast.makeText(MainActivity.this, "Find image crop app", Toast.LENGTH_SHORT).show();
            intent1.setData(mImageCaptureUri);
            intent1.putExtra("outputX", 256);
            intent1.putExtra("outputY", 256);
            intent1.putExtra("aspectX", 4);
            intent1.putExtra("aspectY", 3);
            intent1.putExtra("scale", true);
            intent1.putExtra("scaleType","centerCrop");
            intent1.putExtra("adjustViewBounds",true);
            intent1.putExtra("return-data", true);
            if (size == 1)
            {
                Intent i2= new Intent(intent1);
                ResolveInfo res	= list.get(0);
                i2.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                startActivityForResult(i2, CROP_FROM_CAMERA);
            }
            else
            {
                for (ResolveInfo res : list)
                {
                    final CropOption co = new CropOption();
                    co.title 	= MainActivity.this.getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon		= MainActivity.this.getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent= new Intent(intent1);
                    co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }
                CropOptionAdapter adapter = new CropOptionAdapter(MainActivity.this.getApplicationContext(), cropOptions);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.choose_app);
                builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int item ) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });
                builder.setCancelable(false);
                builder.setOnCancelListener( new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel( DialogInterface dialog ) {

                        if (mImageCaptureUri != null ) {
                            MainActivity.this.getContentResolver().delete(mImageCaptureUri, null, null );
                            mImageCaptureUri = null;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    private class Registration_Async extends AsyncTask<String, Void, Void> implements OnCancelListener{
        ProgressHUD mProgressHUD;
        @Override
        protected void onPreExecute() {
            mProgressHUD = ProgressHUD.show(MainActivity.this,"Connecting", true,true,this);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("fname",name.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("email",email.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password",password.getText().toString()));
            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(glbconf.Server_Registration);
                httppost.addHeader("Content-Type","application/x-www-form-urlencoded");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                Log.e("pass 1", "connection success ");
            }
            catch(Exception e)
            {
                Log.e("Fail 1", e.toString());
                Toast.makeText(getApplicationContext(), "Invalid IP Address",
                        Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Void doInBackground(String... params) {
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
            mProgressHUD.dismiss();
            super.onPostExecute(aVoid);
            try
            {
                JSONObject json_data = new JSONObject(result);
                JSONObject Log_status=json_data.getJSONObject("userinfo");
                Log.d("userinfo",""+Log_status);
                String status = Log_status.getString("status").toString();
                Log.d("status : ",""+status);
                if (status.equals("1"))
                {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else
                {
                    new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name).setMessage(R.string.User_already_exists).setNeutralButton(R.string.OK_btn,null).setInverseBackgroundForced(true).show();

                }

            }
            catch(Exception e)
            {
                Log.e("Fail 3", e.toString());
            }
        }
        @Override

        public void onCancel(DialogInterface dialog) {
            this.cancel(true);
            mProgressHUD.dismiss();
        }
    }
    // for downloading Profile Pics from server
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @SuppressLint("NewApi") @Override
        protected void onPreExecute() {
            super.onPreExecute();
			/*
			 * pDialog = new ProgressDialog(MainActivity.this);
			 * pDialog.setMessage("Loading Image ...."); pDialog.show();
			 */
            mImageView.setBackground(null);
        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(
                        args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                mImageView.setImageBitmap(image);
				/* pDialog.dismiss(); */
            } else {
				/* pDialog.dismiss(); */
                Toast.makeText(MainActivity.this,
                        "Image Does Not exist or Network Error",
                        Toast.LENGTH_SHORT).show();
                Log.d("Image : ","Image Does Not exist or Network Error");
            }
        }
    }
    @SuppressWarnings("deprecation")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
          finish();
        }
        return false;
    }
    public void customDialog()
    {
        Context context=null;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        final FrameLayout frameView = new FrameLayout(MainActivity.this);
        builder.setView(frameView);
        final AlertDialog alertDialog = builder.create();
        final AlertDialog alertDialog1=builder1.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.custom_alert_dialog, frameView);
        // alertDialog.show();
        Animation bottomUp = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.anim1);
        layout.startAnimation(bottomUp);
        layout.setVisibility(View.VISIBLE);
        Button btn_photo_library=(Button)layout.findViewById(R.id.button_photo_library);
        btn_photo_library.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                layout.setVisibility(View.GONE);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle(R.string.app_name);
                alertBuilder.setMessage(R.string.access_photo);
                alertBuilder.setPositiveButton(R.string.OK_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_FROM_FILE);                                }
                });
                alertBuilder.setNegativeButton(R.string.not_allow, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alertBuilder.show();
            }
        });
        Button btn_camera=(Button)layout.findViewById(R.id.button2_camera);
        btn_camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                layout.setVisibility(View.GONE);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, PICK_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        Button btn_cancel=(Button)layout.findViewById(R.id.button3_cancel);
        btn_cancel.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                layout.setVisibility(View.GONE);

            }
        });
    }
}