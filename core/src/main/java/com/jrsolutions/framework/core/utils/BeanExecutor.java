/*
 * BeanExecutor.java
 *
 * Created on 26 de noviembre de 2007, 20:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import com.jrsolutions.framework.core.Contexto;
import com.jrsolutions.framework.core.Servicio;
import com.jrsolutions.framework.core.context.Context;
import com.jrsolutions.framework.core.context.MInfoMeta;
import com.jrsolutions.framework.core.context.MService;
import com.jrsolutions.framework.core.metamodel.MetaInfoFactory;
import com.jrsolutions.framework.core.model.Bean;
import com.jrsolutions.framework.core.model.HasModelProperties;
import com.jrsolutions.framework.core.model.ModelProperty;
import com.jrsolutions.framework.core.model.Operation;
import com.jrsolutions.framework.core.model.Window;
import com.jrsolutions.framework.core.utilitybeans.DataGenerator;
import com.jrsolutions.framework.core.utilitybeans.ScriptBean;
import com.jrsolutions.framework.core.utilitybeans.Totalizador;
import es.indra.humandev.runner.core.utils.StringToObject2;

/**
 * Clase de utilidad para manejar la introspecci�n.
 * 
 * Crea los objetos (beans), pone las propiedades a los elementos, llama a
 * los metodos, etc,....
 * 
 * 
 */
public class BeanExecutor {
    
	private static Logger log= Logger.getLogger(BeanExecutor.class.getName());

	   private static Map mappedClasses=new HashMap();
	    static {
	        mappedClasses.put("com.jrsolutions.introspect.components.beans.ScriptBean",ScriptBean.class);
	        mappedClasses.put("com.jrsolutions.introspect.components.beans.utils.Totalizador",Totalizador.class);
	        mappedClasses.put("com.jrsolutions.introspect.components.beans.Totalizador",Totalizador.class);
	        mappedClasses.put("com.jrsolutions.introspect.components.beans.DataGenerator",DataGenerator.class);	        
	    }
	
	public static void createBeans(Window w, Context ctx,MetaInfoFactory factory) throws Exception {
	        Iterator it=w.getBeans().iterator();
	        while(it.hasNext()){
	            Bean b=(Bean)it.next();
	            Object obj=createBean(b);
	            if(obj instanceof MService){
	                MService s=(MService)obj;
	                s.setContext(ctx);
	            }    
	            if(obj instanceof MInfoMeta){
	            	MInfoMeta s=(MInfoMeta)obj;
	                s.setInfoFactory(factory);
	            }    
	            setProperties(obj, b.getModelProperties());
	            ctx.putBean(b.getName(), obj);
	        }        
	    }

    /**
     * Crea un Bean a partir de sus atributos y ModelProperties.
     * 
     */


    /**
     * Crea un bean y le asigna las propiedades del modelo.
     * 
     * @param b
     * @return
     */
	private static Object createBean(Bean b) throws Exception{
		String value = b.getValue();
		if (value != null) {
			try{
				return StringToObject.getObject(value);
			}catch(Exception ex){
				log.severe("Valor no valido: '"+value+"' ");
			}
		}
		String cname=b.getClassName();
		Class c=(Class)mappedClasses.get(cname);
		if(c==null){
			c = Class.forName(cname);
		}
		Object obj=c.newInstance();
		if(b.getInitMethod()!=null)
			callInitMethod(b,b.getInitMethod(),null);
		return obj;                       
	}

 
    
    /**
     * Aplica las 'propiedades del modelo' a un objeto ya creado.
     * 
     * @param obj
     * @param props lista de ModelProperties
     * 
     * @see ModelProperty
     */
    public static void setProperties(Object obj,ArrayList props){
        Iterator it=props.iterator();
        while(it.hasNext()){
            ModelProperty mp=(ModelProperty)it.next();            
            setProperty(obj,mp.getName(),(String)mp.getValue());
        }
    }
    
    /**
     * Cambia una propiedad en el objeto indicado.
     * Busca el método accesor (el getter)y lo invoca por introspeccion.
     * 
     * @param obj
     * @param name
     * @param value
     */
//    public static void setProperty(Object obj,String name,String value){
//        try{
//            String mName="set"+firstCap(name);
//            	Object v=StringToObject.getObject(value);
//            	Method m=obj.getClass().getMethod(mName,new Class[]{v.getClass()});
//                m.invoke(obj,new Object[]{v});
//        }catch(NoSuchMethodException ex){
//             ex.printStackTrace();
//        }catch(IllegalAccessException ex){
//             ex.printStackTrace();
//        }catch(InvocationTargetException ex){
//             ex.printStackTrace();
//        }catch(Exception ex){
//        	error("Seteando una propiedad", ex);
//        }
//        
//    }
//    private static String firstCap(String str){
//        char c=str.charAt(0);
//        String s2=str.substring(1);
//        return Character.toUpperCase(c)+s2;
//    }

    public static void setProperty(Object obj,String pName,String pValue){
        try {
            Class c = obj.getClass();
            BeanInfo info = Introspector.getBeanInfo(c);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for(int i=0;i<props.length;i++){
                if(props[i].getName().equals(pName)){
                    Method m=props[i].getWriteMethod();
                    Object param=StringToObject2.getObject(pValue,props[i].getPropertyType());
                    m.invoke(obj,new Object[]{param});
                    return;
                }
            }
            throw new IllegalArgumentException("No se como poner el atributo "+pName+" a un "+obj.getClass());
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    /**
     * Crea un Objecto a partir del nombre de su clase (sin parametros)
     *
     * Si queremos utilizar otro classLoader hemos de cambiar esta clase.
     */
    public static Object creaObject(String className) {
        try {
            Class clase = Class.forName(className);
            return clase.newInstance();
        } catch (ClassNotFoundException ex) {
//            try {
//                Class clase = Plugins.classLoader.loadClass(className);
//                return clase.newInstance();
//            } catch (Exception ex2) {
//                log.error("Creando Objeto ", ex2); //$NON-NLS-1$
//                //ex2.printStackTrace();
//            }
            
        } catch (InstantiationException ex) {
            error("Creando Objeto ", ex); //$NON-NLS-1$
            //ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            error("Creando Objeto ", ex); //$NON-NLS-1$
            //ex.printStackTrace();
        }
        // not reached
        return null;
    }
    
    /**
     * Metodo privado que inicializa un bean.
     * Busca un metodo con parametros (Context) y si no lo encuentra
     * busca un m�todo sin parametros.
     */
    private static void callInitMethod(Object bean, String mName, Context ctx) {
    	callMethod(bean,mName,ctx);
    }

    private static void callDestroyMethod(Object bean, String mName, Context ctx) {
    	callMethod(bean,mName,ctx);
    }
    
    /**
     * LLama a un m�todo con un atributo de tipo Context
     *  y si no lo encuentra busca otro sin atributos.
     */ 
    private static void callMethod(Object bean, String mName,Context ctx) {
        try {
            Class c = bean.getClass();
            Class[] argsT = new Class[1];
            argsT[0] = Context.class;
            Method m = c.getMethod(mName, argsT);
            Object[] args = new Object[0];
            args[0] = ctx;
            m.invoke(bean, args);
        } catch (NoSuchMethodException ex) {
            try{
                Class c = bean.getClass();
                Method m = c.getMethod(mName, new Class[0]);
                Object[] args = new Object[0];
                m.invoke(bean, args);
            }catch(Exception ex2){
                error("Llamando al destroyMethod ", ex2);
            }
        } catch (IllegalAccessException ex) {
            error("Llamando al destroyMethod ", ex); //$NON-NLS-1$
        } catch (InvocationTargetException ex) {
            error("Llamando al destroyMethod ", ex); //$NON-NLS-1$
        }
    }
        
    /**
     * Setea a un objeto las ModelProperties definidas en el modelo.
     * @param p Entidad del modelo que tiene propiedades
     * @param obj La instancia (u objeto) a modificar.
     */
     public static void setPropiedades(HasModelProperties p, Object obj) {
         setPropiedades(p.getModelProperties(),obj);
     }
//    public static void setPropiedades(HasModelProperties p, Object jpanel) {
//        try {
//            Class c = jpanel.getClass();
//            log.debug("panel:" + c); //$NON-NLS-1$
//            BeanInfo info = Introspector.getBeanInfo(c);
//            PropertyDescriptor[] props = info.getPropertyDescriptors();
//            Iterator it = p.getModelProperties().iterator();
//            while (it.hasNext()) {
//                ModelProperty prop = (ModelProperty) it.next();
//                PropertyDescriptor pDesc = getProp(props, prop.getName());
//                if (pDesc != null) {
//                    // Comentada la llamada a BeanUtils porque daba IllegalAccess
//                    // en el cliente ligero  -- JOSE-- bueno, lo volvemos a dejar
//                    // para que funcione bien.
////					BeanUtils.setProperty(jpanel, prop.getName(), prop.getValue());
//                    Method m=pDesc.getWriteMethod();
//                    log.debug("Llamo a "+m.getName()+"("+prop.getValue()+")");
//                    Object args[]=new Object[1];
//                    //args[0]=ConvertUtils.convert((String)prop.getValue(),m.getParameterTypes()[0]);
//                    args[0]=StringToObject2.getObject((String)prop.getValue(),m.getParameterTypes()[0]);
//                    m.invoke(jpanel,args);
//                }
//                //prop.
//            }
//        } catch (IntrospectionException ex) {
//            ex.printStackTrace();
//        } catch (IllegalAccessException ex) {
//            ex.printStackTrace();
//        } catch (InvocationTargetException ex) {
//            //ex.printStackTrace();
//            System.err.println(ex);
//            System.err.println(p);
//            Iterator it = p.getModelProperties().iterator();
//            while (it.hasNext()) {
//                ModelProperty prop = (ModelProperty) it.next();
//                System.err.println(prop.getName() + "=" + prop.getValue()); //$NON-NLS-1$
//            }
//        }catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        
//    }
    
    /**
     * Pone una propiedad a un objeto.
     * @param obj  El objeto a modificar
     * @param pName El nombre de la propiedad
     * @param pValue el valor de la propiedad
     */
    public static void setPropertyString(Object obj,String pName,Object pValue){
        try {
            Class c = obj.getClass();
            BeanInfo info = Introspector.getBeanInfo(c);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for(int i=0;i<props.length;i++){
                if(props[i].getName().equals(pName)){
                    Method m=props[i].getWriteMethod();
                    Object param=pValue;
                    m.invoke(obj,new Object[]{param});
                    return;
                }
            }
            throw new IllegalArgumentException("No se como poner el atributo "+pName+" a un "+obj.getClass());
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    
    /**
     * Obtiene el valor de una propiedad.
     * @param bean El objeto que contiene la propiedad
     * @param propName El nombre de la propiedad en cuestion.
     */
    public static Object getProperty( Object bean,String propName) {
        if (bean == null)
            return null;
        try {
            Class c = bean.getClass();
            BeanInfo info = Introspector.getBeanInfo(c);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            PropertyDescriptor pDesc = getProp(props, propName);
            if (pDesc != null) {
                Method m = pDesc.getReadMethod();
                Object args[] = new String[1];
                log.fine("Llamo a " + m.getName() + "()"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Object ret=m.invoke(bean,(Object[]) null);
                return ret;
            }
            //prop.
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * Pone un conjunto de propiedades a un Bean
     * @param propiedadades  ArrayList de ModelProperties
     * @param bean     Objeto a modificar
     */
    public static void setPropiedades(ArrayList propiedades, Object bean) {
        if (bean == null)
            return;
        try {
            Class c = bean.getClass();
            BeanInfo info = Introspector.getBeanInfo(c);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            Iterator it = propiedades.iterator();
            while (it.hasNext()) {
                ModelProperty prop = (ModelProperty) it.next();
                PropertyDescriptor pDesc = getProp(props, prop.getName());
                if (pDesc != null) {
                    Method m = pDesc.getWriteMethod();
                    Object args[] = new String[1];
                    args[0] = prop.getValue();
                    log.fine("Llamo a " + m.getName() + "(" + prop.getValue() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    m.invoke(bean, args);
                }
                //prop.
            }
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        
    }
    
    /**
     * Busca el descriptor de la propiedad pedida entre la lista pasada.
     * @param props  Array de propiedades de una clase.
     * @param name  Nombre de la propiedad dque se est� buscando.
     */
    private static PropertyDescriptor getProp(PropertyDescriptor[] props, String name) {
        for (int i = 0; i < props.length; i++) {
            if (name.equals(props[i].getName())) {
                return props[i];
            }
        }
        // throw "No existe la propiedad name en la clase"
        return null;
    }
    
    public static Object executeOperation(Context ctx, Operation op) {
        try{            
            Object bean=ctx.getBean(op.getBeanName());
            if(bean==null){
                throw new IllegalArgumentException("No tengo en el contexto el bean con nombre:["+op.getBeanName()+"]");
            }
//          // Compatibilidad Servicio   (trapicheo, porque Servicio no esta presente)
            
            // OjO a este cambio....
            // El contexto cambia cada vez que se llama a un bean
            if(bean instanceof MService){
                MService s=(MService)bean;
                s.setContext(ctx);
            }
            if(bean instanceof Servicio){  // Compatibilidad con lo antiguo
                Servicio s=(Servicio)bean;
                s.setContext(new Contexto(ctx));
            }
            if (bean!=null){
            	Class c=bean.getClass();            
                String mName=op.getNMethod();
                Object []args=op.getParamValues(ctx);
                Method m=c.getMethod(mName,op.getParamTypes());
                Object res=m.invoke(bean, args);
                if(op.varResult()!=null){
                    ctx.put(op.varResult(), res);
                }
                return res;
            }
                   
        }catch(NoSuchMethodException ex){
            ex.printStackTrace();
        }catch(IllegalAccessException ex){
            ex.printStackTrace();
        }catch(InvocationTargetException ex){
            ex.printStackTrace();
        }
        return null;
    }
    private static void error(String msg,Throwable ex){
    	log.severe(msg+":"+ex.getLocalizedMessage());
    }
}
