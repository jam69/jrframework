/*
 * ParserTotales.java
 *
 * Created on 10 de enero de 2008, 18:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.tabledefinition;

import com.jrsolutions.framework.core.utils.informationnode.InformationArray;
import com.jrsolutions.framework.core.utils.informationnode.InformationNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author UF768023
 */
public class ParserTotales {
    static Map map=new HashMap();
   
    
    private  Scanner s;
    public ParserTotales(){
       
       // s.setDebug(true);
    }
    
    public Object parse(String str) {
    	 s=new Scanner(str);
        try {
            Token t=s.next(); //(
            Object obj=readObject();
            t=s.next(); // ')'
            return obj;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }
    
    private InformationNode parseArray(String str) {
    	if(str==null)return null;
   	 	s=new Scanner(str);
   	   return readArrayObject();
    }
   /**
     * Lee un objeto de tipo
     * str='name[attr1="v1" attr2="v2" attr2="v3"]'
     * Suponemos que los atributos son propiedades accesibles por introspecci�n
     * sobre la clase indicada.
     *
     * @param name  String que anuncia el objeto
     * @param c  La clase del objeto
     */
    public InformationNode readObject() {
        try {
            Token t=s.next();
            if(t.getTipo()!=Token.STR){
                s.pushBack();
                return null;
            }
            String ctx=t.getTxt();
            InformationNode ent=new InformationNode();
            ent.setType(ctx);
            Token t1=s.next(); // [
            Token tAtrib=s.next();
            while(tAtrib.getTipo()==Token.STR){
                Token t2=s.next(); // =
                Token tval=s.next();
                if(tAtrib.getTxt().equals("columnModelDefinition")){
                    ParserTotales pt=new ParserTotales();
                    InformationNode kk=pt.parseArray(tval.getTxt());
                    //InformationNode kk=readArrayObject();
                    ent.setProp(tAtrib.getTxt(),kk);
                }else{
                    ent.setProp(tAtrib.getTxt(),tval.getTxt());
                }
                tAtrib=s.next();
            }
            return ent;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
     public InformationNode readArrayObject() {
        try {
            Token t=s.next();
            if(t.getTipo()!=Token.LPAR){
                s.pushBack();
                return null;
            }
            t=s.next();
            String ctx=t.getTxt();
            InformationArray ent=new InformationArray();
            ent.setType(ctx);
            Token t1=s.next(); // [
            Token t2=s.next();
            while(t2.getTipo()==Token.LPAR){
                 InformationNode obj=readObject();
                ent.addItem(obj);
                t2=s.next(); // ")"
                t2=s.next(); // '('    
            }
            s.pushBack();
            return ent;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

