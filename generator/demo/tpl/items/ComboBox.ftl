<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		<#assign mandatory  = item.@mandatory >
		final JComboBox ${id}=new JComboBox();
		${id}.setFont(RunnerLF.getFont("textfield.font"));
		${id}.setBackground(null);
		<#if item.@nullAllowed=="true">
			<#if item.@nullName?is_string>
		final String nullName="${item.@nullName}";
			<#else>
		final String nullName="";
			</#if>
		${id}.addItem(nullName);
		</#if>
		<#if item.@data[0]?? && item.@data[0]!="" && ! item.@variable[0]??>
			<#assign datos =  item.@data?split(",")>
			<#list datos as pp>
		${id}.addItem("${pp}");
			</#list>
		</#if>
		<#if item.@variable[0]??>
			<#assign nameVariable = "var"+id>
			<#-- creo que esto no hace falta
			${id}.removeAllItems();
			<#if item.@nullAllowed=="true">
			${id}.addItem(nullName);			
			</#if>		
			-->
		Collection<?> ${nameVariable}= (Collection<?>)ctx.get("${item.@variable}");
		if (${nameVariable}!=null){
			for (Object x:${nameVariable}){
				${id}.addItem(x);
			}			
		}
		ctx.addListener("${item.@variable}", new ContextListener(){
			@Override
			public void valueChanged(String key, Object value) {
				${id}.removeAllItems();
			<#if item.@nullAllowed=="true">
				${id}.addItem(nullName);
			</#if>
				Collection<?> ${nameVariable}= (Collection<?>)ctx.get("${item.@variable}");
				if (${nameVariable}==null){					
					return;
				}			
				for (Object x:${nameVariable}){
					${id}.addItem(x);
				}				
			}
		});
		</#if>
		
		<#if item.@varSelected[0]??>
			<#assign variableSel =item.@varSelected>		
		${id}.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
        	   	 if(${id}.getSelectedItem() == null){
        	   		 ctx.put("${variableSel}", null);
            <#if item.@nullAllowed=="true">
        	     } else if(${id}.getSelectedItem().equals(nullName)){
        	    	 ctx.put("${variableSel}", null);
			</#if>            	    	 
        	     } else {
        	    	 ctx.put("${variableSel}",${id}.getSelectedItem());
        	     }
        	}
		});			
		if (ctx.get("${variableSel}")==null){
			<#if item.@nullAllowed=="true">
			${id}.setSelectedItem(nullName);
			<#else>
			${id}.setSelectedItem(null);
			</#if>
		}else{
			${id}.setSelectedItem(ctx.get("${variableSel}"));
		}
		ctx.addListener("${variableSel}", new ContextListener(){
			@Override
			public void valueChanged(String key, Object value) {
				if (value==null){
			<#if item.@nullAllowed=="true">
					${id}.setSelectedItem(nullName);
			<#else>
					${id}.setSelectedItem(null);
			</#if>
				}else{
					${id}.setSelectedItem(ctx.get("${variableSel}"));
				}
			}
			});
		</#if> 		
		<#if item.@tooltip[0]??>
			<#assign tooltip = item.@tooltip >
			<#if tooltip?starts_with("$") >
	 	${id}.setToolTipText(ctx.get("${tooltip?substring(1)}");
	 	ctx.addListener(${tooltip?substring(1)},new ContextListener(){
			public void valueChanged(String key, Object value) {
				 ${id}.setToolTipText(ctx.get(key));
			}
		 });
			<#else>
	 	${id}.setToolTipText("${tooltip?j_string}");
			</#if>
		</#if>
		<#if item.@enableCond[0]??>
			<#assign enableCond =item.@enableCond?j_string>
		${id}.setEnabled(ctx.check("${enableCond}"));
		for(String varName:	ctx.evalVarNames("${enableCond}")){
			ctx.addListener(varName, new ContextListener(){
				@Override
				public void valueChanged(String key, Object value) {
					${id}.setEnabled(ctx.check("${enableCond}"));
				}
			});
		}
		</#if>
		<#assign controlValBack="false"> 	
		<#assign mandatory  = item.@mandatory >
		<#if mandatory?has_content> 
			<#if mandatory=="true"> 
				<#assign valBack="valBack" + id> 
				<#assign controlValBack="true"> 	
		Object ${valBack} = RunnerLF.getLabel("mandatory.background");
		if(${valBack}!=null &&
			!${valBack}.toString().equals("")&&
			!${valBack}.toString().equalsIgnoreCase("default")){
				${valBack} = RunnerLF.getColor("mandatory.background");
				${id}.setBackground((Color)${valBack});
		}else{
			${valBack}=null;
		}
				<#assign valBorder="valBorder" + id> 
		Object ${valBorder} = RunnerLF.getLabel("mandatory.border");
		if(${valBorder} != null &&
			!${valBorder}.toString().equals("")&&
			!${valBorder}.toString().equalsIgnoreCase("default")){
				${id}.setBorder(BorderFactory.createLineBorder(RunnerLF.getColor("mandatory.border")));
		}
			</#if>	
		</#if>	
		<#if item.@errorCond[0]??>
			<#assign colorBackGro =id + "ColorBack">
			<#if controlValBack == "true">
		final Color ${colorBackGro} = (Color)${valBack};
			<#else>
		final Color ${colorBackGro} = null;
			</#if>	
			<#assign errorCond = item.@errorCond?j_string>
		if(ctx.check("${errorCond}")){
			${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
		}else{
			${id}.setBackground( ${colorBackGro});
		}
    	for(String v:ctx.evalVarNames("${errorCond}")){
    		ctx.addListener(v,new ContextListener(){
				public void valueChanged(String key, Object value) {							
					if(ctx.check("${errorCond}")){
						${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
					}else{
						${id}.setBackground( ${colorBackGro});
					}
				}	        			
        	});
       	}
		</#if>	
