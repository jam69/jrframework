<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> Spinner
		<#assign min="0">
		<#assign max="100">
		<#assign step="0.1">	
		<#if item.@min[0]??>
		<#assign min=item.@min>
		</#if>
		<#if item.@max[0]??>
		<#assign max=item.@max>
		</#if>
		<#if item.@step[0]??>
		<#assign step=item.@step>
		</#if>
		<#assign model ="model"+id> 
		
		<#assign minFloat ="minFloat"+id> 
		Float ${minFloat} = Float.parseFloat("${min}");
		<#assign maxFloat ="maxFloat"+id> 
		Float ${maxFloat} = Float.parseFloat("${max}");
		<#assign stepFloat ="stepFloat"+id> 
		Float ${stepFloat} = Float.parseFloat("${step}");
		
		SpinnerModel ${model}=new SpinnerNumberModel(${minFloat}, 
													${minFloat}, 
													${maxFloat}, 
													${stepFloat});
		final JSpinner ${id}=new JSpinner(${model});
		<#assign editor = "deeditor" + id>
		final JSpinner.DefaultEditor ${editor} = (JSpinner.DefaultEditor)${id}.getEditor();
 		${editor}.getTextField().setUI(new javax.swing.plaf.basic.BasicFormattedTextFieldUI());
 		${editor}.getTextField().setBorder(BorderFactory.createCompoundBorder(
  											BorderFactory.createMatteBorder(2, 0, 2, 0, Color.white), 
  											BorderFactory.createMatteBorder(1, 1, 1, 0, Color.black)));
		${id}.setFont(RunnerLF.getFont("SpinnerFloat.font"));
		<#if item.@variable[0]??>
		<#assign obj="obj"+id>
		Object ${obj}=ctx.get("${item.@variable}");
		if( ${obj}!=null)
			${id}.setValue(${obj}!=null?Float.parseFloat(${obj}.toString()):null);
		${id}.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				ctx.put("${item.@variable}",${id}.getValue());
			}
		});
		ctx.addListener("${item.@variable}", new ContextListener(){
			public void valueChanged(String key, Object value) {
				value = ctx.get("${item.@variable}");
				if (ctx.get("${item.@variable}")==null)return;
				Object obj=ctx.get("${item.@variable}");
				${id}.setValue(obj!=null?Float.parseFloat(obj.toString()):null);
			}
		});
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
		
		<#if item.@enableCond[0]??>
				<#assign enableCond =item.@enableCond?j_string>
		${id}.setEnabled(ctx.check("${enableCond}"));
		<#assign listaVar = "listaVar"+id>
		ArrayList ${listaVar}= ctx.evalVarNames("${enableCond}");
		for(int i =0;i<${listaVar}.size();i++ ){
		ctx.addListener(${listaVar}.get(i).toString(), new ContextListener(){

				@Override
				public void valueChanged(String key, Object value) {
					${id}.setEnabled(ctx.check("${enableCond}"));
					
				}
				
			});
		
		}
		</#if>
		<#assign mandatory  = item.@mandatory >
		<#if mandatory?has_content> 
		<#if mandatory=="true"> 
		<#assign valBack="valBack" + id> 
		Object ${valBack} = RunnerLF.getLabel("mandatory.background");
    		if(${valBack}!=null &&
			!${valBack}.toString().equals("")&&
			!${valBack}.toString().equalsIgnoreCase("default")){
			${valBack} = RunnerLF.getColor("mandatory.background");
			${id}.setBackground((Color)${valBack});
			${editor}.getTextField().setBackground((Color)${valBack});
			}
		<#assign valBorder="valBorder" + id> 
		Object ${valBorder} = RunnerLF.getLabel("mandatory.border");
			if(${valBorder}!=null &&
			!${valBorder}.toString().equals("")&&
			!${valBorder}.toString().equalsIgnoreCase("default")){
			${editor}.getTextField().setBorder(BorderFactory.createCompoundBorder(
  											BorderFactory.createMatteBorder(2, 0, 2, 0, Color.white), 
  											BorderFactory.createMatteBorder(1, 1, 1, 0, RunnerLF.getColor("mandatory.border"))));
		}
		</#if>	
		</#if>
			
		// Fin-Spinner
		
		