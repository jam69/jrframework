/*
 * ParserModificadores.java
 *
 * Created on 10 de enero de 2008, 13:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.tabledefinition;

import com.jrsolutions.framework.core.utils.informationnode.InformationNode;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author UF768023
 */
public class ParserModificadores {
   
    
    private  Scanner s;
    public ParserModificadores(){       
      //  s.setDebug(true);
    }
    
    public ArrayList parse(String str) {
    	if(str==null)return null;
        try {
        	 s=new Scanner(str);
            Token t=s.next(); //(
            ArrayList lista=new ArrayList();
            while(t.getTipo()==Token.LPAR){
                Object[] res=new Object[3];
                res[0]=readObject();
                res[1]=readObject();
                res[2]=readObject();
                lista.add(res);
                t=s.next(); // ')'
                t=s.next(); // '('
            }           
            return lista;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }
    
    public InformationNode parseOne(String str){
    	 s=new Scanner(str);
    	return readObject();
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
                //BeanExecutor.setProperty(obj,tAtrib.getTxt(),tval.getTxt());
                ent.setProp(tAtrib.getTxt(),tval.getTxt());
                tAtrib=s.next();
            }
            return ent;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}