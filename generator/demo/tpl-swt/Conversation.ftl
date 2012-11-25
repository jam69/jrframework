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
 *   Fichero generado automáticamente:    NO EDITAR
 */
package app.${conversation.id};

import swtrunner.*;

public class ${conversation.id} extends Conversacion{

	private String name="${conversation.name}";
	private String id="${conversation.id}";
	
	
	public ${conversation.id}(){
		
	}
	
	public Window getInitWindow(Main app){
		return new ${conversation.initWindowName}(app);
	}
		
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name;
	}
		
}

