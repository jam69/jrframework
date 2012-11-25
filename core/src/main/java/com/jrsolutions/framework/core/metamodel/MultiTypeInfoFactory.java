
package com.jrsolutions.framework.core.metamodel;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *  Implementacion de una factoria, que busca informaci�n sobre clases, que
 *  utiliza a su vez, varias factorias.
 */
public class MultiTypeInfoFactory implements MetaInfoFactory {

	private static Logger log = Logger.getLogger(MultiTypeInfoFactory.class.getName()); //$NON-NLS-1$
	  
	
	private ArrayList<MetaInfoFactory> factorys=new ArrayList<MetaInfoFactory>();
	
	public MultiTypeInfoFactory(){
		
	}
	
	public MultiTypeInfoFactory(MetaInfoFactory[] facs){
		for(int i=0;i<facs.length;i++){
			factorys.add(facs[i]);
		}
	}
	
	public void addFactory(MetaInfoFactory fac){
		factorys.add(fac);
	}
	
	public MetaEntity getTypeInfo(String className) {
		for(int i=0;i<factorys.size();i++){
			MetaInfoFactory fac=factorys.get(i);
			MetaEntity info=fac.getTypeInfo(className);
			if(info!=null)return info;
		}
		log.severe("NO existe la clase: "+className);
		return null;
	}

}
