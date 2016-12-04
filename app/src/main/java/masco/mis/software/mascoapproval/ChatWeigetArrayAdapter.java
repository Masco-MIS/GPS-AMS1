package masco.mis.software.mascoapproval;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import masco.mis.software.mascoapproval.pojo.ChatWeiget;

/**
 * Created by ARMAN on 03-Dec-16.
 */

public class ChatWeigetArrayAdapter extends ArrayAdapter<ChatWeiget> {

    private Context _context;
    int _layoutId;

    public ChatWeigetArrayAdapter(Context context,
                            int layoutId,
                            List<ChatWeiget> items) {
        super(context, layoutId, items);
        _context = context;
        _layoutId = layoutId;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ChatWeiget chatWeigetItem = getItem(position);


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)_context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(_layoutId, parent,false);
        }

        TextView chatweiget_top = (TextView)view.findViewById(R.id.txt_chatweiget_top);
        TextView chatweiget_middle = (TextView)view.findViewById(R.id.txt_chatweiget_middle);
        TextView chatweiget_bottom = (TextView)view.findViewById(R.id.txt_chatweiget_bottom);
        TextView chatweiget_topRight = (TextView)view.findViewById(R.id.txt_chatweiget_topRight);


        chatweiget_top.setText(chatWeigetItem.txtTop);
        chatweiget_middle.setText(chatWeigetItem.txtMiddle);
        chatweiget_bottom.setText(chatWeigetItem.txtBottom);
        chatweiget_topRight.setText(chatWeigetItem.txtTopRight);

        return view;
    }
}
