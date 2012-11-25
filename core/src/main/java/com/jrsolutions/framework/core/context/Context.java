/*
 * Context.java
 *
 * Created on 26 de noviembre de 2007, 19:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.jrsolutions.framework.core.context;

import com.jrsolutions.framework.core.expressions.ParseExpression;
import com.jrsolutions.framework.core.metamodel.EntityHash;
import com.jrsolutions.framework.core.utils.BeanExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;



/**
 * Contiene los datos de la aplicacion que se intercambian entre componentes y
 * operaciones.
 * <li>Mantiene tambien las variables globales y los listeners.
 * <li>Mantiene los beans creados por la aplicacion
 * <li>Hereda de un contexto padre las variables, beans, etc,....
 *
 * @see ContextListener
 */
public class Context {

	private static Logger log = Logger.getLogger(Context.class.getName()); //$NON-NLS-1$
	  
	
    public static final String CONTEXT_NAME = "context";
    
    // TODO: falta removeListeners Variados
    private Map globalVars; // Map<String,Object>
    private Map vars = new HashMap(); // Map<String,Object>
    private Map listeners = new HashMap(); // <String,ArrayList<ContextListener>>
    private Map beans = new HashMap(); // Map<String,Object>   // Object==Bean
    private Context parent;

    /** Creates a new instance of Context */
    public Context(Context parent) {
        this.parent = parent;
        if (parent != null) {
            this.globalVars = parent.globalVars;
        } else {
            this.globalVars = new HashMap(); //<String,Object>
        }

    }

    public Object get(String key) {
        if (key.startsWith("$")) {
            return get2(getGlobalVars(),key);
        } else {
            return get2(vars, key);
        }
    }

    private Object get2(Map map, String key) { //<String,Object>
        int ndx = key.indexOf(".");
        if (ndx > 0) {
            Object obj = get3(map, key.substring(0, ndx));
            try {
                //return BeanUtils.getProperty(obj,key.substring(ndx+1));
            	Object o= null;
            	if(obj instanceof EntityHash)
            		o = ((EntityHash)obj).getProperty(key.substring(ndx+1));
            	else
            		o= BeanExecutor.getProperty(obj,key.substring(ndx+1));
                return o;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return get3(map, key);
        }
    }

    private Object get3(Map map, String key) { //<String,Object>
        Object obj = map.get(key);
        if (obj != null) {
            return obj;
        }
        if (parent != null) {
            return parent.get(key);
        }
        return null;
    }

    public void put(String key, Object value) {
        if (key.startsWith("$")) {
          //  key = key.substring(1);
            put2(getGlobalVars(), key, value);
        } else {
            put2(vars, key, value);
        }
    }

    private void put2(Map map, String key, Object value) { //<String,Object>
        int ndx = key.indexOf(".");
        if (ndx > 0) {
            String k2 = key.substring(0, ndx);
            Object obj = get3(map, k2);
            if(obj!=null)
	            try {
	                BeanExecutor.setPropertyString(obj,key.substring(ndx+1),value);
	                fire(k2, value);

	            } catch (Exception ex) {
	                ex.printStackTrace();
	            	}
            else{
            	return;
            }
           
        } else {
            // en la version original se comprobaba que el valor
            // nuevo y viejo eran distintos antes de lanzar el
            // evento. (y habia un metodo putOrUpdate() )
            map.put(key, value);
            fire(key, value);
        }
    }

    public void addListener(String key, ContextListener ct) {
		int p = key.indexOf(".");
		if (p > 0) {
			key = key.substring(0, p);
		}
    	if (key.startsWith("$")) {
			key = key.substring(1);	
		}
    	Context padre = this;
		while (padre.parent!= null)padre = padre.parent;
		ArrayList x = (ArrayList) padre.listeners.get(key);
		if (x != null) {
			padre.listeners.put(key, x);
		}
		else{
			 x = (ArrayList) listeners.get(key);
			if (x == null) {
				x = new ArrayList();
				listeners.put(key, x);
			}
		}
		x.add(ct);
		
//		int p = key.indexOf(".");
//		if (p > 0) {
//			key = key.substring(0, p);
//		}

//		ArrayList x = (ArrayList) listeners.get(key);
//		if (x == null) {
//			x = new ArrayList();
//			listeners.put(key, x);
//		}
//		x.add(ct);
    }

    public void removeListener(ContextListener ct) {
        int i = 0;
        for (ArrayList aux = (ArrayList) ((ArrayList) listeners.values()).get(i); i < ((ArrayList) listeners.values()).size(); i++) {
            //for(ContextListener c:aux){
            int j = 0;
            for (ContextListener c = (ContextListener) ((ArrayList) aux).get(j); j < aux.size(); j++) {
                if (c == ct) {
                    aux.remove(c);
                }
            }
        }
    }

    /**
     * Quitamos un listener de las listas de listeners.
     *
     * @param key Nombre de la variable
     * @param c  Listener que tenemos que eliminar
     */
    public void removeListener(String key, ContextListener c) {
        if (key.startsWith("$")) {
            key = key.substring(1);
        }
        ArrayList x = (ArrayList) listeners.get(key);
        if (x != null) {
            x.remove(c);
        }
    }

    /** se utiliza para cuando cambia el interior del objeto, no su referencia
     * en el contexto.
     * @param key   Nombre de la variable
     * @param value  Valor cambiado.
     */   
    public void fireChanged(String key){
    	fire(key,get(key));
    }
    
//    private void fire(String key, Object value) {
//        ArrayList aux = (ArrayList) listeners.get(key); //<ContextListener>
//        if (aux == null) {
//            return;
//        }
//        for (int i = 0; i < aux.size(); i++) {
//            ContextListener c = (ContextListener) aux.get(i);
//            c.onChange(key, value);
//        }
//    }
    private void fire(String key, Object value) {
			if (key.startsWith("$")) {
				key = key.substring(1);
				
			}
			ArrayList x = (ArrayList) listeners.get(key);
			if (parent != null) {
				parent.fire(key, value);
			}
			//else{
				if (x != null) {
					// Clonamos el array para evitar ConcurrentModificationException,
					// si durante el fire, algunos de los escuchadores a�ade o elimina otro escuchador
					Iterator it = ((ArrayList) x.clone()).iterator();
					while (it.hasNext()) {
						ContextListener c = (ContextListener) it.next();
						//log.trace("fireValueChanged: key=" + key + " value=" + value + " listener=" + c);							
						c.onChange(key, value);
					}
				//}
//				// Ejecutamos los escuchadores gen�ricos
//				int size = alListeners.size();
//				for (int i = 0; i < size; i++) {
//					ContextListener c = (ContextListener) alListeners.get(i);
//					//log.trace("fireValueChanged: key=" + key + " value=" + value + " listener=" + c);
//					c.onChange(key, value);
//				}
			}
		
			
	}

    public void putBean(String key, Object obj) {
        beans.put(key, obj);
    }

    public Object getBean(String key) {
        Object obj = beans.get(key);
        if (obj == null && parent != null) {
            return parent.getBean(key);
        }
        return obj;
    }

    public Map getGlobalVars() {
        return globalVars;
    }

    public void setGlobalVars(Map globalVars) {
        this.globalVars = globalVars;
    }

    public boolean hasListener(String var) {
        return listeners.get(var) != null;
    }

    /** Devuelve el resultado de evaluar una expresi�n, y a�ade <br/> 
     *  los listener necesarios al objeto.<br/>
     * @param ctxListener El listener que se a�adir� a las variables
     * @param expression El titulo a evaluar
     * @return Devuelve la expresi�n evaluada
     */
    public String exprEval(String expression, ContextListener ctxListener) {
        // Se a�aden los listeners
        if (expression == null) {
            return "";
        }

        ArrayList varList = new ArrayList();
        Iterator it = null;
        if (expression.indexOf("$") != (-1)) {
            varList = evalVarNames(expression);
            it = varList.iterator();
            while (it.hasNext()) {
                String varName = (String) it.next();
                addListener(varName, ctxListener);
            }
        }
        // Se devuelve el resultado de evaluar la expresi�n
        return exprEval(expression);
    }
    
    /**
     * Evalua una expresi�n boolean sobre el contexto, y pone los listeners sobre
     * las variables usadas.
     * Sintaxis: (por ahora)
     *  'VAR'   : donde VAR es el nombre de una variable. Es true, si Var es distinto de nulo.
     *  'VAR=ALGO' : donde VAR es el nombre de una variable y ALGO es un valor constante. Retorna true, si el valor de la variable (.toString()) es igual a la constante.
     * 
     * @param expression  Nombre de una variable o igualdad con una constante.
     * @param ctxListener Listener a poner en cada variable.
     * @return
     */
     public boolean boolExprEval(String expression, ContextListener ctxListener) {
        // Se a�aden los listeners
        if (expression == null) {
            return true;
        }

        int p=expression.indexOf("=");
        if(p<0){
            addListener(expression,ctxListener);
        }else{
            String var=expression.substring(0,p);
            addListener(var,ctxListener);
        }
        // Se devuelve el resultado de evaluar la expresi�n
        return boolExprEval(expression);
    }
     
     public boolean removeBoolExprEval(String expression, ContextListener ctxListener) {
        // Se a�aden los listeners
        if (expression == null) {
            return true;
        }

        int p=expression.indexOf("=");
        if(p<0){
            removeListener(expression,ctxListener);
        }else{
            String var=expression.substring(0,p);
            removeListener(var,ctxListener);
        }
        // Se devuelve el resultado de evaluar la expresi�n
        return boolExprEval(expression);
    }

             /** Devuelve el resultado de evaluar una expresion. */
    public boolean boolExprEval(String expression) {
        if (expression == null) {
            return true;
        }
        int p=expression.indexOf("=");
        if(p<0){
            Object r=get(expression);
            return r!=null;
        }else{
            String var=expression.substring(0,p).trim();
            String value=expression.substring(p+1).trim();
            Object r=get(var);
            if(r==null){
                return value.equalsIgnoreCase("null");
            }else{
                return r.toString().equalsIgnoreCase(value);
            }            
        }               
    }

     
    /** Mediante el metodo {@link #eval(String, ContextListener)}, se evalua una
     * expresi�n y se a�aden escuchadores al contexto. Esto escuchadores es necesario
     * quitarlos para liberar memoria, si el componente que los tiene se deja de usar.
     * Mediante este m�todo, se quitan los escuchadores asociados a la expresi�n */
    public void exprRemoveListener(String expression, ContextListener ctxListener) {
        if (expression != null) {
            ArrayList varList = new ArrayList();
            Iterator it = null;
            if (expression.indexOf("$") != (-1)) {
                varList = evalVarNames(expression);
                it = varList.iterator();
                while (it.hasNext()) {
                    String varName = (String) it.next();
                    removeListener(varName, ctxListener);
                }
            }
        }
    }


    /** Devuelve el resultado de evaluar una expresion. */
    public String exprEval(String expression) {
        if (expression == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(expression.length());
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '$') {
                int index = i + 1;
                while (i < expression.length() && expression.charAt(i) != ' ') {
                    i++;
                }
                String varName = expression.substring(index, i);
                String varResult = "";
                if (varName != null && get(varName) != null) {
                    varResult = get(varName).toString();
                }
                result.append(varResult);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /** Devuelve una lista con las variables encontradas en la expresi�n */
    public ArrayList<String> evalVarNames(String expression) {
//        if (expression == null) {
//            return null;
//        }
//        ArrayList list = new ArrayList();
//        for (int i = 0; i < expression.length(); i++) {
//            char c = expression.charAt(i);
//            if (c == '$') {
//                int index = i;
//                while (i < expression.length() && expression.charAt(i) != ' ') {
//                    i++;
//                }
//                String varName = expression.substring(index, i);
//                list.add(evalVarName(varName));
//            }
//        }
//        return list;
    	if (expression.equalsIgnoreCase("SIEMPRE")|| expression.length()==0){
    		return new ArrayList<String>();
    	}
    	return ParseExpression.varListExpresion(expression, this);
    }

   
    /** Devuelve el nombre de la variable encontrada en la expresi�n */
//    private String evalVarName(String expresion) {
//        if (expresion == null) {
//            return "";
//        }
//        if (expresion.startsWith("$")) {
//            return expresion.substring(1);
//        } else if (expresion.startsWith("<html>")) {
//            int p = expresion.indexOf("$");
//            if (p >= 0) {
//                String pre = expresion.substring(0, p);
//                int a = expresion.indexOf(" ", p);
//                if (a < 0) {
//                    a = expresion.length();
//                }
//                String var = expresion.substring(p + 1, a);
//                String pos = expresion.substring(a, expresion.length());
//                Object obj = var;
//                return pre + (obj != null ? obj.toString() : "") + pos;
//            }
//        }
//        return expresion;
//
//    }
    
    /**
     * devuelve un ArrayList con las variables encontradas en la empresion pasada
     */
    
	//    private String[] operator ={"==","=","!=","<",">","<=",">="," le "," lt "," ge "," gt "," eq "," ne "};
	//    private String[] reservedWords={"not " ," and "," or "};
	//    
	//    private ArrayList arrVariables ;
	//    
    public ArrayList<String> varExpresion(String expr){
    	if (expr.equalsIgnoreCase("SIEMPRE")|| expr.length()==0){
    		return new ArrayList<String>();
    	}
//    	expr= expr.replaceAll(" and "," ");
//    	expr=expr.replaceAll(" or "," ");
//    	expr= expr.replaceAll("not "," ");
//    	arrVariables= new ArrayList(); 
//    	String[]partes = expr.split(" ");
//    	comparador2(partes);
//    	return arrVariables;
    	return ParseExpression.varListExpresion(expr, this);
    }
    
//    public void comparador(String[] partes){
//    	for (int i=0;i<partes.length;i++){
//    		for (int j=0;j<operator.length;j++){
//    			if (partes[i].trim().equals(operator[j])){
//    				arrVariables.add(partes[i-1]);
//    				i++;
//    			}else{
//    				if (partes[i].trim().indexOf(operator[j])>0){
//        				arrVariables.add(partes[i].substring(0,partes[i].trim().indexOf(operator[j])));
//        			}else{
//        				if (!arrVariables.contains(partes[i])){
//        					arrVariables.add(partes[i]);
//        				}
//        			}
//    			}
//    		}
//    	}
//    }
    
//    public void comparador2(String[] partes){
//    	for (int i=0;i<partes.length;i++){
//    		if(!contains(operator, partes[i])){
//    			String vble=partes[i];
//    			if (comparator3(partes[i])){
//    				vble=comparator4(partes[i]);
//    			}
//    			if (!arrVariables.contains(partes[i])){
//    				arrVariables.add(vble.trim());
//    			}
//    			
//    		}else{
//    			i++;
//    		}
//    	}
//    }
//    /*
//     * comprueba si en la expresion estan los comparadores sin espacios
//     * ej: var=12
//     */
//    private String[] op ={"==","=","!=","<",">","<=",">="};
//    public boolean comparator3(String st){
//    	for (int i=0;i<op.length;i++){
//    		if (st.indexOf(op[i])>0){
//    			return true;
//    		}
//    	}
//    	return false;
//    }
//    
//    /*
//     * devuelve la variable de una expresion sin espacios
//     * ej var=12 devuelve var
//     */
//    public String comparator4(String st){
//    	for (int i=0;i<op.length;i++){
//    		if (st.indexOf(op[i])>0){
//    			return st.substring(0,st.indexOf(op[i]));
//    		}
//    	}
//    	return st; 	
//    }
//    
//    public boolean contains ( String[] operadores,String cadena ){
//    	for (int i=0;i<operadores.length;i++){
//    		if (cadena.trim().equals(operator[i].trim())){
//    			return true;
//    		}
//    	}
//    	return false;
//    }
    
    
	/**
	 * TODO
	 * EVALUADOR DE EXPRESIONES COMPLEJAS
	 * 
	 */
	
	
//	public class JEXLAdapter implements JexlContext, Map{
//
//		// Esto es la clave del truco, devuelvo un Map 'raro' (this)
//	
//		public Map getVars() {
//			return this;
//		}
//		public Object get(Object key) {
//			String s=(String)key;
//			Object o=key;
//			if (vars.containsKey(s)||globalVars.containsKey(s )){
//				if (Context.this.get(s)!=null){
//					o=Context.this.get(s); //OJO, llamamos al get de Contexto
//				}
//				
//			}
//			return o;
//		}
//
//		public void setVars(Map arg0) {
//		}
//		public void clear() {
//		}
//		public boolean containsKey(Object key) {
//			return this.containsKey(key);
////			return false;
//		}
//		public boolean containsValue(Object value) {
//			return false;
//		}
//		public Set entrySet() {
//			return null;
//		}
//		public boolean isEmpty() {
//			return this.values().isEmpty();
//			//return false;
//		}
//		public Set keySet() {
//			return null;
//		}
//		public Object put(Object key, Object value) {
//			return null;
//		}
//		public void putAll(Map t) {
//		}
//		public Object remove(Object key) {
//			return null;
//		}
//		public int size() {
//			return 0;
//		}
//		public Collection values() {
//			return null;
//		}
//	}



/**
	 * Comprueba una condicion sobre el contexto. 
	 * Se utiliza para activar/desactivar operaciones o componentes.
	 * Por ahora es solo una prueba y solo admite, 
	 * o bien el nombre de una variable (es true si existe)
	 * o bien el nombre de una variable '=' valor (sin blancos) 
	 * En el futuro se hara un analizador mejor. 
	 * @param cond
	 * @return
	 */
	//JexlContext adapterJEXL;
	
	public boolean check(String cond){
//		try {
//			if(adapterJEXL==null){
//				adapterJEXL=new JEXLAdapter();
//			}
//			Expression e = ExpressionFactory.createExpression(cond);;		
//			
//			Object o = e.evaluate(adapterJEXL);
//			if(o instanceof Boolean){
//				return ((Boolean)o).booleanValue();
//			}else{
////				log.error("La expresion no es de tipo boolean:"+o);
////				return o!=null;
//				
//				
//				return Context.this.boolExprEval(cond.trim());
//				
//				
//				
//			}
//		}catch (NumberFormatException e){
//			
//			return false;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return false;
//		}
		if (cond.equalsIgnoreCase("SIEMPRE")|| cond.length()==0){
    		return true;
    	}
		return ParseExpression.checkExpression(cond, this);
	}
    
 
	
//	String[] operaciones ={"=","=="," eq ","!="," ne ","<", " lt ","<="," le ",">"," gt ",">="," ge "};
//	String[] logicos = {"(",")"," and "," or "};
//	 ArrayList<String>lista;
//	public ArrayList<String> getVariablesExpresion(String expresion){
//		lista = new ArrayList<String>();
//		String[] expPorPartes = expresion.split(" ");
//		if (expPorPartes.length==1){
//			lista.add(expresion);
//		}else{
//			for (int i=0;i<expPorPartes.length;i++){
//				if (esta(expPorPartes[i])){
//					i++;
//				}else{
//					if (!esta2(expPorPartes[i])){
//						lista.add(expPorPartes[i]);
//					}
//				}
//			}
//		}
//		return lista;
//	}
//	
//	public boolean esta(String exp){
//		for (int i=0;i<operaciones.length;i++){
//			if (operaciones[i].equalsIgnoreCase(exp)){
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	public boolean esta2(String exp){
//		for (int i=0;i<logicos.length;i++){
//			if (logicos[i].equalsIgnoreCase(exp)){
//				return true;
//			}
//		}
//		return false;
//	}
}