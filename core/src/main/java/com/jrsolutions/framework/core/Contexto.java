package com.jrsolutions.framework.core;

import com.jrsolutions.framework.core.context.Context;

public class Contexto {
	
	final private Context ctx;

	public Contexto(Context ctx){
		this.ctx=ctx;
	}
	
	public Object get(String key) {
		return ctx.get(key);
	}
	
	public void put(String key, Object value) {
		ctx.put(key, value);
	}
	
}
