/**
 * 
 */
package de.fhb.dloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;

/**
 * This Class handles the connection to simple DB.
 * @author MaccaPC
 *
 */
public class DloaderDB {
    /** The aws simple db instance*/
    private AmazonSimpleDBClient sDB = null;
    /** This constant determines the name of the item chart in the simple DB storage*/
    private final String DLOADERLIST = "dloaderlist";
    
    /**
     * Constructor
     * @param aSDB the simple db client
     */
    public DloaderDB(AmazonSimpleDBClient aSDB){
        this.sDB = aSDB;
    }
    /**
     * creates a new sDB Domain.
     * @param aDomainName the name of the domain
     */
    public void doCreateDomain(String aDomainName){
        sDB.createDomain(new CreateDomainRequest(aDomainName));
        sDB.putAttributes(new PutAttributesRequest(aDomainName, DLOADERLIST, null));

    }
    /**
     * Adds a Link to a specified simple DB
     * @param aDomainName 
     * @param aLinkKey 
     */
    public void addLinksToChart(String aDomainName, String aLinkKey){
        List<ReplaceableAttribute> attrLst = new ArrayList<ReplaceableAttribute>();
        attrLst.add(new ReplaceableAttribute(aLinkKey, aLinkKey, false));
        sDB.putAttributes(new PutAttributesRequest(aDomainName, DLOADERLIST, attrLst));
    }

    /**
     * Returns the Dloader links.
     * @param aDomainName 
     * @return a list containing all Items
     */
    public List<Item> getDloaderLinks(String aDomainName){
        String sql = "select " + aDomainName + " from `" + DLOADERLIST + "`";;
        SelectResult selectResult = sDB.select(new SelectRequest(sql));
        return selectResult.getItems();
    }
    
    
    
}
