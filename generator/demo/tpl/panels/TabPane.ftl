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
<#macro macroPanel id panel>  
		final JTabbedPane ${id}=new JTabbedPane();
		${id}.setUI(new es.indra.isl.humandev.swing.TabbedPaneUI());

		<#list panel.panels as phijo >
			<#local idh = id+phijo_index>
		JComponent ${idh}=get${idh?cap_first}();
			<#if phijo.label??>
				<#local titulo=phijo.label>
		${idh}.setName("${titulo}");
			</#if>
		${id}.add(${idh});
			<#if phijo.label?starts_with("$")>
		${id}.setTitleAt(${phijo_index},(String)ctx.get("${phijo.label}".substring(1)));
		ctx.addListener("${phijo.label}".substring(1), new ContextListener(){
				@Override
				public void valueChanged(String key, Object value) {
						${id}.setTitleAt(${phijo_index},(String)ctx.get("${phijo.label}".substring(1)));	
					}
			});
			<#else>
		${id}.setTitleAt(${phijo_index},"${phijo.label}");
			</#if>
		
		</#list>		
</#macro>		
