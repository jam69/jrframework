
package com.jrsolutions.framework.core.context;

/**
 * Interfaz de los Listeners sobre datos del contexto.
 */
public interface ContextListener {
    
    /** Metodo a ejecutar cuando cambia la variable 'key'. */
    public void onChange(String key,Object value);
    
}
