		<#import "util/UtilEditor.ftl" as util>
		
		<#macro macroPanel idh editor >
		 <#-- final EditorString ${idh} =new EditorString();
		 ${idh}.setOpaque(true);
		  ${idh}.setFont(RunnerLF.getFont("EditorString.font"));-->
		<#assign filas="-1">
		<#assign columnas="-1">
		 
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign alin="D">
		<#if strParametrosEditor != "">
			<#assign alin=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">	
				<#assign editores = util.getListParametros(strParametrosEditor)> 
				<#if editores[0]??>
				 	<#assign filas = editores[0]/>
				 </#if>
				<#if editores[1]??>
				 	<#assign columnas = editores[1]/>		
				</#if>		
			</#if>			
		</#if>
		
		<#assign textArea = idh + "TA">
		JTextArea ${textArea} = new JTextArea();
		
		<#if filas!="-1">
		${textArea}.setRows(${filas});
		</#if>
		<#if columnas!="-1">
		${textArea}.setColumns(${columnas});
		</#if>
		
		${idh}.setClaseEditor("lightswingrunner.editors.EditorTextArea");
		${idh}.setAlineacionEditor("${alin}");
		${idh}.setInfo(${textArea});	 
		 
		</#macro>
 