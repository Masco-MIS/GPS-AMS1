package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
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
import masco.mis.software.mascoapproval.auxiliary.Values;
import masco.mis.software.mascoapproval.pojo.Employee;
import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

public class TestActivity extends Activity {

    private AutoCompleteTextView actvColor;
    ArrayList<Employee> employeeArrayList = new ArrayList<>();
    AutoCompleteAdapter adapter;
   // ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        actvColor = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actvColor.setThreshold(1);
    //    pDialog =  Tapplication.pleaseWait(this,"Fetching");
     //   pDialog.show();
        try {
            JSONObject json = new JSONObject();
            TRequest tRequest = new TRequest();
            tRequest.setSp("usp_M_get_emp_for_auto");
            tRequest.setDb("SCM");
            List<TParam> tParamList = new ArrayList<TParam>();
            //     tParamList.add(new TParam("@key", "Tahmid"));
            tRequest.setDict(tParamList);
            Gson gson = new Gson();
            json = new JSONObject(gson.toJson(tRequest, TRequest.class));
            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, Values.ApiGetData, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        Toast.makeText(Tapplication.getContext(), "Done", Toast.LENGTH_SHORT).show();
                        Gson Res = new Gson();
                        List<Employee> lstData = new ArrayList<Employee>();
                        JSONArray data = response.getJSONArray("data");
                        if (data.length() > 0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject j = data.getJSONObject(i);
                                Employee employee = new Employee();
                                employee.setEmpID(j.getString("EmpID"));
                                employee.setEmpNo(j.getString("EmpNo"));
                                employee.setEmpName((j.getString("EmpName")));
                                employee.setEmpDept(j.getString("EmpDept"));
                                employee.setEmpSection(j.getString("EmpSection"));
                                employee.setEmpDesignation(j.getString("EmpDesignation"));
                                employeeArrayList.add(employee);
                                adapter = new AutoCompleteAdapter(TestActivity.this, R.layout.auto_emp_row_item, employeeArrayList);
                                actvColor.setAdapter(adapter);

                            }


                        } else {

                        }
                    } catch (Exception e) {
                        Toast.makeText(Tapplication.getContext(), "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, genericErrorListener()));

        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }





    }

    private Response.ErrorListener genericErrorListener() {
        return new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {

                try {
                    Toast.makeText(Tapplication.getContext(), "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Tapplication.getContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        };
    }


}
