<#-- plantilla para CheckBox -->
<#macro Item item pos>
		<attribute>
		<!-- CheckBox -->
		<id>${item.id!}</id>
		<name>${item.prop("variable")}</name>
		<description>${item.prop("tooltip")!}</description>
		<type></type>
		<range>${item.prop("data")!}</range>
	</attribute>
</#macro>  