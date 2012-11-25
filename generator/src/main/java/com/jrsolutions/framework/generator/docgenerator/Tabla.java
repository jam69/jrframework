package com.jrsolutions.framework.generator.docgenerator;

import java.util.HashMap;
import java.util.Map;

public class Tabla {
	String name;
	String desc;
	String cap;
	Map<String,Field>campos=new HashMap<String,Field>();
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDesc()
	{
		return desc;
	}
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	public String getCap()
	{
		return cap;
	}
	public void setCap(String cap)
	{
		this.cap = cap;
	}
	public Map<String, Field> getCampos()
	{
		return campos;
	}
	public void setCampos(Map<String, Field> campos)
	{
		this.campos = campos;
	}
	
}
