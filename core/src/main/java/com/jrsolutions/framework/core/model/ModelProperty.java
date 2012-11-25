
package com.jrsolutions.framework.core.model;

/**
 * Modeliza una propiedad de ciertas entidades del Modelo.
 * 
 * @see Panel
 * @see Bean
 */
public class ModelProperty implements ModelEntity{
    
    private String name;
    private Object value;
    
    /** Creates a new instance of ModelProperty */
    public ModelProperty() {
    }

    /** Devuelve el nombre de la propiedad */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Devuelve el valor de la propiedad 
     * 
     * TODO: �String2Object?
     * */
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
}
