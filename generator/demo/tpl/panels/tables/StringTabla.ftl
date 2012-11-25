		<#macro macroPanel idh editor >
		 <#-- final EditorString ${idh} =new EditorString();
		 ${idh}.setOpaque(true);
		  ${idh}.setFont(RunnerLF.getFont("EditorString.font"));-->
		 <#assign longitud="25">
		 <#assign alin="I">
		  <#if editor?split("(")?size!=1>
		 <#assign p = editor?split("(")[1]>
		 <#assign parametros = p?replace(")","")>
		 <#list parametros?split(",") as cadena>
		 	<#if cadena?matches("[0-9]+")>
				<#assign longitud=cadena>
			<#else>
					<#if cadena=="C" ||cadena=="c" >
					<#assign alin="C">
			  		<#else>
					  	<#if cadena=="D" ||cadena=="d" >
					  	<#assign alin="D" >
					 	<#else>
								<#if cadena=="I" ||cadena=="i" >
								<#assign alin="D" >
							</#if>
						</#if>		 
			 		</#if>
			 </#if>		 
		 </#list>
		 </#if>
		${idh}.setClaseEditor("lightswingrunner.editors.EditorString");
		${idh}.setAlineacionEditor("${alin}");
		${idh}.setInfo(${longitud});	 
		 
		</#macro>
 