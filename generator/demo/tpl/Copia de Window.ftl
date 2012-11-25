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

/*
 *
 *   Fichero generado automaticamente  NO EDITAR
 */

package app.${conversation.id};


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Logger;
import java.text.*;
import java.math.BigDecimal;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.border.EmptyBorder;

import com.jrsolutions.framework.ContextListener;
import com.jrsolutions.framework.Contexto;
import com.jrsolutions.framework.Servicio;

import es.indra.humandev.runner.core.utils.informationnode.InformationNode;
import es.indra.isl.humandev.swing.ConstrainedLayout;

import lightswingrunner.internacionalizer.GestorMultilingualidad;
import lightswingrunner.panels.*;
import lightswingrunner.editors.*;
import lightswingrunner.swing.tables2.*;
import lightswingrunner.swing.tables2.filtertable.*;
import lightswingrunner.swing.tables2.headertable.HeaderNode;
import lightlookandfeel.RunnerLF;

import swingrunner.ActionOperation;
import swingrunner.Main;
import swingrunner.Window;
import swingrunner.SwingUtils;
import swingrunner.swing.MiSelectionListener;

<#macro OPER operation >
		<#if operation.activation.equalsIgnoreCase("TOOLBAR")>    		
			<#if operation.visibility??>
	 			 <#assign visibility = operation.visibility >
	 		<#else>
	 		 	<#assign visibility = "" >
	 		</#if>
			<#if operation.roles??>
	 			<#assign roles = operation.roles >
	 		<#else>
	 			<#assign roles = "" >
	 		</#if>	 		
		new ActionOperation("${operation.label}", "${visibility?j_string}", "${roles}"){
		<#elseif operation.activation.startsWith("Onvar") || operation.activation.startsWith("OnVar") || 
    operation.activation.startsWith("onvar") || operation.activation.startsWith("ONVAR") ||
    operation.activation.startsWith("onVar")>
    	new ActionOperation("${operation.activation}"){
    	<#elseif operation.activation.equalsIgnoreCase("init")> 
    	new ActionOperation("${operation.name}") {   
    	<#elseif operation.activation.startsWith("Onvar") || operation.activation.startsWith("OnVar") || 
    operation.activation.startsWith("onvar") || operation.activation.startsWith("ONVAR") ||
    operation.activation.startsWith("onVar")>
    	new ActionOperation("${operation.activation}") {
    	<#elseif operation.activation.startsWith("Button") || operation.activation.startsWith("button") || 
    operation.activation.startsWith("BUTTON") >
    <#assign activ = operation.activation.substring(6).trim() >
    	new ActionOperation("${activ}") {
    	 <#elseif operation.activation.equalsIgnoreCase("OnExit")>
    	new ActionOperation("${operation.name}") {  
    	</#if>
    		@SuppressWarnings("cast")
			public void actionPerformed(ActionEvent ev) {
		<#if (operation.warning ?? ) && (operation.warning != "") >
				int res = 	JOptionPane.showConfirmDialog(null,
								GestorMultilingualidad.buscar("${operation.warning}"),
								"",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE,
								SwingUtils.QUESTION_ICON);
				if (res != JOptionPane.OK_OPTION){
					return;
				}
		</#if>	
		<#if operation.beanName ?? >
				if(${operation.beanName?uncap_first} instanceof Servicio){
					((Servicio)${operation.beanName?uncap_first}).setContext(ctx);
				}
			<#if operation.method??>
				try{
				<#assign method = methodUtil(operation.method) >
					// bean= ${operation.beanName}
					// method= ${operation.method!}
				<#if method.ret??>
					ctx.put("${method.ret}",
						${operation.beanName?uncap_first}.${method.name}(<#rt>
						<#list method.getParams() as p>
					   		<#if p.type=="CTX">
							(${p.className!})ctx.get("${p.name}")<#t>
							<#else>
							${p.valueToString()}<#t>
							</#if><#if p_has_next>,</#if><#t>
					<#lt></#list>)
					);
				<#else>
					${operation.beanName?uncap_first}.${method.name}(<#rt>
					<#list method.getParams() as p>
					   <#if p.type=="CTX">
						(${p.className!})ctx.get("${p.name}")<#t>
						<#else>
						${p.valueToString()}<#t>
						</#if><#if p_has_next>,</#if><#t>
					<#lt></#list>);
				</#if>	
				}catch(Exception ex){
					log.severe("Excepcion al ejecutar ${operation.method} sobre ${operation.beanName}"+ex.getLocalizedMessage());
				}
			<#else>
				${operation.beanName?uncap_first}.execute();
			</#if>
			<#if operation.message ??>
				<#assign needShowMessage = true>
				showMessage("${operation.message}");
    		</#if>
    	</#if>
        <#list operation.options as option >
			<#if option.result ?? >
				Object result = ctx.get("result");
				if (result!= null && result.equals("${option.result}")) {
			</#if>
			<#if option.dest ?? >
				<#if option.dest.equalsIgnoreCase("CLOSE") >
				    // CLOSE
				    app.closeConversation();
				<#elseif option.dest.equalsIgnoreCase("FIRST") >
					//first		
					app.firstWindowConversation("${conversation}",ctx);	    
				<#elseif option.dest.equalsIgnoreCase("BACK") >
					app.backWindow("${conversation}",ctx);			    
				<#elseif option.dest.startsWith("$")>
					app.navegaCasoUso("${option.dest?replace(" ", "")}");
				<#else>
					app.navega(new ${option.dest?replace(" ", "")}(app),ctx,Boolean.TRUE);
				</#if>				
			</#if>
		</#list>			
    	<#list operation.options as option>
			<#if option.dialog ?? >	
				show${option.dialog}();
			</#if>
		<#if option.result ?? >	
					}
		</#if>				
	</#list>
				}
			}
</#macro>

<#assign needShowMessage = false>
<#assign windowID= window.id?replace(" ","")>
/**
 *   Ventana ${window.id} de la conversacion ${conversation.id}
 */ 
public class ${windowID} extends Window {

    private static final Logger log=Logger.getLogger("${conversation.id}-${windowID}");

	private final transient Main app;
	private final static String WINDOW_ID ="${windowID}";
	private String name ="${window.name}";
	private transient Contexto ctx;
	
	
	//-------------- Beans -------------------------------------------
	<#list window.getBeans() as bean >
		<#if bean.className == "com.jrsolutions.introspect.components.beans.ScriptBean">
	private transient swingrunner.app.beans.ScriptBean ${bean.name?uncap_first};
		<#else>
	private transient ${bean.className} ${bean.name?uncap_first};
		</#if>
    </#list>

	// Dialogs	
	<#list window.getDialogs() as dialog >
	private	JDialog ${dialog.name};
	</#list>
	
	/**
	 * Constructor de la ventana.
	 * @parameter mainWindow Ventana principal de la aplicacion
	 */
    public ${windowID}(final Main mainWindow) {
    	super();
		this.app = mainWindow;

		// Inicializa beans 
		<#list window.getBeans() as bean >
		<#if bean.className == "com.jrsolutions.introspect.components.beans.ScriptBean">
		${bean.name?uncap_first}=new swingrunner.app.beans.ScriptBean();
		<#else>
		try{
			${bean.name?uncap_first}=new ${bean.className}();
		}catch(Exception ex){
			ex.printStackTrace();
			log.severe(ex.getLocalizedMessage());
		}
		</#if>
			<#list bean.modelProperties as prop>
		${bean.name?uncap_first}.set${prop.name?capitalize}("${escape(prop.value)}");
			</#list>
    	</#list>

	}
	
	//----------- Creación de paneles --------------------------------

	public JComponent getPanel(Contexto ctx2) {
		this.ctx=ctx2;	
		setName(name);
		JComponent panel=getPanel();		
		Dimension dim = new Dimension(
			app.getWorkpaneDimension().width-30,
			app.getWorkpaneDimension().height-30
			);
		panel.setPreferredSize(dim);
		panel.setOpaque(false);
		return panel;
	}
		
	
<#macro recorre panel nombre>

	<#-- Construimos un panel (como antes pero sin recursividad) -->
	/**
	 * Crea el panel ${nombre} de tipo ${panel}
	 *
	 * name=${panel.name!}
	 * label=${panel.label!}
	 * type=${panel.type}
	 *
	 * Properties:
	<#list  panel.getModelProperties() as prop >
     *      ${prop.name}=${prop.value}
	</#list>
	 */
	private JComponent get${nombre?cap_first}(){
	<#import  "panels/"+panel+".ftl" as pm />
	<@pm.macroPanel nombre panel  />
		return ${nombre};
	}
	<#-- creamos los paneles hijos -->	
	<#list panel.panels as ph>
		<@recorre ph nombre+ph_index/>
	</#list>
		
</#macro>
	
	<#-- Aquí creamos el panel principal "panel" -->
	<@recorre window.panel "panel"/>
	
	<#-- Aquí creamos los paneles de los diálogos -->
<#list 	window.getDialogs() as dialog >
	<@recorre dialog.panel "panel"+dialog.name />
</#list>
	
	//------------ Creación de dialogos --------------------------------	
	
<#list window.getDialogs() as dialog >
    private void create${dialog.name}(){
	<#include "Dialog.ftl" parse=true/>
	}
</#list>

	//----- funciones de acceso a los dialogos -----------		
	<#list window.getDialogs() as dialog >
    private void show${dialog.name}(){
		if(${dialog.name}==null){
		    create${dialog.name}();
		    }
		${dialog.name}.setVisible(true);     
	}
    </#list>
	
	// --------------- Actions --------------------------
	private final ActionOperation[] actionsToolbar = {
	<#list window.getOperations() as operation >
    	<#if operation.activation.equalsIgnoreCase("TOOLBAR")>    					
    		<@OPER operation/>
    		<#if operation_has_next>,</#if>
		</#if>	
	</#list>
	};

<#assign hasOpInit = false>
 <#list window.getOperations() as operation >
    <#if operation.activation.equalsIgnoreCase("init")>
    	<#assign hasOpInit = true>
    </#if>
</#list>    

<#if hasOpInit>
	private final ActionOperation[] actionsInit = {
    <#list window.getOperations() as operation >
    	<#if operation.activation.equalsIgnoreCase("init")>
    		<@OPER operation />
			<#if operation_has_next>,</#if>
		</#if>	
    </#list>
	};
</#if>

<#assign hasOpOnVar = false>
 <#list window.getOperations() as operation >
    <#if operation.activation.startsWith("Onvar") || operation.activation.startsWith("OnVar") || 
    operation.activation.startsWith("onvar") || operation.activation.startsWith("ONVAR") ||
    operation.activation.startsWith("onVar")>
        <#assign hasOpOnVar = true>
    </#if>
</#list>    

<#if hasOpOnVar>
	private final ActionOperation[] actionsOnVar = {
    <#list window.getOperations() as operation >
    	<#if operation.activation.startsWith("Onvar") || operation.activation.startsWith("OnVar") || 
    operation.activation.startsWith("onvar") || operation.activation.startsWith("ONVAR") ||
    operation.activation.startsWith("onVar")>    	
    		<@OPER operation />
			<#if operation_has_next>,</#if>
		</#if>	
    </#list>
	};
</#if>

<#assign hasOpButton = false>
<#list window.getOperations() as operation >
    <#if operation.activation.startsWith("Button") || operation.activation.startsWith("button") || 
    operation.activation.startsWith("BUTTON") >
        <#assign hasOpButton = true>
    </#if>
</#list>    

<#if hasOpButton>
	private final ActionOperation[] actionsButton = {
	<#assign first=0>
    <#list window.getOperations() as operation >    
    	<#if operation.activation.startsWith("Button") || operation.activation.startsWith("button") || 
    operation.activation.startsWith("BUTTON") >
    		<@OPER operation />
    		<#if operation_has_next>,</#if>
		</#if>	
    </#list>
		};
</#if>

<#assign hasOpExit = false>
<#list window.getOperations() as operation >
	<#if operation.activation.equalsIgnoreCase("OnExit")>
        <#assign hasOpExit = true>
    </#if>
</#list>    
<#if hasOpExit>	
	private final ActionOperation[] actionsOnExit = {
    <#list window.getOperations() as operation >
    	<#if operation.activation.equalsIgnoreCase("OnExit")>
    		<@OPER operation />
	 		<#if operation_has_next>,</#if>
		</#if>	
    </#list>
	};
</#if>

	public String getId(){
		return WINDOW_ID;
	}
	
	public ActionOperation[] getActionsInit(){
		<#if hasOpInit >
		return actionsInit;
		<#else>
		return new ActionOperation[0];
		</#if>
	}

	public ActionOperation[] getActionsOnVar(){
		<#if hasOpOnVar>
		return actionsOnVar; 
		<#else>
		return new ActionOperation[0];
		</#if>
	}

	public ActionOperation[] getActionsButton(){
		<#if hasOpButton>
		return actionsButton;
		<#else>
		return new ActionOperation[0];
		</#if>
	}

	public ActionOperation[] getActionsOnExit(){
		<#if hasOpExit>
		return actionsOnExit;
		<#else>
		return new ActionOperation[0];
		</#if>
	}

	public ActionOperation[] getActionsToolbar(){
		return actionsToolbar;
	}	

    /** Nombre elegante de la ventana. */
    public String getName() {
    	if (name==null || name.length()==0){
    		return "";
    		}
    	if (name.charAt(0)=='$'){
    		return (String)ctx.get(name.substring(1));
    	}else{
    		return name;
    	}
        
    }

    // --------------- metodos de utilidad -------------------
    
    public void setName(final String n) {
    	if (n.charAt(0)=='$'){
    		name=(String)ctx.get(n.substring(1));
    		ctx.addListener(n.substring(1), new ContextListener(){
				public void valueChanged(String key, Object value) {
					app.ActualizaTitulo((String)ctx.get(n.substring(1)));	
					name = n;
				}
			});
    	}else{
     		name = n;
    	}       
    }
    
<#if needShowMessage>    
    /**
    * Muestra un mensaje, resultado de la operación, si es necesario.
    * @parameter s Nombre de la variable que contiene el mensaje
    */
    private void showMessage(String msgVar){
    	String msg =(String)ctx.get(msgVar);
    	if(msg==null || msg.length()==0){
    		return;
    		}
    	Icon icon;
    	int type;
    	switch (msg.charAt(0)){
		case 'E':
			msg = msg.substring(1);
    		icon= SwingUtils.ERROR_ICON;
			type= JOptionPane.ERROR_MESSAGE;
			break;
		case 'W':
			msg = msg.substring(1);
	    	icon= SwingUtils.WARNING_ICON;
	    	type= JOptionPane.WARNING_MESSAGE;
	    	break;
		case 'I':
			msg = msg.substring(1);
    		icon= SwingUtils.INFO_ICON;
			type= JOptionPane.INFORMATION_MESSAGE;
			break;
		case 'Q':
			msg = msg.substring(1);
    		icon= SwingUtils.QUESTION_ICON;
	    	type= JOptionPane.QUESTION_MESSAGE;
	    	break;
	    default:
	    	icon= SwingUtils.QUESTION_ICON;
	    	type= JOptionPane.QUESTION_MESSAGE;
	    }
		JOptionPane.showMessageDialog(null,GestorMultilingualidad.buscar(msg),"",type,icon);
    }
</#if>    
}
