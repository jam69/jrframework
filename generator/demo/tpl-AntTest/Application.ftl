<#--   Genera el MIDlet de esta aplicacion -->
/*
 *
 *    Fichero generado automaticamente - NO EDITAR
 *
 */
package app;
 
import es.indra.modeler.runnerme.*;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class ${app.id} extends MIDlet implements CommandListener {

	private static ${app.id} instance;
  
  	/** main MIDlet display */
    private Display display;
    
    /** Ventana inicial */
    private List initScreen;    
        
    /** Comands */
    Command exit=new Command("Salir",Command.EXIT,1);
  
	  <#list app.getConversations()  as conv>
    Command comand_${conv.id} = new Command("${conv.id}",Command.SCREEN,1);
	  </#list>   
	  

    
    /** Constructor de la aplicacion */
    public ${app.id}() {    	
    	display = Display.getDisplay(this);    	
    	instance = this;                
    }
    
    
    public static ${app.id} getInstance() {
        if (instance == null) {
            instance = new ${app.id}();
        }
        return instance;
    }
    
    /** Implementa el arranque de la aplicacion */
    public void startApp() {
        if (display.getCurrent() == null) {
            // startApp called for the first time
            showInitScreen();
        } else {
           
        }
    }

    /** Implementa la pausa de la aplicacion */
    public void pauseApp() {
        // app.disable();
    }

	/** Implementa la finalizacion de la aplicacion */
    public void destroyApp(boolean unconditional) {
        // app.cleanup();
        notifyDestroyed();
    }
    
    /** Bucle principal de la aplicacion */
    public void commandAction(Command c, Displayable s) {
        if (c == List.SELECT_COMMAND){
    		switch(initScreen.getSelectedIndex()){ //opcion del menu    
	  <#list app.getConversations()  as conv>
	  		case ${conv_index}:
	  			display.setCurrent(new app.${conv.id}.${conv.initWindowName}(new ConvState(display)));
	  		    break;
      </#list>
    		}
    	}
    	if (c == exit){
        	// Fin app
        	notifyDestroyed();        	
   	    } 
    	
	 }

    /** Muestra la ventana inicial */
	public void showInitScreen(){
	    if (initScreen == null) {
	    		initScreen = new List("Elige Conversacion",List.IMPLICIT);
	    		<#list app.getConversations()  as conv>
	    		initScreen.insert(0,"${conv.name}",null);
	    		</#list>
	    		initScreen.addCommand(exit);
	    		initScreen.setCommandListener(this);
	    	}
	   	display.setCurrent(initScreen);
	 }	 

	public static void showInit(){
	   	 ${app.id} aux=getInstance();
	   	 aux.showInitScreen();
	   	 }
}		
