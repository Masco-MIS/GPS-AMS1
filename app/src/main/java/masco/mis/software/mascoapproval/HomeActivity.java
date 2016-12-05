package masco.mis.software.mascoapproval;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import masco.mis.software.mascoapproval.pojo.ChatWeiget;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        setContentView(R.layout.activity_home);
        populateChatList();

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
    }


}
