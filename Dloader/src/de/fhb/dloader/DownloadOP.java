/**
 * 
 */
package de.fhb.dloader;

import java.io.IOException;
import java.util.ArrayList;

import com.amazonaws.services.sqs.model.Message;

/**
 * @author MaccaPC
 *
 */
public class DownloadOP implements Runnable{
    /**
     * The Arraylist containing the messages
     */
    private ArrayList<Message> messageList = new ArrayList<Message>();
    
    /**
     *  Default constructor
     * @param aMessageList 
     */
    public DownloadOP(ArrayList<Message> aMessageList){
        this.messageList = aMessageList;
    }





    @Override
    public void run() {
        for (Message aMessage : messageList) {
            try {
                new Downloader().doDownloadFile(aMessage.getBody());
            } catch (IOException e) {
                // do nothing
            }
        }
        
    }
}
