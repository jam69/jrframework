package com.jrsolutions.framework.core.metamodel;

import java.io.Serializable;
import java.util.Collection;

/**
 * Es una entidad formada por atributos.
 * 
 *
 */
public interface Entity extends Cloneable, Serializable{

	public Collection<String> getKeys();
	public Object clone(); // el clone como lo quieran implementar, como copia por referencia o como una copia por valor. 
						  //Se ha implmentando en EntityHash y en EntityArray como una copia por valor
	public void setProperty(String name,Object value);
	public Object getProperty(String name);
	public String getTypeName();

}
