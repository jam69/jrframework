		<#import "../../util/UtilEditor.ftl" as util>
		
		<#macro macroPanel idh editor >
		<#assign formatText="-1">
		 
		<#assign editor = editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>	
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 	<#assign formatText =valores[0]/>
				</#if>
			</#if>
		
		</#if>
		 
		 ${idh}.setClaseEditor("lightswingrunner.editors.EditorFormato");
		 ${idh}.setAlineacionEditor("${ali}");
		 ${idh}.setInfo(null);
		 <#if formatText!="-1">
		try {
			${idh}.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("${formatText}")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		 </#if>
		 
		</#macro>