
<font size="2">
<table border='1'>
<tr><th>Propiedad</th><th>Valor</th></tr>
<#list  panel.modelProperties() as prop >
 <tr><td> ${prop.name}</td><td> ${prop.value} </td></tr>
</#list>
</table>
</font>
