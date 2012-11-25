		<#macro macroPanel idh editor >
		 <#assign enteros="-1">
		 <#assign decimales="-1">
		 <#assign locale="ES">
		 <#assign pos ="-1">
		 <#assign p = editor?split("(")[1]>
		 <#assign parametros = p?replace(")","")>
		 <#list parametros?split(",") as cadena>
		 	<#if cadena?matches("[0-9]")>
				<#if enteros=="-1">
					<#assign enteros =cadena/>
				<#else>
				   <#assign decimales =cadena/>	
				 </#if>
			<#else>
					<#if cadena=="C" ||cadena=="c" >
					<#assign alin = "c">
					  
			  		<#else>
					  	<#if cadena=="D" ||cadena=="d" >
					  	<#assign alin = "d">
					 	<#else>
								<#if cadena=="I" ||cadena=="i" >
								<#assign alin = "i">
							 <#else>
							 	<#assign locale=cadena>
							</#if>
						</#if>		 
			 		</#if>
			 </#if>		 
		 </#list>
		<#assign nf="nf"+id>
		 final NumberFormat ${nf} = NumberFormat.getNumberInstance(new Locale("${locale}"));
		 <#if decimales!="-1">
		 ${nf}.setMaximumFractionDigits(${decimales});
		 </#if>
		 <#if enteros!="-1">
		 ${nf}.setMaximumIntegerDigits(${enteros});
		 </#if>
		${idh}.setClaseEditor("lightswingrunner.editors.EditorDouble");
		 ${idh}.setAlineacionEditor("${alin}");
		 ${idh}.setInfo(${nf});
		 
		</#macro>
					  