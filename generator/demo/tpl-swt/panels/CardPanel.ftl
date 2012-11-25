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
		// CardPanel ${panel}
		CardLayout ${id}Layout =new CardLayout(); 
		JTabPanel ${id}=new JTabPanel(${id}Layout);
<#assign panel0 = panel />
<#assign id0 = id />
<#list panel0.panels as phijos >
	<#assign panel = phijos>
	<#assign id = id0 + phijos_index>
	<#include panel+'.ftl' parse=true/>
		${id0}.add(${panel.label},${id});
</#list>
<#assing v = panel.getModelProperty("varName") > 	
		ctx.addPropertyChangeListener("${v}",new PropertyChangeListener(){
			public void changeProperty(ChangePropertyEvent ev){
				Object v=ctx.get("${v}");
				${id}Layout.active(v);
				}
		});
		//  Fin CardPanel