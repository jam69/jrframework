package com.jrsolutions.framework.core.metamodel.creators;


/**
 * Genera numeros enteros positivos aleatorios.
 * 
 * @author UF768023
 *
 */
public class IntegerSecuencialCreator implements DataCreator {

	static int cont=0;
	/**
	 * Va dando cada vez un numero distinto 
	 */
	public Object create(String[] par) {
		
		return new Integer( cont++);
	}

}
