/**
 * 
 */
package de.fhb.dloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author MaccaPC
 *
 */
public class Downloader {

    /**
     * 
     */
    public Downloader(){
        
    }
    /**
     * 
     * @param aFileUrl
     * @throws IOException 
     */
    public void doDownloadFile(String aFileUrl) throws IOException{
        URL fileUrl = new URL(aFileUrl);
        fileUrl.openConnection();
        InputStream dloaderReader = fileUrl.openStream();
        
        
        FileOutputStream dloaderWriter = new FileOutputStream("");
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
    
    
    
    
}
