/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.metamodel.intro;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Esta clase es un truco para mejorar la información de las clases.
 * La idea es usar la información de BeanInfo, pero en lugar de usar
 * PropertyDescritor, utilizar esta clase, que permite añadir mas
 * información.
 * <p>Al definir la clase'BeanInfo' para que el introspector la encuentre,
 * en lugar de generar PropertyDescriptor, ponemos esta clase. De este 
 * modo ampliamos la descripci�n de las propiedades.
 * 
 * 
 * @see BeanInfo
 * @see Introspector
 */
public class JRPropertyDescriptor extends PropertyDescriptor {
    
        private String editorString;
	private String helpText;
	private String toolTipText;
	private int orden;
	private boolean mandatory;
	private Object defaultValue;

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getEditorString() {
		return editorString;
	}
	
	public void setEditorString(String editorString) {
		this.editorString = editorString;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public String getToolTipText() {
		return toolTipText;
	}

	/**
	 * Información para el tooltip, admite HTML siempre que empieze
	 * con &lt;html&gt; y termine con &lt;/html&gt;
	 * 
	 * @param toolTipText
	 */
	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	public JRPropertyDescriptor(String arg0, Class arg1)
			throws IntrospectionException {
		super(arg0, arg1);
	}

	public JRPropertyDescriptor(String arg0, Class arg1, String arg2,
			String arg3) throws IntrospectionException {
		super(arg0, arg1, arg2, arg3);
	}

	public JRPropertyDescriptor(String propertyName, Method readMethod,
			Method writeMethod) throws IntrospectionException {
		super(propertyName, readMethod, writeMethod);
	}

	public JRPropertyDescriptor(int orden, String propertyName, Class clas,
			String label, String tooltip) throws IntrospectionException {
		this(propertyName, clas);
		setOrden(orden);
		setDisplayName(label);
		setToolTipText(tooltip);
	}

	public JRPropertyDescriptor(int orden, String propertyName, Class clas,
			String label, String tooltip, String readMethod, String writeMethod)
			throws IntrospectionException {
		this(propertyName, clas, readMethod, writeMethod);
		setOrden(orden);
		setDisplayName(label);
		setToolTipText(tooltip);
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
}