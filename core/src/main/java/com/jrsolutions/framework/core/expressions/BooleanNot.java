
package com.jrsolutions.framework.core.expressions;


/**
 * Expresion que evalua la negacion de una expresion
 */
class BooleanNot implements Expression{
    private final Expression op1;
    
    public BooleanNot(Expression op1){
        this.op1=op1;
    }
    public Object eval(Object ctx) {
        Boolean o1=(Boolean)op1.eval(ctx);
        if(o1==null)return Boolean.FALSE;
        return Boolean.valueOf(!o1.booleanValue());
    }
        
}
