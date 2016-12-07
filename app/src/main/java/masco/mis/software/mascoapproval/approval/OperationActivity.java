package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.approval.pojo.Operation;
import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

public class OperationActivity extends Activity {
    ListView lstView;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(OperationActivity.this);
        setContentView(R.layout.activity_operation);


        lstView = (ListView) findViewById(R.id.listview_operation_list);
        try {
//            pDialog = new ProgressDialog(OperationActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
            pDialog = Tapplication.pleaseWait(OperationActivity.this, "Downloading......");
            JSONObject json = new JSONObject();
            TRequest tRequest = new TRequest();
            tRequest.setSp(getString(R.string.get_po));
            tRequest.setDb(getString(R.string.DB_SCM));
            List<TParam> tParamList = new ArrayList<TParam>();
            tRequest.setDict(tParamList);
            Gson gson = new Gson();
            json = new JSONObject(gson.toJson(tRequest, TRequest.class));
            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, "http://192.168.2.72/TWebApiSearch/api/v1/TService/GetData", json, loginListener(), genericErrorListener()));

        } catch (Exception ex) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            Toast.makeText(getApplicationContext(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

    private Response.ErrorListener genericErrorListener() {
        return new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                if (pDialog != null) {
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                }
                try {
                    Toast.makeText(OperationActivity.this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    if (error instanceof NoConnectionError) {


                        //    ((TextView) findViewById(R.id.tv_login_status)).setText("No Connection");

                    } else if (error instanceof NetworkError) {

                        //    ((TextView) findViewById(R.id.tv_login_status)).setText("Network Error");
                    } else if (error instanceof ServerError) {
                        //   ((TextView) findViewById(R.id.tv_login_status)).setText("Server Errpr");
                    } else if (error instanceof TimeoutError) {
                        //   ((TextView) findViewById(R.id.tv_login_status)).setText("Timneout");
                    } else if (error instanceof VolleyError) {
                        try {
                            //       ((TextView) findViewById(R.id.tv_login_status)).setText("Volley Error");
                        } catch (Exception e) {

                        }
                    }

                } catch (Exception e) {
                    //  ((TextView) findViewById(R.id.tv_login_status)).setText("Error");
                    Toast.makeText(OperationActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    List<Operation> lstData = new ArrayList<>();
                    JSONArray data = response.getJSONArray("data");
                    if (data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject j = data.getJSONObject(i);
                            Operation operation = new Operation();
                            operation.setAtt1(j.getString("Att1"));
                            operation.setAtt2(j.getString("Att2"));
                            operation.setAtt3(j.getString("Att3"));
                            lstData.add(operation);
                        }
                        lstView.setAdapter(new OperationAdapter(OperationActivity.this, lstData.toArray(new Operation[lstData.size()])));

                    } else {

                    }

                } catch (Exception e) {
                    Log.v("mango", e.getMessage());
                }
            }
        };
    }
}
