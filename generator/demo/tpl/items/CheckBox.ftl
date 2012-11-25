<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// CheckBox 
		final JCheckBox ${id}=new JCheckBox();
		<#if item.@text[0]?? >
			<#assign texto = item.@text?j_string>
		${id}.setText("${texto}");
		</#if>
		<#if item.@marcado=="true" >
		${id}.setSelected(Boolean.TRUE);
		<#else>
		${id}.setSelected(Boolean.FALSE);
		</#if>
		<#assign tooltip    = item.@tooltip >
		<#if tooltip?is_string> 
 			<#if tooltip?starts_with("$") >		
		${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
		ctx.addListener("${tooltip?substring(1)}", new ContextListener(){
			public void valueChanged(String key, Object value) {
				${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
			}
		});		
			<#else>
		${id}.setToolTipText("${tooltip}");
			</#if>
		</#if>	
		
		<#if item.@variable[0]?? >
		ctx.put("${item.@variable}",${id}.isSelected());
		${id}.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
           		ctx.put("${item.@variable}",${id}.isSelected());    	
        	}
        });
		ctx.addListener("${item.@variable}", new ContextListener(){
			@Override
			public void valueChanged(String key, Object value) {
				if (ctx.get("${item.@variable}").toString().equalsIgnoreCase("true")){
					${id}.setSelected(Boolean.TRUE);
				}else{
					${id}.setSelected(Boolean.FALSE);
				}
			}
		});
		</#if>
		
		<#if item.@enableCond[0]??>
			<#assign enableCond =item.@enableCond?j_string>
		${id}.setEnabled(ctx.check("${enableCond}"));
		for(String varName:ctx.evalVarNames("${enableCond}")){
			ctx.addListener(varName, new ContextListener(){
				@Override
				public void valueChanged(String key, Object value) {
					${id}.setEnabled(ctx.check("${enableCond}"));
				}
			});
		}
		</#if>
		// Fin-CheckBox
				