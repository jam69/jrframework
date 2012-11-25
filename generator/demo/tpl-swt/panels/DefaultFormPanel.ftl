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
    Para cada formulario creamos una clase en el package 'forms' y con nombre
    el mismo del fichero 'javaizado' (toJava)
-->  
	// PanelForm
	<#assign n = toJava(panel.modelProperty("xml")) >
	forms.${n} ${id}=new forms.${n}(ctx,composite);
	composite.setLayout(panel); 		
	// End-PanelForm
		