/**
 * 
 */
package de.fhb.dloader;

import java.io.IOException;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;

/**
 * @author MaccaPC
 *
 */
public class AwsSQSCredentials {

    /** This is the AWS Sqs credentials instance */
    private static AwsSQSCredentials aIns = null;
    
    /** The path to credentials file*/
    private String pth;
    
    private AwsSQSCredentials(String aPth){
        this.pth = aPth;
    }
    /**
     * Get the singleton of this instance.
     * @param aPthToAuthPrp the path to credentials
     * @return the instance
     */
    public static AwsSQSCredentials getIns(String aPthToAuthPrp){
        if (aIns == null) {
            aIns = new AwsSQSCredentials(aPthToAuthPrp);
        }
        return aIns;
    }
    /**
     * initiates and returns the sqs client instance
     * @return the sqs client instance
     * @throws IOException
     */
    public AmazonSQSClient initSqsCredentials() throws IOException{
        AmazonSQSClient aSqsIns = new AmazonSQSClient(new PropertiesCredentials(AwsSQSCredentials.class.getResourceAsStream(pth)));
        return aSqsIns;
    }
}

