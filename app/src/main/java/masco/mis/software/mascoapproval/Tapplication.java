package masco.mis.software.mascoapproval;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import masco.mis.software.mascoapproval.DB.TDbOpenHelper;
import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

/**
 * Created by TahmidH_MIS on 11/29/2016.
 */

public class Tapplication extends Application {
    public static final String TAG = Tapplication.class.getSimpleName();
    private static final int MY_SOCKET_TIMEOUT_MS = 120000;
    public static String PrefName;
    private static Tapplication mInstance;
    private static Context mContext;
    private static SharedPreferences sharedPref;
    private RequestQueue mRequestQueue;
    private  static TDbOpenHelper tDbOpenHelper;

    public static Context getContext() {
        return mContext;
    }

    public static synchronized Tapplication getInstance() {

        return mInstance;
    }

    public static SharedPreferences Pref() {
        if (sharedPref == null) {
            sharedPref = mContext.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        }
        return sharedPref;

    }

    public static void FullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static String ID() {
        try {
            // return Settings.Secure.ANDROID_ID;
            return Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            return "NA";
        }
    }

    public static ProgressDialog pleaseWait(Activity activity, String msg) {
        ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setMessage(msg);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        return pDialog;
    }
    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
    private void CatchDragon()
    {
        Thread.UncaughtExceptionHandler myHandler = new ExceptionReporter(
                getDefaultTracker(),
                Thread.getDefaultUncaughtExceptionHandler(),
                mContext);

// Make myHandler the new default uncaught exception handler.
        Thread.setDefaultUncaughtExceptionHandler(myHandler);
    }


    @Override

    public void onCreate() {
        super.onCreate();


        mInstance = this;
        mContext = getApplicationContext();
        sharedPref = mContext.getSharedPreferences(getString(R.string.Pref), Context.MODE_PRIVATE);
        PrefName = getString(R.string.Pref);
//        try
//        {
//            copyAppDbToDownloadFolder();
//        }
//        catch(Exception e)
//        {
//            Toast.makeText(mInstance, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
        try
        {
            CatchDragon();
        }
        catch (Exception e)
        {
            Toast.makeText(mInstance, "Dragon "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }





    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {

//        req.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static JSONObject intiJson(String sp, String db, List<TParam> tParamList) {

        JSONObject json = new JSONObject();
        try {

            TRequest tRequest = new TRequest();
            tRequest.setSp(sp);
            tRequest.setDb(db);
            tRequest.setDict(tParamList);
            Gson gson = new Gson();
            json = new JSONObject(gson.toJson(tRequest, TRequest.class));

        } catch (Exception e) {
            Toast.makeText(Tapplication.getContext(), "IN T :" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return json;
    }
    public static TDbOpenHelper getTDbHelper()
    {
        if (tDbOpenHelper ==null)
        {
            return new TDbOpenHelper(Tapplication.getContext());
        }
        return tDbOpenHelper;


    }
    public static SQLiteDatabase WriteDB()
    {
        return  Tapplication.getTDbHelper().getWritableDatabase();
    }
    public static SQLiteDatabase ReadDB()
    {
        return  Tapplication.getTDbHelper().getReadableDatabase();
    }
    public void copyAppDbToDownloadFolder() throws IOException {
        File backupDB = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "TDB.db"); // for example "my_data_backup.db"
        File currentDB = getApplicationContext().getDatabasePath("TDB.db"); //databaseName=your current application database name, for example "my_data.db"
        if (currentDB.exists()) {
            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        }
    }
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//
//        // Create an ConfigurationBuilder. It is prepopulated with values specified via annotation.
//        // Set any additional value of the builder and then use it to construct an ACRAConfiguration.
//
//        final ACRAConfiguration config = new ConfigurationBuilder(this).build();
//
//        // Initialise ACRA
//        ACRA.init(this, config);
//    }




}
