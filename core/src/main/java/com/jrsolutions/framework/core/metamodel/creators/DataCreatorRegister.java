package com.jrsolutions.framework.core.metamodel.creators;

import com.jrsolutions.framework.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Mantiene un registro de los generadores de datos aleatorios.
 * 
 * Los generadores est�n asociados a los tipos b�sicos y a ciertas palabras
 * clave, pej. "Apellidos", "Nombres", etc,....
 * 
 * Los generadores siempre dan el mismo tipo, y hemos registrado constructores
 * con los tipos b�sicos, as� que se puede utilizar el nombre de la clase como
 * constructor.
 * 
 * @see DataCreator
 * 
 */
public class DataCreatorRegister {

	private static Map<String, Class<? extends DataCreator>> creators = new HashMap<String, Class<? extends DataCreator>>();

	static {
		creators.put("int", IntegerCreator.class);
		creators.put("java.lang.int", IntegerCreator.class);
		creators.put("Integer", IntegerCreator.class);
		creators.put("java.lang.Integer", IntegerCreator.class);

		creators.put("short", ShortCreator.class);
		creators.put("java.lang.short", ShortCreator.class);
		creators.put("Short", ShortCreator.class);
		creators.put("java.lang.Short", ShortCreator.class);

		creators.put("long", LongCreator.class);
		creators.put("java.lang.long", LongCreator.class);
		creators.put("Long", LongCreator.class);
		creators.put("java.lang.Long", LongCreator.class);

		creators.put("float", FloatCreator.class);
		creators.put("java.lang.float", FloatCreator.class);
		creators.put("Float", FloatCreator.class);
		creators.put("java.lang.Float", FloatCreator.class);

		creators.put("double", DoubleCreator.class);
		creators.put("java.lang.double", DoubleCreator.class);
		creators.put("Double", DoubleCreator.class);
		creators.put("java.lang.Double", DoubleCreator.class);

		creators.put("boolean", BooleanCreator.class);
		creators.put("java.lang.boolean", BooleanCreator.class);
		creators.put("Boolean", BooleanCreator.class);
		creators.put("java.lang.Boolean", BooleanCreator.class);

		creators.put("Date", DateCreator.class);
		creators.put("java.util.Date", DateCreator.class);
		creators.put("java.sql.Date", DateCreator.class);

		creators.put("String", StringCreator.class);
		creators.put("java.lang.String", StringCreator.class);

		creators.put("StringCaps", StringCapsCreator.class);
		creators.put("Apellidos", ApellidosCreator.class);
		creators.put("Nombres", NombresCreator.class);
		creators.put("Provincias", ProvinciasCreator.class);
		creators.put("Lista", ListaCreator.class);
		creators.put("DNI", DNICreator.class);
		creators.put("IntegerSecuencial", IntegerSecuencialCreator.class);
	}

	/**
	 * Permite a�adir nuevos creators a la f�brica.
	 * 
	 * @param type Nombre del creador
	 * @param c    Clase que lo implementa
	 */
	static public void register(String type,Class<? extends DataCreator> c) {
		creators.put(type,c);
	}

	/**
	 * Construye un objeto del tipo indicado, con los parametros indicados
	 * p.ej.  create("String","String(10)");
	 * Si param es != nulo, el parametro type se ignora.
	 */
	static public Object create(String type,String param) {		
		if(param!=null){
			String str=StringUtils.command(param);
			String[] p=StringUtils.parameters(param);
			return create(str,p);						
		}else{
			return create(type);
		}
	}	
	
	/**
	 * Crea un objeto, con el constructor indicado y sin parametros.
	 * @param str Nombre del constructor
	 * @return El objeto creado
	 */
	static public Object create(String str) {
		try {
			Class<?> c = creators.get(str);  // o bien Class<? extends DataCreator>
			if (c != null) {
				DataCreator dc = (DataCreator) c.newInstance();
				return dc.create(null);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * Crea un objeto con el constructor indicado y los parametros que se indican.
	 * 
	 * @param str  Nombre del constructor
	 * @param params  Parametros para el constructor
	 * @return
	 */
	static public Object create(String str, String[] params) {
		Class<? extends DataCreator> c = creators.get(str);
		if (c != null) {
			try {
				DataCreator dc = c.newInstance();
				return dc.create(params);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
