/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.expressions;

/**
 * Expresion evaluable sobre el contexto
 */
public interface Expression {

    Object eval(Object ctx);
}
