package masco.mis.software.mascoapproval;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import masco.mis.software.mascoapproval.approval.ApprovalType;
import masco.mis.software.mascoapproval.pojo.ChatWeiget;

public class HomeActivity extends Activity {

    ImageButton imApproval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        setContentView(R.layout.activity_home);
        imApproval = (ImageButton)findViewById(R.id.btn_home_approval);
        imApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(HomeActivity.this, ApprovalType.class);
                startActivity(intent);
                finish();
            }
        });
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
