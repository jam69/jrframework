<#macro macroPanel idh editor >
<#assign dateFormat="dd/MM/yyyy">

<#assign p = editor?split("(")[1]>
<#assign parametros = p?replace(")","")>
<#list parametros?split(",") as cadena>
<#if cadena=="C" ||cadena=="c" >
	  <#assign alin="C">
  		<#else>
	  	<#if cadena=="D" ||cadena=="d" >
	 	<#assign alin="D">
	 	<#else>
				<#if cadena=="I" ||cadena=="i" >
					<#assign alin="I">
			 <#else>
			 	<#assign dateFormat = cadena>
			 	
			</#if>
		</#if>		 
 		</#if>
</#list>
 <#assign formato = "f"+idh>
 final SimpleDateFormat ${formato} = new SimpleDateFormat("${dateFormat}");
${idh}.setClaseEditor("lightswingrunner.editors.EditorDateCalendar");
${idh}.setInfo(${formato});
</#macro>