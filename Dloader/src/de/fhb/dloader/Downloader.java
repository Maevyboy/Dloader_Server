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
     * Extracts the filename of the given link.
     * @param aLink
     * @return the extracted file name
     */
    private synchronized String extractFilename(String aLink) {
     if (aLink.endsWith("/")) {
      aLink = aLink.substring(0, aLink.length() - 1);
     }

     int beginIndex = aLink.lastIndexOf("/") + 1;

     aLink = aLink.substring(beginIndex);

     return aLink;
    }

    @Override
    public void run() {
        try {
            doDownloadFile();
        } catch (IOException e) {
            // do nothing
        }
        
    }
    
    
    
}
