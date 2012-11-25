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
<#include "../ListaProps.ftl" />
<#local v = panel.modelProperty("varName") >
		// CardPanel ${panel}
		final java.awt.CardLayout ${id}Layout =new java.awt.CardLayout(); 
		final JPanel ${id}=new JPanel(${id}Layout);
	<#list panel.panels as phijo >
		<#local idh = id+phijo_index>
		<#import phijo+".ftl" as pm/>
		<@pm.macroPanel idh phijo /> 
		${id}.add(${idh},"${phijo.name}");
	</#list>
		Object obj=ctx.get("${v}");
		if(obj!=null){
			${id}Layout.show(${id},(String)obj);
			}	 	
		ctx.addListener("${v}",new ContextListener(){
			public void valueChanged(String key,Object value){
				Object v=ctx.get("${v}");				
				${id}Layout.show(${id},(String)v);
				}
		});
		//  Fin CardPanel
</#macro>
		