package com.jazeit.jazeitapp.globalclass;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.net.ConnectivityManager;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;

public class _glbFuncs {
	//_glbMessages _MsgStore = new _glbMessages(); 
	
	public void SetText(int TextVw, String Text, View vR) {
		TextView oTextView = (TextView) vR.findViewById(TextVw);
		oTextView.setText(Text); 
	}

	public void SetTextE(int TextVw, String Text, View vR) {
		EditText oTextView = (EditText) vR.findViewById(TextVw);
		oTextView.setText(Text); 
	}

	public void SetTextB(int TextVw, String Text, View vR) {
		Button oButton = (Button) vR.findViewById(TextVw);
		oButton.setText(Text); 
	}

	public String GetText(int TextVw, View vR) {
		TextView oTextView = (TextView) vR.findViewById(TextVw);
		String retVal = (String) oTextView.getText();
		return retVal ;
	}

	public String GetTextE(int TextVw, View vR) {
		EditText oTextView = (EditText) vR.findViewById(TextVw);
		String retVal = (String) oTextView.getText().toString();
		return retVal ;
	}
	
	public boolean isEmptyET(EditText etText) {
	    return etText.getText().toString().trim().length() == 0;
	}
	
	public void savePrefs(String key, String value, Context oContext) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(oContext);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String GetPrefs(String Key, Context oContext) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(oContext);
		String retVal = sharedPreferences.getString(Key, "");
		return retVal; 
	}

	public String GetPrefs(String Key, Context oContext, String Default) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(oContext);
		String retVal = sharedPreferences.getString(Key, Default);
		return retVal; 
	}
	
	public String GetAppId () {	return "";	}

	public String GetAuthcode (Context oContext) 
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(oContext);
		String retVal = sharedPreferences.getString("AuthCode", "ERR");
		return retVal; 
	}

	public void SetToast(String text,Context ocontext ){
		Toast.makeText(ocontext, text, Toast.LENGTH_LONG).show();
	}

	//public String PrepTransport ( String InputURL, String Direction )
//	{
		//if (Direction.compareTo(_MsgStore.ENUM_TO_HTTP)==0)
			//return InputURL.replace("+", "plus").replace(" ", "_SP_").replace("/", "slash").replace("=", "equalto");
		//else 
			//return InputURL.replace("plus", "+").replace("slash", "/").replace("equalto", "=").replace("_SP_", " ");
	//}
	
	public String getPackageName(Context oContext){
		String packageName = null;
		try {
			packageName = oContext.getPackageManager().getPackageInfo(oContext.getPackageName(), 0).packageName.toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageName;
	}
	
	public String getPath(String _Filename) {
		String _rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + _Filename ;
		return _rootPath;
	}
	public String getCachePath(Context oContext){
		String _rootPath =oContext.getCacheDir()+"/";
		return _rootPath;
	}
	public boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public File createDirectory(String dirName) {
		String _rootPath = Environment.getExternalStorageDirectory()
				+ dirName;
		File _file = new File(_rootPath);
		_file.mkdirs();
		return _file;
	}
	
	/*
	@SuppressWarnings("rawtypes")
	public void getNotification(Integer[] progress,Class runActivityOnSuccess,Context callingContext ) {
		String _notificationService = Context.NOTIFICATION_SERVICE;
		int mNotificationId = 001;
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(callingContext)
		.setSmallIcon(R.drawable.download)
		.setContentTitle("EasyLife Report Center")
		.setContentText("Download is " + progress[0] + "% done")
		.setProgress(100, progress[0], false)
		.setAutoCancel(true);
		
		Intent resultIntent = new Intent(callingContext,runActivityOnSuccess);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(callingContext, 0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotifyMgr = (NotificationManager) callingContext.getSystemService(_notificationService);
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}
	*/
	
	public void getVibrate(Context oContext){
		Vibrator v = (Vibrator) oContext.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(400);
	}
	
	public boolean StringMatcher(String originalString, String searchString, boolean isIgnoreCase){
		boolean matchString = true; 

		String[] searchValue =  searchString.split(" ");
		int sLength = searchValue.length;
		int istartPos = 0; 
		int icurrPos = 0; 

		if (originalString.startsWith(searchValue[0]) == true) {
			for (int iCtr = 0; iCtr <= sLength - 1; iCtr ++) {
				
				icurrPos = originalString.indexOf(searchValue[iCtr], istartPos) ;
				if (icurrPos != -1 ) {
					istartPos = icurrPos + searchValue [iCtr].length();
				} else {
					matchString = false;
					break ;
				}
			}
		}
		else { matchString = false; }
		return matchString;
	}	
	
	public boolean isInternetOn(Context _oContext, boolean ShowToast) {
	        Runtime runtime = Runtime.getRuntime();
	        /*try
	        {
	            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
	            int mExitValue = mIpAddrProcess.waitFor();
	            if( mExitValue == 0) { return true; }
	            else { return false; }
	        }
	        catch (InterruptedException ignore) { ignore.printStackTrace(); System.out.println(" Exception:"+ignore); } 
	        catch (IOException e) { e.printStackTrace(); System.out.println(" Exception:"+e); }
	        return false;*/
		ConnectivityManager connec = (ConnectivityManager) _oContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo _mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		android.net.NetworkInfo _wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		WifiManager wifiMgr = (WifiManager) _oContext.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		String wifiName = wifiInfo.getSSID();

		if(_wifi.isConnected() || connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING){
			if ( ShowToast == true ) Toast.makeText(_oContext, "Using Wi-fi Network .. " + wifiName , Toast.LENGTH_SHORT).show();
			return true;
		}else if(_mobile.isConnected()|| connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING) {
			if ( ShowToast == true ) Toast.makeText(_oContext, "Using Mobile Data Network ..", Toast.LENGTH_SHORT).show();
			return true;
		} else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED
				|| connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
			//if ( ShowToast == true ) Toast.makeText(_oContext, _MsgStore.MSG_NO_CONNECTION,Toast.LENGTH_SHORT).show();
			return false;
		}
		return false;
	}
	
	 
	    public boolean isConnectingToInternet(Context _oContext){
	        ConnectivityManager connectivity = (ConnectivityManager) _oContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null) 
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null) 
	                  for (int i = 0; i < info.length; i++) 
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	 
	          }
	          return false;
	    }
}