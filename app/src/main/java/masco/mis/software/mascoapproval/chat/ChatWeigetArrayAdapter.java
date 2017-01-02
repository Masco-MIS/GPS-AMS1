package masco.mis.software.mascoapproval.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;
import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.chat.pojo.ChatWeiget;

/**
 * Created by ARMAN on 03-Dec-16.
 */

public class ChatWeigetArrayAdapter extends ArrayAdapter<ChatWeiget> {

    private Context _context;
    private int _layoutId;
    List<ChatWeiget> _items;
    ImageLoader imageLoader = Tapplication.getInstance().getImageLoader();

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

            if (imageLoader == null)
                imageLoader = Tapplication.getInstance().getImageLoader();

            viewHolder.emp_Name = (TextView) view.findViewById(R.id.txt_chatweiget_emp_Name);
            viewHolder.emp_Designation = (TextView) view.findViewById(R.id.txt_chatweiget_middle);
            viewHolder.chat_MessageStatus = (TextView) view.findViewById(R.id.txt_chatweiget_bottom);
            viewHolder.chat_MessageTime = (TextView) view.findViewById(R.id.txt_chatweiget_topRight);
            viewHolder.emp_Image = (NetworkImageView) view.findViewById(R.id.img_home_employee);



            viewHolder.emp_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ListView mListView = (ListView) view.getParent().getParent();
                    final int position = mListView.getPositionForView((View) view.getParent());
                    Toast.makeText(_context, "Test :" + _items.get(position).emp_Code, Toast.LENGTH_SHORT).show();

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

        viewHolder.emp_Name.setText(chatWeigetItem.emp_Name);
        viewHolder.emp_Designation.setText(chatWeigetItem.emp_Designation);
        viewHolder.chat_MessageStatus.setText(chatWeigetItem.chat_MessageStatus);
        viewHolder.chat_MessageTime.setText(chatWeigetItem.chat_MessageTime);
        viewHolder.emp_Image.setImageUrl(chatWeigetItem.emp_ImgUrl,imageLoader);

        return view;
    }

    private static class ViewHolder {
        protected TextView emp_Name;
        protected TextView emp_Designation;
        protected TextView chat_MessageStatus;
        protected TextView chat_MessageTime;
        protected NetworkImageView emp_Image;

    }
}
