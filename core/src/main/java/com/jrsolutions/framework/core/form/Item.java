
package com.jrsolutions.framework.core.form;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Simplemente contiene la informaci�n de lo que hay dentro de una celda
 * en un formulario.
 * Es simplemente un identificador (el tipo de componente) y un conjunto
 * de propiedades (que dependen del tipo de componente).
 * No hay ninguna comprobaci�n sobre si el tipo es conocido o no, o sobre
 * si las propiedades son validas o no.
 * Es simplemente la informaci�n obtenida del XML
 * 
 * @see ParserForm
 * 
 */
public class Item implements Serializable {

    public String type;
    
    private Map<String,String> map = new HashMap<String,String>();

    /** Creates a new instance of Item */
    public Item() {
    }

    /** Devuelve el valor de la propiedad 'key' del item.
     *  (igual que {@link #prop(String)} )
     * @param key Nombre de la propiedad
     * @return Null si no est� definida la propiedad
     */
    public String getProp(String key) {
        return (String) map.get(key);
    }
   
    /**  
     * Devuelve el valor de la propiedad 'key' del item.
     * (igual que {@link #getProps()} )
    * @param key Nombre de la propiedad
    * @return Null si no est� definida la propiedad
    */   
    public String prop(String key) {
        return (String) map.get(key);
    }

    public void setProp(String key, String value) {
        map.put(key, value);
    }
    
    /** Retorna el tipo de Item */
    public String getType(){
    	return type;
    }
    public void setType(String t){
    	type=t;
    }
    /** Devuelve todas las propiedades */
    public Map<String,String> getProps(){
    	return map;
    }
    
}
