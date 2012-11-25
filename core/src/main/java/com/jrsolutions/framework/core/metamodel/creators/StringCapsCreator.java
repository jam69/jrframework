package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;

/**
 * Devuelve Strings aleatorios, de longitud 10, y con la primera letra
 * en mayusculas y las dem�s en min�sculas.
 * 
 * @author UF768023
 *
 */
public class StringCapsCreator implements DataCreator {

	static Random rnd = new Random();

	public Object create(String []par){
		int longitud=10;
		int rango='z'-'a';
		StringBuffer sb=new StringBuffer(longitud);
		for (int i=0;i<longitud;i++){
			int x=rnd.nextInt() % rango;
			if(x<0)x=-x;		
			char ch=(char)(x+'a');			
			if(i==0){
				sb.append(Character.toUpperCase(ch));
			}else{
				sb.append( ch );
			}
		}
		return sb.toString();
	}

}
