package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;

/**
 *  Devuelve Strings de longitud 10.
 *  Est�n en May�sculas.
 *  
 * @author UF768023
 *
 */
public class StringCreator implements DataCreator {

	static Random rnd = new Random();

	public Object create(String[] par){
		int longitud=10;
		if(par!=null){
			int v=Integer.parseInt(par[0]);
			longitud=v;
		}
		int rango='Z'-'A';
		StringBuffer sb=new StringBuffer(longitud);
		for (int i=0;i<longitud;i++){
			int x=rnd.nextInt() % rango;
			if(x<0)x=-x;			
			Character c=new Character((char)(x+'A'));
			sb.append( c );
		}
		return sb.toString();
	}

}
