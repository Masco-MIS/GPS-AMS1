package masco.mis.software.mascoapproval.chat.pojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ARMAN on 03-Dec-16.
 */

public class ChatWeiget {
    public String emp_ImgUrl;
    public String emp_Code;
    public String emp_Name;
    public String emp_Designation;
    public String chat_MessageStatus;
    public String chat_MessageTime;

    public ChatWeiget() {
    }
    public String getemp_ImgUrl() {
        return emp_ImgUrl;
    }

    public void setemp_ImgUrl(String emp_ImgUrl) {
        this.emp_ImgUrl = emp_ImgUrl;
    }

    public ChatWeiget(String emp_ImgUrl, String emp_Code, String emp_Name, String emp_Designation,
                      String chat_MessageStatus, String chat_MessageTime) {
        this.emp_ImgUrl = emp_ImgUrl;
        this.emp_Code = emp_Code;
        this.emp_Name = emp_Name;
        this.emp_Designation = emp_Designation;
        this.chat_MessageStatus = chat_MessageStatus;
        this.chat_MessageTime =chat_MessageTime;

    }


    //demo list item get method
    public static ArrayList<ChatWeiget> getChatWeigetList() {
        ArrayList<ChatWeiget> chatWeiget = new ArrayList<ChatWeiget>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm a");
        String formattedDate = dateFormat.format(new Date()).toString();

        chatWeiget.add(new ChatWeiget("http://mis.mascoknit.com:421/Images/EmpImage/13044",
                "20772","Md. Arman Hossain", "Executive,MIS","1 unread message",formattedDate));
        return chatWeiget;
    }
}
