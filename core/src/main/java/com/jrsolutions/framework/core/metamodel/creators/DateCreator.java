package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Date;
import java.util.Random;

/**
 * Genera fechas aleatorias dentro de los ultimos 10 a�os.
 * 
 * @author UF768023
 *
 */
public class DateCreator implements DataCreator {

	static Random rnd = new Random();
	static long   anos10= 1000L*60L*60L*24L*365L*10L;
	
	/**
	 * Si par es distinto de nulo, indica el valor m�ximo a pedir
	 */
	public Object create(String[] par) {
		Date today=new Date();
		long hoy=today.getTime();
		
		long x=rnd.nextLong();		
		if(x<0)x=-x;
		x %= anos10;
		
		return new Date(hoy-x);
	}

}
