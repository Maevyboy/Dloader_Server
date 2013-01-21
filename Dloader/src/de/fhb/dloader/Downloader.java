/**
 * 
 */
package de.fhb.dloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ListDomainsResult;

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
     * Downloads the destinated file
     * @param aFileUrl
     * @throws IOException 
     */
    public synchronized void doDownloadFile() throws IOException{
        URL fileUrl = new URL(fileUrlStr);
        fileUrl.openConnection();
        InputStream dloaderReader = fileUrl.openStream();
        
        String filename = extractFilename(fileUrlStr);
        
        FileOutputStream dloaderWriter = new FileOutputStream(filename);
        byte[] dloaderBuffer = new byte[153600];
        int totalBytesRead = 0;
        int bytesRead = 0;
        
        while((bytesRead = dloaderReader.read(dloaderBuffer)) > 0){
            dloaderWriter.write(dloaderBuffer, 0, bytesRead);
            dloaderBuffer = new byte[153600];
            totalBytesRead += bytesRead;
        }
        dloaderWriter.close();
        dloaderReader.close();
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
            
            List<Item> itemLst = aSdb.getDloaderLinks("dloaderdomain");
            
        } catch (IOException e) {
            // do nothing
        }
        
    }
    
    
    
}
