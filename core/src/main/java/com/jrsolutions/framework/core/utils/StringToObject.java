/*
 * StringToObject.java
 *
 * Created on 16 de noviembre de 2007, 19:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils;

import com.jrsolutions.framework.core.model.Operation;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Convierte un String en Objeto.
 * 
 * Se utiliza en varios sitios y puede ser utilizado por los clientes.
 * 
 * Se le puede a�adir nuevos 'conversores' con el metodo addFunc(Function).
 * 
 * @see Operation#getMethod()
 * 
 * 
 */
public class StringToObject {

	interface Funcion {
		public String getNombre();

		public Object execute(Object[] objs) throws Exception;
	}

	private static SimpleDateFormat format = new SimpleDateFormat("d/M/y");

//	private static ArrayList<Funcion> funcs = new ArrayList<Funcion>();
	
	private static HashMap<String, Funcion>  funcsMap= new HashMap<String, Funcion>();
	
	/**
	 * Convierte un string a objeto
	 * 
	 * OjO. Que devuelve Objetos, (no int sino Integer, etc,..)
	 * 
	 * <ul>
	 * p.ej
	 * <li>" true " devuelve Boolean.TRUE
	 * <li>" FaLsE " devuelve Boolean.FALSE
	 * <li>" 34234 " devuelve Integer(34234)
	 * <li>"-34" devuelve Ingeger(-34)
	 * <li>"456.5" devuelve Double(456.5)
	 * <li>"456.5d " devuelve Double(456.5)
	 * <li>"456.5f" devuelve Float(456.5)
	 * <li>"-445.2e-3" devuelve Double(-445.3e-3)
	 * <li>"\"algo\"" devuelve String("algo")
	 * <li>"'algo'" devuelve String("algo")
	 * <li>"Date(  xxx      )" devuelve Date.parseDate(xxx)
	 * <li>"Date(xxxx ,fmt)" devuelve Date DateFormat(fmt).parseDate(xxx)
	 * <li>"Color(1,1,1)" devuelve new Color(1,1,1)
	 * <li>"Font('arial plain 15')" devuelve una Font
	 * <li>"Font('arial','plain',15)" devuelve una Font
	 *</ul>
	 * <p>
	 * Quita los blancos sobrantes al principio y final.
	 * <p>
	 * Se llama recursivamente para los parametros de las funciones.
	 * <p>
	 * Las mayusculas y minusculas en los nombres de las funciones son
	 * significativos.
	 * <ul>
	 * Las funciones definidas son:
	 * <li>Date('d/M/y')
	 * <li>Date('fecha','formato') {@link SimpleDateFormat}
	 * <li>Color(int,int,int)
	 * <li>Font("def font") {@link Font#decode(String)}
	 * <li>Font("familia","plain|bold|italic",tama�o)
	 * <li>ImageIcon('nombre_icono')
	 * <li>Border('nombreDelBorde') o Border('Nombre del Border',r,g,b)
	 * </ul>
	 * 
	 * 
	 * @param str
	 * @return
	 */
	public static Object getObject(String str) throws Exception {
		// Si empieza por " o ' return String quitando las comillas
		// Si empieza por numero o +/-
		// si termina en 'd' Convertir a Doble
		// si termina en 'f' Convertir a Float
		// si tiene '.' convertir a double
		// convertir a entero
		// Si tiene (
		// FUNC es la parte izquierda del (
		// PARAM es la parte entre el ( y el )
		// busca Func En las f-registradas
		// f.execute(params[])
		// [tambien podemos hacer param[i]-->convierte --> Object[i]
		// trim blancos
		str = str.trim();
		if (str.length() == 0)
			return null;
		if (str.equalsIgnoreCase("true"))
			return Boolean.TRUE;
		if (str.equalsIgnoreCase("false"))
			return Boolean.FALSE;
		if (str.startsWith("\"")) {
			int p = str.lastIndexOf("\"");
			return str.substring(1, p);
		}
		if (str.startsWith("'")) {
			int p = str.lastIndexOf("'");
			return str.substring(1, p);
		}
		if (Character.isDigit(str.charAt(0)) || str.startsWith("-")
				|| str.startsWith("+")) {
			if (str.endsWith("d")) {
				Double d = new Double(str);
				return d;
			}
			if (str.endsWith("f")) {
				Float f = new Float(str);
				return f;
			}
			if (str.indexOf(".") >= 0) {
				Double d = new Double(str);
				return d;
			}
			Integer i = new Integer(str);
			return i;
		}

		int x = str.indexOf("(");
		if (x > 0) {
			String nf = str.substring(0, x);
			Funcion f = buscaFuncion(nf);
			if (f == null) {
				throw new Exception("Funcion [" + nf + "]no conocida");
			}
			int pf = str.indexOf(")");
			if (pf < 0) {
				throw new Exception("Falta ')' en la funcion");
			}
			String args = str.substring(x + 1, pf);
			String arg[] = args.split(",");
			Object obj[] = new Object[arg.length];
			for (int i = 0; i < arg.length; i++) {
				obj[i] = getObject(arg[i]);
			}
			Object r = f.execute(obj);
			return r;
		}
		// lo dejo como estaba
		// No lo he podido analizar
		return null;
	}

	public static void addFuncion(Funcion f) {
		//funcs.add(f);
		funcsMap.put(f.getNombre(), f);
		
	}

	static {
		
//		funcs.add(new Funcion() {
//			public String getNombre() {
//				return "Date";
//			}
//
//			public Object execute(Object[] objs) throws Exception {
//				try {
//					switch (objs.length) {
//					case 1:
//						return format.parse((String) objs[0]);
//					case 2:
//						SimpleDateFormat f = new SimpleDateFormat(
//								(String) objs[1]);
//						return f.parse((String) objs[0]);
//					default:
//						throw new Exception(
//								"Numero de args erroneo Date('d/M/y') o Date('fmt','fecha')");
//					}
//				} catch (Exception e) {
//					throw e;
//				}
//			}
//		});
		
		funcsMap.put("Date", new Funcion(){
			public String getNombre() {
				return "Date";
			}

			public Object execute(Object[] objs) throws Exception {
				try {
					switch (objs.length) {
					case 1:
						return format.parse((String) objs[0]);
					case 2:
						SimpleDateFormat f = new SimpleDateFormat(
								(String) objs[1]);
						return f.parse((String) objs[0]);
					default:
						throw new Exception(
								"Numero de args erroneo Date('d/M/y') o Date('fmt','fecha')");
					}
				} catch (Exception e) {
					throw e;
				}
			}			
		});
		
		
//		funcs.add(new Funcion() {
//			public String getNombre() {
//				return "Color";
//			}
//
//			public Object execute(Object[] objs) throws Exception {
//				int r = ((Integer) objs[0]).intValue();
//				int g = ((Integer) objs[1]).intValue();
//				int b = ((Integer) objs[2]).intValue();
//				return new Color(r, g, b);
//			}
//		});
		
		funcsMap.put("Color", new Funcion() {
			public String getNombre() {
				return "Color";
			}

			public Object execute(Object[] objs) throws Exception {
				int r = ((Integer) objs[0]).intValue();
				int g = ((Integer) objs[1]).intValue();
				int b = ((Integer) objs[2]).intValue();
				return new Color(r, g, b);
			}
		});
		
		
//		funcs.add(new Funcion() {
//			public String getNombre() {
//				return "Font";
//			}
//
//			public Object execute(Object[] objs) throws Exception {
//				try {
//					switch (objs.length) {
//					case 1:
//						return Font.decode((String) objs[0]);
//					case 3:
//						int s = 0;
//						String style = (String) objs[1];
//						if (style.equalsIgnoreCase("bold"))
//							s = Font.BOLD;
//						else if (style.equalsIgnoreCase("plain"))
//							s = Font.PLAIN;
//						else if (style.equalsIgnoreCase("italic"))
//							s = Font.ITALIC;
//						return new Font((String) objs[0], s,
//								((Integer) objs[2]).intValue());
//					default:
//						throw new Exception(
//								"Numero de args erroneo Font('arial plain 10') o Font('arial','plain','10')");
//					}
//				} catch (Exception e) {
//					throw e;
//				}
//			}
//		});
		
		funcsMap.put("Font", new Funcion(){
			public String getNombre() {
				return "Font";
			}

			public Object execute(Object[] objs) throws Exception {
				try {
					switch (objs.length) {
					case 1:
						return Font.decode((String) objs[0]);
					case 3:
						int s = 0;
						String style = (String) objs[1];
						if (style.equalsIgnoreCase("bold"))
							s = Font.BOLD;
						else if (style.equalsIgnoreCase("plain"))
							s = Font.PLAIN;
						else if (style.equalsIgnoreCase("italic"))
							s = Font.ITALIC;
						return new Font((String) objs[0], s,
								((Integer) objs[2]).intValue());
					default:
						throw new Exception(
								"Numero de args erroneo Font('arial plain 10') o Font('arial','plain','10')");
					}
				} catch (Exception e) {
					throw e;
				}
			}			
		});
		
		
//		funcs.add(new Funcion() {
//			public String getNombre() {
//				return "ImageIcon";
//			}
//
//			public Object execute(Object[] objs) throws Exception {
//				try {
//					switch (objs.length) {
//					case 1:
//						return ImageLoader.loadIcon((String) objs[0]);
//						// return new
//						// ImageIcon(getClass().getResource((String)objs[0]));
//					default:
//						throw new Exception(
//								"Numero de args erroneo ImageIcon('path_icono')");
//					}
//				} catch (Exception e) {
//					throw e;
//				}
//			}
//		});
		
		funcsMap.put("ImageIcon", new Funcion(){
			public String getNombre() {
				return "ImageIcon";
			}

			public Object execute(Object[] objs) throws Exception {
				try {
					switch (objs.length) {
					case 1:
						return ImageLoader.loadIcon((String) objs[0]);
						// return new
						// ImageIcon(getClass().getResource((String)objs[0]));
					default:
						throw new Exception(
								"Numero de args erroneo ImageIcon('path_icono')");
					}
				} catch (Exception e) {
					throw e;
				}
			}
		});
		// new Funcion(){
		// public String getNombre(){return "Border";}
		// public Object execute(Object[] objs)throws Exception{
		// try{
		// switch(objs.length){
		// case 1: return BorderFactoryUtil.createBorder((String)objs[0]);
		// case 4:
		// return
		// BorderFactoryUtil.createBorder((String)objs[0],((Integer)objs[1]).intValue(),
		// ((Integer)objs[2]).intValue(),((Integer)objs[3]).intValue());
		// default: throw new Exception
		// ("Numero de args erroneo Border('nombreDelBorde',r,g,b))");
		// }
		// }catch(Exception e){
		// throw e;
		// }
		// }
		// },
//		funcs.add(new Funcion() {
//			public String getNombre() {
//				return "Insets";
//			}
//
//			public Object execute(Object[] objs) throws Exception {
//				try {
//					switch (objs.length) {
//					case 4:
//						return new Insets(((Integer) objs[0]).intValue(),
//								((Integer) objs[1]).intValue(),
//								((Integer) objs[2]).intValue(),
//								((Integer) objs[3]).intValue());
//					default:
//						throw new Exception(
//								"Numero de args erroneo Insets(margen_top,margen_izda,margen_abajo,margen_dcha))");
//					}
//				} catch (Exception e) {
//					throw e;
//				}
//			}
//		});
		
		funcsMap.put("Insets", new Funcion(){
			public String getNombre() {
				return "Insets";
			}

			public Object execute(Object[] objs) throws Exception {
				try {
					switch (objs.length) {
					case 4:
						return new Insets(((Integer) objs[0]).intValue(),
								((Integer) objs[1]).intValue(),
								((Integer) objs[2]).intValue(),
								((Integer) objs[3]).intValue());
					default:
						throw new Exception(
								"Numero de args erroneo Insets(margen_top,margen_izda,margen_abajo,margen_dcha))");
					}
				} catch (Exception e) {
					throw e;
				}
			}			
		});

		
		
//		funcs.add(new Funcion() {
//			public String getNombre() {
//				return "ArrayList";
//			}
//
//			public Object execute(Object[] objs) throws Exception {
//
//				ArrayList<Object> res = new ArrayList<Object>();
//				for (int i = 0; i < objs.length; i++) {
//					res.add(objs[i]);
//				}
//				return res;
//
//			}
//		});
		funcsMap.put("ArrayList", new Funcion() {
			public String getNombre() {
				return "ArrayList";
			}

			public Object execute(Object[] objs) throws Exception {

				ArrayList<Object> res = new ArrayList<Object>();
				for (int i = 0; i < objs.length; i++) {
					res.add(objs[i]);
				}
				return res;

			}
		});
	};

	private static Funcion buscaFuncion(String s) throws Exception {
		// TODO: optimizar la busqueda (un HashMap en lugar de Array)
		// Busca
		for (Funcion f : funcsMap.values()) {
			if (f.getNombre().equals(s))
				return f;
		}
		return null;
	}

	// -----------------------Tests --------------------------
	public static void main(String args[]) {
		test("34343");
		test("-34");
		test("  45534.45  ");
		test("432432.432d  ");
		test("432432.432f");
		test(" -344.2e-3 ");
		test("'algo'");
		test("\"algo\"");
		test("Date( '12/03/2007')");
		test("Date('12/03/2007',  'M/d/y')");
		test(" Color(1,1, 1)");
		test(" Font('arial plain 15')");
		test(" Font('arial-plain-15')");
		test(" Font('arial','plain',15)");
		test(" true ");
		test(" false");
		test(" ArrayList( \"uno\", true,34343)");
		test("ArrayList( 'uno', true,34343)");
		// test("ArrayList( 432432.432d, Date( '12/03/2007'),34343 ) " );
		// test("ArrayList( 432432.432d, ArrayList(true,false ) ,34343 ) " );
		// test("ArrayList( 432432.432d, ArrayList( ) ,34343 ) " );
	}

	private static void test(String str) {
		try {
			Object obj = StringToObject.getObject(str);
			System.out.println("\"" + str + "\" --> ["
					+ obj.getClass().getName() + "](" + obj.toString() + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
