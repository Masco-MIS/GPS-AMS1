package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.auxiliary.Data;
import masco.mis.software.mascoapproval.pojo.Employee;


/**
 * Created by TahmidH_MIS on 12/12/2016.
 */


public class EmpAutoAdapter extends ArrayAdapter<Employee> {
    private final Activity context;
    private final List<Employee> values;


    public EmpAutoAdapter(Activity context, List<Employee> values) {
        super(context, R.layout.auto_emp_row_item, values);
        this.context = context;
        this.values = values;
    }
    public EmpAutoAdapter(Activity context, String name) {
        super(context, R.layout.auto_emp_row_item);
        this.context = context;
        this.values = new ArrayList<Employee>();
    }

    static class ViewHolder {
        protected TextView name;
        protected TextView id;
        protected TextView designation;
        protected TextView dept;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.auto_emp_row_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.txt_auto_emp_row_name);
            viewHolder.id = (TextView) convertView.findViewById(R.id.txt_auto_emp_row_no);
            viewHolder.designation = (TextView) convertView.findViewById(R.id.txt_auto_emp_row_designation);
            viewHolder.dept = (TextView) convertView.findViewById(R.id.txt_auto_emp_row_dept);
            viewHolder.name.setText(values.get(position).getEmpName());
            viewHolder.id.setText(values.get(position).getEmpID());
            viewHolder.designation.setText(values.get(position).getEmpDesignation());
            viewHolder.dept.setText(values.get(position).getEmpDept());

            convertView.setTag(viewHolder);
            convertView.setTag(R.id.txt_auto_emp_row_name, viewHolder.name);
            convertView.setTag(R.id.txt_auto_emp_row_no, viewHolder.id);
            convertView.setTag(R.id.txt_auto_emp_row_designation, viewHolder.designation);
            convertView.setTag(R.id.txt_auto_emp_row_dept, viewHolder.dept);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.name.setText(values.get(position).getEmpName());
        viewHolder.id.setText(values.get(position).getEmpID());
        viewHolder.designation.setText(values.get(position).getEmpDesignation());
        viewHolder.dept.setText(values.get(position).getEmpDept());
        return convertView;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Nullable
    @Override
    public Employee getItem(int position) {
        return values.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults filterResults = new FilterResults();
                filterResults.count = Data.employees.size();
                filterResults.values = Data.employees;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
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
