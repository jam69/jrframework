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
	// HorizontalPanel
 	JPanel ${id}=new JPanel(new FlowLayout());
 /* Props	
 #list defs.parse(panel.modelProperty("definition")) as d>
 ++ ${d}
 Expandable:<#if d.expandable>Expandable<#else>Fijo</#if>
 Scrollable:<#if d.scrollable >Scrollable<#else>Sin Scroll</#if>
 withTitle: <#if d.withTitle >Con Titulo<#else>Sin Titulo</#if>
 Tamaños:   
    minSizeSet=              ${d.minSizeSet.toString()} //boolean
    minSizePreferred=        ${d.minSizePreferred.toString()} //boolean
    minSizeFixed=            ${d.minSizeFixed.toString()} //boolean
    minSizeFixedHeight=      ${d.minSizeFixedHeight}
    normalSizePreferred=     ${d.normalSizePreferred.toString()} //boolean
    normalSizeFixed=         ${d.normalSizeFixed.toString()} //boolean
    normalSizeFixedHeight=   ${d.normalSizeFixedHeight}
    normalSizeRelative=      ${d.normalSizeRelative.toString()} //boolean
    normalSizeRelativeHeight=${d.normalSizeRelativeHeight}
    maxSizeSet=              ${d.maxSizeSet.toString()} //boolean
    maxSizePreferred=        ${d.maxSizePreferred.toString()} //boolean
    maxSizeFixed=            ${d.maxSizeFixed.toString()} //boolean
    maxSizeFixedHeight=      ${d.maxSizeFixedHeight}
 
<#list  panel.modelProperties() as prop >
    ${prop.name}="${prop.value}"
</#list>
*/
<#list panel0.panels as phijos >
	<#assign panel = phijos>
	<#assign id = id0 + phijos_index>
	<#include panel+'.ftl' parse=true/>
		${id0}.add(${id});
</#list>
	// Fin HorizontalPanel
 
