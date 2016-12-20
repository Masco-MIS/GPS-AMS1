package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.approval.pojo.Operation;
import masco.mis.software.mascoapproval.auxiliary.Data;
import masco.mis.software.mascoapproval.auxiliary.Database;
import masco.mis.software.mascoapproval.auxiliary.StoredProcedure;
import masco.mis.software.mascoapproval.auxiliary.Values;
import masco.mis.software.mascoapproval.pojo.Employee;
import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

/**
 * Created by TahmidH_MIS on 12/6/2016.
 */

public class OperationAdapter extends ArrayAdapter<Operation> {
    private final Activity context;
    private final List<Operation> values;
    boolean checkAll_flag = false;
    boolean checkItem_flag = false;

    AutoCompleteAdapter adapter;

    public OperationAdapter(Activity context, List<Operation> values) {
        super(context, R.layout.operation_row_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //     View rowView = inflater.inflate(R.layout.operation_row_item, parent, false);
        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.operation_row_item, null);

            viewHolder = new ViewHolder();


            viewHolder.btnCheck = (CheckBox) convertView.findViewById(R.id.im_operation_row_item_check);
            viewHolder.btnCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int getPosition = (Integer) compoundButton.getTag();
                    values.get(getPosition).setAtt4(compoundButton.isChecked());
                }
            });
            viewHolder.t1 = (TextView) convertView.findViewById(R.id.im_operation_row_item_t1);
            viewHolder.t2 = (TextView) convertView.findViewById(R.id.im_operation_row_item_t2);
            viewHolder.t3 = (TextView) convertView.findViewById(R.id.im_operation_row_item_t3);
            viewHolder.t1.setText(values.get(position).att1);
            viewHolder.t2.setText(values.get(position).att2);
            viewHolder.t3.setText(values.get(position).att3);
            viewHolder.imForward = (ImageButton) convertView.findViewById(R.id.im_operation_row_item_forward);

            viewHolder.imForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListView mListView = (ListView) view.getParent().getParent();
                    final int position = mListView.getPositionForView((View) view.getParent());
                    final String AdditionalID = values.get(position).getAtt2();
                    final Operation tempOp = values.get(position);
                    final Employee tempEmp = new Employee();
                    //    final AutoCompleteTextView auto = (AutoCompleteTextView)findViewById(R.id.auto_dialog_search);
                    Toast.makeText(Tapplication.getContext(), "Test :" + values.get(position).getAtt2(), Toast.LENGTH_SHORT).show();
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_forward_to);
                    dialog.setTitle("Forwarding.....");
                    final AutoCompleteTextView auto = (AutoCompleteTextView) dialog.findViewById(R.id.auto_dialog_search);
//                    auto.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable editable) {
//                            try {
//                                JSONObject json = new JSONObject();
//                                TRequest tRequest = new TRequest();
//                                tRequest.setSp("usp_M_get_emp_for_auto");
//                                tRequest.setDb("SCM");
//                                List<TParam> tParamList = new ArrayList<TParam>();
//                                tParamList.add(new TParam("@key", "Tahmid"));
//                                tRequest.setDict(tParamList);
//                                Gson gson = new Gson();
//                                json = new JSONObject(gson.toJson(tRequest, TRequest.class));
//                                Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, "http://192.168.2.72/TWebApiSearch/api/v1/TService/GetData", json, new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        try {
//                                            Gson Res = new Gson();
//                                            List<Employee> lstData = new ArrayList<Employee>();
//
//                                            JSONArray data = response.getJSONArray("data");
//                                            //    Toast.makeText(Tapplication.getContext(), data.toString(), Toast.LENGTH_SHORT).show();
//                                            if (data.length() > 0) {
//                                                for (int i = 0; i < data.length(); i++) {
//                                                    JSONObject j = data.getJSONObject(i);
//                                                    Employee employee = new Employee();
//                                                    employee.setEmpNo(j.getString("EmpNo"));
//                                                    employee.setEmpName((j.getString("EmpName")));
//                                                    employee.setEmpDept(j.getString("EmpDept"));
//                                                    employee.setEmpSection(j.getString("EmpSection"));
//                                                    employee.setEmpDesignation(j.getString("EmpDesignation"));
//
//
//                                                    adp.add(employee);
//                                                }
//                                                adp.notifyDataSetChanged();
//
//
//                                            } else {
//
//                                            }
//                                        } catch (Exception e) {
//                                            Toast.makeText(Tapplication.getContext(), "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                }, genericErrorListener()));
//
//                            } catch (Exception e) {
//
//                            }
//                        }
//                    });
//                    auto.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Toast.makeText(Tapplication.getContext(), "testing", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                   // adp = new EmpAutoAdapter(context, new ArrayList<Employee>());
                    auto.setThreshold(1);
                    adapter = new AutoCompleteAdapter(context, R.layout.auto_emp_row_item, (ArrayList<Employee>) Data.getEmployees());
                    auto.setAdapter(adapter);
                    auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Employee model = (Employee) view.getTag();
                            tempEmp.setEmpNo(model.getEmpNo());
                            tempEmp.setEmpID(model.getEmpID());


                        }
                    });


                    ((Button) dialog.findViewById(R.id.btn_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    ((Button) dialog.findViewById(R.id.btn_dialog_selector)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            try {
                                Toast.makeText(context, AdditionalID + " will be forwared to " + tempEmp.getEmpNo(), Toast.LENGTH_LONG).show();
                                JSONObject json = new JSONObject();
                                TRequest tRequest = new TRequest();
                                tRequest.setSp(StoredProcedure.forward_approval);
                                tRequest.setDb(Database.SCM);
                                List<TParam> tParamList = new ArrayList<TParam>();
                                tParamList.add(new TParam("@SenderId", Data.getUserID()));
                                tParamList.add(new TParam("@ReceiverId", tempEmp.getEmpID()));
                                tParamList.add(new TParam("@ApprovalNo", tempOp.getAtt1()));
                                tParamList.add(new TParam("@ApprovalId", tempOp.getApprovalId()));

                                tRequest.setDict(tParamList);
                                Gson gson = new Gson();
                                json = new JSONObject(gson.toJson(tRequest, TRequest.class));
                                Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, Values.ApiSetData, json, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(context, "Done with " + response.toString(), Toast.LENGTH_SHORT).show();
                                        remove(tempOp);
                                        notifyDataSetChanged();
                                    }
                                }, genericErrorListener()));
                                //        tempOp.getAtt1();
                                //dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(context, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    dialog.show();


                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.im_operation_row_item_t1, viewHolder.t1);
            convertView.setTag(R.id.im_operation_row_item_t2, viewHolder.t2);
            convertView.setTag(R.id.im_operation_row_item_t3, viewHolder.t3);
            convertView.setTag(R.id.im_operation_row_item_check, viewHolder.btnCheck);
            convertView.setTag(R.id.im_operation_row_item_forward, viewHolder.imForward);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.btnCheck.setTag(position); // This line is important.

        viewHolder.t1.setText(values.get(position).getAtt1());
        viewHolder.t2.setText(values.get(position).getAtt2());
        viewHolder.t3.setText(values.get(position).getAtt3());

        viewHolder.btnCheck.setChecked(values.get(position).isAtt4());


        //return rowView;
        return convertView;
    }

    static class ViewHolder {
        protected TextView t1;
        protected TextView t2;
        protected TextView t3;
        protected CheckBox btnCheck;
        protected ImageButton imForward;
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

//    private Response.Listener<JSONObject> loginListener() {
//        return new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//
//                    Gson Res = new Gson();
//                    List<Employee> lstData = new ArrayList<Employee>();
//                    JSONArray data = response.getJSONArray("data");
//                    Toast.makeText(Tapplication.getContext(), data.toString(), Toast.LENGTH_SHORT).show();
//                    if (data.length() > 0) {
//                        for (int i = 0; i < data.length(); i++) {
//                            JSONObject j = data.getJSONObject(i);
//                            Employee employee = new Employee();
//                            employee.setEmpNo(j.getString("EmpNo"));
//                            employee.setEmpName((j.getString("EmpName")));
//                            employee.setEmpDept(j.getString("EmpDept"));
//                            employee.setEmpSection(j.getString("EmpSection"));
//                            employee.setEmpDesignation(j.getString("EmpDesignation"));
//
//                            lstData.add(employee);
//                        }
////                        adapter = new OperationAdapter(OperationActivity.this, lstData);
////                        //    lstView.setAdapter(new OperationAdapter(OperationActivity.this, lstData));
////                        lstView.setAdapter(adapter);
////                        lstView.setOnItemClickListener(OperationActivity.this);
//
//
//                    } else {
//
//                    }
//
//                } catch (Exception e) {
//                    Log.v("mango", e.getMessage());
//                }
//            }
//        };
//    }

}
