
package com.jrsolutions.framework.core.expressions;

import com.jrsolutions.framework.core.context.Context;

/**
 * Expresion que evalua un objeto del contexto
 */
class Variable implements Expression {
    private final String str;
    
    public Variable(String str){
        this.str=str;
        if(str==null){
            throw new IllegalArgumentException("El nombre de una variable no puede ser 'null'");
        }
    }
    
    public Object eval(Object obj){
        Context ctx=(Context) obj;
        return ctx.get(str);
    }
}