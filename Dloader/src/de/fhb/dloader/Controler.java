/**
 * 
 */
package de.fhb.dloader;

import java.io.IOException;

/**
 * This is the entrypoint of the Dloader Server implementation
 * 
 * 
 * @author MaccaPC
 * @author Tony Hoffmann
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
