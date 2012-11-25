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
		// PanelVacio 
		<#if panel.modelProperty("text")??>
		<#local txt = panel.modelProperty("text") />
		<#else>
		<#local txt = "Texto de Prueba" />
		</#if>		
		final JPanel ${id} = new JPanel();

		${id}.add(new JLabel("${escape(txt)}"));
		// Fin PanelVacio
</#macro>		