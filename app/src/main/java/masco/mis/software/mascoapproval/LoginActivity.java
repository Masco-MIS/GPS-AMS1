package masco.mis.software.mascoapproval;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;
import masco.mis.software.mascoapproval.pojo.TResult;

public class LoginActivity extends Activity {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lTest = Tapplication.Pref().getString(getString(R.string.pref_login_data),"");
        if( lTest != null ||!lTest.isEmpty())
        {

        }
        else
        {

        }
        setContentView(R.layout.activity_login);

        ((Button) findViewById(R.id.btn_login_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pDialog = new ProgressDialog(LoginActivity.this);
                    pDialog.setMessage("Please wait...");
                    pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    JSONObject json = new JSONObject();
////                    json.put("id", ((EditText) findViewById(R.id.edt_login_user)).getText().toString());
////                    json.put("pass", ((EditText) findViewById(R.id.edi_login_password)).getText().toString());
//                    Map<String, String> mp = new HashMap();
//
//                    mp.put("@id", ((EditText) findViewById(R.id.edt_login_user)).getText().toString());
//                    mp.put("@pass", ((EditText) findViewById(R.id.edi_login_password)).getText().toString());
//                    JSONObject id = new JSONObject();
//                    id.put("@id",((EditText) findViewById(R.id.edt_login_user)).getText().toString());
//                    JSONObject pass = new JSONObject();
//                    pass.put("@pass",((EditText) findViewById(R.id.edi_login_password)).getText().toString());
//                    json.put("db", getString(R.string.DB_SCM));
//                    json.put("sp", "usp_m_login");
//                  //  json.put("sp","test_api");
//                    JSONArray ss = new JSONArray();
//                    ss.put(id);
//                    ss.put(pass);
//                    json.put("dict", ss);
                    TRequest tRequest = new TRequest();
                    tRequest.setSp("usp_m_login");
                    tRequest.setDb(getString(R.string.DB_SCM));
                    List<TParam> tParamList = new ArrayList<TParam>();
                    tParamList.add(new TParam("@id",((EditText) findViewById(R.id.edt_login_user)).getText().toString()));
                    tParamList.add(new TParam("@pass",((EditText) findViewById(R.id.edi_login_password)).getText().toString()));
                    tRequest.setDict(tParamList);
                    Gson gson = new Gson();
                    json = new JSONObject(gson.toJson(tRequest,TRequest.class));





        //            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.api_v1_get_data), json, loginListener(), genericErrorListener());
                   //http://192.168.2.72/TWebApiSearch/api/v1/TService/GetData
                    Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Method.POST, "http://192.168.2.72/TWebApiSearch/api/v1/TService/GetData", json, loginListener(), genericErrorListener()));
                 //   Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Method.POST, getString(R.string.api_v1_get_data), json, loginListener(), genericErrorListener()));
               //    Tapplication.getInstance().addToRequestQueue(jsonObjectRequest);


                } catch (Exception ex) {
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }

                    Toast.makeText(getApplicationContext(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private Response.ErrorListener genericErrorListener() {
        return new ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                if (pDialog != null) {
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                }
                Toasts.biscuit(getApplicationContext(),error.getMessage(), Color.RED);
                try {
                    if (error instanceof NoConnectionError) {
                        //   tvValidationMessage.setText(getString(R.string.txt_login_internet));

                    } else if (error instanceof NetworkError) {
                        //    tvValidationMessage.setText(getString(R.string.txt_login_network_error));

                    } else if (error instanceof ServerError) {
                        //   tvValidationMessage.setText(getString(R.string.txt_login_server));
                    } else if (error instanceof TimeoutError) {
                        //    tvValidationMessage.setText(getString(R.string.txt_login_time_out));
                    } else if (error instanceof VolleyError) {
                        try {
                            //           tvValidationMessage
                            //                  .setText(getString(R.string.txt_login_volley_error) + " " + error.getMessage());
                        } catch (Exception e) {
                            //           tvValidationMessage.setText(getString(R.string.txt_login_internet));
                        }
                    }

                    // tvValidationMessage.setText(error.toString());
                    // tvValidationMessage
                    // .setText("Code :" + error.networkResponse.statusCode +
                    // "\nNetwork Error\nPlease Try Again");
                    // Toast.makeText(getApplicationContext(),
                    // "Code :" + error.networkResponse.statusCode + "\nNetwork
                    // Error\nPlease Try Again",
                    // Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // tvValidationMessage.setText("No internet connection available\n" + error.getMessage());
                    // Toast.makeText(getApplicationContext(),
                    // Toast.makeText(getApplicationContext(), "No internet
                    // connection available\n" + error.getMessage(),
                    // Toast.LENGTH_LONG).show();
                }
            }

        };
    }

    private Response.Listener<JSONObject> loginListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    Tapplication.Pref().edit().putString(getString(R.string.pref_login_data),response.toString()).apply();

                    Toast.makeText(getApplicationContext(),
                            response.toString(),
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.v("mango",e.getMessage());
                }
            }
        };
    }
}
