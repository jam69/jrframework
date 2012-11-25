<#--
    Plantilla de Panel
    se llama desde la plantilla  'window.ftl'
    Recibe las  variables:
       panel:  Componente Panel a generar
       id:     variable que debe contener el panel
    Variables a usar en el codigo:
       ctx:    Contexto.
    Notas:
       como va a anidar llamadas a otros paneles copiamos panel e id para guardar
       su valor original. Y luego vamos seteando las variables y llamando a las
       templates 'hijas'
-->  
<#macro macroPanel id panel >
 		final HorizontalPanel  ${id}= new HorizontalPanel();
 	<#local cons="cons"+id>
 		ConstrainedLayout ${cons} = new ConstrainedLayout();
 		${cons}.setOrientation(SwingConstants.HORIZONTAL);
 		${id}.setLayout(${cons});
 		
  	<#if panel.modelProperty("definition")??> 	
 		<#list  panelDefinitionsParser.parser(panel.modelProperty("definition")) as d >
 			<#local nameDefinition="def"+d_index>
 		ConstraintsDefinition ${nameDefinition}= new ConstraintsDefinition();
 	 	${nameDefinition}.setExpandable( ${d.expandable.toString()});
 	 	${nameDefinition}.setScrollable(${d.scrollable.toString()});
 	 	${nameDefinition}.setWithTitle(${d.withTitle.toString()});
 	 	${nameDefinition}.setNormalSizeFixed(${d.normalSizeFixed.toString()});
 	 	${nameDefinition}.setNormalSizeFixedHeight(${d.normalSizeFixedHeight});
 	 	${nameDefinition}.setNormalSizePreferred(${d.normalSizePreferred.toString()});
 	 	${nameDefinition}.setNormalSizeRelative(${d.normalSizeRelative.toString()});
 	 	<#local NormalSizeRelativeHeight=d.normalSizeRelativeHeight.toString()?replace(",",".")>
 	 	${nameDefinition}.setNormalSizeRelativeHeight(Double.parseDouble("${NormalSizeRelativeHeight}"));
 	 	 	 	
		</#list>
		<#list panel.panels as phijo >
			<#local idh = id+phijo_index>
		JComponent ${idh}=get${idh?cap_first}();		
			<#if phijo.label??>
				<#local titulo=phijo.label>
		${idh}.setName("${titulo}");
			</#if>
		${id}.add(${idh},${"def"+phijo_index});
		
		</#list>
	</#if>
 </#macro>