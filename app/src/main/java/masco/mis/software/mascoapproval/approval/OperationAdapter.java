package masco.mis.software.mascoapproval.approval;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.approval.pojo.Operation;

/**
 * Created by TahmidH_MIS on 12/6/2016.
 */

public class OperationAdapter extends ArrayAdapter<Operation> {
    private final Context context;
    private final Operation[] values;

    public OperationAdapter(Context context, Operation[] values) {
        super(context, R.layout.operation_row_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.operation_row_item, parent, false);
        ImageButton btnForward = (ImageButton) rowView.findViewById(R.id.im_operation_row_item_forward);
        CheckBox btnCheck = (CheckBox) rowView.findViewById(R.id.im_operation_row_item_check);
        TextView t1 = (TextView) rowView.findViewById(R.id.im_operation_row_item_t1);
        TextView t2 = (TextView) rowView.findViewById(R.id.im_operation_row_item_t2);
        TextView t3 = (TextView) rowView.findViewById(R.id.im_operation_row_item_t3);
        t1.setText(values[position].att1);
        t2.setText(values[position].att2);
        t3.setText(values[position].att3);
        return rowView;
    }

}
