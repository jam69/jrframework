package com.jrsolutions.framework.core.expressions;

public class Numeric implements Expression {

    private final Number number;
    
    public Numeric(Number number){
    	this.number=number;
    }
	
	@Override
	public Object eval(Object ctx) {
		return number;
	}

}
