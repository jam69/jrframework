<#--
    Plantilla de Clases de Ventana
    se llama desde la plantilla  MainGenerate.render(Conversation,Ventana,...)
    Recibe las  variables:
         	"window":		Ventana a convertir en Clase
            "conversation":	Conversacion a la que pertenece
            "appName":		Nombre de la Aplicacion ??
            "conv":			Lista de conversaciones ??
            "definitions"   Metodo que parsea las Definiciones (tablas)
            "defs"			Metodo que parsea las definiciones (vertiacal/horizontal panel)
            "stripHTML" 	Metodo que elimina el HTML ??
            "genID"			Metodo que genera ID únicos ??
            "escape"		Metodo que quotea las " en un texto
            "toJava"		Metodo que Javaiza un nombre de fichero
    Notas:
    	Llama a las Plantillas del directorio 'panels'
-->   

/**
 *   Fichero generado automaticamente  NO EDITAR
 */

package app.${conversation.id};


import org.eclipse.swt.SWT;
import org.eclipse.jface.action.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;

import swtrunner.*;

public class ${window.id} extends Window {

	private Main app;
	private Contexto ctx;

	
	
	// Beans
	<#list window.getBeans() as bean >
	${bean.className} ${bean.name}=new ${bean.className}();
    </#list>

	// Dialogs
	<#list window.getDialogs() as dialog >
		Dialog ${dialog.nombre};
    </#list>
    
    public ${window.id}(Main m) {
		this.app = m;
		<#list window.getDialogs() as dialog >
		<#include "Dialog.ftl" parse=true/>
    	</#list>
	}
	
	public Menu getMenu(final Contexto ctx,Shell shell){
	    Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		MenuItem operacionItem = new MenuItem (bar, SWT.CASCADE);
		operacionItem.setText ("Operaciones");
		Menu submenu = new Menu (shell, SWT.DROP_DOWN);
		operacionItem.setMenu (submenu);
			
		MenuItem item;	
		<#list window.getOperations() as operation >
			<#if operation.activation = "TOOLBAR" >
			 item = new MenuItem (submenu, SWT.PUSH);
			 item.addListener (SWT.Selection, new Listener () {
				public void handleEvent (Event e) {
					System.out.println ("Ejecutar Operacion: ${operation.label}");
					<#if operation.beanName ?? >
					${operation.beanName}.${operation.method!"execute()"}; // faltan los parametros
					/* ctx.put("resultado",res); */
					</#if>
					
					<#list operation.options as option >
						<#if option.result ?? >
						Object result = ctx.get(Contexto.RESULT);
						if (result.equals(${option.value})) {
						</#if>
			   			<#if option.dest ?? >
			    			<#if option.dest == "CLOSE" >
			    				// CLOSE
			    			<#else>							
								app.navega(new ${option.dest}(app),ctx);
							</#if>				
			   			</#if>
			   			<#if option.dialog ?? >											
						showDialog(${option.dialog});
			   			</#if>
					<#if option.result ?? >	
					}
					</#if>
					</#list>	
		
				}
			});
			item.setText ("${operation.label}");
			//item.setAccelerator (SWT.MOD1 + 'A');		
			</#if>
			
		</#list>
		return bar;
	}
	
	public Composite getPanel(Contexto ctx,Composite shell) {
	    final Composite composite=new Composite(shell,SWT.NONE);
		this.ctx=ctx;				
		<#assign panel = window.getPanel()/>
		<#assign id = "panel" />
		<#include "panels/"+window.getPanel()+".ftl" parse=true/> 
		
		// onInit
		//bean2.test();
		
	    //composite.setLayout(panel);
		return composite;
	}
		
	
	private void showDialog(Dialog d){		
		//d.setVisible(true);
		}

<#--

	public Action[] getActions(){
		return actions;
	}
	
	
	// Actions
	private Action[] actions = {
	
    <#list window.getOperations() as operation >
    new ActionOperation("${operation.label}") {   // ${operation}
		public void actionPerformed(ActionEvent ev) {
		<#if operation.beanName ?? >
			${operation.beanName}.${operation.method!"execute()"}; // faltan los parametros
			/* ctx.put("resultado",res); */
		</#if>	

        <#list operation.options as option >
			// op1
			<#if option.result ?? >
			Object result = ctx.get(Contexto.RESULT);
			if (result.equals(${option.value})) {
			</#if>
			   <#if option.dest ?? >
			    <#if option.dest == "CLOSE" >
			    // CLOSE
			    <#else>							
				app.navega(new ${option.dest}(app),ctx);
				</#if>				
			   </#if>
			   <#if option.dialog ?? >											
				showDialog(${option.dialog});
			   </#if>
			<#if option.result ?? >	
			}
			</#if>
		</#list>	
		}
	} <#if operation_has_next>,</#if>
    </#list>

	};
-->
}
