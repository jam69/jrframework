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
	// EditBeanPanel
 	JPanel ${id}=new JPanel();
 /* Props	
<#list  panel.modelProperties() as prop >
    ${prop.name}="${prop.value}"
</#list>
*/
	// Fin EditBeanPanel