package de.fhb.dloader;

import java.io.IOException;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;


/**
 * This Class manages the AWS credential authorization.
 * @author MaccaPC
 *
 */
public class AwsS3Credentials {

    /**
     * The Instance of the AwsCredentials Class.
     */
    private static AwsS3Credentials aIns = null;
    
    /**
     * Is the String path to the propertyfile where the keypair is stored.
     */
    private String aPth;

    /**
     * Default constructor
     */
    private AwsS3Credentials(String aPthToAuthPrp){  
        aPth = aPthToAuthPrp;
    }

    /**
     * The Session Singleton for the amazone s3 credentials
     * @param aPthToAuthPrp
     * @return the Amazon webeservice credential unique instance
     */
    public static AwsS3Credentials getIns(String aPthToAuthPrp){
        if (aIns == null) {
            aIns = new AwsS3Credentials(aPthToAuthPrp);
        }
        return aIns;
    }
    
    /**
     * Initiates the credential authorization
     * @return the amazon s3 authorization instance
     * @throws IOException 
     */
    public AmazonS3 initCredentials() throws IOException{
        AmazonS3 aAmzIns = new AmazonS3Client(new PropertiesCredentials(AwsS3Credentials.class.getResourceAsStream(aPth))); 
         return aAmzIns;
    }
}
