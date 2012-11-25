<#--
    Plantilla de Panel
    se llama desde la plantilla  'window.ftl'
    Recibe las  variables:
       panel:  Componente Panel a generar
       id:     variable que debe contener el panel
       parserTabla, para parsear el columnmodeldefinition
    Variables a usar en el codigo:
       ctx:    Contexto.
    Notas:
     
-->  
<#macro macroPanel id panel >
	<#local nameFiltros =id+"filtros">
		final TablaFiltros ${nameFiltros} = new TablaFiltros();
		${nameFiltros}.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final TablaAvanzadaMultiple ${id}=new TablaAvanzadaMultiple(${nameFiltros},null,null);
	<#assign col="columns"+id>
	<#assign buton="button"+id>
		ArrayList<HeaderNode>${col}= new ArrayList<HeaderNode>();
 		${id}.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
 		TableOptionsButton ${buton} =new TableOptionsButton(${id},${col});
 		${id}.setCornerUpperRight(${buton});
 	<#local root="root"+id?replace(" ", "")>
 		HeaderNode ${root} = ${nameFiltros}.getRoot();
	<#list  panel.getModelProperties() as prop >
    	<#if "${prop.name}"=="columnModelDefinition">
       		<#local definicionValue=prop.value?j_string />	
       		<#list parserTabla.parse(prop.value) as d>
       			<#if d??>
					<#list d.getAlElements() as g>
						<#local groupdefinition=g>
						<#if g.getAlElements().size()!=0>
							<#list g.getAlElements() as cgDefinition >
								<#if cgDefinition.toString().startsWith("Group")>
									<#local idh = cgDefinition.getDescription().toString().trim().replaceAll(" ","_")>
									<#import "tables/GroupDefinition.ftl" as pm/>
									<@pm.macroPanel idh cgDefinition nameFiltros col /> 
		${root}.add(${idh});
								<#else>
									<#local idh = cgDefinition.getId().replaceAll(" ","_")>
									<#import "tables/ColumnDefinition.ftl" as pm/>
									<@pm.macroPanel idh cgDefinition nameFiltros col /> 
		${root}.add(${idh});
								</#if>
							</#list>
						</#if>
					</#list>
       			</#if>
       		</#list>
    	</#if>
  		<#if "${prop.name}"=="menu">
    		<#if "${prop.value}"=="true">
    	${buton}.setMenu();
    		</#if>
    	</#if>
    	<#if "${prop.name}"=="selectionVar">
       	${nameFiltros}.getSelectionModel().addListSelectionListener(
			new MiSelectionListener(${nameFiltros}, "${prop.value}",ctx)
			);
    	</#if>
    	<#if "${prop.name}"=="listNameVar">  
   			<#local variableTableCollection = prop.value>
    	ctx.addListener("${prop.value}", new ContextListener(){
		@Override
		public void valueChanged(String key, Object value) {
			${id}.setDatos((Collection<?>)ctx.get("${variableTableCollection}"));
			${nameFiltros}.setCollection((Collection<?>)ctx.get("${variableTableCollection}"));
			${nameFiltros}.rebuildModel();								
			}
    	});
    	</#if>    
    	<#if "${prop.name}"=="selectionType">
			${nameFiltros}.getSelectionModel().setSelectionMode(${prop.value});
    	</#if >
		<#if "${prop.name}"=="modifiersDefinition" >
			<#local i=0>
			<#list parserModificadores.parse("${prop.value}") as modi2>
			<#list modi2 as modi>
				<#local i=i+1>
				<#if modi.getType()=="Renderer">
					<#local nameRenderer ="Renderer"+modi.getProp("id")+id+i>
		InformationNode ${nameRenderer} = new InformationNode();
		${nameRenderer}.setType("${modi.getType()}");
					<#list  modi.getPropNames() as propiRenderer>
		${nameRenderer}.setProp("${propiRenderer}","${modi.getProp(propiRenderer)}");
					</#list>
					<#local nameTableCellRenderer ="cellRenderer"+modi.getProp("id")+id+i>
		TableCellRenderer ${nameTableCellRenderer}=ModifiersHelper.creaPreEstilo(${nameRenderer});
				</#if>
				<#if modi.getType()=="Editor">
					<#local nameEditor ="Editor"+modi.getProp("id")+id+i>
		final InformationNode ${nameEditor} = new InformationNode();
		${nameEditor}.setType("${modi.getType()}");
					<#list  modi.getPropNames() as propiEditor>
		${nameEditor}.setProp("${propiEditor}","${modi.getProp(propiEditor)}");
					</#list>
					<#local nameTableCellEditor ="cellEditor"+modi.getProp("id")+id+i>
		TableCellEditor ${nameTableCellEditor}=ModifiersHelper.creaEditor(${nameEditor});
				</#if>
				<#if modi.getType()=="Filter">
					<#local nameFiltro ="Filter"+modi.getProp("id")+id+i>
		InformationNode ${nameFiltro} = new InformationNode();
					<#list  modi.getPropNames() as propiFiltro>
		${nameFiltro}.setProp("${propiFiltro}","${modi.getProp(propiFiltro)}");
					</#list>
					<#local filtro ="filtro"+modi.getProp("id")+id+i>
		Filtro ${filtro} =FilterHelper.creaFiltro(null, ${nameFiltro});
		${nameFiltros}.addFiltroModifier(${filtro}, ${nameTableCellRenderer}, ${nameTableCellEditor});
	   	${nameTableCellEditor}.addCellEditorListener(new CellEditorListener(){
				@Override
				public void editingCanceled(ChangeEvent e) {}
				@Override
				public void editingStopped(ChangeEvent e) {
					app.execButtonOp(${nameEditor}.getProp("operation").toString(), ctx);
				}
   		});
				</#if>
			</#list>
			</#list>
   		</#if>
   </#list>
		${buton}.addTable(${col});
		${nameFiltros}.rebuildModel();
		<#--
		${id}.setPreferredSize(${nameFiltros}.getPreferredSize());
		-->
	<#if variableTableCollection??>
    	if ((ctx.get("${variableTableCollection}")!=null)&&(((Collection<?>)ctx.get("${variableTableCollection}")).size()>0)){
    		${nameFiltros}.setCollection((Collection<?>)ctx.get("${variableTableCollection}"));
    	}
    </#if>
		${nameFiltros}.rebuildModel();
		
</#macro>	