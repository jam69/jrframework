<#-- plantilla para EditorItem -->
<#macro Item item pos>
		<!-- EditorItem -->
		<tr><a name="#${item.id!}" />
		<td>${item.prop("objectVar")}</td>
		<td>${item.prop("tooltip")!}</td>
		<td>${item.prop("editor")}</td>
		</tr>
</#macro>  