<#import "../../util/UtilEditor.ftl" as util>

<#macro macroPanel idh editor >
<#assign formatDate="dd/MM/yyyy">

		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign editores = util.getListParametros(strParametrosEditor)> 
					<#if editores[0]??>
					 	<#assign formatDate =editores[0]/>
					</#if>
			</#if>
		</#if>
 <#assign formato = "f"+idh>
 final SimpleDateFormat ${formato} = new SimpleDateFormat("${formatDate}");
${idh}.setClaseEditor("lightswingrunner.editors.EditorTimeStampSpin");
${idh}.setAlineacionEditor("${ali}");
${idh}.setInfo(${formato});
</#macro>
