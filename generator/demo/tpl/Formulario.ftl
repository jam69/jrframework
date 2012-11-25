<#--
    Plantilla de clases de Formulario
    se llama desde MainGenerate.procesaForm()
    Recibe las  variables:
     	"name":			Nombre del formulario (javaizado)
        "doc":			XML-DOC del formulario (DOM)
        "stripHTML":	Metodo que elimina el HTML ??
        "genID":		Metodo que genera ID únicos ??
        "escape":		Metodo que quotea las "
        "toJava":		Metodo que javaiza un String
    Notas:
    	La clase se genera en el packete: forms
    	Llama a las plantillas del directorio 'items'
    	Atencion que FreeMarker funciona en modo XML (DOM) con lo que se usa XPATH
-->
<#-- vuelva los atributos que nos pasan en un item
     se utiliza en los componentes para mostrar que estamos implementando y
     poder depurar los programas -->
<#macro dumpProps item>
	<#list item.@@ as attr>
		<#assign nn = attr?node_name >
		<#if nn!="x" && nn!="y" && nn!="h" && nn!="w" && nn!="name" >
			${attr?node_name}=${attr} <#t>
		</#if>	 
	</#list>   
</#macro>  
<#macro comment item>
	/**
	 * ${item?node_name}
	 *
	<#list item.@@ as attr>
		<#assign nn = attr?node_name >
		<#if nn!="x" && nn!="y" && nn!="h" && nn!="w" && nn!="name" >
	 *		${attr?node_name}="${attr?replace("*/","* /")}"
		</#if>	 
	</#list>
	 */  
</#macro>  
<#function helpName item>
	<#assign cname = "">
	<#if item.@objectVar[0] ??>
    	<#assign cname = item.@objectVar[0]>
    	<!-- var: ${cname} -->
	<#elseif item.@varSelected[0] ??>
    	<#assign cname = item.@varSelected[0]>
    	<!-- var: ${cname} -->
	<#elseif item.@variable[0] ??>
    	<#assign cname = item.@variable[0]>
    	<!-- var: ${cname} -->
	</#if>    	
	<#return "."+docInfo.toKey(cname)/>
</#function>
/*
 *   Fichero generado automáticamente - NO EDITAR
 */

<#assign cols = doc.FormModel.@columns >
<#assign rows = doc.FormModel.@rows > 

package forms;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.math.BigDecimal;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.*;

import lightlookandfeel.RunnerLF;
import lightswingrunner.editors.*;
import lightswingrunner.editors.PanelButtonPreviewPropertyEditor.*;
import swingrunner.*;
import com.jrsolutions.framework.*;
import es.indra.humandev.runner.core.utils.ImageLoader;

/**
 * Formulario ${name}
 */
public class ${name} extends JPanel{
	
	private final Contexto ctx;
	
	<#--
	   NO tenemos soporte a paneles hijo de los formularios.
       Deberíamos implementar el constructor
          public ${name}(Contexto ctx2,HashMap<String, JPanel> panels){
       --> 
	public ${name}(Contexto ctx2,String helpID){
		this.ctx=ctx2;
		final String columns="${cols}"; 
		final String rows="${rows}";
		final FormLayout layout=new FormLayout(columns,rows);
		setLayout(layout);
		setOpaque(false);
		final CellConstraints cc=new CellConstraints();
	<#list doc.FormModel.CellSpecModel as cell>    
		<#list cell.* as item>
			<#assign id = "c"+cell_index >
		JComponent ${id}=creaComponent${id?cap_first}();	
		cc.xywh(${cell.@col?string}+1,${cell.@row?string}+1,${cell.@colSpan?string},${cell.@rowSpan?string});
		cc.insets = new Insets(${cell.@insets?string});
			<#if item?node_name=="TextField">
		${id}.setPreferredSize(new Dimension(${id}.getPreferredSize().width + 40, ${id}.getHeight()));
			</#if>
			<#if item.@editor?has_content >
				<#assign editor = item.@editor?split("(")[0]>
				<#if editor=="BigDecimal" || 
					editor=="Double" || 
					editor=="Float" || 
					editor=="Long" || 
					editor=="Short" || 
					editor=="Integer" || 
					editor=="String">
		${id}.setPreferredSize(new Dimension(${id}.getPreferredSize().width + 40, ${id}.getHeight()));
				</#if>
			</#if>
		<#assign helpKey = helpName(item)>
		<#if helpKey??>
		HelpUtil.asociaHelp(${id},helpID+"${helpKey}");
		<#else>
		// No tiene ayuda
		</#if>	
		add(${id},cc);    		
		</#list>
	</#list>
	}

	<#list doc.FormModel.CellSpecModel as cell>    
		<#list cell.* as item>
			<#assign id = "c"+cell_index >
    <@comment item/> 
    private JComponent creaComponent${id?cap_first}(){	
			<#attempt>
		 		<#include "items/"+item?node_name+".ftl"/>
			<#recover>
			<#assign pluginName=item?node_name>
		  		<#include "items/plugins.ftl"/>
			</#attempt>
		return ${id};
	}
	
		</#list>
	</#list>
		
}
