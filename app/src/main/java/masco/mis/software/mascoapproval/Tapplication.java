package masco.mis.software.mascoapproval;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by TahmidH_MIS on 11/29/2016.
 */

public class Tapplication extends Application {
    private RequestQueue mRequestQueue;
    private static Tapplication mInstance;
    public static final String TAG = Tapplication.class.getSimpleName();
    private static final int MY_SOCKET_TIMEOUT_MS = 120000;
    private static Context mContext;
    private static SharedPreferences sharedPref;
    public static String PrefName;

    @Override

    public void onCreate() {
        super.onCreate();


        mInstance = this;
        mContext = getApplicationContext();
        sharedPref = mContext.getSharedPreferences(getString(R.string.Pref), Context.MODE_PRIVATE);
        PrefName = getString(R.string.Pref);


    }

    public static Context getContext() {
        return mContext;
    }

    public static synchronized Tapplication getInstance() {

        return mInstance;
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



    public static SharedPreferences Pref() {
        if (sharedPref == null) {
            sharedPref = mContext.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        }
        return sharedPref;

    }


}
