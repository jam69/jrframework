		//--> Integer
		
		 <#import "../util/UtilEditor.ftl" as util>
		 
		 final JFormattedTextField ${id} =new JFormattedTextField();
		 ${id}.setOpaque(true);
		 ${id}.setFont(RunnerLF.getFont("EditorCurrency.font"));
		 ${id}.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		 
		<#assign locale="ES">
		 
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 	<#assign locale =valores[0]/>
				</#if>
			</#if>
			
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
	
		<#assign nfEdicion ="nfEdicion"+id/>
		<#assign nfDisplay ="nfDisplay"+id/>
		final NumberFormat ${nfEdicion}= NumberFormat.getNumberInstance(
					es.indra.humandev.runner.core.utils.LocaleUtils.getLanguage("${locale}"));
		final NumberFormat  ${nfDisplay}= NumberFormat.getCurrencyInstance(
				es.indra.humandev.runner.core.utils.LocaleUtils.getLanguage("${locale}"));
		
		<#assign nfFormatterEdicion ="formatterEdicion"+id/>
		<#assign nfFormatterDisplay ="formatterDisplay"+id/>
		final javax.swing.text.NumberFormatter ${nfFormatterEdicion} = new javax.swing.text.NumberFormatter(${nfEdicion});
		final javax.swing.text.NumberFormatter ${nfFormatterDisplay} =new javax.swing.text.NumberFormatter(${nfDisplay});
		
		${nfDisplay}.setGroupingUsed(false);
		${nfFormatterEdicion}.setAllowsInvalid(true);
		
		
		<#assign factory ="factory"+id>
		DefaultFormatterFactory ${factory} =
			new DefaultFormatterFactory(${nfFormatterDisplay},${nfFormatterDisplay}, ${nfFormatterEdicion});
		${id}.setFormatterFactory(${factory});
		
		
		
		<#if item.@data[0]??>
		<#assign data =item.@data[0]>
		${id}.setText(${nf}.format(${data}));
		</#if>
		<#if item.@objectVar[0]??>
		<#assign objectVar = item.@objectVar>
		${id}.addFocusListener(new FocusAdapter(){
	    	public void focusLost(FocusEvent ev){
	    		String v = ${id}.getText();
	    		if (v!=null || v.trim().length()==0){
	    			if (v.equals(ctx.get("${item.@objectVar}") + "")) ctx.put("${objectVar}", null);
	    		}
    			Object valor  = null;
				try {
					valor = ${nfEdicion}.parse(v);
					${id}.setValue(valor);
					ctx.put("${objectVar}", valor);
				} catch (Exception e) {
					try {
						valor = ${nfDisplay}.parse(v);
						${id}.setValue(valor);
						ctx.put("${objectVar}", valor);
					} catch (ParseException e1) {
						${id}.setText("");
						ctx.put("${objectVar}", null);
					}
				}
			}
    	});
    	 ${id}.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
               String v = ${id}.getText();
	    		if (v!=null || v.trim().length()==0){
	    			if (v.equals(ctx.get("${item.@objectVar}") + "")) ctx.put("${objectVar}", null);
	    		}
    			Object valor  = null;
				try {
					valor = ${nfEdicion}.parse(v);
					${id}.setValue(valor);
					ctx.put("${objectVar}", valor);
				} catch (Exception e) {
					try {
						valor = ${nfDisplay}.parse(v);
						${id}.setValue(valor);
						ctx.put("${objectVar}", valor);
					} catch (ParseException e1) {
						${id}.setText("");
						ctx.put("${objectVar}", null);
					}
					
				}
             }
         });
    	try {
    		if(ctx.get("${objectVar}") != null){
				try {
					Object valor = ctx.get("${objectVar}");
					if(valor instanceof Number)
						${id}.setText(${nfDisplay}.format(valor));
					else
						${id}.setText(${nfDisplay}.format((${nfEdicion}.parse(valor.toString()))));
					} 
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					}
				else{
					${id}.setText("");
					}
				}
			catch (Exception e) {
				${id}.setText("");
				ctx.put("${objectVar}", null);
			}

    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    		 {
	    		if(ctx.get("${objectVar}") != null){
	    			Object valor=null;
					try {
						valor = ctx.get("${objectVar}");
						if(valor instanceof Number)
							${id}.setText(${nfDisplay}.format(valor));
						else
							${id}.setText(${nfDisplay}.format((${nfEdicion}.parse(valor.toString()))));
						} 
					catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
						}
					else{
						${id}.setText("");
						}
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
		if(ctx.check("${errorCond}")){
			${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
		}else{
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
		
		//--> fin Integer
		