
 <#import "../../util/UtilEditor.ftl" as util>
<#macro macroPanel idh editor >
<#assign filterFile="-1">
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign editores = util.getListParametros(strParametrosEditor)> 
					<#if editores[0]??>
					 	<#assign filterFile=editores[0]/>
					</#if>
			</#if>		
		</#if>
		 ${idh}.setClaseEditor("lightswingrunner.editors.EditorInputFile");
		 ${idh}.setAlineacionEditor("${ali}");
		 
		 <#if filterFile!="-1">
		 ${idh}.setInfo("${filterFile}");
		 <#else>
			${idh}.setInfo(null);		 
		 </#if>
		 
</#macro>
 