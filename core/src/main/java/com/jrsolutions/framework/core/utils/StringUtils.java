
package com.jrsolutions.framework.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/** Clase de utilidad con diversos metodos estaticos para la gesti�n de
 * Strings especialmente.
 * 
 */
public class StringUtils {

	
	
	/**
	 * Cuenta las veces que aparece el string t dentro de str
	 * 
	 * @param str
	 * @param t
	 * @return
	 */
	public static int countMatches(String str,String t){
		int i=0;
		int p=str.indexOf(t);		
		while(p>0){
			p=str.indexOf(t,p);
			i++;
		}
		return i;
	}
	
	/**
	 * Divide un String por comas y devuelve el array
	 * Hace split(",") 
	 * @param txt
	 * @return
	 */
	public static List<String> toList(String txt){
        if(txt==null){
            return Collections.EMPTY_LIST;
        }
            
        String[] s=txt.split(",");
        
        // TODO: es posible que haya un metodo mejor para pasar de array a ArrayList
        ArrayList<String> list=new ArrayList<String>(s.length);
        for(int i=0;i<s.length;i++){
            list.add(s[i]);            
        }
        
        return list;
    }
	
	/**
	 * Clase de utilidad que analiza un string y lo separa en partes.
	 * Teniendo en cuenta los parentesis
	 *  
	 *  <br>pej: <i>funo,fdos,ftres(p1),fcuatro(p1,p2)</i>
	 *  <ul>se separa en:
	 *  <li>  funo
	 *  <li>  fdos
	 *  <li>  ftres(p1)
	 *  <li>  fcuatro(p1,p2)
	 *  </ul>
	 *  <br>Cuidado con los blancos, pueden dar problemas
	 */
	public static String[]divide(String s){
		StringTokenizer st=new StringTokenizer(s,"(),",true);
		int estado=0;
		ArrayList<String> res=new ArrayList<String>();
		String str="";
		StringBuffer pars=new StringBuffer();
		while(st.hasMoreTokens()){
			String tk=st.nextToken();
			if(tk.equals("(")){
				estado=1;
			}else if(tk.equals(")")){
				estado=0;				
			}else if(tk.equals(",")){
				if(estado==0){
					if(pars.length()>0){
						res.add(str+"("+pars.toString()+")");
						pars=new StringBuffer();
					}else{
						res.add(str);
					}
				}
			}else {
				if(estado==1){
				   if(pars.length()!=0){
					   pars.append(",");
				   	   }
				   pars.append(tk);				   
				}if(estado==0){
					str=tk;
				}
			}
		}
		// fin cadena
		if(estado==0){
			if(pars.length()>0){
				res.add(str+"("+pars.toString()+")");
				pars=new StringBuffer();
			}else{
				res.add(str);
			}
		}
		
		return res.toArray(new String[0]);
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
	
	
	
//	public static void main(String args[]){
//		
//		String[] s=StringUtils.divide("funo,fdos,ftres(p1),fcuatro(p1,p2)");
//		System.out.println("s:"+s);
//		
//	}
}
