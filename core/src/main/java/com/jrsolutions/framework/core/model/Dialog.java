
package com.jrsolutions.framework.core.model;


/**
 * Representa a un Dialogo.
 * 
 */
public class Dialog implements ModelEntity {
    
    private String name;
    private String title;
    private Panel panel;
    private String dismissOperation;
    
    /** Creates a new instance of Dialog */
    public Dialog() {
    }

    /** Devuelve el id o nombre del di�logo. */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Devuelve el titulo del dialogo. */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    /** Devuelve el panel con la parte visual de este dialogo. */
    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    /** 
     * Devuelve el nombre de la operaci�n que se lanzar� al desaparecer
     * el dialogo.
     *  TODO: (el nombre es ONBUTTON?)
     */
    public String getDismissOperation() {
        return dismissOperation;
    }

    public void setDismissOperation(String dissmissOperation) {
        this.dismissOperation = dissmissOperation;
    }
    
}
