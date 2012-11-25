		<#import "../../util/UtilEditor.ftl" as util>
		
		<#macro macroPanel idh editor >
		<#assign formato="dd/MM/yy">
		 
		<#assign editor = editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 	<#assign formato =valores[0]/>
				</#if>
			</#if>
		
		</#if>
		<#assign nf="df"+idh>
		 final SimpleDateFormat ${nf} = new SimpleDateFormat("${formato}");
		 ${idh}.setClaseEditor("lightswingrunner.editors.EditorDateMask");
		 ${idh}.setAlineacionEditor("${ali}");
		 ${idh}.setInfo(${nf});
		</#macro>
		