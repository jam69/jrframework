/*
 * CParser.java
 *
 * Created on 8 de enero de 2008, 18:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.constrainedpanel;

import java.util.ArrayList;
import java.util.Iterator;

import es.indra.humandev.runner.core.utils.StringToObject2;

/**
 *
 * @author UF768023
 */
public class ConstraintsParser {
    
    /** Creates a new instance of CParser */
    public ConstraintsParser() {
    }
    
    /** TEST PanelConstraintsDefinition */
      public final static void main(String args[]){
          
        String str="[withTitle=\"true\" expandable=\"true\" scrollable=\"false\" minSizeSet=\"false\" minSizePreferred=\"true\" minSizeFixed=\"false\" minSizeFixedHeight=\"0\" normalSizePreferred=\"false\" normalSizeFixed=\"true\" normalSizeFixedHeight=\"300\" normalSizeRelative=\"false\" normalSizeRelativeHeight=\"50.0\" maxSizeSet=\"false\" maxSizePreferred=\"true\" maxSizeFixed=\"false\" maxSizeFixedHeight=\"0\"][withTitle=\"true\" expandable=\"true\" scrollable=\"false\" minSizeSet=\"false\" minSizePreferred=\"true\" minSizeFixed=\"false\" minSizeFixedHeight=\"0\" normalSizePreferred=\"false\" normalSizeFixed=\"false\" normalSizeFixedHeight=\"0\" normalSizeRelative=\"true\" normalSizeRelativeHeight=\"1.0\" maxSizeSet=\"false\" maxSizePreferred=\"true\" maxSizeFixed=\"false\" maxSizeFixedHeight=\"0\"]";
         
        ParserConClase pcc=new ParserConClase(str,ConstraintsDefinition.class);
        ArrayList<ConstraintsDefinition> a=(ArrayList<ConstraintsDefinition>)pcc.parse();
        System.out.println("orig="+str);
        System.out.println("a   ="+a);
        System.out.println("pcc ="+ConstraintsParser.dump(a));
        
         
    }
      
      /**
       * 
       * @param str ArrayList&gt;ConstraintsDefinitions&lt;
       * @return
       */
      public ArrayList<ConstraintsDefinition> parse(String str){
    	  ParserConClase pcc=new ParserConClase(str,ConstraintsDefinition.class);
          ArrayList<ConstraintsDefinition> a=(ArrayList<ConstraintsDefinition>)pcc.parse();
          return a;
      }
      
      public static String dump(Object obj){
          ArrayList<ConstraintsDefinition> lista=(ArrayList<ConstraintsDefinition>)obj;
          StringBuffer sb=new StringBuffer();
          Iterator<ConstraintsDefinition> it=lista.iterator();
          while(it.hasNext()){
              sb.append("[");
              Object obj2=it.next();
              sb.append(StringToObject2.toString(obj2));
              sb.append("]");
          }
          return sb.toString();
      }
}
    
        /*
         
         *En el fichero xml: (Todo en la misma linea)
         def="[withTitle=&quot;true&quot; expandable=&quot;true&quot; scrollable=&quot;false&quot; minSizeSet=&quot;false&quot; minSizePreferred=&quot;true&quot; minSizeFixed=&quot;false&quot; minSizeFixedHeight=&quot;0&quot; normalSizePreferred=&quot;false&quot; normalSizeFixed=&quot;true&quot; normalSizeFixedHeight=&quot;300&quot; normalSizeRelative=&quot;false&quot; normalSizeRelativeHeight=&quot;50.0&quot; maxSizeSet=&quot;false&quot; maxSizePreferred=&quot;true&quot; maxSizeFixed=&quot;false&quot; maxSizeFixedHeight=&quot;0&quot;][withTitle=&quot;true&quot; expandable=&quot;true&quot; scrollable=&quot;false&quot; minSizeSet=&quot;false&quot; minSizePreferred=&quot;true&quot; minSizeFixed=&quot;false&quot; minSizeFixedHeight=&quot;0&quot; normalSizePreferred=&quot;false&quot; normalSizeFixed=&quot;false&quot; normalSizeFixedHeight=&quot;0&quot; normalSizeRelative=&quot;true&quot; normalSizeRelativeHeight=&quot;1.0&quot; maxSizeSet=&quot;false&quot; maxSizePreferred=&quot;true&quot; maxSizeFixed=&quot;false&quot; maxSizeFixedHeight=&quot;0&quot;]" />
         
         En el String que llega:
         [withTitle="true" expandable="true" scrollable="false" minSizeSet="false" minSizePreferred="true" minSizeFixed="false" minSizeFixedHeight="0" normalSizePreferred="false" normalSizeFixed="true" normalSizeFixedHeight="300" normalSizeRelative="false" normalSizeRelativeHeight="50.0" maxSizeSet="false" maxSizePreferred="true" maxSizeFixed="false" maxSizeFixedHeight="0"][withTitle="true" expandable="true" scrollable="false" minSizeSet="false" minSizePreferred="true" minSizeFixed="false" minSizeFixedHeight="0" normalSizePreferred="false" normalSizeFixed="false" normalSizeFixedHeight="0" normalSizeRelative="true" normalSizeRelativeHeight="1.0" maxSizeSet="false" maxSizePreferred="true" maxSizeFixed="false" maxSizeFixedHeight="0"]" />
         
         Son TOKEN:
            [ ]  = ID "valor"
         
         */
    

    
  
    