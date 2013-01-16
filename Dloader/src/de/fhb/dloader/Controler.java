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
       
        new SQSController().doCheckMessages();
    }

}
