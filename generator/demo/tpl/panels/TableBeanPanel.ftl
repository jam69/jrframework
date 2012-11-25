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
<#macro macroPanel id panel>
	// TableBeanPanel
 	JPanel ${id}=new JPanel();
 /* Props	
<#list  panel.modelProperties() as prop >
    ${prop.name}="${prop.value}"
</#list>
*/
	// Fin TableBeanPanel
</#macro>	