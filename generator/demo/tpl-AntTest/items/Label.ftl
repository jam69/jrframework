<#-- plantilla para Labels -->

<#macro Item item pos >
		//Label       
		append("${stripHTML(item.props.get("text"))}");        
</#macro>        