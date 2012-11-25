/*
 * InformationNode.java
 *
 * Created on 10 de enero de 2008, 9:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.informationnode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Es un nodo que guarda informacion sobre cosas en general.
 * Tiene forma de �rbol.
 *
 * Es algo parecido a un LDAP, o JNDI pero en 1 clase.
 *
 * La idea es no usar directamente esta clase, sino generalizarla
 * y adaptarla a nuestras necesidades.
 * En esta clase est�n los m�todos de uso general.
 * 
 *
 */
public class InformationNode {
    
    private String type;
    
    private final Map<String,Object> props=new HashMap<String,Object>();
    
    /** Creates a new instance of InformationNode */
    public InformationNode() {
    }
    
    public void setProp(String key,Object value){
        props.put(key,value);
    }
    
    public Object getProp(String key){
        return props.get(key);
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        this.type=type;
    }
    
    public ArrayList<String> getPropNames(){
        ArrayList<String> list=new ArrayList<String>(props.keySet());
        Collections.sort(list);
        return list;
    }
    
}
