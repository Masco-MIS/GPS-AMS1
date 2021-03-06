package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.auxiliary.Data;
import masco.mis.software.mascoapproval.auxiliary.StoredProcedure;
import masco.mis.software.mascoapproval.pojo.Employee;
import masco.mis.software.mascoapproval.pojo.TParam;

import static masco.mis.software.mascoapproval.auxiliary.Values.ApiGetData;

public class ApprovalType extends Activity {
    Button btnPr, btnSr, btnLa;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        setContentView(R.layout.activity_approval_type);


//        try {
//            pDialog = Tapplication.pleaseWait(ApprovalType.this, "Downloading......");
//            pDialog.show();
//            TRequest tRequest = new TRequest();
//            tRequest.setSp(StoredProcedure.sp_get_emp_list);
//            tRequest.setDb(getString(R.string.DB_SCM));
//            List<TParam> tParamList = new ArrayList<TParam>();
//            tRequest.setDict(tParamList);
//            Gson gson = new Gson();
//            JSONObject json = new JSONObject(gson.toJson(tRequest, TRequest.class));
//            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, ApiGetData, json, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    if (pDialog.isShowing()) {
//                        pDialog.dismiss();
//                    }
//                    try
//                    {
//                        ArrayList<Employee> lstData = new ArrayList<Employee>();
//                        JSONArray data = response.getJSONArray("data");
//                        if(data.length()>0)
//                        {
//                            for (int i = 0; i < data.length(); i++) {
//                                JSONObject j = data.getJSONObject(i);
//                                Employee employee = new Employee();
//                                employee.setEmpNo(j.getString("EmpNo"));
//                                employee.setEmpID(j.getString("EmpID"));
//                                employee.setEmpName(j.getString("EmpName"));
//                                employee.setEmpDept(j.getString("EmpDept"));
//                                employee.setEmpDesignation(j.getString("EmpDesignation"));
//                                lstData.add(employee);
//                            }
//                        }
//                        Data.setEmployees(lstData);
//                    }
//                    catch (Exception e)
//                    {
//
//                    }
//                }
//            }, Data.genericErrorListener(pDialog, ApprovalType.this)));
//
//        }
//        catch (Exception e)
//        {
//
//        }

        btnPr = (Button) findViewById(R.id.btn_approvaltype_pr);
        btnSr = (Button) findViewById(R.id.btn_approvaltype_sr);
        btnLa = (Button) findViewById(R.id.btn_approvaltype_la);
        final Intent intent = new Intent(this, OperationActivity.class);
        btnPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);

            }
        });
        btnSr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        btnLa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });


        if (Data.getEmployees().size() == 0) {
            List<TParam> params = new ArrayList<>();
            JSONObject json = Tapplication.intiJson(StoredProcedure.sp_get_emp_list, getString(R.string.DB_SCM), params);
            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, ApiGetData, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (progressDialog != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                        Gson Res = new Gson();
                        List<Employee> lstData = new ArrayList<Employee>();
                        JSONArray data = response.getJSONArray("data");
                        //    Toast.makeText(Tapplication.getContext(), data.toString(), Toast.LENGTH_SHORT).show();
                        if (data.length() > 0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject j = data.getJSONObject(i);
                                Employee employee = new Employee();
                                employee.setEmpNo(j.getString("EmpNo"));
                                employee.setEmpName((j.getString("EmpName")));
                                employee.setEmpDept(j.getString("EmpDept"));
                                employee.setEmpSection(j.getString("EmpSection"));
                                employee.setEmpDesignation(j.getString("EmpDesignation"));
                                employee.setEmpImage(j.getString("EmpImage"));
                                lstData.add(employee);
                            }
//                            JSONObject s = new Gson().toJson(lstData,lstData.getClass());
                            //    Tapplication.Pref().edit().putString(Values.pref_emp_List,new Gson().toJson(lstData,lstData.getClass())).apply();
                            Data.setEmployees(lstData);
                        }


                    } catch (Exception e) {
                        Toast.makeText(ApprovalType.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, Data.genericErrorListener(progressDialog, ApprovalType.this)));
        }
        if (Data.nCount.containsKey("MPO"))
        {
            ((TextView)findViewById(R.id.counterValue)).setText(Data.nCount.get("MPO").toString());
        }

//        List<TParam> params = new ArrayList<>();
//        params.add(new TParam("@id",Data.getUserID()));
//        JSONObject json = Tapplication.intiJson(StoredProcedure.count_approval_type, Database.SCM, params);
//        Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, ApiGetData, json, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    if (progressDialog != null) {
//                        if (progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
//                    }
//                    Gson Res = new Gson();
//                    List<Employee> lstData = new ArrayList<Employee>();
//                    JSONArray data = response.getJSONArray("data");
//                    //    Toast.makeText(Tapplication.getContext(), data.toString(), Toast.LENGTH_SHORT).show();
//                    if (data.length() > 0) {
//                        for (int i = 0; i < data.length(); i++) {
//                            JSONObject j = data.getJSONObject(i);
//                            ((TextView)findViewById(R.id.counterValue)).setText(j.getString("COUNT")); //setText(j.getString("COUNT"));
//                        }
////                            JSONObject s = new Gson().toJson(lstData,lstData.getClass());
//                        //    Tapplication.Pref().edit().putString(Values.pref_emp_List,new Gson().toJson(lstData,lstData.getClass())).apply();
//
//                    }
//
//
//                } catch (Exception e) {
//                    Toast.makeText(ApprovalType.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, Data.genericErrorListener(progressDialog, ApprovalType.this)));

    }
}
