<#-- plantilla para TextField -->
<#macro Item item pos>
		<tr><a name="#${item.id!}" />
		<!-- TextField -->		
		<td>${item.prop("variable")}</td>
		<td>${item.prop("tooltip")!}</td>
		<td>bla....</td>
		</tr>
</#macro>  