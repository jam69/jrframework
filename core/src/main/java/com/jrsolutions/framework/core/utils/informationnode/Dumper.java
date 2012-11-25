/*
 * Dumper.java
 *
 * Created on 10 de enero de 2008, 10:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.informationnode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author UF768023
 */
public class Dumper {
    
    interface IDumper {
        public String dump(Object obj);
    }
    
    static Map<Class<?>,IDumper> dumpers=new HashMap<Class<?>,IDumper> ();
    
    static {
        dumpers.put(InformationNode.class,new IDumper(){
            public String dump(Object obj){
                boolean first=true;
                InformationNode n=(InformationNode)obj;
                StringBuffer sb=new StringBuffer();
                sb.append(n.getType());
                sb.append("(");
                Iterator<String> it=n.getPropNames().iterator();
                while(it.hasNext()){
                    if(first)first=false;
                    else sb.append(" ");
                    String k=(String)it.next();
                    sb.append(k);
                    sb.append("=");
                    sb.append("\"");
                    sb.append(Dumper.dump(n.getProp(k)));
                    sb.append("\"");
                }
                sb.append(")");
                return sb.toString();
            }
        });
        
        dumpers.put(InformationArray.class,new IDumper(){
            public String dump(Object obj){
                InformationArray nn=(InformationArray)obj;
                StringBuffer sb=new StringBuffer();
                Iterator<?> it=nn.getItems().iterator();
                while(it.hasNext()){
                    Object item=it.next();
                    sb.append(Dumper.dump(item));
                }
                sb.append(")");
                return sb.toString();
            }
        });
        
    }
    
    public static String dump(Object obj){
        if(obj==null)return null;
        Class<?> c=obj.getClass();
        IDumper d=(IDumper)dumpers.get(c);
        if(d==null){
            return obj.toString();
        }else{
            return d.dump(obj);
        }
    }
    
    
}
