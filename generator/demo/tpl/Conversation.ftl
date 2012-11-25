<#--
    Plantilla de Clases de Conversacion
    se llama desde la plantilla  MainGenerate.render(Conversation,...)
    Recibe las  variables:
            "conversation":	Conversacion a la que pertenece
            "definitions"   Metodo que parsea las Definiciones (tablas) ??
            "defs"			Metodo que parsea las definiciones (vertiacal/horizontal panel)??
            "stripHTML" 	Metodo que elimina el HTML ??
            "genID"			Metodo que genera ID únicos ??
            "escape"		Metodo que quotea las " en un texto
            "toJava"		Metodo que Javaiza un nombre de fichero
    Notas:
    	
-->   
/*
 *
 *   Fichero generado automáticamente:    NO EDITAR
 */
package app.${conversation.id};

import swingrunner.Conversacion;
import swingrunner.Main;
import swingrunner.Window;

/**
 * Clase que implementa la conversacion: ${conversation.id}
 */
public class ${conversation.id} extends Conversacion{

<#if conversation.roles??>
	private static final String roles="${conversation.roles}";
<#else>
	private static final String roles=null;
</#if>
	
	private static final String name="${conversation.name}";
	private static final String id="${conversation.id}";

	/**
	 * Constructor sin parametros.
	 */
	public ${conversation.id}(){
	}
	
	/** 
	 * Obtiene la ventana Inicial de esta conversacion
	 */
	public Window getInitWindow(Main app){
		return new ${conversation.initWindowName?replace(" ","")}(app);
	}
		
	
	public String toString(){
		return name;
	}	
		
	public String getRoles(){
		return roles;
	}
	
	public String getId(){
		return id;
	}	
	
	public String getTitle(){
		return name;
	}
}

