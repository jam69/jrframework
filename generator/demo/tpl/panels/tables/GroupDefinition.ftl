<#--
  Define un grupo de la tabla (que tendrá columnas o grupos debajo)
  Recibe:
      id:           Identificador del grupo
      groupDefinition: Definición del grupo (de tipo GroupDefinition)
      nameFiltros:  nombre de la tabla
      col:          identificador de la columna (column+index)
-->
<#macro macroPanel id groupDefinition nameFiltros col>
		HeaderNode ${id}= new HeaderNode("${groupDefinition.getDescription()}");
		<#local nombregestor= id+"gestor">
       	${id}.setTitle(GestorMultilingualidad.buscar("${groupDefinition.getDescription()}"));
       	${id}.setAttribute(null);
       	${id}.setEditable(false);
       	${id}.setCollapsable(${groupDefinition.expandable.toString()});
       	${id}.setExpanded(${groupDefinition.expanded.toString()});
       	<#if groupDefinition.getColumnId()??>
       	${id}.setColumnID("${groupDefinition.getColumnId()}");
       	</#if>
       	<#list groupDefinition.getAlElements() as groupDefinition2 >
			<#if groupDefinition2.toString().startsWith("Group")>
				<#local idh = id+groupDefinition2.getDescription()>
				<#local idh = idh?replace(" ", "")>
				<#import "GroupDefinition.ftl" as pm/>
				<@pm.macroPanel idh groupDefinition2 nameFiltros col/> 
			<#else>
				<#local idh = id+groupDefinition2.getId()>
				<#local idh = idh?replace(" ", "")>
				<#import "ColumnDefinition.ftl" as pm/>
				<@pm.macroPanel idh groupDefinition2 nameFiltros col/> 
			</#if>
		${id}.add(${idh});
		</#list>
</#macro>