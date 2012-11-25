
package com.jrsolutions.framework.core.form;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa un Formulario.
 * No tiene apenas funcionalidad, simplemente es el resultado de leer el XML que
 * describe un formulario.
 * S�lo contiene una collecci�n de celdas, y su posicion en filas y columnas.
 * 
 * @see Cell
 * @see ParserForm
 */
public class Form implements Serializable {

    private String rows;
    private String columns;
    private ArrayList<Cell> cells = new ArrayList<Cell>();

    /** Creates a new instance of Form */
    public Form() {
    }

    public void addCell(Cell c) {
        cells.add(c);
    }

    /** Devuelve la lista de celdas de este formulario. */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /** Devuelve el String que define las filas. */
    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    /** Devuelve el String que define las columnas */
    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }
}
