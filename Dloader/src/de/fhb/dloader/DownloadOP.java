/**
 * 
 */
package de.fhb.dloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.amazonaws.services.sqs.model.Message;

/**
 * @author MaccaPC
 *
 */
public class DownloadOP implements Observer{
    /**
     * The Arraylist containing the messages
     */
    private ArrayList<Message> messageList = new ArrayList<Message>();
    
    /**
     *  Default constructor
     */
    public DownloadOP(){
        
    }


    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable o, Object arg) {
        messageList = (ArrayList<Message>)arg;
        
        for (Message aMessage : messageList) {
            try {
                new Downloader().doDownloadFile(aMessage.getBody());
            } catch (IOException e) {
                // do nothing
            }
        }
        
    }
}
