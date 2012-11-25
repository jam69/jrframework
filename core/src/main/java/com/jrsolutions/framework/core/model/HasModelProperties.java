
package com.jrsolutions.framework.core.model;

import java.util.ArrayList;

/**
 * Marca las entidades del Modelo que tienen Propiedades (son Customizables)
 */
public interface HasModelProperties {
    
    public ArrayList<ModelProperty> getModelProperties(); 
    public void setModelProperties(ArrayList<ModelProperty> props);
    public void addModelProperty(ModelProperty p);
    public Object modelProperty(String key);
    
}
