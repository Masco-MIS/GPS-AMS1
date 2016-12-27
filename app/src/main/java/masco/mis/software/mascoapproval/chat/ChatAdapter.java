package masco.mis.software.mascoapproval.chat;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.auxiliary.Data;
import masco.mis.software.mascoapproval.auxiliary.Database;
import masco.mis.software.mascoapproval.chat.pojo.ChatMessage;
import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

import static masco.mis.software.mascoapproval.auxiliary.Values.ApiGetData;

/**
 * Created by ARMAN on 14-Dec-16.
 */

public class ChatAdapter extends BaseAdapter {
    JSONObject json = new JSONObject();

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
        ChatMessage message = chatMessageList.get(position);
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
            layout.setBackgroundResource(R.drawable.bubble_blue2);
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

                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    Log.v("arman", "SCROLL_STATE_TOUCH_SCROLL...a");
                }
                if (scrollState == SCROLL_STATE_FLING) {
                    Log.v("arman", "SCROLL_STATE_FLING...a");
                }
                if (scrollState == SCROLL_STATE_IDLE) {
                    Log.v("arman", "SCROLL_STATE_IDLE...a");
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
                if (currentScrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    Log.v("arman", "onScroll... /firstVisibleItem:" + firstVisibleItem +
                            "/visibleItemCount" + visibleItemCount + "/totalItemCount" + totalItemCount);
                }
            }


            private void isScrollCompleted() {
                Log.v("arman", "isScrollCompleted... /firstVisibleItem:" + currentFirstVisibleItem +
                        "/visibleItemCount" + currentVisibleItemCount + "/totalItemCount" + totalItem);
                if (currentFirstVisibleItem == 0
                        && this.currentScrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    /** To do code here*/
                    Log.v("arman", "end of list...");
                    Toast.makeText(Tapplication.getContext(), "end of list...", Toast.LENGTH_LONG);

                    //  chatMessageList.addAll(MoreDemoChatMessage());
                    /*for (ChatMessage item : MoreDemoChatMessage()) {
                        chatMessageList.add(0, item);

                    }*/
                    ChatMessage lastChatMessage = chatMessageList.get(currentFirstVisibleItem);
                    Log.v("armanx", "just before api call. last msgid: "+lastChatMessage.msgid);
                    GetChatMessageFromApi(lastChatMessage.msgid,lastChatMessage.sender);
                    // final int  position = msgListView.getSelectedItemPosition();
                    notifyDataSetChanged();

                    //Use the post method to wait for the list to finish updating after you call notifyDataSetChanged
                    msgListView.post(new Runnable() {
                        @Override
                        public void run() {
                            //call smooth scroll
                            msgListView.smoothScrollToPosition(currentFirstVisibleItem);
                        }
                    });
                }
            }
        });
        return vi;
    }
    private void GetChatMessageFromApi(String MessageID, String emp_id_other){
        try {
            TRequest tRequest = new TRequest();
            tRequest.setSp("usp_m_getchatmessage");
            tRequest.setDb(Database.SCM);
            List<TParam> tParamList = new ArrayList<TParam>();
            tParamList.add(new TParam("@MessageID", "0"));
            tParamList.add(new TParam("@EmpFrom", emp_id_other));
            tParamList.add(new TParam("@EmpTo", Data.getUserID()));
            tRequest.setDict(tParamList);
            Gson gson = new Gson();
            json = new JSONObject(gson.toJson(tRequest, TRequest.class));
            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(
                    Request.Method.POST, ApiGetData, json, GetChatMessageOnSuccessListDataBind(), saveMessageError()));

        } catch (Exception ex) {

            Toast.makeText(Tapplication.getContext(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private  Response.Listener<JSONObject> GetChatMessageOnSuccessListDataBind() {
        //ArrayList<ChatMessage> tempchatMessagesList = new ArrayList<ChatMessage>();
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray data = response.getJSONArray("data");
                    if (data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject j = data.getJSONObject(i);
                            ChatMessage chatMessage = new ChatMessage(
                                    j.getString("MessageEMP_IDFrom"),
                                    j.getString("MessageEMP_IDTo"),
                                    j.getString("MessageText"),
                                    j.getString("MessageID"),
                                    Boolean.parseBoolean(j.getString("isMINE")));
                            chatMessageList.add(chatMessage);
                        }


                    }

                } catch (Exception e) {
                    Log.v("arman", e.getMessage());
                }
            }
        };

    }
    private Response.ErrorListener saveMessageError() {
        return new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(Tapplication.getContext(), "No Connection", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(Tapplication.getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(Tapplication.getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(Tapplication.getContext(), "Timeout", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof VolleyError) {
                        Toast.makeText(Tapplication.getContext(), "Volley Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.v("arman", e.toString());
                }
            }

        };
    }
    private ArrayList<ChatMessage> MoreDemoChatMessage() {
        ArrayList<ChatMessage> chatMessagesList = new ArrayList<ChatMessage>();
        boolean forwhom;
        for (int i = 100; i > 0; i--) {
            if (i % 2 == 0) {
                forwhom = true;
            } else {
                forwhom = false;
            }

            chatMessagesList.add(new ChatMessage("20772", "11111", String.valueOf(i) + " " + System.currentTimeMillis(), "2", forwhom));
        }
        return chatMessagesList;
    }

    public void add(ChatMessage object) {
        chatMessageList.add(object);
    }
}
