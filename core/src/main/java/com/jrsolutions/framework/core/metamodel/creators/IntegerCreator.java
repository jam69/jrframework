package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;

/**
 * Genera numeros enteros positivos aleatorios.
 * 
 * @author UF768023
 *
 */
public class IntegerCreator implements DataCreator {

	static Random rnd = new Random();

	/**
	 * Si par es distinto de nulo, indica el valor m�ximo a pedir
	 */
	public Object create(String[] par) {
		int x=rnd.nextInt();
		if(par!=null && par.length>0){
			int m=Integer.parseInt(par[0]);
			x %= m;
		}
		return new Integer( x<0 ? -x : x );
	}

}
