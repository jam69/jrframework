package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;
/**
 * Genera numeros de tipo Long aleatorios.
 * 
 * @author UF768023
 *
 */
public class LongCreator implements DataCreator {

	static Random rnd = new Random();

	/**
	 * Si par es distinto de nulo, indica el valor m�ximo a pedir
	 */
	public Object create(String[] par) {
		long x=rnd.nextLong();
		if(par!=null && par.length>0){
			long m=Long.parseLong(par[0]);
			x %= m;
		}
		return new Long( x<0 ? -x : x );
	}

}

/*
 * $Log: LongCreator.java,v $
 * Revision 1.1  2009/02/09 15:28:02  UF768023gcv
 * Cambios de paquetes.
 *
 * Revision 1.1  2007/01/15 11:46:15  B0000005gcv
 * Cambios para incluir los generadores de tipo Long y Short
 *
 */