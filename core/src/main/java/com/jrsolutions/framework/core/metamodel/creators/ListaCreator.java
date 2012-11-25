package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;

/**
 *  Devuelve Apellidos como Strings.
 *  Est�n en May�sculas.
 * @author UF768023
 *
 */
public class ListaCreator implements DataCreator {

	static Random rnd = new Random();
	
	public Object create(String []par){		
		int p=rnd.nextInt()%par.length;
		if(p<0) p= -p;
		return par[p];
	}
}