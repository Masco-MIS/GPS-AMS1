package masco.mis.software.mascoapproval.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.approval.OperationActivity;
import masco.mis.software.mascoapproval.approval.OperationAdapter;
import masco.mis.software.mascoapproval.approval.pojo.Operation;
import masco.mis.software.mascoapproval.auxiliary.Data;
import masco.mis.software.mascoapproval.auxiliary.Database;
import masco.mis.software.mascoapproval.auxiliary.StoredProcedure;
import masco.mis.software.mascoapproval.auxiliary.Util;
import masco.mis.software.mascoapproval.chat.pojo.ChatMessage;
import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

import static masco.mis.software.mascoapproval.auxiliary.Values.ApiGetData;

public class ChatOperationActivity extends Activity {

    private EditText msg_edittext;
    private String from_emp = "", to_emp = "";
    private Random random;
    public static ArrayList<ChatMessage> chatlist;
    public static ChatAdapter chatAdapter;
    ListView msgListView;
    ProgressDialog pDialog;
    JSONObject json = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        setContentView(R.layout.activity_chat_operation);

        from_emp= Data.getUserID();

        Bundle bundle = getIntent().getExtras();
        String to_emp = bundle.getString("to_emp_code");
        Log.v("arman", "chat op ac!"+ " from:"+from_emp+" to"+to_emp);


       // random = new Random();

        msg_edittext = (EditText) findViewById(R.id.messageEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);
        ImageButton sendButton = (ImageButton) findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sendMessageButton:
                        sendTextMessage(view);
                }
            }
        });

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);


        //todo api call to get chatMessage and do the rest on success
        chatlist = DemoChatMessage();//new ArrayList<ChatMessage>();
        try {
            pDialog = Tapplication.pleaseWait(this, "Loading Messages...");
            pDialog.show();
            TRequest tRequest = new TRequest();
            tRequest.setSp("usp_m_getchatmessage");
            tRequest.setDb(Database.SCM);
            List<TParam> tParamList = new ArrayList<TParam>();
            tParamList.add(new TParam("@MessageID", "0"));
            tRequest.setDict(tParamList);
            Gson gson = new Gson();
            json = new JSONObject(gson.toJson(tRequest, TRequest.class));
            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(
                    Request.Method.POST, ApiGetData, json, GetChatMessageOnSuccessListDataBind(), saveMessageError()));

        } catch (Exception ex) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            Toast.makeText(getApplicationContext(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }





        chatAdapter = new ChatAdapter(this, chatlist);
        msgListView.setAdapter(chatAdapter);

        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new Runnable()
        {
            @Override
            public void run()
            {
                Log.v("arman", "call from timer!");
            }
        }, 1, 5, TimeUnit.SECONDS);


    }



    private Response.Listener<JSONObject> GetChatMessageOnSuccessListDataBind() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    Gson Res = new Gson();
                    JSONArray data = response.getJSONArray("data");
                    if (data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject j = data.getJSONObject(i);
                            ChatMessage chatMessage = new ChatMessage("20772","11111",String.valueOf(i),"2",true );
//                            chatMessage.setAtt1(j.getString("Att1"));
//                            chatMessage.setAtt2(j.getString("Att2"));
//                            chatMessage.setAtt3(j.getString("Att3"));
//                            chatMessage.setAtt4(true);
//                            chatMessage.setPROId(j.getString("PROId"));
//                            chatMessage.setApprovalId(j.getString("ApprovalId"));
//                            chatMessage.setAutoDtlId(j.getString("AutoDtlId"));
                            chatlist.add(chatMessage);
                        }

                    } else {

                    }

                } catch (Exception e) {
                    Log.v("arman", e.getMessage());
                }
            }
        };
    }

    /*class LoadTimer extends TimerTask{
        public void run()
        {
            loadNextDataFromApi(5);
        }

    }*/
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`
        Toast.makeText(getApplicationContext(),"api call for next 50",Toast.LENGTH_SHORT).show();
    }
    private ArrayList<ChatMessage> DemoChatMessage()
    {
        ArrayList<ChatMessage> chatMessagesList = new ArrayList<ChatMessage>();

        for (int i=100; i>0; i--){
            chatMessagesList.add(new ChatMessage("20772","11111",String.valueOf(i),"2",true ));
        }


        return  chatMessagesList;
    }

    public void sendTextMessage(View v) {
        String message = msg_edittext.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
            final ChatMessage chatMessage = new ChatMessage(from_emp, to_emp,
                    message, "" + random.nextInt(1000), true);
            chatMessage.setMsgID();
            chatMessage.body = message;
            chatMessage.Date = Util.getCurrentDate();
            chatMessage.Time = Util.getCurrentTime();
            msg_edittext.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();

            saveMessage(chatMessage);
        }
    }

    private void saveMessage(ChatMessage chatMessage) {

        try {
            JSONObject json = new JSONObject();
            TRequest tRequest = new TRequest();
            tRequest.setSp("usp_m_chatMessage_Insert");
            tRequest.setDb(getString(R.string.DB_SCM));
            List<TParam> tParamList = new ArrayList<TParam>();
            tParamList.add(new TParam("@Message", chatMessage.body));
            tParamList.add(new TParam("@mFrom", chatMessage.sender));
            tParamList.add(new TParam("@mTo", chatMessage.receiver));
            tRequest.setDict(tParamList);
            Gson gson = new Gson();

            json = new JSONObject(gson.toJson(tRequest, TRequest.class));

            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST,
                    "http://mis.mascoknit.com:8095/api/v1/TService/SaveData", json, saveMessageSuccess(), saveMessageError()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Response.ErrorListener saveMessageError(){
        return new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(Tapplication.getContext(),"No Connection",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(Tapplication.getContext(),"Network Error",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(Tapplication.getContext(),"Server Error",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(Tapplication.getContext(),"Timeout",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof VolleyError) {
                        Toast.makeText(Tapplication.getContext(),"Volley Error",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.v("arman",e.toString());
                }
            }

        };
    }

    private Response.Listener<JSONObject> saveMessageSuccess() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    /*Gson Res = new Gson();

                    JSONArray data = response.getJSONArray("data");
                    if (data.length() > 0) {
                        Tapplication.Pref().edit().putString(getString(R.string.pref_login_data), response.toString()).apply();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ((TextView) findViewById(R.id.tv_login_status)).setText("Invalid Credentials");
                    }*/

                } catch (Exception e) {
                    Log.v("arman", e.getMessage());
                }
            }
        };
    }


}
