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
     * @param aLinkLst 
     */
    public void addLinksToChart(String aDomainName, HashMap<String , String> aLinkLst){
        List<ReplaceableItem> itemList = getAsAwsDBLst(aLinkLst);
        sDB.batchPutAttributes(new BatchPutAttributesRequest(DLOADERLIST, itemList));
    }
    /**
     * Gets the raw HashMap as a AWS ReplaceItemList.
     * @return the itemList
     */
    private List<ReplaceableItem> getAsAwsDBLst(HashMap<String, String> aLinkLst){
        List<ReplaceableItem> awsLst = new ArrayList<ReplaceableItem>();
        List<ReplaceableAttribute> awsAttrLst = new ArrayList<ReplaceableAttribute>();
        for (String aKey : aLinkLst.keySet()) {
            awsAttrLst.add(new ReplaceableAttribute(aKey, aLinkLst.get(aKey) , false));
        }
        awsLst.add(new ReplaceableItem(DLOADERLIST, awsAttrLst));
        return awsLst;
    }
    /**
     * Returns the Dloader links.
     * @return a list containing all Items
     */
    public List<Item> getDloaderLinks(){
        String sql = "select * from `" + DLOADERLIST + "`";;
        SelectResult selectResult = sDB.select(new SelectRequest(sql));
        return selectResult.getItems();
    }
    
    
    
}
