/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrsolutions.framework.core.metamodel.intro;

import com.jrsolutions.framework.core.metamodel.MetaEntity;
import com.jrsolutions.framework.core.metamodel.MetaInfoFactory;
import java.beans.IntrospectionException;


/**
 *
 * Obtiene la MetaInformaci�n de una clase a partir de Introspecci�n.
 * 
 * @see IntroInfoFactory
 * 
 */
public class IntroInfoFactory implements MetaInfoFactory {

    public MetaEntity getTypeInfo(String className) {
        try {
            return new IntroInfoEntity(className);
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
           // System.out.println("ERROR: no existe la clase " + ex.getMessage() + ".class");
            return null;
        }catch (IntrospectionException ex) {
            //ex.printStackTrace();
            //System.out.println("ERROR: puedo instanciar la clase " + ex.getMessage() + ".class");
            return null;
        }
    }
}
