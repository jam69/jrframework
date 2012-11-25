/*
 * ParserTabla.java
 *
 * Created on 9 de enero de 2008, 18:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.tabledefinition;

import com.jrsolutions.framework.core.utils.BeanExecutor;
import es.indra.humandev.runner.core.utils.StringToObject2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author UF768023
 */
public class ParserTabla {
	
    private Scanner s;
    
    public ParserTabla(){        
    }
    
    public GroupDefinition[] parse(String str){
    	s=new Scanner(str);
        GroupDefinition[] g;
        try {
            g = parseGroup2();
            return g;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } // ]
        return null;
    }
    
    private GroupDefinition[] parseGroup2() throws Exception{
    	GroupDefinition[] res=new GroupDefinition[3];
    	res[0]=parseGroup();
    	s.next(); // ]
    	s.next(); // ,
    	res[1]=parseGroup();
    	s.next(); // ]
    	s.next(); // ,   	
    	res[2]=parseGroup();
    	s.next(); // ]    	
    	return res;
    }
    
    
    
    private GroupDefinition parseGroup()throws Exception {
    	GroupDefinition g=parseGroupDef();
        if(g==null){
            return null;
        }
        Token t=s.next(); // [
        do{
        GroupDefinition g2=parseGroup();
        if(g2==null){
        	ColumnDefinition c =parseColumns(g);
        	if(c!=null)
        		g.addElement(c);
        }else{
            g.addElement(g2);
        }
        }while(s.next().getTipo()==Token.COMA);
  
        return g;
    }
    
    private GroupDefinition parseGroupDef()throws Exception {
        Token t=s.next();
        if(t.getTipo()!=Token.STR || !t.getTxt().equals("Group")){
            s.pushBack();
            return null;
        }
        GroupDefinition cDef=new GroupDefinition();
        Token t2=s.next(); // (
        Token tAtring=s.next();
        while(tAtring.getTipo()==Token.STR){
            Token te=s.next(); // =
            Token tv=s.next();
            BeanExecutor.setProperty(cDef,tAtring.getTxt(),tv.getTxt());
            tAtring=s.next();
        }
        if(tAtring.getTipo()!=Token.RPAR){
            System.err.println("ERR_2");
        }
        return cDef;
    }
    
    private ColumnDefinition parseColumns(GroupDefinition g)throws Exception {
        Token t=s.next();
        if(t.getTipo()!=Token.STR || !t.getTxt().equals("Column")){
            s.pushBack();
            return null;
        	}
        ColumnDefinition cDef=new ColumnDefinition();
        Token t2=s.next(); // (
        Token tAtring=s.next();
        while(tAtring.getTipo()==Token.STR){   
        	Token t2x=s.next(); // =
        	Token tval=s.next();
        	if(tAtring.getTxt().equals("attributeDefinition")){
        		ArrayList kk=ParserAtributos.parse(tval.getTxt());
        		//InformationNode kk=readArrayObject();
        		cDef.setAttributeDefinition(kk);
        	}else{
        		BeanExecutor.setProperty(cDef,tAtring.getTxt(),tval.getTxt());
        	}
        	tAtring=s.next();
        }
        if(tAtring.getTipo()!=Token.RPAR){
        	System.err.println("ERR_3");
        }
    return cDef;
    }
    
    public static String toString(GroupDefinition g){
        StringBuffer sb=new StringBuffer();
        sb.append("\nGroup(");
        sb.append(StringToObject2.toString(g));
        sb.append(")");
        sb.append("\n [");
        boolean first=true;
        Iterator it=g.getAlElements().iterator();
        while(it.hasNext()){
            if(!first)sb.append("\n   ,");
            else first=false;
            Object o=it.next();
            if(o instanceof ColumnDefinition){
                sb.append(StringToObject2.toString(g));
            }else{
                sb.append(toString((GroupDefinition)o));
            }
            
        }
        sb.append("\n ]");
        return sb.toString();
    }
}
