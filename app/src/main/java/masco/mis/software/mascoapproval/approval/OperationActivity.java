package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
import masco.mis.software.mascoapproval.auxiliary.Data;
import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

import static masco.mis.software.mascoapproval.auxiliary.Values.ApiGetData;

public class OperationActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView lstView;
    ArrayAdapter<Operation> adapter;
    List<Operation> list = new ArrayList<Operation>();
    JSONObject json = new JSONObject();
    ProgressDialog pDialog;
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
        TextView t1 = (TextView) v.getTag(R.id.im_operation_row_item_t1);
        TextView t2 = (TextView) v.getTag(R.id.im_operation_row_item_t2);
        TextView t3 = (TextView) v.getTag(R.id.im_operation_row_item_t3);
        ImageButton imForward = (ImageButton) v.getTag(R.id.im_operation_row_item_forward);
        CheckBox checkbox = (CheckBox) v.getTag(R.id.im_operation_row_item_check);

    }

    private String isCheckedOrNot(CheckBox checkbox) {
        if (checkbox.isChecked())
            return "is checked";
        else
            return "is not checked";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(OperationActivity.this);
        setContentView(R.layout.activity_operation);


        lstView = (ListView) findViewById(R.id.listview_operation_list);
        //lstView.setOnItemClickListener(this);
        try {
//            pDialog = new ProgressDialog(OperationActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();

            pDialog = Tapplication.pleaseWait(OperationActivity.this, "Downloading......");
            pDialog.show();
            TRequest tRequest = new TRequest();
            tRequest.setSp(getString(R.string.get_po));
            tRequest.setDb(getString(R.string.DB_SCM));
            List<TParam> tParamList = new ArrayList<TParam>();
            tParamList.add(new TParam("@id", Data.getUserID()));
            tRequest.setDict(tParamList);
            Gson gson = new Gson();
            json = new JSONObject(gson.toJson(tRequest, TRequest.class));
            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, ApiGetData, json, loginListener(), genericErrorListener()));

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
                    List<Operation> lstData = new ArrayList<Operation>();
                    JSONArray data = response.getJSONArray("data");
                    if (data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject j = data.getJSONObject(i);
                            Operation operation = new Operation();
                            operation.setAtt1(j.getString("Att1"));
                            operation.setAtt2(j.getString("Att2"));
                            operation.setAtt3(j.getString("Att3"));
                            operation.setAtt4(true);
                            lstData.add(operation);
                        }
                        adapter = new OperationAdapter(OperationActivity.this, lstData);
                        //    lstView.setAdapter(new OperationAdapter(OperationActivity.this, lstData));
                        lstView.setAdapter(adapter);
                        lstView.setOnItemClickListener(OperationActivity.this);

                    } else {

                    }

                } catch (Exception e) {
                    Log.v("mango", e.getMessage());
                }
            }
        };
    }
}
