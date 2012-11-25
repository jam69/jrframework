
package com.jrsolutions.framework.core.expressions;


/**
 * Expresion que evalua la operacion logica de dos expresiones.
 */
class BooleanBinario implements Expression{
    public static final int AND=0;
    public static final int OR=1;
    public static final int XOR=2;
    
    private final int oper;
    private final Expression op1;
    private final Expression op2;

    public BooleanBinario(int oper,Expression op1,Expression op2){
        this.oper=oper;
        this.op1=op1;
        this.op2=op2;
        if(oper<AND || oper>XOR){
            throw new IllegalArgumentException("No es operador Boolean binario ["+oper+"]");
        }
    }
    public BooleanBinario(String op,Expression op1,Expression op2){
        if(op.equalsIgnoreCase("AND"))oper=AND;
        else if (op.equalsIgnoreCase("OR"))oper=OR;
        else if (op.equalsIgnoreCase("XOR"))oper=XOR;
        else throw new IllegalArgumentException("No es operador Boolean binario ["+op+"]");
        this.op1=op1;
        this.op2=op2;
    }
    
    
    public Object eval(Object ctx) {
        // No optimiza, siempre evalua ambas expresiones.
    	
        Boolean o1;
		try {
			Boolean bol =(Boolean)op1.eval(ctx);
			o1 = bol!=null?bol:false;
		} catch (Exception e) {
			if (op1.eval(ctx)!=null){
				o1=true;
			}else{
				o1=false;
			}
		}
        Boolean o2;
		try {
			Boolean bol =(Boolean)op2.eval(ctx);
			o2 = bol!=null?bol:false;
			
		} catch (Exception e) {
			if (op1.eval(ctx)!=null){
				o2=true;
			}else{
				o2=false;
			}
		}
        if(o1==null || o2==null)return Boolean.FALSE;
        switch(oper){
            case OR:return Boolean.valueOf(o1.booleanValue()|o2.booleanValue());
            case AND:return Boolean.valueOf(o1.booleanValue()&o2.booleanValue());
            case XOR:return Boolean.valueOf(o1.booleanValue()^o2.booleanValue());
            default:throw new IllegalArgumentException("No es operador Boolean binario ["+oper+"]");
        }        
    }
    
}
