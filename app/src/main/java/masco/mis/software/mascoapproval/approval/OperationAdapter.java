package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.Toasts;
import masco.mis.software.mascoapproval.approval.pojo.Operation;

/**
 * Created by TahmidH_MIS on 12/6/2016.
 */

public class OperationAdapter extends ArrayAdapter<Operation> {
    private final Activity context;
    private final List<Operation> values;
    boolean checkAll_flag = false;
    boolean checkItem_flag = false;

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
                    Toast.makeText(Tapplication.getContext(), "Test :" + values.get(position).getAtt2(), Toast.LENGTH_SHORT).show();
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

}
