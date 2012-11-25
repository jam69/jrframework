package com.jrsolutions.framework.core.utils.tabledefinition;

import com.jrsolutions.framework.core.utils.BeanExecutor;
import java.util.ArrayList;


public class ParserObject {

	Scanner s;
	Token t;
	public ParserObject(Scanner s){
		this.s=s;		
	}
	
    private Object readObject(Object c) throws Exception{
           while(setProps(c));
           t = s.next();
           Token control = s.next();
           if(control.getTipo() == Token.END)
        	   t=control;
           else
        	   s.pushBack();
           return c;
            
    }
    
    public ArrayList readArray(Class c)throws Exception{
    	t=s.next();
        ArrayList res=new ArrayList();
        if(t.getTipo()!=Token.LPAR) return res;
        Object o=null;
        do{
        	o=c.newInstance();
     	    readObject(o);
     	    res.add(o);
        }while(t.getTipo() != Token.END);
    	return res;
    }
    
    private boolean setProps(Object o) throws Exception{
    		t=s.next();
    		if(t.getTipo()!=Token.STR){
    			return false;
    		}
            Token te=s.next(); // =
            Token tv=s.next();
            BeanExecutor.setProperty(o,t.getTxt(),tv.getTxt());
            return true;
           
    }
    
  
}
