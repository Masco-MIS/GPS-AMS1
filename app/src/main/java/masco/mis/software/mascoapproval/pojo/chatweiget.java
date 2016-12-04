package masco.mis.software.mascoapproval.pojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ARMAN on 03-Dec-16.
 */

public class ChatWeiget {
    public String txtTop;
    public String txtMiddle;
    public String txtBottom;
    public String txtTopRight;

    public ChatWeiget() {
    }

    public ChatWeiget(String txtTop, String txtMiddle, String txtBottom, String txtTopRight) {
        this.txtTop = txtTop;
        this.txtMiddle = txtMiddle;
        this.txtBottom=txtBottom;
        this.txtTopRight=txtTopRight;

    }


    //todo refactor below method to get actual data from api
    public static ArrayList<ChatWeiget> getChatWeigetList() {
        ArrayList<ChatWeiget> chatWeiget = new ArrayList<ChatWeiget>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm a");
        String formattedDate = dateFormat.format(new Date()).toString();

        chatWeiget.add(new ChatWeiget("Md. Arman Hossain", "Executive,MIS","1 unread message",formattedDate));
        chatWeiget.add(new ChatWeiget("b1", "b2","b3",formattedDate));
        chatWeiget.add(new ChatWeiget("c1", "c2","c3",formattedDate));
        return chatWeiget;
    }
}
