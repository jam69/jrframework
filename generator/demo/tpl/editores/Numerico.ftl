		 <#import "../util/UtilEditor.ftl" as util>
		 final JFormattedTextField ${id} =new JFormattedTextField();
		 ${id}.setOpaque(true);
		 ${id}.setFont(RunnerLF.getFont("EditorDouble.font"));
		 ${id}.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		 
		<#assign decimales="-1">
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
					 	<#assign decimales =valores[0]/>
				</#if>
				<#if valores[1]??>
					 	<#assign locale =valores[1]/>
				</#if>
			</#if>
			
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
		<#assign nf ="nf"+id>
		final NumberFormat ${nf} = NumberFormat.getNumberInstance(new Locale("${locale}"));
		
		<#if ((decimales?number)  >0) >
	 	${nf}.setMinimumFractionDigits(${enteros});
		${nf}.setMaximumFractionDigits(${enteros});
		<#else>
		${nf}.setMinimumFractionDigits(0);
		${nf}.setMaximumFractionDigits(0);
		</#if>
		<#if item.@data[0]??>
		<#assign data =item.@data[0]>
		${id}.setText(${nf}.format(${data}));
		</#if>
		<#if item.@objectVar[0]??>
		<#assign objectVar = item.@objectVar>
		${id}.addFocusListener(new FocusAdapter(){
	    	public void focusLost(FocusEvent ev){
	    		String v = ${id}.getText();
	    		if (v!=null){
	    			if (ctx.get("${item.@objectVar}")!=null && v.equals(ctx.get("${item.@objectVar}").toString())) return ;
	    		}
    			if (v!=null && v.trim().length()>0){
    				Number num = null;
    				try {
						num = (${nf}.parse(v));
						<#if (decimales?number>0)>
						ctx.put("${objectVar}", num.doubleValue());
						<#else>
						ctx.put("${objectVar}", num.intValue());
						</#if>
					} catch (ParseException e) {
						System.out.println("Formato no valido");
						ctx.put("${objectVar}", null);
						${id}.setText("");
					}
    			}else{
    				ctx.put("${objectVar}", null);
    				${id}.setText("");
    			}
	    	}
    	});
    	 ${id}.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
              	String v = ${id}.getText();
	    		if (v!=null){
	    			if (ctx.get("${item.@objectVar}") != null &&
	    				v.equals((ctx.get("${item.@objectVar}").toString()))) return ;
	    		}
    			if (v!=null && v.trim().length()>0){
    				Number num = null;
    				try {
						num = (${nf}.parse(v));
						<#if (decimales?number>0)>
						ctx.put("${objectVar}", num.doubleValue());
						<#else>
						ctx.put("${objectVar}", num.intValue());
						</#if>
					} catch (ParseException e) {
						System.out.println("Formato no valido");
						ctx.put("${objectVar}", null);
						${id}.setText("");
					}
    			}else{
    				ctx.put("${objectVar}", null);
    				${id}.setText("");
    			}
             }
         });
    	try {
			Object valor;
			if(ctx.get("${objectVar}") instanceof Integer || 
				ctx.get("${objectVar}") instanceof Double){
				valor = ctx.get("${objectVar}");
				${id}.setText(${nf}.format(valor));
			}
			else
				${id}.setText("");
			
		} catch (Exception e) {
			${id}.setText("");
		}
    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    		if (ctx.get("${objectVar}")==null)return ;
    		if (value.toString().equals(${id}.getText())) return;
    			value = ctx.get("${item.@objectVar}");
				if (value!=null){
					try {
						Number valor;
						if(value instanceof Integer || value instanceof Double)
							${id}.setText(${nf}.format(value));
						else{
							<#if (decimales?number>0)>
							valor = new Double(${nf}.parse(value.toString()).toString());
							<#else>
							valor = new Integer(${nf}.parse(value.toString()).toString());
							</#if>
							${id}.setText(${nf}.format(valor));
						}
			
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
