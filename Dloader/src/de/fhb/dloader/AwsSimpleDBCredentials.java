/**
 * 
 */
package de.fhb.dloader;

import java.io.IOException;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

/**
 * @author MaccaPC
 *
 */
public class AwsSimpleDBCredentials {

    /** This instance placeholder*/
    private static AwsSimpleDBCredentials aIns;
    /**  The Path to credentials file*/
    private String pth = null;
    
    /**
     * Constructor
     * @param aPth the path to Credentials file.
     */
    private AwsSimpleDBCredentials(String aPth){
        this.pth = aPth;
    }
    /**
     * Returns the singleton of this instance.
     * @param aPthToAuthPrp
     * @return this instance
     */
    public static AwsSimpleDBCredentials getIns(String aPthToAuthPrp){
        if (aIns == null) {
            aIns = new AwsSimpleDBCredentials(aPthToAuthPrp);
        }
        return aIns;
    }
    /**
     * Initiates the simple DB client credentials.
     * @return the simple db client instance
     * @throws IOException
     */
    public AmazonSimpleDBClient initCredentials() throws IOException{
     AmazonSimpleDBClient aAwsSDBClient = new AmazonSimpleDBClient(new PropertiesCredentials(AwsSQSCredentials.class.getResourceAsStream(pth)));
     return aAwsSDBClient;
    }   
}
