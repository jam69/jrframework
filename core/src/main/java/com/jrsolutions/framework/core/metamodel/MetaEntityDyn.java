package com.jrsolutions.framework.core.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementacion Dinamica de MetaEntity
 * 
 * @see Entity
 *
 */
public class MetaEntityDyn implements MetaEntity{

	private String displayName;
	private String name;
	
	private Map<String,MetaAttribute>map=new HashMap<String,MetaAttribute>();
	private ArrayList<MetaAttribute> lista=new ArrayList<MetaAttribute>();
	public MetaEntityDyn(){
		
	}
	
	public MetaEntityDyn(String name){
		this.name=name;
	}
	public void add(MetaAttribute attribute){
		lista.add(attribute);
		map.put(attribute.getName(),attribute);
	}
	
	public MetaAttribute getAttribute(String name) {
		return map.get(name);
	}

	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String dName){
		this.displayName=dName;
	}

	public String getName() {
		return name;
	}
	public void setName(String s){
		this.name=s;
	}

	public List<MetaAttribute> getAttributes() {
		return lista;
	}

	public MetaAttribute[] getAttributesA() {
		return (MetaAttribute[]) lista.toArray(new MetaAttribute[0]);
	}

	public String getAttributeNames(){
		StringBuffer ret=new StringBuffer();
		boolean first=true;
		for(MetaAttribute a:lista){
			if(first){
				first=false;
			}else{
				ret.append(",");
			}
			ret.append(a.getName());			
		}
		return ret.toString();
	}
	
	public Object newInstance() {
		// if existe la clase 'name' return clase.newInstance();
		return new EntityHash(name);
	}

}
