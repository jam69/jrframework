<#--
    Plantilla de Panel
    se llama desde la plantilla  'window.ftl'
    Recibe las  variables:
       panel:  Componente Panel a generar
       id:     variable que debe contener el panel
    Variables a usar en el codigo:
       ctx:    Contexto.
    Notas:
-->  
	// TreeTableBeanPanel
 	JPanel ${id}=new JPanel();
 /* Props	
<#list  panel.modelProperties() as prop >
    ${prop.name}="${prop.value}"
</#list>
*/
	// Fin TreeTableBeanPanel