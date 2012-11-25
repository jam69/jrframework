<#--
   Que chulos los comentarios
-->   	
		<#import "../util/UtilEditor.ftl" as util>
		final JFormattedTextField ${id} =new JFormattedTextField();
		${id}.setOpaque(true);
		${id}.setFont(RunnerLF.getFont("EditorDouble.font"));
		${id}.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		<#assign enteros="-1">
		<#assign decimales="-1">
		<#assign locale="ES">
		 
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">	
				<#assign editores = util.getListParametros(strParametrosEditor)> 
					<#if editores[0]??>
					 	<#assign enteros =editores[0]/>
					 </#if>
					<#if editores[1]??>
					 	<#assign decimales =editores[1]/>		
					</#if>		
					<#if editores[2]??>
					 	<#assign locale =editores[2]/>		
					</#if>		
			</#if>			
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
		<#assign nf ="nf"+id>
		final NumberFormat ${nf} = NumberFormat.getNumberInstance(new Locale("${locale}"));
		<#if decimales!="-1">
		${nf}.setMaximumFractionDigits(${decimales});
		</#if>
		<#if enteros!="-1">
		${nf}.setMaximumIntegerDigits(${enteros});
		</#if>
		<#if item.@data[0]??>
		<#assign data =item.@data[0]>
		${id}.setText(${nf}.format(${data}));
		</#if>
		<#if  item.@objectVar[0] ??>
		${id}.addFocusListener(new FocusAdapter(){
	    	public void focusLost(FocusEvent ev){
	    		String v = ${id}.getText();
	    		if (v!=null){
	    			if (ctx.get("${item.@objectVar}") != null &&
	    				v.equals((ctx.get("${item.@objectVar}").toString()))) return ;
	    		}
    			if (v!=null&& v.trim().length()>0){
    				try {
    					Double valor = ${nf}.parse((${nf}.format(${nf}.parse(v)))).doubleValue();
    					${id}.setValue(valor);
    					ctx.put("${item.@objectVar}", valor);
    				} catch (Exception e) {
    					${id}.setText("");
    					//para que no salte el listener de la perdida de foco
    				}
    			}else{
    				${id}.setText("");
    				ctx.put("${item.@objectVar}", null);
    			}
	    	}
    	});
    	${id}.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
                String v = ${id}.getText();
    			if (v!=null&& v.trim().length()>0){
    				try {
    					Double valor = ${nf}.parse((${nf}.format(${nf}.parse(v)))).doubleValue();
    					${id}.setValue(valor);
    					ctx.put("${item.@objectVar}", valor);
    				} catch (Exception e) {
    					${id}.setText("");
    					//para que no salte el listener de la perdida de foco
    				}
    			}else{
    				${id}.setText("");
    				ctx.put("${item.@objectVar}", null);
    			}
                }
        });
    	try {
			Double valor;
			if(ctx.get("${item.@objectVar}") instanceof Double){
				valor = ((Double)ctx.get("${item.@objectVar}"));
				${id}.setText(${nf}.format(valor));
			}
			else
				${id}.setText("");
			
		} catch (Exception e) {
			${id}.setText("");
		}
    	ctx.addListener("${item.@objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    		if (ctx.get("${item.@objectVar}")==null)return ;
    			value = ctx.get("${item.@objectVar}"); 
				if (value!=null){
				if (value.toString().equals(${id}.getText())) return;
					try {
						Double valor;
						if(value instanceof Double)
							valor = ((Double)value);
						else
							 valor = new Double(${nf}.parse(value.toString()).toString());
						${id}.setValue(valor);
					} catch (Exception e) {
						//para que no salte el listener de la perdida de foco
					}
				}else{
					${id}.setText("");
				}
          }
    	});
    	</#if>
    	<#if item.@enableCond[0]??>
    	<#assign enableCond = item.@enableCond?j_string>
    	${id}.setEnabled((Boolean)ctx.check("${enableCond}"));
    	for(String varName: ctx.evalVarNames("${enableCond}")){
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
		if((Boolean)ctx.check("${errorCond}")){
			${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
			}
		else{
			${id}.setBackground(${colorBackGro});
			}
		for(String varName:	ctx.evalVarNames("${errorCond}")){
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