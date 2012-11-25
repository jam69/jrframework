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
		// FlowPanel ${panel} 
		RowLayout ${id} = new RowLayout();
 		${id}.wrap = false;
 		${id}.pack = false;
 		${id}.justify = true;
 		${id}.type = SWT.VERTICAL;
 		${id}.marginLeft = 5;
 		${id}.marginTop = 5;
 		${id}.marginRight = 5;
 		${id}.marginBottom = 5;
 		${id}.spacing = 0;
 		//
 		shell.setLayout(${id});
<#assign panel0 = panel />
<#assign id0 = id />
<#list panel0.panels as phijos >
	<#assign panel = phijos>
	<#assign composite = id >
	<#assign id = id0 + phijos_index>
	<#include panel+'.ftl' parse=true/>
		// en SWT no es neceario${id0}.add(${id});
</#list>
		//  Fin FlowPanel