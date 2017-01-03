package masco.mis.software.mascoapproval.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.pojo.Employee;

/**
 * Created by ARMAN on 03-Jan-17.
 */

public class EmployeeAutoCompleteAdapter extends ArrayAdapter<Employee> {

    private ArrayList<Employee> list;
    Context context;
    LayoutInflater inflater;
    int resource;
    ImageLoader imageLoader = Tapplication.getInstance().getImageLoader();

    public EmployeeAutoCompleteAdapter(Context context, int resource, ArrayList<Employee> list) {
        super(context, resource);
        this.context = context;
        this.resource = resource;

        this.list = list;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = inflater.inflate(resource, null);
        }

        if (imageLoader == null) {
            imageLoader = Tapplication.getInstance().getImageLoader();
        }

        Employee model=getItem(position);

        TextView name = (TextView) view.findViewById(R.id.auto_emp_name);
        TextView dept = (TextView) view.findViewById(R.id.auto_emp_dept);
        TextView designation = (TextView) view.findViewById(R.id.auto_emp_designation);
        TextView no = (TextView) view.findViewById(R.id.auto_emp_no);
        NetworkImageView emp_img = (NetworkImageView) view.findViewById(R.id.auto_emp_img);

        name.setText(model.getEmpName());
        dept.setText(model.getEmpDept());
        designation.setText(model.getEmpDesignation());
        no.setText(model.getEmpNo());
        emp_img.setImageUrl(model.getEmpImage(),imageLoader);
        view.setTag(model);
        return view;
    }


    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Employee) (resultValue)).getEmpName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                ArrayList<Employee> suggestions = new ArrayList<>();

                for (Employee employee : list) {
                    if (employee.getEmpName().toLowerCase().contains(constraint.toString().toLowerCase())
                            ||employee.getEmpNo().contains(constraint.toString().toLowerCase()) ) {
                        suggestions.add(employee);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Employee>) results.values);
            }
            notifyDataSetChanged();
        }
    };
}
