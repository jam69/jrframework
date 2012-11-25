		<#import "../../util/UtilEditor.ftl" as util>
		
		<#macro macroPanel idh editor >
		<#assign decimales="-1">
		<#assign locale="ES">
		 
		<#assign editor = editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>			
		</#if>

		${idh}.setClaseEditor("lightswingrunner.editors.EditorRadio");
		${idh}.setAlineacionEditor("${ali}");
		${idh}.setInfo("${strParametrosEditor}");
		 
		</#macro>
 