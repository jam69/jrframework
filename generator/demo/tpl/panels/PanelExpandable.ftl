<#--
 Panel expandable
    Sin comprobar
    <#local exp = panel.modelProperty("expandable")>
-->
<#macro macroPanel id panel>
		//Panel Expandable ${panel}

		Expandable ${id}=new Expandable();
	
	<#if panel.label??>
	<#if panel.label?starts_with("$")>
	<#else>
		${id}.setTitulo("${panel.label}");
	</#if>
	</#if>
	<#if panel.name??>
	//${panel.name}
	</#if>
	
		
<#list panel.panels as phijo >	
	<#local idh=id+phijo_index>
	<#import phijo+'.ftl' as pm/>
	<@pm.macroPanel idh phijo/>
		${id}.add(${idh});
</#list>
		//Panel Expandable 
</#macro>		