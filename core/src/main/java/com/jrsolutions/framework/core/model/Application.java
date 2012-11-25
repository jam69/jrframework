
package com.jrsolutions.framework.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Modelo de Aplicacion.
 * 
 */
public class Application implements ModelEntity {

    private String id;
    private String name;
    private List<Conversation> conversations = new ArrayList<Conversation>(); //<Conversation>
    private List<Window> windows = new ArrayList<Window>(); //<Window>

    /** Creates a new instance of Application */
    public Application() {
    }

    /** Nombre de la aplicacion */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Devuelve la lista de todas las conversaciones. */
    public List<Conversation> getConversations() { //<Conversation>
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) { //<Conversation>
        this.conversations = conversations;
    }

    /** Devuelve la lista de ventanas (fuera-conversaciones) */     
    public List<Window> getWindows() { //<Window>
        return windows;
    }

    public void setWindows(List<Window> windows) { //<Window>
        this.windows = windows;
    }

    /** Devuelve la ventana con el id indicado (de las de fuera-conversaciones) */
    public Window getWindow(String id) {
        //for(Window w : windows ){
        Iterator<Window> it = getWindows().iterator();
        while (it.hasNext()) {
            Window b = (Window) it.next();
            if (b.getName().equals(id)) {
                return b;
            }
        }
        return null;
    }

    /** Devuelve la conversacion con el id indicado. */
    public Conversation getConversation(String id) {
        Iterator<Conversation> it = getConversations().iterator();
        while (it.hasNext()) {
            Conversation b = (Conversation) it.next();
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public void addWindow(Window w) {
        windows.add(w);
    }

    public void addConversation(Conversation c) {
        conversations.add(c);
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
    
    public String toString(){
    	return getName();
    }
}
