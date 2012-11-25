		<#import "../../util/UtilEditor.ftl" as util>
		
		<#macro macroPanel idh editor >
		<#assign min="0">
		 <#assign max="100">
		 <#assign inc="-1">
		 <#assign ali="D">	
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign editores = util.getListParametros(strParametrosEditor)> 
					<#if editores[0]??>
					 	<#assign min =editores[0]/>
					 </#if>
					 <#if editores[1]??>
					 	<#assign max =editores[1]/>		
					</#if>	
					<#if editores[2]??>
					 	<#assign inc =editores[2]/>		
					</#if>		
			</#if>			
		</#if>
		<#assign modelspin="model"+idh>
		SpinnerModel ${modelspin}=new SpinnerNumberModel(${min},${min},${max},${inc});
		 ${idh}.setClaseEditor("lightswingrunner.editors.EditorRangoInt");
		 ${idh}.setAlineacionEditor("${ali}");
		 ${idh}.setInfo(${modelspin});
		 
		</#macro>
 