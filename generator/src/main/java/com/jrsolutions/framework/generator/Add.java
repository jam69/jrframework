package com.jrsolutions.framework.generator;

import java.util.ArrayList;
import java.util.Collection;

public 	class Add  {
	public Object create(){
		return new ArrayList();
	}
	public Object add(Object col,Object obj){
		Collection c=(Collection)col;
		c.add(obj);
		return obj;
	}
	public String toString(){
		return "CUtil";
	}
}
