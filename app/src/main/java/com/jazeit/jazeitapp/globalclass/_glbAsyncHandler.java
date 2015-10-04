package com.jazeit.jazeitapp.globalclass;

        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import android.annotation.SuppressLint;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.text.Html;
        import android.util.Log;
        import android.widget.TextView;
        import android.widget.Toast;

@SuppressWarnings("rawtypes")
public  class _glbAsyncHandler  extends AsyncTask<String, Void, Void>  {

    public String PARAM_PostHandler = "";
    public TextView PARAM_FillTextViewOnSuccess = null ;
    public Class PARAM_RunActivityOnSuccess = null;

    public boolean PARAM_ShowDowloadDialog = false ;
    private ProgressDialog Dialog;
    private String RETURN_OutReturnValue = "";
    public String PARAM_DailogMessage = "Downloading..";
    public Context PARAM_CallingContext;
    public String PARAM_FileName = "";
    public String PARAM_PrefKey = "";
    public String PARAM_CUSTOM1 = "";

    _glbMessages _MsgStore = new _glbMessages();
    _glbFileHandler glbFile;
    _glbFuncs glbFuncs = new _glbFuncs();

    protected void onPreExecute() {
        try {
            if(glbFuncs.isInternetOn(PARAM_CallingContext, false) == true){
                if (PARAM_ShowDowloadDialog == true ) {
                    Dialog = new ProgressDialog (PARAM_CallingContext);
                    Dialog.setMessage(PARAM_DailogMessage);
                    Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    Dialog.show();
                }
            }
            else{ super.cancel(isCancelled()); }
        }
        catch (Exception e ) { Toast.makeText(PARAM_CallingContext, e.getMessage(), Toast.LENGTH_LONG).show(); }
    }

    @SuppressLint("DefaultLocale")
    protected void onPostExecute(Void unused) {
        try {
            if (PARAM_ShowDowloadDialog == true) Dialog.dismiss();
            if (PARAM_PostHandler.compareTo(_MsgStore.HANDLER_START_ACTIVITY) == 0 ) {
                if ( RETURN_OutReturnValue.startsWith("ERR") ){
                    Toast.makeText(PARAM_CallingContext, RETURN_OutReturnValue, Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(PARAM_CallingContext, PARAM_RunActivityOnSuccess);
                    i.putExtra("Data", RETURN_OutReturnValue);
                    i.putExtra("ID", RETURN_OutReturnValue);
                    i.putExtra("CUSTOM1", PARAM_CUSTOM1);
                    PARAM_CallingContext.startActivity (i);
                }
            }

            if (PARAM_PostHandler.compareTo(_MsgStore.HANDLER_POPULATE_TEXTVIEW_HTML) == 0  ) { PARAM_FillTextViewOnSuccess.setText(Html.fromHtml(RETURN_OutReturnValue)); }
            if (PARAM_PostHandler.compareTo(_MsgStore.HANDLER_POPULATE_TEXTVIEW) == 0 ) {PARAM_FillTextViewOnSuccess.setText(RETURN_OutReturnValue); }
            if (PARAM_PostHandler.compareTo(_MsgStore.HANDLER_WRITE_TOAST) == 0) Toast.makeText(PARAM_CallingContext, RETURN_OutReturnValue, Toast.LENGTH_LONG).show();

            if (PARAM_PostHandler.compareTo(_MsgStore.HANDLER_SAVE_PREF) == 0) {
                if ( RETURN_OutReturnValue.contains("ERROR") ){
                    Toast.makeText(PARAM_CallingContext, RETURN_OutReturnValue, Toast.LENGTH_LONG).show();
                }else {
                    glbFuncs.savePrefs(PARAM_PrefKey, RETURN_OutReturnValue, PARAM_CallingContext);
                }
            }

            if (PARAM_PostHandler.compareTo(_MsgStore.HANDLER_WRITE_TO_FILE) == 0) {
                if ( RETURN_OutReturnValue.contains("ERROR") ){
                    Toast.makeText(PARAM_CallingContext, RETURN_OutReturnValue, Toast.LENGTH_LONG).show();
                } else {
                    if ( RETURN_OutReturnValue.length() < 2048) {
                        glbFile = new _glbFileHandler(PARAM_FileName);
                        glbFile.DeleteFile(PARAM_CallingContext);
                        glbFile.AppendLine(RETURN_OutReturnValue, PARAM_CallingContext);
                        glbFile = null;
                    }
                }
            }
        }
        catch (Exception e ) { Toast.makeText(PARAM_CallingContext, e.getMessage(), Toast.LENGTH_LONG).show(); }
    }

    @Override
    protected void onCancelled(){ Toast.makeText(PARAM_CallingContext, _MsgStore.MSG_REQUEST_CALNCELLED, Toast.LENGTH_SHORT).show(); }
    @SuppressLint("NewApi")
    protected Void doInBackground(String... urls) {
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urls[0]);
            Log.d("originalURL", "" + urls[0]);
            Toast.makeText(PARAM_CallingContext, "Exception: " + urls[0], Toast.LENGTH_LONG).show();

            HttpResponse execute = client.execute(httpGet);
            InputStream content = execute.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) { RETURN_OutReturnValue += s; }
        } catch (Exception e) {
            Toast.makeText(PARAM_CallingContext, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if ( RETURN_OutReturnValue.isEmpty() == true) {  RETURN_OutReturnValue = "ERROR: INTERNET PROBLEM."; }
        return null;
    }

}