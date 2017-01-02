package masco.mis.software.mascoapproval.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.auxiliary.Data;
import masco.mis.software.mascoapproval.auxiliary.Database;
import masco.mis.software.mascoapproval.chat.pojo.ChatWeiget;
import masco.mis.software.mascoapproval.pojo.TParam;
import masco.mis.software.mascoapproval.pojo.TRequest;

import static masco.mis.software.mascoapproval.auxiliary.Values.ApiGetData;

public class ChatActivity extends Activity {
    ProgressDialog pDialog;
    JSONObject json = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        setContentView(R.layout.activity_chat);

        GetChatEmpListFromApi();

        ListView chatlist = (ListView) findViewById(R.id.lv_home_chatlist);
        chatlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.v("arman",String.valueOf(l));


                //ListView mListView = (ListView) view.getParent().getParent();
                ChatWeiget value = (ChatWeiget) adapterView.getItemAtPosition(i);
                //Toast.makeText(Tapplication.getContext(), "Test :" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Tapplication.getContext(), ChatOperationActivity.class);
                intent.putExtra("from_emp_code", value.emp_Code);
                startActivity(intent);

                //Log.v("arman",String.valueOf(value.emp_Code));
            }
        });


    }

    private void GetChatEmpListFromApi() {
        try {
            pDialog = Tapplication.pleaseWait(ChatActivity.this, "Loading Chat List...");
            pDialog.show();
            TRequest tRequest = new TRequest();
            tRequest.setSp("usp_m_getchatemplist");
            tRequest.setDb(Database.SCM);
            List<TParam> tParamList = new ArrayList<TParam>();
//            tParamList.add(new TParam("@MessageID", "0"));
//            tParamList.add(new TParam("@EmpFrom", emp_id_other));
            tParamList.add(new TParam("@EmpTo", Data.getUserID()));
            tRequest.setDict(tParamList);
            Gson gson = new Gson();
            json = new JSONObject(gson.toJson(tRequest, TRequest.class));
            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(
                    Request.Method.POST, ApiGetData, json, GetChatEmpListOnSuccessListDataBind(), saveMessageError()));

        } catch (Exception ex) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            Toast.makeText(getApplicationContext(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private Response.Listener<JSONObject> GetChatEmpListOnSuccessListDataBind() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    ArrayList<ChatWeiget> chatWeigetList = new ArrayList<ChatWeiget>();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm a");
                    String formattedDate = dateFormat.format(new Date()).toString();
                    Log.v("arman", "network call for chat emp list");
                    //Gson Res = new Gson();
                    JSONArray data = response.getJSONArray("data");
                    if (data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject j = data.getJSONObject(i);
                            ChatWeiget chatWeiget = new ChatWeiget(
                                    j.getString("EMP_IMGURL"),
                                    j.getString("EMP_ID"),
                                    j.getString("EMP_ENAME"),
                                    j.getString("EMP_DESIG"),
                                    j.getString("EMP_MSG"),
                                    formattedDate);
                            chatWeigetList.add(chatWeiget);
                        }

                        // Create the adapter to convert the array to views
                        int layoutId = R.layout.layout_home_chatweiget;
                        ChatWeigetArrayAdapter adapter = new ChatWeigetArrayAdapter(Tapplication.getContext(), layoutId, chatWeigetList);

                        // Attach the adapter to a ListView
                        ListView listView = (ListView) findViewById(R.id.lv_home_chatlist);
                        listView.setAdapter(adapter);

                    } else {

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

    private void populateChatList() {
        // Construct the data source
        ArrayList<ChatWeiget> arrayOfChatWeiget = ChatWeiget.getChatWeigetList();

        // Create the adapter to convert the array to views
        int layoutId = R.layout.layout_home_chatweiget;
        ChatWeigetArrayAdapter adapter = new ChatWeigetArrayAdapter(this, layoutId, arrayOfChatWeiget);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lv_home_chatlist);
        listView.setAdapter(adapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Log.v("arman",String.valueOf(l));
            }
        });*/

    }
}
