<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> SpinnerDate
		
		
		final JSpinner ${id}=new JSpinner();
		${id}.setFont(RunnerLF.getFont("SpinnerDate.font"));
		<#if item.@formato[0]??>
			<#assign formato=item.@formato>
			<#assign formatFecha="formatFecha">
		final SimpleDateFormat ${formatFecha} = new SimpleDateFormat("${formato}");
		</#if>
		
	
	
		
		<#assign minDate="minDate">
		Date ${minDate}=null;
		<#if item.@min[0]??>
		<#assign min=item.@min>
		<#if min!="null">
		
		try {
			${minDate} = ${formatFecha}.parse("${min}");
		} catch (Exception e1) {
		} 
		</#if>	
		</#if>
		<#assign maxDate="maxDate">
		Date ${maxDate}=null;
		<#if item.@max[0]??>
		<#assign max=item.@max>	
		<#if max!="null">
		try {
			maxDate = ${formatFecha}.parse("${max}");
		} catch (ParseException e1) {
			maxDate=new Date();
		}			
		</#if>
		</#if>
		<#assign step="MONTH">
		<#if item.@step[0]??>
		<#assign step=item.@step>
		</#if>
		if(${minDate}!=null || ${maxDate}!=null){
		<#assign model ="model"+id> 
			if(${minDate}==null){
				es.indra.isl.humandev.swing.ModelSpinDate ${model} = new es.indra.isl.humandev.swing.ModelSpinDate(new Date(),${minDate},${maxDate},es.indra.humandev.runner.core.utils.DateUtils.obtenerStep("${step}"));
				${model}.setCalendarField(java.util.Calendar.MONTH);
				${id}.setModel(${model});	
			}
			else{
				es.indra.isl.humandev.swing.ModelSpinDate ${model} = new es.indra.isl.humandev.swing.ModelSpinDate(${minDate},${minDate},${maxDate},es.indra.humandev.runner.core.utils.DateUtils.obtenerStep("${step}"));
				${model}.setCalendarField(java.util.Calendar.MONTH);
				${id}.setModel(${model});	
				}	
		}
		else{
			${id}.setModel(new javax.swing.SpinnerDateModel());
		}
		<#if formato??>	
		${id}.setEditor(new JSpinner.DateEditor(${id}, "${formato}"));
		</#if>
		<#assign editor = "deeditor" + id>		
		final JSpinner.DefaultEditor ${editor} = (JSpinner.DefaultEditor)${id}.getEditor();
 		${editor}.getTextField().setUI(new javax.swing.plaf.basic.BasicFormattedTextFieldUI());
 		${editor}.getTextField().setBorder(BorderFactory.createCompoundBorder(
  											BorderFactory.createMatteBorder(2, 0, 2, 0, Color.white), 
  											BorderFactory.createMatteBorder(1, 1, 1, 0, Color.black)));
		
		<#if item.@variable[0]??>
		<#assign variable=item.@variable[0]>
		<#assign obj="obj"+id>
		Object ${obj}=ctx.get("${item.@variable}");
		if(${obj}!=null)
			${id}.setValue(${obj}!=null?${obj}.toString():"");
		${id}.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
				ctx.put("${item.@variable}",c1.getValue());
			}
		});
		ctx.addListener("${variable}",new ContextListener(){
				public void valueChanged(String key, Object value) {
				value = ctx.get("${item.@objectVar}");
				if(!value.equals(${id}.getValue())){
					Object obj=ctx.get(key);
					${id}.setValue(obj!=null?obj.toString():"");
					}
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
		// Fin-SpinnerDate
		