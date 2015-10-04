package com.jazeit.jazeitapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


public class Profile_Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        TextView tv = (TextView) findViewById(R.id.Company);
        tv.setText(getIntent().getStringExtra("title_profiledetails"));
        /*ImageView link= (ImageView) findViewById(R.id.linkedin);
        ImageView fb= (ImageView) findViewById(R.id.facebook);
        ImageView twit= (ImageView) findViewById(R.id.twitter);
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
            Log.wtf("twitter", "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
