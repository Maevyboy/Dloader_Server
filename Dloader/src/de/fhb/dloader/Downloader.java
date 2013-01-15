/**
 * 
 */
package de.fhb.dloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author MaccaPC
 *
 */
public class Downloader {

    /**
     * Default constructor
     */
    public Downloader(){
        
    }
    
    /**
     * Downloads the destinated file
     * @param aFileUrl
     * @throws IOException 
     */
    public void doDownloadFile(String aFileUrl) throws IOException{
        URL fileUrl = new URL(aFileUrl);
        fileUrl.openConnection();
        InputStream dloaderReader = fileUrl.openStream();
        
        String filename = extractFilename(aFileUrl);
        
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
     * Extracts the filename of the given link.
     * @param aLink
     * @return
     */
    private String extractFilename(String aLink) {
     if (aLink.endsWith("/")) {
      aLink = aLink.substring(0, aLink.length() - 1);
     }

     int beginIndex = aLink.lastIndexOf("/") + 1;

     aLink = aLink.substring(beginIndex);

     return aLink;
    }
    
    
    
}
