
package com.jrsolutions.framework.core.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * Modeliza una ventana de la aplicaci�n (cada estado de una conversaci�n).
 * 
 */
public class Window implements ModelEntity {
    
    private String id;
    private String name;
    private Panel panel;
    private ArrayList<Bean> beans=new ArrayList<Bean>();
    private ArrayList<Operation> operations=new ArrayList<Operation>();
    private ArrayList<Dialog> dialogs=new ArrayList<Dialog>();
    
    /** Creates a new instance of Window */
    public Window() {
    }
    
    /**
     * Identificador de la aplicaci�n.
     * No puede contener blancos ni signos raros, no puede empezar por n�mero,...
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    /** Nombre elegante de la ventana. */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Devuelve el panel de esta ventana.
     * 
     * <p>Solo hay un panel, que a su vez puede tener hijos,....
     * 
     * @return
     */
    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    /** Devuelve la lista de Beans de esta ventana */
    public ArrayList<Bean> getBeans() {
        return beans;
    }

    public void setBeans(ArrayList<Bean> beans) {
        this.beans = beans;
    }

    /** Devuelve la lista de operaciones de esta ventana. */
    public ArrayList<Operation> getOperations() { 
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    /** Devuelve la lista de dialogos de esta ventana. */
    public ArrayList<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(ArrayList<Dialog> dialogs) {
        this.dialogs = dialogs;
    }
    
    /** Devuelve el Bean con el id indicado */
    public Bean getBean(String id){
        Iterator<Bean> it=getBeans().iterator();
        while(it.hasNext()){
            Bean b=(Bean)it.next();
            if(b.getName().equals(id))return b;
        }
        return null;
    }

    /** Devuelve el Dialogo con el id indicado */
    public Dialog getDialog(String id){
        Iterator<Dialog> it=getDialogs().iterator();
        while(it.hasNext()){
            Dialog d=(Dialog)it.next();
            if(d.getName().equals(id))return d;
        }
        return null;
    }
    
    /** Devuelve la operacion con el id indicado */
    public Operation getOperation(String id){
        //for(Operation op:operations){
        Iterator<Operation> it=getOperations().iterator();
        while(it.hasNext()){
            Operation op=(Operation)it.next();
            if(op.getName().equals(id))return op;
        }
        return null;
    }
    
    public void addBean(Bean b){
        beans.add(b);
    }
    
    public void addOperation(Operation op){
        operations.add(op);
    }
    
    public void addDialog(Dialog d){
        dialogs.add(d);
    }
   
}
