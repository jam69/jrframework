package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;

/**
 *  Devuelve Nombres como Strings.
 *  Est�n en May�sculas.
 *  
 * @author UF768023
 *
 */
public class ProvinciasCreator implements DataCreator {

	static Random rnd = new Random();
	
	

	public Object create(String[] par){		
		int p=rnd.nextInt()%nombres.length;
		if(p<0) p= -p;
		return nombres[p];
	}

	static String[] nombres={
		"A Coru�a",
		"�lava",
		"Albacete",
		"Alicante",
		"Almeria",
		"Asturias",
		"�vila",
		"Badajoz",
		"Baleares",
		"Barcelona",
		"Burgos",
		"C�ceres",
		"C�diz",
		"Cantabria",
		"Castell�n",
		"Ceuta",
		"Ciudad Real",
		"C�rdoba",
		"Cuenca",
		"Girona",
		"Granada",
		"Guadalajara",
		"Guip�zcoa",
		"Huelva",
		"Huesca",
		"Ja�n",
		"La Rioja",
		"Las Palmas",
		"Le�n",
		"Lleida",
		"Lugo",
		"Madrid",
		"M�laga",
		"Melilla",
		"Murcia",
		"Navarra",
		"Ourense",
		"Palencia",
		"Pontevedra",
		"Salamanca",
		"S.C. Tenerife",
		"Segovia",
		"Sevilla",
		"Soria",
		"Tarragona",
		"Teruel",
		"Toledo",
		"Valencia",
		"Valladolid",
		"Vizcaya",
		"Zamora",
		"Zaragoza"
	};
}
