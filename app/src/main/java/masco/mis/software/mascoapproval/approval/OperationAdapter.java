package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    private final ArrayList<Operation> arrayList;
    List<Operation> mOriginalValues;
    boolean checkAll_flag = false;
    boolean checkItem_flag = false;


    AutoCompleteAdapter adapter;

    public OperationAdapter(Activity context, List<Operation> values) {
        super(context, R.layout.operation_row_item, values);
        this.context = context;
        this.values = values;
        this.arrayList = (ArrayList<Operation>) values;
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

            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_operation_row_item_ts);
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_operation_item_details_full);
                    dialog.setTitle("Details");

                    ListView mListView = (ListView) view.getParent().getParent();
                    final int position = mListView.getPositionForView((View) view.getParent());
                    final Operation tempOp = values.get(position);
                    try {
                        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.ll_operationa_row_details_full);
                        TextView t1 = new TextView(context);
                        t1.setText("ApprovalId :" + tempOp.getApprovalId());

                        TextView t2 = new TextView(context);
                        t2.setText("Att1 :" + tempOp.getAtt1());

                        TextView t3 = new TextView(context);
                        t3.setText("Att2 :" + tempOp.getAtt2());

                        TextView t4 = new TextView(context);
                        t4.setText("Att3 :" + tempOp.getAtt3());


                        TextView t5 = new TextView(context);
                        t5.setText("AutoDtlId :" + tempOp.getAutoDtlId());

                        TextView t6 = new TextView(context);
                        t6.setText("PROId :" + tempOp.getPROId());

                        linearLayout.addView(t6);
                        linearLayout.addView(t5);
                        linearLayout.addView(t4);
                        linearLayout.addView(t3);
                        linearLayout.addView(t2);
                        linearLayout.addView(t1);
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dialog.show();

                }
            });
            viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context, "disum disum......", Toast.LENGTH_SHORT).show();
                    ListView mListView = (ListView) view.getParent().getParent();
                    final int position = mListView.getPositionForView((View) view.getParent());
                    final Operation tempOp = values.get(position);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_operation_item_details);
                    dialog.setTitle("Delete");
                    TextView t1 = (TextView) dialog.findViewById(R.id.dia_operation_item_details_t1);
                    TextView t2 = (TextView) dialog.findViewById(R.id.dia_operation_item_details_t2);
                    TextView t3 = (TextView) dialog.findViewById(R.id.dia_operation_item_details_t3);
                    Button yes = (Button) dialog.findViewById(R.id.btn_operation_item_details_yes);
                    Button no = (Button) dialog.findViewById(R.id.btn_operation_item_details_no);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, "Will be deleted!! now", Toast.LENGTH_SHORT).show();
                            //dialog.hide();
                            try {

                                JSONObject json = new JSONObject();
                                TRequest tRequest = new TRequest();
                                tRequest.setSp(StoredProcedure.update_approval_status);
                                tRequest.setDb(Database.SCM);
                                List<TParam> tParamList = new ArrayList<TParam>();
                                tParamList.add(new TParam("@id", tempOp.getAutoDtlId()));
                                tRequest.setDict(tParamList);
                                Gson gson = new Gson();
                                json = new JSONObject(gson.toJson(tRequest, TRequest.class));
                                Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, Values.ApiSetData, json, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //   Toast.makeText(context, "Done with " + response.toString(), Toast.LENGTH_SHORT).show();
                                        remove(tempOp);

                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                }, genericErrorListener()));
                                //        tempOp.getAtt1();
                                //dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(context, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.hide();

                        }
                    });


                    t1.setText(values.get(position).getAtt1());
                    t2.setText(values.get(position).getAtt2());
                    t3.setText(values.get(position).getAtt3());
                    dialog.show();
                    return false;
                }
            });
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
                                        //   Toast.makeText(context, "Done with " + response.toString(), Toast.LENGTH_SHORT).show();
                                        remove(tempOp);

                                        notifyDataSetChanged();
                                        dialog.dismiss();
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
            convertView.setTag(R.id.im_operation_row_item_forward, viewHolder.linearLayout);

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

    //    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        values.clear();
//        if (charText.length() == 0) {
//            values.addAll(arrayList);
//        }
//        else
//        {
//            for (Operation op : arrayList)
//            {
//                if (op.getAtt1().toLowerCase(Locale.getDefault()).contains(charText))
//                {
//                    values.add(op);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
    static class ViewHolder {
        protected TextView t1;
        protected TextView t2;
        protected TextView t3;
        protected CheckBox btnCheck;
        protected ImageButton imForward;
        protected LinearLayout linearLayout;
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
    // Filter Class


    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<Operation> FilteredArrList = new ArrayList<Operation>();
                if (mOriginalValues == null) {
                    try {
                        mOriginalValues = new ArrayList<Operation>();
                        for (Operation op : values) {
                            mOriginalValues.add(op);

                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                if (charSequence.length() == 0) {

                    for (Operation op : mOriginalValues
                            ) {

                        FilteredArrList.add(op);
                    }

                } else {
                    charSequence = charSequence.toString().toLowerCase();

                    for (Operation op : mOriginalValues) {
                        if (op.getAtt1().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            FilteredArrList.add(op);
                        }

                    }

                }
                results.count = FilteredArrList.size();
                results.values = FilteredArrList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                String ddd = "";
//                values.clear();
//                for (Operation ik : (List<Operation>) filterResults.values) {
//                    values.add(ik);
//                }
//                for (Operation op : values
//                        ) {
//                    values.remove(op);
//
//                }
                values.clear();
                try {
                    if (charSequence != "") {
                        List<Operation> it = (List<Operation>) filterResults.values;


                        for (Operation ik : it) {
                            values.add(ik);
                        }

                    } else {
                        for (Operation op : mOriginalValues
                                ) {
                            values.add(op);

                        }
                    }
                    notifyDataSetChanged();

                } catch (Exception e) {
                    Toast.makeText(context, "error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        };
        return filter;
    }
}
