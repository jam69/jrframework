
package com.jrsolutions.framework.core.expressions;

import com.jrsolutions.framework.core.context.Context;
import com.jrsolutions.framework.core.utils.BeanExecutor;


/**
 * Expresion que evalua un objeto del contexto con una propiedad
 */
class VariableProp implements Expression {
    private final String str;
    private final String propName;
    
    public VariableProp(String str,String propName){
        this.str=str;
        if(str==null){
            throw new IllegalArgumentException("El nombre de una variable no puede ser 'null'");
        }
        this.propName=propName;
        if(propName==null){
            throw new IllegalArgumentException("El nombre de la propiedad de una variable no puede ser 'null'");
        }
    }
    
    public Object eval(Object obj){
        Context ctx=(Context) obj;
        Object o=ctx.get(str);
        if(o==null)return null;
        return BeanExecutor.getProperty(o, propName);
    }
}