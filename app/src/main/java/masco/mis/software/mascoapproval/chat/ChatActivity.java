package masco.mis.software.mascoapproval.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import masco.mis.software.mascoapproval.ChatWeigetArrayAdapter;
import masco.mis.software.mascoapproval.HomeActivity;
import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.pojo.ChatWeiget;

public class ChatActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        setContentView(R.layout.activity_chat);

        populateChatList();

        ListView chatlist = (ListView) findViewById(R.id.lv_home_chatlist);
        chatlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.v("arman",String.valueOf(l));


                //ListView mListView = (ListView) view.getParent().getParent();
                ChatWeiget value = (ChatWeiget)adapterView.getItemAtPosition(i);
                //Toast.makeText(Tapplication.getContext(), "Test :" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Tapplication.getContext(), ChatOperationActivity.class);
                intent.putExtra("to_emp_code",value.emp_code);
                startActivity(intent);

                //Log.v("arman",String.valueOf(value.emp_code));
            }
        });


    }

    private void populateChatList() {
        // Construct the data source
        ArrayList<ChatWeiget> arrayOfChatWeiget = ChatWeiget.getChatWeigetList();

        // Create the adapter to convert the array to views
        int layoutId = R.layout.layout_home_chatweiget;
        ChatWeigetArrayAdapter adapter = new ChatWeigetArrayAdapter(this, layoutId ,arrayOfChatWeiget);

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
