<#-- plantilla para CheckBox -->
<#macro Item item pos>
		<!--CheckBox -->
		<tr><a name="#${item.id!}" />
		<td>${item.prop("variable")!}</td>
		<td>${item.prop("data")!}</td>
		<td>bla...</td>
		</tr>
</#macro>  