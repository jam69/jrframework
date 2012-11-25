/*
 * Conversation.java
 *
 * Created on 26 de noviembre de 2007, 18:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Modeliza una Conversacion (o Caso de Uso) de la aplicacion
 *
 */
public class Conversation implements ModelEntity {
    
    private String id;
    private String name;
    private String initWindowName;
    private String roles;
    private ArrayList<Window> windows=new ArrayList<Window>(); //<Window>
    
    /** Creates a new instance of Conversation */
    public Conversation() {
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
    /** Devuelve el nombre de la conversacion (puede contener blancos, etc,..) */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Devuelve el nombre de la ventana inicial de la conversacion */
    public String getInitWindowName() {
        return initWindowName;
    }

    public void setInitWindowName(String initWindowName) {
        this.initWindowName = initWindowName;
    }

    /** Devuelve los Roles asociados a esta conversacion. */
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    /** Devuelve las ventanas de esta conversacion.  */
    public ArrayList<Window> getWindows() { //<Window>
        return windows;
    }

    public void setWindows(ArrayList<Window> windows) { //<Window>
        this.windows = windows;
    }
    
    /** Devuelve la ventana con el id indicado */
    public Window getWindow(String id){
        //for(Window w : windows ){
        Iterator<Window> it=getWindows().iterator();
        while(it.hasNext()){
            Window b=(Window)it.next();
            if(b.getId().equals(id))return b;
        }
        return null;
    }
    
    public void addWindow(Window w){
        windows.add(w);
    }

    
    
    public String toString(){
        return name;
    }
}
