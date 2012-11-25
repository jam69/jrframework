<#--
	Genera una columna de la tabla.
	recibe:
	  ID:          identificador de la columna
	  columnDefinition:        columnDefinition (de tipo AttributeDefinition)
	  nameFiltros: nombre de la tabla
	  col: 		   identificador de la columna padre (column+index)
	
-->
<#macro macroPanel id columnDefinition nameFiltros col>

		HeaderNode ${id}= new HeaderNode("${columnDefinition.id}");
		  ${col}.add(${id});
		  ${id}.setAttribute("${columnDefinition.attribute}");
       	  ${id}.setColumnID("${columnDefinition.id}");
       	<#if columnDefinition.getAttrDescription()??>
       	<#local tituloColumna = "gestor"+id>
   		  ${id}.setTitle(GestorMultilingualidad.buscar("${columnDefinition.attrDescription}"));
       	<#else>
       	  ${id}.setTitle("${columnDefinition.attribute}");
       	</#if>
       	<#if columnDefinition.editable>
       	  ${id}.setEditable(true);
       	<#else>
       		<#if columnDefinition.getEditableDefinition()??>
       			<#local name = "node"+columnDefinition.getId()>
       			<#local filtro = parserModificadores.parseOne(columnDefinition.getEditableDefinition())>
		InformationNode ${name} = new InformationNode();
		    ${name}.setType("${filtro.type}");
				<#list filtro.getPropNames() as propi>
		    ${name}.setProp("${propi}","${filtro.getProp(propi)}");
				</#list>
		    ${nameFiltros}.addFiltroEditable("${columnDefinition.id}",FilterHelper.creaFiltro(${nameFiltros}, ${name}));
       		<#else>
       	  ${id}.setEditable(false);
       		</#if>
       	</#if>
       	  ${id}.setVisible(${columnDefinition.visible.toString()});
       	  ${id}.setHidden(${columnDefinition.hidden.toString()});
       	  ${id}.setSortable(${columnDefinition.sortable.toString()});
		<#--<#if columnDefinition.getPrefSize()??>
		  ${id}.setPrefWidth(${columnDefinition.getPrefSize()});
		</#if> -->
		<#if ! columnDefinition.isUseDefaultEditor()>
			<#local idh = id+columnDefinition.getId()>
			<#import columnDefinition.getCustomEditor()?split("(")[0]+"Tabla"+".ftl" as pm/>
			<@pm.macroPanel id columnDefinition.getCustomEditor()  /> 	
		</#if> 
      	<#if columnDefinition.getPrefSize()??>
		  ${id}.setPrefWidth(${columnDefinition.prefSize});
		</#if>       	
</#macro>