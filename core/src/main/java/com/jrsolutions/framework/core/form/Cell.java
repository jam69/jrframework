

package com.jrsolutions.framework.core.form;

import java.io.Serializable;

/**
 * Representaci�n de una celda en un formulario.
 * No tiene funcionalidad
 * 
 * @see Item
 * @see FormPanel
 * 
 */
public class Cell implements Serializable {
    
	public String hAligment;
    public String vAligment;
    public int col;
    public int row;
    public int colspan;
    public int rowspan;
    public String insets;
    //public int x,y,w,h; // Ya no se usan
    public Item item;
    
    /** Creates a new instance of Cell */
    public Cell() {
    }
    
    /** Alineacion horizontal
     *  valores:  LEFT,RIGHT,CENTER,FILL
     *  defecto:  
     */
    public String getHAligment() {
		return hAligment;
	}
	public void setHAligment(String aligment) {
		hAligment = aligment;
	}
	/** Alineacion Vertical
	 *  Valores: TOP,CENTER,BOTTOM,FILL
	 * @return
	 */
	public String getVAligment() {
		return vAligment;
	}
	public void setVAligment(String aligment) {
		vAligment = aligment;
	}
	
	/** Devuelve la columna */
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	/** Devuelve la fila */
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	/** Devuelve el numero de columnas que ocupa la celda */
	public int getColspan() {
		return colspan;
	}
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	/** Devuelve el numero de filas que ocupa la celda. */
	public int getRowspan() {
		return rowspan;
	}
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	/** Devuelve los margenes interiores de la celda. */
	public String getInsets() {
		return insets;
	}
	public void setInsets(String insets) {
		this.insets = insets;
	}
	/** Devuelve el item que est� en esta celda. */
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}

    
}
