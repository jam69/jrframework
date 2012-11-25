		<#import "../../util/UtilEditor.ftl" as util>
		
		<#macro macroPanel idh editor >
		<#assign enteros="-1">
		<#assign locale="ES">
		 
		<#assign editor = editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#if strParametrosEditor != "">
				<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 	<#assign enteros =valores[0]/>
				</#if>
			</#if>
		
		</#if>
		<#assign nf="nf"+idh>
		 final NumberFormat ${nf} = NumberFormat.getNumberInstance(new Locale("${locale}"));
		 <#if enteros!="-1">
		 ${nf}.setMaximumIntegerDigits(${enteros});
		 </#if>
			<#--${idh}.setNF(nf);-->
		 ${idh}.setClaseEditor("lightswingrunner.editors.EditorShort");
		 ${idh}.setAlineacionEditor("${ali}");
		 ${idh}.setInfo(${nf});
		 
		</#macro>
 