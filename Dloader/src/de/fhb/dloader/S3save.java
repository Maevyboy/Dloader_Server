/**
 * 
 */
package de.fhb.dloader;

/**
 * @author MaccaPC
 *
 */
public class S3save {
    /** */
    AwsS3Credentials s3Cli;
    
    /**
     * Constructor
     */
    public S3save(){
        s3Cli = AwsS3Credentials.getIns("AwsCredentials.properties");
    }
    
    
}
