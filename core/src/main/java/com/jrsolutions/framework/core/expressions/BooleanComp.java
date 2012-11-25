
package com.jrsolutions.framework.core.expressions;


/**
 *  Expresion que evalua la comparacion de dos valores
 */
class BooleanComp implements Expression{
	
    public static final int EQ=0;
    public static final int NE=1;
    public static final int LT=2;
    public static final int LE=3;
    public static final int GT=4;
    public static final int GE=5;
    
    private final int oper;
    private final Expression op1;
    private final Expression op2;

    public BooleanComp(int oper,Expression op1,Expression op2){
        this.oper=oper;
        this.op1=op1;
        this.op2=op2;
        if(oper<EQ || oper>GE){
            throw new IllegalArgumentException("No es operador de relacion ["+oper+"]");
        }
    }
    
    public BooleanComp(String op,Expression op1,Expression op2){
        if(op.equals("=="))oper=EQ;
        else if (op.equals(">="))oper=GE;
        else if (op.equals("<="))oper=LE;
        else if (op.equals(">"))oper=GT;
        else if (op.equals("<"))oper=LT;
        else if (op.equals("!="))oper=NE;
        else throw new IllegalArgumentException("No es operador de relacion ["+op+"]");
        this.op1=op1;
        this.op2=op2;
    }
    
    public Object eval(Object ctx) {
        Comparable o1=(Comparable)op1.eval(ctx);
        Comparable o2=(Comparable)op2.eval(ctx);
        if(o1==null || o2==null)return Boolean.FALSE;
        if (o2 instanceof Number || o1 instanceof Number){
        	o1 = Double.valueOf(o1.toString());
        	o2 = Double.valueOf(o2.toString());
        }
        int r=o1.compareTo(o2);
        switch(oper){
            case EQ:return Boolean.valueOf(r==0);
            case NE:return Boolean.valueOf(r!=0);
            case LT:return Boolean.valueOf(r<0);
            case LE:return Boolean.valueOf(r<=0);
            case GT:return Boolean.valueOf(r>0);
            case GE:return Boolean.valueOf(r>=0);
            default:throw new IllegalArgumentException("No es operador de relacion ["+oper+"]");
        }        
    }
    
}
