package com.jrsolutions.framework.core.metamodel.creators;

/**
 *  Genera datos (normalmente aleatorios) para los prototipos y las pruebas.
 *  
 *  @see DataCreatorRegister
 *  
 */
	public interface DataCreator{
		/**
		 * Genera un dato seg�n el parametro que se le pasa.
		 * @param par Parametro que configura el dato (acepta null)
		 * @return un objeto distinto cada vez (normalmente)
		 */
		public Object create(String [] par);
	}

