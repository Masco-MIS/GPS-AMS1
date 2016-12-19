package masco.mis.software.mascoapproval.pojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ARMAN on 03-Dec-16.
 */

public class ChatWeiget {
    public String emp_code;
    public String txtTop;
    public String txtMiddle;
    public String txtBottom;
    public String txtTopRight;
    public String txtdateTime;

    public ChatWeiget() {
    }

    public ChatWeiget(String emp_code,String txtTop, String txtMiddle, String txtBottom, String txtTopRight) {
        this.emp_code = emp_code;
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

        chatWeiget.add(new ChatWeiget("20772","Md. Arman Hossain", "Executive,MIS","1 unread message",formattedDate));
        return chatWeiget;
    }
}
