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
<#macro macroPanel id panel>
		<#if panel.modelProperty("xml")??>
			<#local n = "Form"+toJava(panel.modelProperty("xml")) >
			<#if panel.getPanels().size()!= 0>
				<#assign nameSubPanel = "subpanel_"+ id/>
		HashMap<String, JPanel> ${nameSubPanel};
				<#assign i = 1/>
		${nameSubPanel} = new HashMap<String, JPanel>();
				<#list panel.getPanels() as subpanel >
					<#import  subpanel.toString()+".ftl" as pane />
					<#assign namePanel = "subpanel"+i/>
					<@pane.macroPanel namePanel panel/>
		${nameSubPanel}.put("${subpanel.getName()}",${namePanel});
					<#assign i = i+1/>
				</#list>
		final forms.${n} ${id}=new forms.${n}(ctx, ${nameSubPanel});
			<#else>
		final forms.${n} ${id}=new forms.${n}(ctx,"${conversation.id}.${window.id}");
			</#if>	
		</#if>
</#macro>		