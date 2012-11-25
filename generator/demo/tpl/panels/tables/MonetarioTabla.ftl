		<#import "../../util/UtilEditor.ftl" as util>
		
		<#macro macroPanel idh editor >
		<#assign locale="ES">
		 
		<#assign editor = editor>
		
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 	<#assign locale =valores[0]/>
				</#if>
			</#if>
			
		</#if>
		
		<#assign locale="locale"+idh>
		 Locale ${locale} = es.indra.humandev.runner.core.utils.LocaleUtils.getLanguage("${locale}");
		
			<#--${idh}.setNF(nf);-->
		 ${idh}.setClaseEditor("lightswingrunner.editors.EditorCurrency");
		 ${idh}.setAlineacionEditor("${ali}");
		 ${idh}.setInfo(${locale});
		 
		</#macro>
 