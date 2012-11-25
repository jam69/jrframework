package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;

/**
 * Genera numeros enteros positivos aleatorios de tipo Short.
 * 
 * @author UF768023
 *
 */
public class ShortCreator implements DataCreator {

	static Random rnd = new Random();

	public Object create(String[] par) {
		short x=(short)rnd.nextInt();
		if(par!=null && par.length>0){
			short m=Short.parseShort(par[0]);
			x %= m;
		}		
		return new Short((short) (x<0 ? -x : x) );
	}

}

/*
 * $Log: ShortCreator.java,v $
 * Revision 1.1  2009/02/09 15:28:02  UF768023gcv
 * Cambios de paquetes.
 *
 * Revision 1.1  2007/01/15 11:46:15  B0000005gcv
 * Cambios para incluir los generadores de tipo Long y Short
 *
 */