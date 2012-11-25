
package com.jrsolutions.framework.core.metamodel;

import java.util.List;

/**
 * Descripcion de una Clase o Tipo
 */
public interface MetaEntity {

	/** Nombre o identificador de esta clase. */
    public String getName();
    
    /** Nombre bonito de esta clase (lo que aparece como titulo en los formularios). */
    public String getDisplayName();
    
    /** Lista de atributos que contiene esta clase. 
     *  (devuelve los mismos que {@link #getAttributesA()} */
    public List<MetaAttribute> getAttributes();
    
    /** Array de atributos de esta clase.
     *  (devuelve los mismos que {@link #getAttributes()} */
    public MetaAttribute[] getAttributesA();
    
    /** Devuelve la descripcion del atributo con nombre 'name'. */
    public MetaAttribute getAttribute(String name);
    
    /** Devuelve una lista de los nombres de los atributos separados por comas. */
    public String getAttributeNames();
    
    /** Crea un nuevo objeto de esta clase. */
    public Object newInstance();
    
}
