<#-- plantilla para ComboBox -->
<#macro Item item pos>
		<tr><a name="#${item.id!}" />
		<td>${item.prop("varSelected")!}</td>
		<td>${item.prop("tooltip")!}</td>
		<td>
		<#if item.prop("data")??>Alguna opción de: ${item.prop("variable")!}
		<#else>Alguno de los siguientes valores:${item.prop("data")!}
		</#if>
		 </td>
		 </tr>
</#macro>  