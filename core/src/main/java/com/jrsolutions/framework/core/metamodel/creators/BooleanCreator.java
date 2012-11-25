package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;

/**
 * Genera numeros enteros positivos aleatorios.
 * 
 * @author UF768023
 *
 */
public class BooleanCreator implements DataCreator {

	static Random rnd = new Random();

	/**
	 * Si par es distinto de nulo, indica el valor m�ximo a pedir
	 */
	public Object create(String[] par) {
		int x=rnd.nextInt(100);
		int medio=50;
		if(par!=null && par.length>0){
			medio=Integer.parseInt(par[0]);
		}
		return Boolean.valueOf( x<medio );
	}

}
