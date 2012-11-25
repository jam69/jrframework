 			
 		<#import "../util/UtilEditor.ftl" as util>
		<#assign min="0">
		<#assign max="100">
		<#assign inc="-1">
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign editores = util.getListParametros(strParametrosEditor)> 
					<#if editores[0]??>
					 	<#assign min =editores[0]/>
					 </#if>
					 <#if editores[1]??>
					 	<#assign max =editores[1]/>		
					</#if>	
					<#if editores[2]??>
					 	<#assign inc =editores[2]/>		
					</#if>	
			</#if>			
		</#if>
		final JSpinner ${id}=new JSpinner();
		${id}.setBorder(null);
		<#assign modelspin="model"+id>
		SpinnerModel ${modelspin}=new SpinnerNumberModel(${min},${min},${max},${inc});
		${id}.setModel(${modelspin});
		<#assign editor = "deeditor" + id>		
		final JSpinner.DefaultEditor ${editor} = (JSpinner.DefaultEditor)${id}.getEditor();	
 		${editor}.getTextField().setUI(new javax.swing.plaf.basic.BasicFormattedTextFieldUI());
 		${editor}.getTextField().setBorder(BorderFactory.createCompoundBorder(
  			BorderFactory.createMatteBorder(2, 0, 2, 0, Color.white), 
  			BorderFactory.createMatteBorder(1, 1, 1, 0, Color.black)));
		${id}.setFont(RunnerLF.getFont("EditoRangoInt.font"));
		<#if item.@objectVar[0]??>
		${id}.addFocusListener(new FocusAdapter(){
	    	public void focusLost(FocusEvent ev){
	    		Integer v = (Integer)${id}.getValue();
    			if (v!=null ){
    				ctx.put("${item.@objectVar?j_string}", v);
    			}else{
    				//${id}.setValue(null);
    				ctx.put("${item.@objectVar}", null);
    			}
	    	}
    	});
	
    	${id}.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				ctx.put("${item.@objectVar}",${id}.getValue());
			}
		});
		
			<#assign valor = "valor"+id>
		Object ${valor};
		try{
    				${valor} = ctx.get("${item.@objectVar}");
    				${id}.setValue(Integer.parseInt(${valor}.toString()));
    			}catch(Exception t){
    				${id}.setValue(${min});
    			}
		//	else
			//	${id}.setValue(null);
    	ctx.addListener("${item.@objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    			try{
    				Object valor = ctx.get("${item.@objectVar}");
    				${id}.setValue(Integer.parseInt(valor.toString()));
    			}catch(Exception t){
    				${id}.setValue(${min});
    			}
    		}
    	});
		</#if>
		<#if item.@enableCond[0]??>
    	${id}.setEnabled(ctx.check("${item.@enableCond?j_string}"));
    	for(String varName:ctx.evalVarNames("${item.@enableCond?j_string}")){
    		ctx.addListener(varName, new ContextListener(){
    			public void valueChanged(String key,Object value){
    				${id}.setEnabled(ctx.check("${item.@enableCond?j_string}"));
    			}
    		});
    	}
		</#if>
		<#assign tooltip    = item.@tooltip > 
		<#if tooltip?is_string> 
 			<#if tooltip?starts_with("$") >		
				${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)?j_string}"));
				ctx.addListener("${tooltip?substring(1)}", new ContextListener(){
				public void valueChanged(String key, Object value) {
					 ${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)?j_string}"));
					}
				});		
			<#else>
			${id}.setToolTipText("${tooltip?j_string}");
			</#if>
		</#if>	
	
		<#assign controlValBack="false"> 	
		<#assign mandatory  = item.@mandatory >
		<#if mandatory?has_content> 
			<#if mandatory=="true"> 
				<#assign controlValBack="true"> 	
				<#assign valBackRangoInt="valBackRangoInt" + id> 	
		Object ${valBackRangoInt} = RunnerLF.getLabel("mandatory.background");
		if(${valBackRangoInt}!=null &&
			!${valBackRangoInt}.toString().equals("")&&
			!${valBackRangoInt}.toString().equalsIgnoreCase("default")){
			${valBackRangoInt} = RunnerLF.getColor("mandatory.background");
			${id}.setBackground((Color)${valBackRangoInt});
			${editor}.getTextField().setBackground((Color)${valBackRangoInt});
			}
		else{
		${valBackRangoInt}=null;
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
		<#if item.@errorCond[0]??>
			<#assign colorBackGro =id + "ColorBack">
			<#if controlValBack == "true">
		final Color ${colorBackGro} = (Color)${valBackRangoInt};
			<#else>
		final Color ${colorBackGro} = null;
			</#if>	
			<#assign errorCond = item.@errorCond?j_string>
		if(ctx.check("${errorCond}")){
				${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
				${editor}.getTextField().setBackground(RunnerLF.getColor("CompBackground.error"));
			}
		else{
			${id}.setBackground( ${colorBackGro});
			${editor}.getTextField().setBackground(${colorBackGro});
		}
		for(String varName:ctx.evalVarNames("${errorCond}")){
			ctx.addListener(varName, new ContextListener(){
				public void valueChanged(String key,Object value){
					if(ctx.check("${errorCond}")){
						${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
						${editor}.getTextField().setBackground(RunnerLF.getColor("CompBackground.error"));
					}
					else{
						${id}.setBackground( ${colorBackGro});
						${editor}.getTextField().setBackground(${colorBackGro});
					}
				}
			});
		}
		</#if>	
		
		 