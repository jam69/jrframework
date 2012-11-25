
package com.jrsolutions.framework.core.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Modeliza un Panel (un fragmento visible) de las aplicaciones. 
 */
public class Panel implements ModelEntity,HasModelProperties {
    
    private String name;
    private String label;
    private ArrayList<Panel> panels=new ArrayList<Panel>();
    private String type;
    private ArrayList<ModelProperty> properties=new ArrayList<ModelProperty>();
    
    /** Creates a new instance of Panel */
    public Panel() {
    }

    /** Nombre del panel. */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Devuelve el titulo del panel.
     * Segun el tipo de panel se muestra o no.
     * 
     * <p>Incluso es posible que no se muestre en el panel pero si
     * en su padre (p.ej. un TabPanel (carpetas), muestra en cada
     * pesta�a el label de los paneles hijos.
     * 
     * @return
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /** Devuelve la lista de sub-paneles */
    public ArrayList<Panel> getPanels() {
        return panels;
    }

    public Panel getPanel(int i) { 
        return (Panel)panels.get(i);
    }
    
    public void setPanels(ArrayList<Panel> panels) {
        this.panels = panels;
    }

    public void addPanel(Panel p){
        getPanels().add(p);
    }
    

    /** Devuelve el tipo del panel.
     * <p>En el runnerSwing viejo coincid�a con la clase que lo implementaba,
     * ahora es simplemente un identificador, que cada runner mapea (transforma)
     * a sus propias clases o implementaciones.
     * 
     * @return
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setModelProperties(ArrayList<ModelProperty> props){
        properties=props;
    }
    public ArrayList<ModelProperty> getModelProperties(){
        return properties;
    }
    
    public void addModelProperty(ModelProperty mp){
        properties.add(mp);
    }
    
    public Object modelProperty(String str){
    Iterator<ModelProperty> it=properties.iterator();
        while(it.hasNext()){
            ModelProperty p=(ModelProperty)it.next();
            if(p.getName().equals(str)) return p.getValue();            
        }
        return null;
    }
    
    public String toString(){
    	return (type == null ? "" : type.substring(type.lastIndexOf(".")+1));
    }
    
  
}
