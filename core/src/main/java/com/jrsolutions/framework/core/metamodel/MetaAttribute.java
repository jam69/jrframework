

package com.jrsolutions.framework.core.metamodel;

/**
 * Descripcion de un Atributo
 */
public interface MetaAttribute {

	/** Nombre o identificador del atributo */
    public String getName();
    
    /** Nombre bonito del atributo (el que sale en los formularios) */
    public String getDisplayName();
    
    /** Nombre de la clase o tipo a la que pertenece su valor. */
    public String getTypeName();
    
    /** Posicion en el formulario. */    
    public int getOrden();
    
    /** String que define el editor por defecto para este atributo.   */
    public String getEditor();
    
    /** Indica si este atributo se debe mostrar en los formularios. */
    public boolean isHidden();
    
    /** Indica si este atributo puede tener valor nulo. */
    public boolean isNullable();
    
    /** Metodo para obtener el valor de esta propiedad de un objeto. */
    public Object getValue(Object obj);
    
    /** Metodo para cambiar el valor de esta propiedad en un objeto. */
    public void setValue(Object obj,Object value);
    
}
