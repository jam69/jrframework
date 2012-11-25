package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;

/**
 *  Devuelve Strings de longitud 10.
 *  Est�n en May�sculas.
 *  
 * @author UF768023
 *
 */
public class DNICreator implements DataCreator {

	static Random rnd = new Random();

	public Object create(String[] par){
		int rango='9'-'0';
		StringBuffer sb=new StringBuffer(8);
		for (int i=0;i<8;i++){
			int x=rnd.nextInt() % rango;
			if(x<0)x=-x;			
			Character c=new Character((char)(x+'0'));
			sb.append( c );
		}
		sb.append("-");
		int x=rnd.nextInt() % ('Z'-'A');
		if(x<0)x=-x;			
		Character c=new Character((char)(x+'A'));
		sb.append( c );
		return sb.toString();
	}

}
