<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> TextArea.ftl  
		<#assign textArea ="textArea"+id>
		final JTextArea ${textArea}=new JTextArea();
		JScrollPane ${id} = new JScrollPane(${textArea});
		${textArea}.setFont(RunnerLF.getFont("TextArea.font"));
		${textArea}.setForeground(RunnerLF.getColor("TextArea.colorletras"));
		
		<#if item.@data[0]??>
			${textArea}.setText("${item.@data}");
		</#if>
		
		<#if item.@variable[0]??>
		<#assign obj="obj"+id>
		Object ${obj}=ctx.get("${item.@variable}");
		${textArea}.setText(${obj}!=null?${obj}.toString():"");
		${textArea}.addFocusListener(new FocusAdapter(){
	    	public void focusLost(FocusEvent ev){
	    		String v = ${textArea}.getText();
    			if (v!=null&& v.trim().length()>0){
    				${textArea}.setText(v.toString());
    				ctx.put("${item.@variable}", v);
    			}else{
    				${textArea}.setText("");
    				ctx.put("${item.@variable}", null);
    			}
	    	}
    	});
		ctx.addListener("${item.@variable}",new ContextListener(){
			public void valueChanged(String key, Object value){
				Object obj=ctx.get("${item.@variable}");
				${textArea}.setText(obj!=null?obj.toString():"");
			}
		});		
		</#if>		
		<#if item.@enableCond??>		
			<#if item.@enableCond?is_string>
				<#assign enableCond = item.@enableCond?j_string>
				${textArea}.setEnabled(ctx.check("${enableCond}"));
	   			for(String varName:ctx.evalVarNames("${enableCond}")){
	   				ctx.addListener(varName, new ContextListener(){
	    				public void valueChanged(String key,Object value){
	    					${textArea}.setEnabled(ctx.check("${enableCond}"));
	    				}
	    			});
	    		}    
			</#if>
		</#if>
		<#assign tooltip    = item.@tooltip >
		<#if tooltip?is_string> 
 			 <#if tooltip?starts_with("$") >		
				${textArea}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
				ctx.addListener("${tooltip?substring(1)}", new ContextListener(){
				public void valueChanged(String key, Object value) {
					 ${textArea}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
					}
				});		
			 <#else>
			${textArea}.setToolTipText("${tooltip}");
			 </#if>
		</#if>
		
		<#assign mandatory  = item.@mandatory >
		<#if mandatory?has_content> 
		<#if mandatory=="true"> 
		<#assign valBack="valBack" + id> 
		Object ${valBack} = RunnerLF.getLabel("mandatory.background");
    	if(${valBack}!=null &&
			!${valBack}.toString().equals("")&&
			!${valBack}.toString().equalsIgnoreCase("default")){
			${textArea}.setBackground(RunnerLF.getColor("mandatory.background"));
			}
		<#assign valBorder="valBorder" + id> 
		Object ${valBorder} = RunnerLF.getLabel("mandatory.border");
		if(${valBorder}!=null &&
			!${valBorder}.toString().equals("")&&
			!${valBorder}.toString().equalsIgnoreCase("default")){
			${textArea}.setBorder(BorderFactory.createLineBorder(RunnerLF.getColor("mandatory.border")));
		}
		</#if>	
		</#if>	
		// Fin-TextArea
		