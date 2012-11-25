package com.jrsolutions.framework.core.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;


/**
 *  Clase de utilidad que analiza un string y lo separa en partes.
 *  
 *  <br>pej: <i>funo,fdos,ftres(p1),fcuatro(p1,p2)</i>
 *  <ul>se separa en:
 *  <li>  funo
 *  <li>  fdos
 *  <li>  ftres(p1)
 *  <li>  fcuatro(p1,p2)
 *  </ul>
 *  <br>Cuidado con los blancos, pueden dar problemas
 *  <br>Cuidado con la mal sintaxis, casi nunca da error aunque lo procese mal.
 *  
 *  <pre>
 * 		AnalizaStringEditors a=new AnalizaStringEditors("funo,fdos(p1,p2,p3),ftres(p1),fcuatro(p1,p2)");
 *		while(a.hasMoreCommands()){
 *			System.out.println("Comm="+a.nextCommand()+" ["+a.nextParameters()+"]");
 *		}
 *  </pre>
 *  
 * @author UF768023
 *
 */
public class AnalizaStringEditors {

	private static String[] ALIGNMENT_PARAMS = new String[] {"I","C","D"};

	/**
	 * Divide un String por las comas (teniendo en cuenta los parentesis)
	 * 
	 * @param s
	 * @return Los diferentes comandos.
	 */
	public static String[] divide(String s){
		//   a[(a[,a]*)]?[a[(a[,a]*)]]?
		if(s==null)return null;
		String a[]=s.split(",");
		ArrayList comandos=new ArrayList();
		String aux="";
		for (int i=0;i<a.length;i++){
			//System.out.println("-->"+a[i]);
			if(a[i].indexOf("(")>0){
				if(a[i].indexOf(")")>0){
					comandos.add(a[i]); // comando con 1 parametro
				}else{ // empieza comando con mas de 1 parametro
					aux=a[i];
				}
			}else{
				if(aux.length()>0){
					aux=aux+","+a[i];
					if(a[i].indexOf(")")>0){ // fin comando con parametros
						comandos.add(aux);
						aux="";
					}
				}else{
					comandos.add(a[i]);  // comando sin parametros
				}
			}
		}
		return (String[])comandos.toArray(new String[0]);
	}
	
	/**
	 * Obtiene el comando de un chisme...
	 * Pej: "uno"  devuelve "uno"
	 *      "dos()" devuelve "dos"
	 *      "tres(p)" devuelve "tres"
	 *      "tres(p,q)" devuelve "tres"
	 * @param s algo similar a comando(...)
	 * @return comando
	 */
	public static String command(String s){
		// busca '('
		int p=s.indexOf('(');
		if(p<0){
			return s;
		}else{
			return s.substring(0,p);
		}
	}
	
	/**
	 * Obtiene los parametros de un chisme...
	 * Pej: "uno"  devuelve [],
	 *      "dos()" devuelve []
	 *      "tres(p)" devuelve [p]
	 *      "tres(p,q)" devuelve [p][q]
	 * 
	 * @param s
	 * @return String[] con los par�metros
	 */
	public static String[] parameters(String s){
		//busca '('
		int p=s.indexOf('(');
		if(p<0)return null;
		//busca ')'
		int p2=s.indexOf(')');
		if(p2<=p){
			throw new IllegalArgumentException("Mal formado. '"+s+"' debe tener la forma comando(para,...) y falta el ultimo parentesis");
		}
		// divide
		String par=s.substring(p+1,p2);
		return par.split(",");
	}

	/** Devuelve los par�metros, eliminando el par�metro de alineaci�n (si existe).<br/>
	 * Si hay m�s de un par�metro de alineaci�n, elimina el �ltimo que se encuentre. */
	public static String[] clearParameters(String[] params) {
		int index = -1;
		for(int i=0;params!=null && i<params.length;i++) {
			if(alignmentOfParam(params[i])!=-1) {
				index = i;
			}
		}
		if(index!=-1) {
			List a = java.util.Arrays.asList(params);
			a.remove(index);
			return (String[])a.toArray();
		}
		return params;
	}
  /** Devuelve el valor del par�metro de alineaci�n. <br/>
   * Si hay varios, devuelve el del �ltimo par�metro que se encuentre.*/
	public static int align(String[] params) {
		int align = -1;
		for(int i=0;params!=null && i<params.length;i++) {
			int alignAux = alignmentOfParam(params[i]);
			if(alignAux!=-1) {
				align = alignAux;
			}
		}
		return align;
	}
	// Devuelve la alineaci�n que corresponde al par�metro
	private static int alignmentOfParam(String param) {
		if(param!=null && param.length()>0) {
			if(param.equalsIgnoreCase(ALIGNMENT_PARAMS[0])) {
				return SwingConstants.LEFT;
			}
			else if(param.equalsIgnoreCase(ALIGNMENT_PARAMS[1])) {
				return SwingConstants.CENTER;
			}
			else if(param.equalsIgnoreCase(ALIGNMENT_PARAMS[2])) {
				return SwingConstants.RIGHT;
			}
		}
		return -1;
	}
	public static String parametersAll(String s){
		//busca '('
		int p=s.indexOf('(');
		if(p<0)return null;
		//busca ')'
		int p2=s.indexOf(')');
		if(p2<=p){
			throw new IllegalArgumentException("Mal formado. '"+s+"' debe tener la forma comando(para,...) y falta el ultimo parentesis");
		}
		// divide
		String par=s.substring(p+1,p2);
		return par;
	}
	

}
