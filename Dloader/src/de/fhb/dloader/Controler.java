/**
 * 
 */
package de.fhb.dloader;

import java.io.IOException;

/**
 * @author MaccaPC
 *
 */
public class Controler {

    /**
     * @param args
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
       SQSController sqsIns =  new SQSController();
       DownloadOP dloadIns = new DownloadOP();
       
       sqsIns.addObserver(dloadIns);
       
       Thread sqsControllerThread = new Thread(new SQSController());
       sqsControllerThread.start();
       Thread.sleep(10000);


    }

}
