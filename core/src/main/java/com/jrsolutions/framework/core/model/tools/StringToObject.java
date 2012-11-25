

package com.jrsolutions.framework.core.model.tools;


/**
 * Obtiene un objeto a partir de su descripci�n en String.
 * Admite 'funciones'
 * Se usa en {@link MethodDec#createParam(String)}
 * 
 * @see MethodDec
 * 
 * @obsolete Utilizar {@link es.indra.humandev.runner.core.utils.StringToObject}
 *    
 */
public class StringToObject {
    
      /**
     * Convierte un string a objeto
     *
     * OjO. Que devuelve Objetos, (no int sino Integer, etc,..)
     *
     * <ul>p.ej
     * <li>  " true " devuelve Boolean.TRUE
     * <li>  " FaLsE " devuelve Boolean.FALSE
     * <li>  " 34234 " devuelve Integer(34234)
     * <li>  "-34"   devuelve Ingeger(-34)
     * <li>  "456.5" devuelve Double(456.5)
     * <li>  "456.5d " devuelve Double(456.5)
     * <li>  "456.5f" devuelve Float(456.5)
     * <li>  "-445.2e-3" devuelve Double(-445.3e-3)
     * <li>  "\"algo\"" devuelve String("algo")
     * <li>  "'algo'" devuelve String("algo")
     * <li>  "Date(  xxx      )" devuelve Date.parseDate(xxx)
     * <li>  "Date(xxxx ,fmt)" devuelve  Date DateFormat(fmt).parseDate(xxx)
     * <li>  "Color(1,1,1)" devuelve new Color(1,1,1)
     * <li>  "Font('arial plain 15')" devuelve una Font
     * <li>  "Font('arial','plain',15)" devuelve una Font
     *</ul>
     * <p> Quita los blancos sobrantes al principio y final.
     * <p> Se llama recursivamente para los parametros de las funciones.
     * <p> Las mayusculas y minusculas en los nombres de las funciones son significativos.
     * <ul> Las funciones definidas son:
     * <li> Date('d/M/y')
     * <li> Date('fecha','formato') {@link SimpleDateFormat}
     * <li> Color(int,int,int)
     * <li> Font("def font") {@link Font#decode(String)}
     * <li> Font("familia","plain|bold|italic",tama�o)
     * <li> ImageIcon('nombre_icono')
     * <li> Border('nombreDelBorde') o Border('Nombre del Border',r,g,b)
     * </ul>
     *
     *
     * @param str
     * @return
     */
    public static Object getObject(String str) throws Exception{
    	// Si empieza por " o '  return String quitando las comillas
    	// Si empieza por numero o +/-
    	     // si termina en 'd' Convertir a Doble
    	     // si termina en 'f' Convertir a Float
    	     // si tiene '.' convertir a double
    	     // convertir a entero
    	// Si tiene (
    	     // FUNC es la parte izquierda del (
    	     // PARAM es la parte entre el ( y el )
    	     // busca Func En las f-registradas
    	     //       f.execute(params[])
    	     // [tambien podemos hacer param[i]-->convierte --> Object[i]
    	// trim blancos
    	str=str.trim();
    	if(str.length()==0)return null;
        if(str.equalsIgnoreCase("null"))return ParamCte.NULL;
    	if(str.equalsIgnoreCase("true"))return Boolean.TRUE;
    	if(str.equalsIgnoreCase("false"))return Boolean.FALSE;
    	if(str.startsWith("\"")){
    		int p=str.lastIndexOf("\"");
    		return str.substring(1,p);
    	}
    	if(str.startsWith("'")){
    		int p=str.lastIndexOf("'");
    		return str.substring(1,p);
    	}
    	if(Character.isDigit(str.charAt(0))|| str.startsWith("-")||str.startsWith("+")){
    		if(str.endsWith("d")){
    			Double d=new Double(str);
    			return d;
    		}
    		if(str.endsWith("f")){
    			Float f=new Float(str);
    			return f;
    		}
    		if(str.indexOf(".")>=0){
    			Double d=new Double(str);
    			return d;
    		}
    		Integer i=new Integer(str);
    		return i;
    	}
    	int x=str.indexOf("(");
    	if(x>0){
    		String nf=str.substring(0,x);
    		Funcion f=buscaFuncion(nf);
    		if(f==null){
    			throw new Exception("Funcion ["+nf+"]no conocida");
    		}
    		int pf=str.indexOf(")");
    		if(pf<0){
    			throw new Exception("Falta ')' en la funcion");
    		}
    		String args=str.substring(x+1,pf);
    		String arg[]=args.split(",");
    		Object obj[]=new Object[arg.length];
    		for(int i=0;i<arg.length;i++){
    			obj[i]=getObject(arg[i]);
    		}
    		Object r=f.execute(obj);
    		return r;
    	}
    	// lo dejo como estaba
    	// No lo he podido analizar
    	return null;
    }

    interface Funcion {
    	 public String getNombre();
    	 public Object execute(Object[] objs)throws Exception;
    }

//    private static SimpleDateFormat format=new SimpleDateFormat("d/M/y");

    private static Funcion[] funcs={
//    	new Funcion(){
//    		public String getNombre(){return "Date";}
//    		public Object execute(Object[] objs)throws Exception{
//    			try{
//    				switch(objs.length){
//    				case 1:	return format.parse((String)objs[0]);
//    				case 2:
//    					SimpleDateFormat f=new SimpleDateFormat((String)objs[1]);
//    					return f.parse((String)objs[0]);
//    				default: throw new Exception ("Numero de args erroneo Date('d/M/y') o Date('fmt','fecha')");
//    				}
//    			}catch(Exception e){
//    				throw e;
//    			}
//    		}
//    	}
    	
    };

    private static Funcion buscaFuncion(String s) throws Exception{
    	for(int i=0;i<funcs.length;i++){
    		if(funcs[i].getNombre().equals(s))return funcs[i];
    	}
    	return null;
    }

         //-----------------------Tests --------------------------
    public static void main(String args[]){
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


    	

    }

   
    private  static  void test(String str){
    	try{
    	  Object obj=StringToObject.getObject(str);
    	  //System.out.println("\""+str+"\" --> ["+obj.getClass().getName()+"]("+obj.toString()+")");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
