<#--
        un comentario nunca viene mal
        
-->        
		final JTextField ${id}=new JTextField();
		${id}.setOpaque(true);
		${id}.setFont(RunnerLF.getFont("EditorString.font"));
		<#if item.@editor?index_of("(")!=-1>
			<#assign p = item.@editor?split("(")[1]>
			<#assign parametros = p?replace(")","")>
		 	<#list parametros?split(",") as cadena>
		 		<#if cadena?matches("[0-9]+")>
					<#assign longitud= cadena>
		${id}.setColumns(${longitud});
		${id}.setDocument(new CharDocument(${longitud}));			
				<#else>
					<#if cadena=="C" ||cadena=="c" >
		${id}.setHorizontalAlignment(JTextField.CENTER);
		  			<#else>
				  		<#if cadena=="D" ||cadena=="d" >
		${id}.setHorizontalAlignment(JTextField.RIGHT);
				 		<#else>
		${id}.setHorizontalAlignment(JTextField.LEFT);
						</#if>		 
		 			</#if>
				</#if>		 
		 	</#list>
		</#if>
		<#if item.@data[0]??>
			<#assign data =item.@data[0]>
		${id}.setText("${data}");
		</#if>
		<#if item.@objectVar[0]??>
		${id}.addFocusListener(new FocusAdapter(){
    		public void focusLost(FocusEvent ev){
    			String v = ${id}.getText();
    			if (v!=null){
    				if (v.equals(ctx.get("${item.@objectVar}"))) return ;
    			}
				if (v!=null&& v.trim().length()>0){
					ctx.put("${item.@objectVar}",v);
				}else{
					${id}.setText("");
					ctx.put("${item.@objectVar}", null);
				}
    		}
		});
		${id}.addActionListener(new ActionListener(){
   	        public void actionPerformed(ActionEvent arg0) {
   	        	Object v=ctx.get("${item.@objectVar}");
   	        	if (v==null || !v.toString().equals(${id}.getText())){
					ctx.put("${item.@objectVar}",${id}.getText());
				}                	                    
            }                
        });
        Object v=ctx.get("${item.@objectVar}");
		if (v!=null && v.toString().trim().length()>0){
			${id}.setText(ctx.get("${item.@objectVar}").toString());
   			}else{
    			${id}.setText("");
		}
		ctx.addListener("${item.@objectVar}", new ContextListener(){
			public void valueChanged(String key,Object value){
				if (ctx.get("${item.@objectVar}")!=null){
					if (ctx.get("${item.@objectVar}").toString().equals(${id}.getText())){
						return;
						}
					String valor = ctx.get("${item.@objectVar}").toString();
					${id}.setText(valor);
				}else{
					${id}.setText("");
				}
    	  }
		});
    	</#if>    	
    	<#if item.@enableCond[0]??>
    	<#assign enableCond = item.@enableCond?j_string>
    	${id}.setEnabled(ctx.check("${enableCond}"));
    	<#assign nameLista = "var"+id>
    	for(String varName: ctx.evalVarNames("${enableCond}")){
			ctx.addListener(varName, new ContextListener(){
				public void valueChanged(String key,Object value){
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
			
		String valBorder = RunnerLF.getLabel("mandatory.border");
		if(valBorder!=null &&
			!valBorder.equals("")&&
			!valBorder.equalsIgnoreCase("default")){
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
			${id}.setBackground(${colorBackGro});
		}
			<#assign nameLista = "error"+id>
		for(String varName:ctx.evalVarNames("${errorCond}")){
			ctx.addListener(varName, new ContextListener(){
				public void valueChanged(String key,Object value){
					if(ctx.check("${errorCond}")){
						${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
					}else{
						${id}.setBackground(${colorBackGro});
					}
				}	
			});
		}									
		</#if>
    