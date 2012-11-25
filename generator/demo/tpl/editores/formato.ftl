<#--  
	Si todavía no tenía comentario... pues ya lo tiene.
-->	 
		 <#import "../util/UtilEditor.ftl" as util>
		 final JFormattedTextField ${id} =new JFormattedTextField();
		 ${id}.setOpaque(true);
		 ${id}.setFont(RunnerLF.getFont("EditorDouble.font"));
		 ${id}.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		<#assign formatText = "-1">
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 	<#assign formatText =valores[0]/>
				</#if>
			</#if>
		</#if>
		<@util.setAlineacion id=id ali=ali/>
		<#if  formatText!="-1">
		try {
			${id}.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("${formatText}")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		</#if>
		<#if item.@data[0]??>
			<#assign data =item.@data[0]>
		${id}.setValue("${data}");
		</#if>
		<#if item.@objectVar[0]??>
			<#assign objectVar = item.@objectVar>
		${id}.addFocusListener(new FocusAdapter(){
	    	public void focusLost(FocusEvent ev){
	    		String v = ${id}.getText();
	    		if (v!=null){
	    			if (v.equals((String)ctx.get("${item.@objectVar}"))) return ;
	    		}
    			if (v!=null && v.trim().length()>0){
    				try {
    					ctx.put("${objectVar}", v);
    				} catch (Exception e) {
    					${id}.setText("");
    					//para que no salte el listener de la perdida de foco
    				}
    			}else{
    				${id}.setText("");
    				ctx.put("${objectVar}", null);
    			}
	    	}
		});
    	${id}.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
	    		String v = ${id}.getText();
	    		if (v!=null){
	    			if (v.equals((String)ctx.get("${item.@objectVar}"))) return ;
	    		}
    			if (v!=null && v.trim().length()>0){
    				try {
    					ctx.put("${objectVar}", v);
    				} catch (Exception e) {
    					${id}.setText("");
    					//para que no salte el listener de la perdida de foco
    				}
    			}else{
    				${id}.setText("");
    				ctx.put("${objectVar}", null);
    			}
	    	}
		});
    	${id}.setText((String)ctx.get("${objectVar}"));
    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    			if (ctx.get("${objectVar}")==null)return ;
    			if (value.toString().equals(${id}.getText())) return;
    			String  valor = (String)ctx.get("${item.@objectVar}");
				if (value!=null){
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
    	for(String varName:ctx.evalVarNames("${enableCond}")){
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
				<#assign valBorder="valBorder" + id> 
		Object ${valBorder} = RunnerLF.getLabel("mandatory.border");
		if(${valBorder}!=null &&
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
		if((Boolean)ctx.check("${errorCond}")){
			${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
		}else{
			${id}.setBackground(${colorBackGro});
		}
		for(String varName:ctx.evalVarNames("${errorCond}")){
			ctx.addListener(varName, new ContextListener(){
				public void valueChanged(String key,Object value){
					if((Boolean)ctx.check("${errorCond}")){
						${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
					}else{
						${id}.setBackground(${colorBackGro});
					}
				}
			});
		}
		</#if>		
		