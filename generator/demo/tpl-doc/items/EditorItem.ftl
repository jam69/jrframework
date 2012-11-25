<#-- plantilla para EditorItem -->
<#macro Item item pos>
	<attribute>
		<!-- EditorItem -->
		<id>${item.id!}</id>
		<name>${item.prop("objectVar")!}</name>
		<description>${item.prop("tooltip")!}</description>
		<type>${item.prop("editor")}</type>
		<range>${item.prop("editor")}</range>
	</attribute>
</#macro>  