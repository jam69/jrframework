/*
 * InformationArray.java
 *
 * Created on 10 de enero de 2008, 9:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.informationnode;

import java.util.ArrayList;

/**
 *
 * @author UF768023
 */
public class InformationArray extends InformationNode {
    
    public final static String ARRAY_TYPE="Array";
    public final static String ARRAY_ITEMS="items";
    
    /** Creates a new instance of InformationArray */
    public InformationArray() {
        super();
        setType(ARRAY_TYPE);
        setProp(ARRAY_ITEMS,new ArrayList());
    }
    
    public ArrayList getItems(){
        return (ArrayList)getProp(ARRAY_ITEMS);
    }
    
    public void setItems(ArrayList nuevosItems){
        setProp(ARRAY_ITEMS,nuevosItems);
    }
    
    public void addItem(InformationNode n){
        ((ArrayList)getProp(ARRAY_ITEMS)).add(n);
    }
    
    
}
