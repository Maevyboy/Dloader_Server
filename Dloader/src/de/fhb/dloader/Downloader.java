/**
 * 
 */
package de.fhb.dloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

/**
 * @author MaccaPC
 *
 */
public class Downloader implements Runnable{

    
    private String fileUrlStr;
    /**
     * Default constructor
     * @param afileUrl the url of the given file
     * @throws IOException 
     */
    public Downloader(String afileUrl) throws IOException{
        this.fileUrlStr = afileUrl;
    }
    
    /**
     * Gets the downloaded file as Inputstream
     * @return the Inputstream
     * @throws IOException 
     * 
     */
    public InputStream getDownloadInputStream () throws IOException{
        URL fileUrl = new URL(fileUrlStr);
        fileUrl.openConnection();
        InputStream dloaderReader = fileUrl.openStream();
        return dloaderReader;
    }

    /**
     * Extracts the filename of the given link.
     * @param aLink
     * @return the extracted file name
     */
    private String extractFilename(String aLink) {
     if (aLink.endsWith("/")) {
      aLink = aLink.substring(0, aLink.length() - 1);
     }

     int beginIndex = aLink.lastIndexOf("/") + 1;

     aLink = aLink.substring(beginIndex);

     return aLink;
    }

    /**
     * This is the run of each download.
     */
    @Override
    public void run() {
        try {
            AmazonS3 s3 = AwsS3Credentials.getIns("AwsCredentials.properties").initCredentials();
            BucketUtil aBck = new BucketUtil(s3);
            aBck.uploadOntoBucket("dloaderbucket", getDownloadInputStream(), extractFilename(fileUrlStr));
            
            AmazonSimpleDBClient aDBCli = AwsSimpleDBCredentials.getIns("AwsCredentials.properties").initCredentials();
            DloaderDB aSdb = new DloaderDB(aDBCli);
            
            aSdb.addLinksToChart("dloaderdomain", extractFilename(fileUrlStr));

        } catch (IOException e) {
            // do nothing
        }
        
    }
    
    
    
}
