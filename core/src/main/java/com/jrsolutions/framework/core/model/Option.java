

package com.jrsolutions.framework.core.model;


/**
 * Representa cada una de las opciones de una operaci�n.
 * 
 */
public class Option implements ModelEntity {
    
	public static final String BACK = "BACK";
	public static final String FIRST = "FIRST";
	public static final String CLOSE = "CLOSE";
	
    private String dest;
    private String dialog;
    private String result;
    
    /** Creates a new instance of Option */
    public Option() {
    }

    /** Devuelve el nombre de la ventana, o caso de uso al cual navegar. */
    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    /** Devuelve el nombre del dialogo al cual navegar.
     * 
     * Si 'getDest()' no es nulo, este valor es in�til.
     * @return
     */
    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    /** String a comparar con el resultado de la operacion.
     * TODO: �Usar String2Object?
     * @return
     */
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
}
