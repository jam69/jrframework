<#-- plantilla para ComboBox -->
<#macro Item item pos>
	<attribute>
		<!-- ComboBox -->
		<name>${item.id!}</name>
		<description>${item.prop("varSelected")!}</description>
		<type>${item.prop("tooltip")!}</type>
		<range><#if item.prop("data")??>Alguna opcion de: ${item.prop("variable")!}
		<#else>Alguno de los siguientes valores:${item.prop("data")!}
		</#if></range>
	</attribute>		
</#macro>  