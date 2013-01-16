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
public class SQSController {

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
     * @throws InterruptedException 
     */
    public void doCheckMessages() throws InterruptedException {
        while (true) {
            ArrayList<Message> messageList = sqsUtil.getMessagefromAnotherEntrypoint("test2013");
            if (messageList != null) {
                Thread dloaderThread = new Thread(new DownloadOP(messageList));
                dloaderThread.run();
                dloaderThread.wait(100000);

            } else {
                System.out.println("NO NEW MESSAGES");
            }
        }

    }
}
