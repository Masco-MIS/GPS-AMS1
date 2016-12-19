package masco.mis.software.mascoapproval;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import masco.mis.software.mascoapproval.pojo.ChatWeiget;

/**
 * Created by ARMAN on 03-Dec-16.
 */

public class ChatWeigetArrayAdapter extends ArrayAdapter<ChatWeiget> {

    private Context _context;
    private int _layoutId;
    List<ChatWeiget> _items;

    public ChatWeigetArrayAdapter(Context context, int layoutId, List<ChatWeiget> items) {
        super(context, layoutId, items);
        _context = context;
        _layoutId = layoutId;
        _items=items;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Get the data item for this position
        ChatWeiget chatWeigetItem = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag

        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) _context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(_layoutId, parent, false);
            //another way of doing this
            //view = LayoutInflater.from(getContext()).inflate(_layoutId, parent, false);

            viewHolder.txtTop = (TextView) view.findViewById(R.id.txt_chatweiget_top);
            viewHolder.txtMiddle = (TextView) view.findViewById(R.id.txt_chatweiget_middle);
            viewHolder.txtBottom = (TextView) view.findViewById(R.id.txt_chatweiget_bottom);
            viewHolder.txtTopRight = (TextView) view.findViewById(R.id.txt_chatweiget_topRight);
            viewHolder.imgEmp = (ImageView) view.findViewById(R.id.img_home_employee);



            viewHolder.imgEmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ListView mListView = (ListView) view.getParent().getParent();
                    final int position = mListView.getPositionForView((View) view.getParent());
                    Toast.makeText(_context, "Test :" + _items.get(position).emp_code, Toast.LENGTH_SHORT).show();

                    /*final Dialog dialog = new Dialog(_context);
                    dialog.setContentView(R.layout.dialog_forward_to);
                    dialog.setTitle("EMP Info.....");
                    ((Button) dialog.findViewById(R.id.btn_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();*/

                    //Log.v("arman",String.valueOf(position));


                }
            });

            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    Log.v("arman",String.valueOf(l));
                }
            });*/



            // Cache the viewHolder object inside the fresh view
            view.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.txtTop.setText(chatWeigetItem.txtTop);
        viewHolder.txtMiddle.setText(chatWeigetItem.txtMiddle);
        viewHolder.txtBottom.setText(chatWeigetItem.txtBottom);
        viewHolder.txtTopRight.setText(chatWeigetItem.txtTopRight);

        return view;
    }

    private static class ViewHolder {
        protected TextView txtTop;
        protected TextView txtMiddle;
        protected TextView txtBottom;
        protected TextView txtTopRight;
        protected ImageView imgEmp;

    }
}
