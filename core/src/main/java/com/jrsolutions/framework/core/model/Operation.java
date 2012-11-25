
package com.jrsolutions.framework.core.model;

import com.jrsolutions.framework.core.context.Context;
import com.jrsolutions.framework.core.model.tools.MethodDec;
import java.util.ArrayList;


/**
 * Modeliza una Operacion.
 */
public class Operation implements ModelEntity {
    
    public final static String VISIBLE_SIEMPRE="SIEMPRE";
    public final static String VISIBLE_NUNCA="NUNCA";
    public final static String INIT="INIT";
    public final static String ONVAR="ONVAR";
    public final static String TOOLBAR="TOOLBAR";
    public final static String BUTTON="BUTTON";
    public final static String ONEXCEP="ONEXCEP";
    public final static String EXIT="ONEXIT";
    public final static String RESULT="result";

    private String name;
    private String label;
    private String beanName;
    private String method;
    private String activation;
    private String roles;
    private String visibility;
    private String warning;
    private String message;       
    private ArrayList<Option>  options=new ArrayList<Option>(); //<Option>
    private transient MethodDec methodDesc;
    
    /** Creates a new instance of Operation */
    public Operation() {
    }

    /** Devuelve el nombre de la operacion */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Devuelve el nombre del Bean que ejecutar� la operaci�n.
     * Puede estar en blanco, y entonces navega con las opciones.
     * @return
     */
    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /** Devuelve el m�todo completo, tal como est� en el application.xml */
    public String getMethod() {
        return method;
    }

    // TODO �admite String2Object?
    public void setMethod(String method) {
        this.method = method;
        methodDesc=new MethodDec(method);
        if (method == null)
            method = methodDesc.getName(); // xa los scripts...
    }

    /** 
     * Devuelve el modo de activaci�n de esta operaci�n
     * Los metodos pueden ser:
     * <ul>
     * <li>"INIT"</li>
     * <li>"TOOLBAR"</li>
     * <li>"ONVAR" variable</li>
     * <li>"BUTTON" buttonID </li>
     * <li>"ONEXIT"</li>
     * </ul>
     * @return
     */
    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    /** Devuelve los Roles que pueden ejecutar esta operaci�n */
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    /** Devuelve la lista de opciones de esta operaci�n */
    public ArrayList<Option>  getOptions() { 
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }
    
    public void addOption(Option opt){
        options.add(opt);
    }

    /** Devuelve el mensaje de confirmaci�n que aparecer� antes
     * de lanzar la operaci�n. 
     * (Solo tiene sentido en las operaciones TOOLBAR y BUTTON)
     * @return
     */
    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    /** Devuelve el mensaje de resultado a mostrar.
     * En esta variable aparece el nombre de una variable.
     * Despues de ejecutar la operacion, se comprueba esta variable.
     * Si no existe o es nula no se hace nada.
     * Si tiene algun valor String se muestra como mensaje de informaci�n (solo con la
     * opci�n de continuar).
     * Adem�s si el primer caracter del String es 'E' se muestra con el icono de Error.
     * Si el primer caracter del String es 'W' se muestra con el icono de Aviso.
     * Si el primer caracter es 'I' ( o no es 'W' ni 'E' ) se muestra con el icono de Informaci�n.
     * (Naturalmente, la 'I','W' o 'E' no se muestran en el mensaje final). 
     * @return
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /** Devuelve el nombre bonito de la operacion.
     * 
     * <p>Es el texto que aparece en la Toolbar (o menu) si la activacion es TOOLBAR
     * En los otros casos no tiene sentido.
     * 
     * <p>Adem�s, se pueden definir sub-menus en la toolbar (o menubar), poniendo
     * en el t�tulo el caracter '/'. P.ej. las operaciones con t�tulo
     * "Clientes/Eliminar" y "Clientes/Editar" aparecer�an en un sub-menu con
     * texto "Clientes", del que tiene como opciones "Eliminar" y "Editar"
     * 
     * <p>Tambi�n se debe implementar los aceleradores. Es decir si el titulo
     * tiene el caracter '&' por ejemplo "Ac&tivar", mostrar�a el texto "Activar"
     * y activar�a un atajo sobre ALT-T que lanzar�a la operaci�n. (Si es posible
     * para el runner.
     *  
     * @return
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    /** Metodo de utilidad que dice si el boton est� en la Toolbar */
    public boolean visible(){    	 
    	String s=activation.trim().toUpperCase(); 
    	return s.equals(TOOLBAR);
//    	return !(s.equals(INIT)
//    		   || s.startsWith(BUTTON)
//    		   || s.startsWith(ONEXCEP)
//    	       || s.startsWith(ONVAR)
//    	       || s.startsWith(EXIT)
//    		   );
    }
     
    /** Metodo de utilidad que devuelve las clases de los parametros del m�todo */
     public Class<?>[] getParamTypes(){
         return methodDesc.getParamTypes();
     }
     
     /** Metodo de utilidad que devuelve la lista de valores de los parametros para
      * el m�todo.
      * 
      * @param ctx
      * @return
      */
     public Object[] getParamValues(Context ctx){
         if(methodDesc==null)setMethod(method);
         return methodDesc.getParamValues(ctx);
     }
     
     /** 
      * Devuelve el nombre de la variable donde se guardar� el resultado de la
      * operacion.
      * <p>En esta implementaci�n, para navegar, siempre se usa la variable
      * con nombre 'result' independiente mente de esta propiedad. Es posible
      * que cambiemos este comportamiento.
      *  
      * @return
      */
     public String varResult(){
         if(methodDesc==null)setMethod(method);
         return methodDesc.getRet();
     }
     
     /** Devuelve el nombre del m�todo (sin parametros, ni parentesis, ni resultado) */
     public String getNMethod(){
         if(methodDesc==null)setMethod(method);
         return methodDesc.getName();
     }

    
     /** Devuelve la visibilidad 
      * TODO: �esta propiedad es necesaria?
      * @return
      */
	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
     
}
