package com.jrsolutions.framework.generator.internacionalizer;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

/**
 * Clase que se encarga de traducir el texto por el que est� definido en el archivo de su locale 
 * Ejemplo: es_Es.properties.
 * */

public class GestorMultilingualidad extends java.util.Properties{

	private boolean traducir = true;
	private Properties properties = new Properties();
	private Locale locale=null;
	//objeto que apunta al contexto de filtros generales
	/**
	 * Constructor. En caso de que no encuentre el fichero o exista una excepci�n de E/S no traduce
	 * */
	
	/***
	 * SOLO PILLA EL IDIOMA QUE SE INTRODUCE POR LAS PROPIEDADES DEL MODELER
	 */
	public GestorMultilingualidad ()
	{
		super();
		String localeString;
		try {
			//properties.load(new URL(MainGenerate..getURLProject(),"uf_runner.properties").openStream()); //$NON-NLS-1$
			localeString =properties.getProperty("LOCALE");		
			if (localeString== null || localeString.equalsIgnoreCase("") )
				traducir = false;
			else{
				traducir = true;
//				locale= new Locale(localeString.substring(0,localeString.indexOf("_")));
//				URL url =  new URL(Main.getURLProject(),"traducciones/"+localeString+".properties");
//				this.load(url.openStream());

			}
		}
//		catch (FileNotFoundException e) {
//			traducir = false;
//			
//		}
		catch (Exception e) {
			e.printStackTrace();
			traducir = false;
		}
	}
	/**
	 * Traduce -busca en el fichero de properties- <code>palabra</code>. 
	 * @param palabra expresi�n a traducir
	 * @return expresi�n traducida
	 * */
	public String buscar (String palabra)
	{
		if ((palabra == null))
			return null;
		palabra = palabra.replace(' ','_');
		String resultado = this.getProperty(palabra);
		if (( resultado != null) && (traducir)  && (resultado.equalsIgnoreCase( "/NOTRANSLATION"))== false)
			return resultado;
		else{
			return palabra.replace('_',' ');
		}
	}

	/**
	 * Traduce -busca en el fichero de properties- <code>palabra</code> teniendo en cuenta a <code>ambito</code>
	 * En caso de que no exista traducci�n para ambito.palabra devuelve la traducci�n de palabra.
	 * @param ambito de la palabra a traducir (ejemplo: aplicaci�n si palabra es un caso de uso)
	 * @param palabra expresi�n a traducir
	 * @return expresi�n traducida
	 * */
	public String buscar (String ambito, String palabra)
	{
	
		String palabraOriginal = null;
		if (traducir == false)
			return palabra;
		if ((ambito == "") || (ambito == null))
			return buscar (palabra);
		if ((palabra == null))
			return null;
		palabra = palabra.replace(' ','_');
		ambito = ambito.replace(' ','_');
		
		if (this.hasBeenTranslated(ambito))
			ambito = this.returnOriginal(ambito);
		if (this.hasBeenTranslated(palabra))
			palabraOriginal = this.returnOriginal(palabra);
		
		String resultado = null;
		
		if (palabraOriginal == null)
		resultado = this.getProperty(ambito+"."+palabra);
		
		else
			resultado = this.getProperty(ambito+"."+palabraOriginal);
		if (( resultado != null) && (traducir) &&  (resultado.equalsIgnoreCase( "/NOTRANSLATION"))== false)
		{		return resultado; }
		else
			return buscar(palabra);	
	}
	
	/**
	 * M�todo que indica si el �mbito ha sido traducido. En caso de ser as�, se llama al m�todo returnOriginal.
	 * @param  ambito
	 * @return boolean
	 * */
	public boolean hasBeenTranslated (String ambito)
	{
		Enumeration e = this.propertyNames();
		while (e.hasMoreElements())
		{
			String llave = (String)e.nextElement();
			
			if (this.getProperty(llave).equalsIgnoreCase(ambito))
			{	 return true;}
		}
		return false;
	}
	public boolean contains (String cadena, char caracter)
	{
		char [] c = cadena.toCharArray();
		for (int i = 0;  i < c.length; ++i)
			if (c[i] == caracter)
				return true;
		return false;
	}
	/**
	 * M�todo que devuelve la traducci�n inversa de <code>ambito </code>
	 * @param ambito
	 * @return original
	 * */
	public String returnOriginal (String ambito)
	{
		Enumeration e = this.propertyNames();
		while (e.hasMoreElements())
		{
			String llave = (String)e.nextElement();
			if (this.getProperty(llave).equalsIgnoreCase(ambito))
				if (contains (llave,'.')) //estamos en un elemento que ha sido traducido a partir  de la especificacion de un contexto, luego lo que nos interesa no es la llave sino dicha especificacion
				{
					
					return this.getProperty(llave);
				}
				else
					{
						return llave;
					}
		}
		
		return "";
	}
	
	
	
	public Locale getLocale(){
		return locale;
	}

}
