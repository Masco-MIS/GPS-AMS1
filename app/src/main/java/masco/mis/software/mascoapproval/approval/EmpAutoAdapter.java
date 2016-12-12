package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.approval.pojo.Operation;
import masco.mis.software.mascoapproval.pojo.*;

import android.app.Activity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
            viewHolder.id = (TextView) convertView.findViewById(R.id.txt_auto_emp_row_id);
            viewHolder.designation = (TextView) convertView.findViewById(R.id.txt_auto_emp_row_designation);
            viewHolder.dept = (TextView) convertView.findViewById(R.id.txt_auto_emp_row_dept);
            viewHolder.name.setText(values.get(position).getEmpName());
            viewHolder.id.setText(values.get(position).getEmpID());
            viewHolder.designation.setText(values.get(position).getEmpDesignation());
            viewHolder.dept.setText(values.get(position).getEmpDept());

            convertView.setTag(viewHolder);
            convertView.setTag(R.id.txt_auto_emp_row_name, viewHolder.name);
            convertView.setTag(R.id.txt_auto_emp_row_id, viewHolder.id);
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


}
