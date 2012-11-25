
package com.jrsolutions.framework.core.model.tools;

/** Implementa un parametro constante. */
public class ParamCte extends Param{
    
        public static final Object NULL=new Object();
        
    	public Object obj;
        
        public String toString(){
            return obj==null?"NULL":obj.toString();
        }
    }
