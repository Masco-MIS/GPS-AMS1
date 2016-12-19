package masco.mis.software.mascoapproval;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

public class LoginActivity extends Activity {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        String lTest = Tapplication.Pref().getString(getString(R.string.pref_login_data), "");
        if (lTest != null || !lTest.isEmpty()) {

        } else {

        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        String priData = Tapplication.Pref().getString(getString(R.string.pref_login_data), "");
        if (priData.length() > 0) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }

        findViewById(R.id.btn_login_login).setOnClickListener(new View.OnClickListener() {
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
                    TRequest tRequest = new TRequest();
                    tRequest.setSp("usp_m_login");
                    tRequest.setDb(getString(R.string.DB_SCM));
                    List<TParam> tParamList = new ArrayList<TParam>();
                    tParamList.add(new TParam("@id", ((EditText) findViewById(R.id.edt_login_user)).getText().toString()));
                    tParamList.add(new TParam("@pass", ((EditText) findViewById(R.id.edi_login_password)).getText().toString()));
                    tRequest.setDict(tParamList);
                    Gson gson = new Gson();
                    json = new JSONObject(gson.toJson(tRequest, TRequest.class));
                    Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Method.POST, "http://mis.mascoknit.com:8095/api/v1/TService/GetData", json, loginListener(), genericErrorListener()));
                    //   Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Method.POST, getString(R.string.api_v1_get_data), json, loginListener(), genericErrorListener()));


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
                try {
                    if (error instanceof NoConnectionError) {

                        ((TextView) findViewById(R.id.tv_login_status)).setText("No Connection");

                    } else if (error instanceof NetworkError) {

                        ((TextView) findViewById(R.id.tv_login_status)).setText("Network Error");
                    } else if (error instanceof ServerError) {
                        ((TextView) findViewById(R.id.tv_login_status)).setText("Server Errpr");
                    } else if (error instanceof TimeoutError) {
                        ((TextView) findViewById(R.id.tv_login_status)).setText("Timneout");
                    } else if (error instanceof VolleyError) {
                        try {
                            ((TextView) findViewById(R.id.tv_login_status)).setText("Volley Error");
                        } catch (Exception e) {

                        }
                    }

                } catch (Exception e) {
                    //  ((TextView) findViewById(R.id.tv_login_status)).setText("Error");
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
                    Gson Res = new Gson();

                    JSONArray data = response.getJSONArray("data");
                    if (data.length() > 0) {
                        Tapplication.Pref().edit().putString(getString(R.string.pref_login_data), response.toString()).apply();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ((TextView) findViewById(R.id.tv_login_status)).setText("Invalid Credentials");
                    }

                } catch (Exception e) {
                    Log.v("mango", e.getMessage());
                    ((TextView) findViewById(R.id.tv_login_status)).setText(e.getMessage());
                }
            }
        };
    }
}
