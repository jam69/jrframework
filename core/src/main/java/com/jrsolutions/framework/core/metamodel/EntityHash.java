package com.jrsolutions.framework.core.metamodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Implementaci�n simple de entidad basado en un 
 * Map de propiedades.
 * 
 * @author Jose Antonio
 *
 */
public class EntityHash implements Entity {

	private Map map=new HashMap();
	private String typeName;
	
	public EntityHash(String typeName) {
		this.typeName=typeName;
	}

	public void setProperty(String name, Object value) {
		map.put(name,value);
	}

	public Object getProperty(String name) {
		return map.get(name);
	}

	public String getTypeName() {		
		return typeName;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer("Entity:");
		Iterator it=map.keySet().iterator();
		while(it.hasNext()){
			Object key=it.next();
			sb.append(key);
			sb.append("=");
			sb.append(map.get(key));
			sb.append(" ");
		}
		return sb.toString();
	}

	public Collection getKeys() {
		// TODO Auto-generated method stub
		return this.map.keySet();
	}
	public Object clone() {
		// TODO Auto-generated method stub
		try {
			EntityHash eh = (EntityHash) super.clone();
			this.typeName = eh.typeName;
			this.map = (HashMap)((HashMap)eh.map).clone();
			return eh;
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
	}

}
