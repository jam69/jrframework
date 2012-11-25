<#--
   Cada vez me gustan mas los comentarios
-->   
  		//editor TextArea
  		<#import "../util/UtilEditor.ftl" as util>	
	 	<#assign scroll = "scroll" + id>
		JScrollPane ${scroll} = new JScrollPane();
		final JTextArea ${id} = new JTextArea();
		${scroll}.setViewportView(${id});
		${id}.setOpaque(true);
		${id}.setFont(RunnerLF.getFont("EditorString.font"));	   
		<#assign filas="-1">
		<#assign columnas="-1">			 
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign editores = util.getListParametros(strParametrosEditor)> 
				<#if editores[0]??>
				 	<#assign filas =editores[0]/>
				 </#if>
				<#if editores[1]??>
				 	<#assign columnas =editores[1]/>		
				</#if>	
			</#if>			
		</#if>			 
		<#if filas!="-1">
			${id}.setRows(Integer.parseInt(${filas}));	
		</#if>
		<#if columnas!="-1">
			 ${id}.setColumns(Integer.parseInt(${filas}));	
		</#if>
		<#if item.@objectVar[0]??>
		${id}.addFocusListener(new FocusAdapter(){
	    	public void focusLost(FocusEvent ev){
	    		String v = ${id}.getText();
	    		if (v!=null){
	    			if (ctx.get("var")!=null && v.equals(ctx.get("${item.@objectVar}").toString())) return ;
	    		}
    			if (v!=null&& v.trim().length()>0){
    				ctx.put("${item.@objectVar}",v);
    			}else{
    				${id}.setText("");
    				ctx.put("${item.@objectVar}", null);
    			}
	    	}
    	});
    	if (ctx.get("${item.@objectVar}")!=null && ctx.get("${item.@objectVar}").toString().trim().length()>0){
    				${id}.setText(ctx.get("${item.@objectVar}").toString());
    			}else{
    				${id}.setText("");
    			}
    	ctx.addListener("${item.@objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    			
				if (ctx.get("${item.@objectVar}")!=null){
				if (ctx.get("${item.@objectVar}").toString().equals(${id}.getText())) return;
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
    	${id}.setEnabled((Boolean)ctx.check("${enableCond}"));
    	for(String varName:ctx.evalVarNames("${enableCond}")){
    		ctx.addListener(varName, new ContextListener(){
    			public void valueChanged(String key,Object value){
    				${id}.setEnabled((Boolean)ctx.check("${enableCond}"));
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
			}
		else{
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
		<#assign colorBackGro =id + "ColorBack">
		<#if controlValBack == "true">
		final Color ${colorBackGro} = (Color)${valBack};
		<#else>
		final Color ${colorBackGro} = null;
		</#if>			
		<#if item.@errorCond[0]??>
			<#assign errorCond = item.@errorCond?j_string>
		if(ctx.check("${errorCond}")){
			${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
			}
		else{
			${id}.setBackground(${colorBackGro});
			}
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
    	//fin editor TextArea
    	