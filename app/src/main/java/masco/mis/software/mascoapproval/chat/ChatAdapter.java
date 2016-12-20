package masco.mis.software.mascoapproval.chat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.chat.pojo.ChatMessage;

import static android.R.id.list;
import static masco.mis.software.mascoapproval.auxiliary.Util.getCurrentDate;
import static masco.mis.software.mascoapproval.auxiliary.Util.getCurrentTime;

/**
 * Created by ARMAN on 14-Dec-16.
 */

public class ChatAdapter extends BaseAdapter{


    private static LayoutInflater inflater = null;
    ArrayList<ChatMessage> chatMessageList;

    public ChatAdapter(Activity activity, ArrayList<ChatMessage> list) {
        chatMessageList = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage message = (ChatMessage) chatMessageList.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.chatbubble, null);

        TextView msg = (TextView) vi.findViewById(R.id.message_text);
        msg.setText(message.body);
        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);

        // if message is mine then align to right
        if (message.isMine) {
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
        }
        // If not mine then align to left
        else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
        }
        msg.setTextColor(Color.BLACK);




        //scroll listener

        final ListView msgListView = (ListView) parent.findViewById(R.id.msgListView);
        msgListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;

                if (scrollState==SCROLL_STATE_TOUCH_SCROLL)
                {
                    Log.v("arman", "SCROLL_STATE_TOUCH_SCROLL...a");
                }
                if (scrollState==SCROLL_STATE_FLING)
                {
                    Log.v("arman", "SCROLL_STATE_FLING...a");
                }
                if (scrollState==SCROLL_STATE_IDLE)
                {
                    Log.v("arman", "SCROLL_STATE_FLING...a");
                }
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
                if (currentScrollState==SCROLL_STATE_TOUCH_SCROLL)
                {
                    Log.v("arman", "onScroll... /firstVisibleItem:"+firstVisibleItem +
                            "/visibleItemCount"+visibleItemCount+ "/totalItemCount"+totalItemCount);
                }
            }

            private void isScrollCompleted() {
                Log.v("arman", "isScrollCompleted... /firstVisibleItem:"+currentFirstVisibleItem +
                        "/visibleItemCount"+currentVisibleItemCount+ "/totalItemCount"+totalItem);
                if (currentFirstVisibleItem == 0
                        && this.currentScrollState == SCROLL_STATE_IDLE) {
                    /** To do code here*/
                    Log.v("arman","load more from api");

                    chatMessageList.addAll(MoreDemoChatMessage());
                    for (ChatMessage item : MoreDemoChatMessage()) {
                        chatMessageList.add(0,item);

                    }
                    msgListView.smoothScrollToPosition(0);



                    notifyDataSetChanged();

                }
            }
        });
        return vi;
    }

    private ArrayList<ChatMessage> MoreDemoChatMessage()
    {
        ArrayList<ChatMessage> chatMessagesList = new ArrayList<ChatMessage>();
        for (int i=100; i>0; i--){
            chatMessagesList.add(new ChatMessage("20772","11111",String.valueOf(i)+ " " + System.currentTimeMillis() ,"2",true ));
        }
        return  chatMessagesList;
    }

    public void add(ChatMessage object) {
        chatMessageList.add(object);
    }
}
