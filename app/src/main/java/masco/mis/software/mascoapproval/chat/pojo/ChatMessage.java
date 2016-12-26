package masco.mis.software.mascoapproval.chat.pojo;

import java.util.Random;

/**
 * Created by ARMAN on 14-Dec-16.
 */

public class ChatMessage {

    public String body, sender, receiver, senderName;
    public String Date, Time;
    public String msgid;
    public boolean isMine;// Did I send the message.

    public ChatMessage(String From, String To, String messageString,String ID, boolean isMINE) {
        body = messageString;
        isMine = isMINE;
        sender = From;
        msgid = ID;
        receiver = To;
        senderName = sender;
    }

    public void setMsgID() {

        msgid += "-" + String.format("%02d", new Random().nextInt(100));

    }
}
