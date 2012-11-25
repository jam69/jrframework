<#-- plantilla para TextField -->
<#macro Item item pos>
		<attribute>
		<!-- TextField -->
		<id>${item.id!}</id>
		<name>${item.prop("variable")}</name>
		<description>${item.prop("tooltip")!}</description>
		<type>String</type>
		<units></units>
		<range></range>
	</attribute>
</#macro>  
