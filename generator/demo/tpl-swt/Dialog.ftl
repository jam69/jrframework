
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
		// Dialog  ${dialog}
	<#assign id = dialog.name />		
		${id}=new JDialog(${dialog.title},false);
	<#assign id0 = id +"Panel"+ />
	<#assign panel = dialog.panel /> 
	<#assign id = id0 />
	<#include panel+'.ftl' parse=true/>
		${id}.setContentPane(${id0});
		${id}.pack();
		// End Dialog
