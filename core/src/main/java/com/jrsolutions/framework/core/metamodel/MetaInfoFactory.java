/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.metamodel;

/**
 * Interfaz de los descriptores de clases.
 */
public interface MetaInfoFactory {
	
	/** Devuelve la informaci�n asociada a una clase o tipo. */
    public MetaEntity getTypeInfo(String className);
}
