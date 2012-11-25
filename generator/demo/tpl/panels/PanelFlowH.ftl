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
		final JPanel ${id}=new JPanel(new FlowLayout());
	<#list panel.panels as phijo >
		<#local idh = id+phijo_index>
		JComponent ${idh}=get${idh?cap_first}();
		${id}.add(${idh});
	</#list>	
	
</#macro>
		