
package com.jrsolutions.framework.core.expressions;


/**
 * Expresion que evalua una constante
 */
class Constant implements Expression {
    private final String str;
    
    public Constant(String str){
        this.str=str;
    }
    
    public Object eval(Object obj){
        return str;
    }
}
