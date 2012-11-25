<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
	final JScrollPane ${id}=new JScrollPane();			
	<#if item.@panelName[0]?? || item.@panelName[0]!="null">
		<#assign panelName = item.@panelName>
		<#assign subPanel = "subPanel" + id >
		if(panels!=null){
			JPanel ${subPanel} = panels.get("${panelName}");	
			if(${subPanel}!=null)
				${id}.setViewportView(${subPanel});
			}
	</#if>