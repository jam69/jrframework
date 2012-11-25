<!-- Panel Flow ${panel}-->
<#assign exp = panel.modelProperty("expandable")!/>
<table width='100%'>
<#list panel.getChildren() as p >
<tr><td>
<#assign panel = p />
<#include "expandable2.ftl" parse=true/> 
</td></tr>
</#list>
</table>
<!-- /Panel Flow -->