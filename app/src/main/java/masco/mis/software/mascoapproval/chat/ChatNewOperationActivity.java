package masco.mis.software.mascoapproval.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.auxiliary.Data;
import masco.mis.software.mascoapproval.auxiliary.StoredProcedure;
import masco.mis.software.mascoapproval.auxiliary.Util;
import masco.mis.software.mascoapproval.chat.pojo.ChatMessage;
import masco.mis.software.mascoapproval.pojo.Employee;
import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

import static masco.mis.software.mascoapproval.auxiliary.Values.ApiGetData;

public class ChatNewOperationActivity extends Activity {
    private EditText msg_edittext;
    private String emp_id_other, emp_id_mine;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        setContentView(R.layout.activity_chat_new_operation);

        emp_id_mine = Data.getUserID();
        //todo get id from auto complete box
        //emp_id_other = "12885";
        msg_edittext = (EditText) findViewById(R.id.edt_chat_new_operation_text);

        FloatingActionButton sendButton = (FloatingActionButton) findViewById(R.id.fab_chat_new_operation_sendchatmessage);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fab_chat_new_operation_sendchatmessage:
                        sendTextMessage(view);
                        Toast.makeText(getApplicationContext(), "sending...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final ArrayList<Employee> employeeArrayList = (ArrayList<Employee>) Data.getEmployees();

        if (employeeArrayList.isEmpty()) {
            try {
                pDialog = Tapplication.pleaseWait(ChatNewOperationActivity.this, "Downloading......");
                pDialog.show();
                TRequest tRequest = new TRequest();
                tRequest.setSp(StoredProcedure.sp_get_emp_list);
                tRequest.setDb(getString(R.string.DB_SCM));
                List<TParam> tParamList = new ArrayList<TParam>();
                tRequest.setDict(tParamList);
                Gson gson = new Gson();
                JSONObject json = new JSONObject(gson.toJson(tRequest, TRequest.class));
                Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, ApiGetData, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (pDialog.isShowing()) {
                            pDialog.dismiss();
                        }
                        try {
                            // ArrayList<Employee> lstData = new ArrayList<Employee>();
                            JSONArray data = response.getJSONArray("data");
                            if (data.length() > 0) {
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject j = data.getJSONObject(i);
                                    Employee employee = new Employee();
                                    employee.setEmpNo(j.getString("EmpNo"));
                                    employee.setEmpID(j.getString("EmpID"));
                                    employee.setEmpName(j.getString("EmpName"));
                                    employee.setEmpDept(j.getString("EmpDept"));
                                    employee.setEmpDesignation(j.getString("EmpDesignation"));
                                    employee.setEmpImage(j.getString("EmpImage"));
                                    employeeArrayList.add(employee);
                                }
                            }
                            Data.setEmployees(employeeArrayList);
                        } catch (Exception e) {

                        }
                    }
                }, Data.genericErrorListener(pDialog, ChatNewOperationActivity.this)));

            } catch (Exception e) {

            }
        }


        final AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.edt_chat_new_operation_to_emp);
        //final Employee tempEmp = new Employee();

        auto.setThreshold(1);
        EmployeeAutoCompleteAdapter adapter = new EmployeeAutoCompleteAdapter(ChatNewOperationActivity.this, R.layout.layout_emp_list,
                (ArrayList<Employee>) Data.getEmployees());
        auto.setAdapter(adapter);
        auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee model = (Employee) view.getTag();
                //tempEmp.setEmpNo(model.getEmpNo());
                //tempEmp.setEmpID(model.getEmpID());
                emp_id_other = model.getEmpID();

                //Log.v("arman", "NO:" + model.getEmpNo() + " ID:" + model.getEmpID());


            }
        });
    }

    public void sendTextMessage(View v) {
        String message = msg_edittext.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
            final ChatMessage chatMessage = new ChatMessage(emp_id_mine, emp_id_other,
                    message, "" + 0, true);
            chatMessage.setMsgID();
            chatMessage.body = message;
            chatMessage.Date = Util.getCurrentDate();
            chatMessage.Time = Util.getCurrentTime();
            msg_edittext.setText("");


            saveMessage(chatMessage);
        }
    }

    private void saveMessage(ChatMessage chatMessage) {

        try {
            JSONObject json = new JSONObject();
            TRequest tRequest = new TRequest();
            tRequest.setSp("usp_m_chatMessage_Insert");
            tRequest.setDb(getString(R.string.DB_SCM));
            List<TParam> tParamList = new ArrayList<TParam>();
            tParamList.add(new TParam("@Message", chatMessage.body));
            tParamList.add(new TParam("@mFrom", chatMessage.sender));
            tParamList.add(new TParam("@mTo", chatMessage.receiver));
            tRequest.setDict(tParamList);
            Gson gson = new Gson();

            json = new JSONObject(gson.toJson(tRequest, TRequest.class));

            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST,
                    "http://mis.mascoknit.com:8095/api/v1/TService/SaveData", json, saveMessageSuccess(), saveMessageError()));
        } catch (JSONException e) {
            e.printStackTrace();
            //Log.v("arman", e.getMessage());
        }
    }

    private Response.ErrorListener saveMessageError() {
        return new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(Tapplication.getContext(), "No Connection", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(Tapplication.getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(Tapplication.getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(Tapplication.getContext(), "Timeout", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof VolleyError) {
                        Toast.makeText(Tapplication.getContext(), "Volley Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    //Log.v("arman", e.toString());
                }
            }

        };
    }

    private Response.Listener<JSONObject> saveMessageSuccess() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Intent intent = new Intent(Tapplication.getContext(), ChatActivity.class);
                    ChatNewOperationActivity.this.finish();
                    startActivity(intent);


                    /*Gson Res = new Gson();

                    JSONArray data = response.getJSONArray("data");
                    if (data.length() > 0) {
                        Tapplication.Pref().edit().putString(getString(R.string.pref_login_data), response.toString()).apply();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ((TextView) findViewById(R.id.tv_login_status)).setText("Invalid Credentials");
                    }*/

                } catch (Exception e) {
                    //Log.v("arman", e.getMessage());
                }
            }
        };
    }
}
