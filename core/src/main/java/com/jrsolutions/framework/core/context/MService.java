/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.context;

/**
 * ModelerService: No se recomienda su uso.
 * 
 * Lo implementa los beans que acceden al contexto.
 * No se deber�an implementar por las aplicaciones cliente.
 * (S�lo se implementa por compatibilidad)
 *
 * @see Context
 */
public interface MService {

    /** Inyecta el contexto en el Objeto */
    public void setContext(Context ctx);
    
    /** Ejecuta el m�todo por defecto */
    public Object execute();
}
