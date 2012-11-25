package com.jrsolutions.framework.core;

/**
 * Son las clases que ejecutan funciones de negocio.
 *
 * 
 *  
 */
public interface Servicio {
    
    public void execute();
    public void setContext(Contexto ctx);
    
}
