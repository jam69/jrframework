		<#import "../../util/UtilEditor.ftl" as util>
		
		<#macro macroPanel idh editor >
		<#assign enteros="-1">
		<#assign decimales="-1">
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
					 	<#assign enteros =valores[0]/>
				</#if>
				<#if valores[1]??>
					 	<#assign decimales =valores[1]/>
				</#if>
				<#if valores[2]??>
					 	<#assign locale =valores[2]/>
				</#if>
			</#if>
		
		</#if>
		<#assign nf="nf"+idh>
		 final NumberFormat ${nf} = NumberFormat.getNumberInstance(new Locale("${locale}"));
		 <#if decimales!="-1">
		 ${nf}.setMaximumFractionDigits(${decimales});
		 </#if>
		 <#if enteros!="-1">
		 ${nf}.setMaximumIntegerDigits(${enteros});
		 </#if>
			<#--${idh}.setNF(nf);-->
		 ${idh}.setClaseEditor("lightswingrunner.editors.EditorFloat");
		 ${idh}.setAlineacionEditor("${ali}");
		 ${idh}.setInfo(${nf});
		 
		</#macro>