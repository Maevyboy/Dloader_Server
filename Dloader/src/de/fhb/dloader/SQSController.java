/**
 * 
 */
package de.fhb.dloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;

/**
 * @author MaccaPC
 *
 */
public class SQSController extends Observable implements Runnable {

    /** The sqs utility instance*/
    private SqsUtil sqsUtil;
    /** The aws sqs Client instance*/
    private AmazonSQSClient asqsCli;

    /**
     * Default constructor
     * @throws IOException 
     */
    public SQSController() throws IOException {
        asqsCli = AwsSQSCredentials.getIns("AwsCredentials.properties").initSqsCredentials();
        sqsUtil = new SqsUtil(asqsCli);
        
    }

    /**
     * check if new messages are receiveable
     */
    private void doCheckMessages() {
            ArrayList<Message> messageList = sqsUtil.getMessagefromAnotherEntrypoint("test2013");
            if (messageList != null) {
                // notify Observers
                notifyObservers(messageList);
                
            }else{
                System.out.println("NO NEW MESSAGES");
            }
        
    }

    @Override
    public void run() {
        doCheckMessages();
        
    }
}
