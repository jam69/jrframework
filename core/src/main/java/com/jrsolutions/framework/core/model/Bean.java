/*
 * Bean.java
 *
 * Created on 26 de noviembre de 2007, 18:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Modelo de un Bean de la aplicaci�n. (Un objeto que lanza m�todos)
 */
public class Bean implements ModelEntity,HasModelProperties  {
    
    private String name;
    private String className;
    private ArrayList<ModelProperty> properties=new ArrayList<ModelProperty>(); //<ModelProperty>
    
    private String value;
    private String initMethod;
    private String destroyMethod;
    
    public Bean() {
    }

    /** Devuelve el identificador del Bean */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Devuelve la clase del objeto que representa. */
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /** Obtiene las propiedades del modelo, que configuran este Bean. */
    public ArrayList<ModelProperty> getModelProperties() { //<ModelProperty>
        return properties;
    }

    public void setModelProperties(ArrayList<ModelProperty> properties) { //<ModelProperty>
        this.properties = properties;
    }
    
    public void addModelProperty(ModelProperty m){
        properties.add(m);
    }
    
    /** Obtiene la propiedad del modelo con el nombre indicado. */
    public Object modelProperty(String str){
    Iterator<ModelProperty> it=properties.iterator();
        while(it.hasNext()){
            ModelProperty p=(ModelProperty)it.next();
            if(p.getName().equals(str)) return p.getValue();            
        }
        return null;
    }
    
    /** Devuelve el valor de inicializaci�n del Bean (Si es una constante) */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /** 
     * Devuelve el m�todo inicial. 
     * El m�todo inicial se llama despues del constructor sin parametros.
     * Primero se busca el m�todo con el nombre y un parametro de tipo Contexto.
     * Si no lo encuentra busca el m�todo con el nombre y sin parametros.
     * Si ha encontrado un m�todo lo lanza.
     * 
     * @return
     */
    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    /** Devuelve el m�todo de finalizaci�n del bean
     * Se ejecuta al destruir la ventana en la que se cre�.
     * 
     * Primero se busca el m�todo con el nombre y un parametro de tipo Contexto.
     * Si no lo encuentra busca el m�todo con el nombre y sin parametros.
     * Si ha encontrado un m�todo lo lanza.
     * 
     * @return
     */
    public String getDestroyMethod() {
        return destroyMethod;
    }

    public void setDestroyMethod(String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }
    
}
