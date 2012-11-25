<#-- 
     un comentario no hace daño
-->
		//--> Integer
		 <#import "../util/UtilEditor.ftl" as util>
		 final JTextField ${id} =new JTextField();
		 ${id}.setOpaque(true);
		 ${id}.setFont(RunnerLF.getFont("EditorDouble.font"));
		<#assign enteros="-1">
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
					 	<#assign enteros =valores[0]/>
				</#if>
			</#if>
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
		<#assign nf ="nf"+id>
		final NumberFormat ${nf} = NumberFormat.getNumberInstance(new Locale("${locale}"));
		<#if enteros!="-1">
		${nf}.setMaximumIntegerDigits(${enteros});
		</#if>
		${id}.setDocument(
			new swingrunner.editors.document.IntegerEditorsDocuments.IntegerDocument(${nf}));
		<#if item.@data[0]??>
			<#assign data =item.@data[0]>
		${id}.setText(${nf}.parse(${nf}.format(${data})) +"");
		</#if>
		<#if item.@objectVar[0]??>
			<#assign objectVar = item.@objectVar>
		${id}.addFocusListener(new FocusAdapter(){
	    	public void focusLost(FocusEvent ev){
	    		String v = ${id}.getText();
	    		if (v!=null){
	    			if (ctx.get("${item.@objectVar}") != null &&
	    				v.equals((ctx.get("${item.@objectVar}").toString()))) return ;
	    		}
    			if (v!=null&& v.trim().length()>0){
    				try {
    					Integer valor = ${nf}.parse((${nf}.format(${nf}.parse(v)))).intValue();
    					${id}.setText(valor +"");
    					ctx.put("${objectVar}", valor);
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
    			if (v!=null&& v.trim().length()>0){
    				try {
    					Integer valor = ${nf}.parse((${nf}.format(${nf}.parse(v)))).intValue();
    					${id}.setText(valor +"");
    					ctx.put("${item.@objectVar}", valor);
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
    	try {
			Integer valor;
			if(ctx.get("${objectVar}") instanceof Integer){
				valor = ((Integer)ctx.get("${objectVar}"));
				${id}.setText(valor +"");
			}
			else
				${id}.setText("");
			
		} catch (Exception e) {
			${id}.setText("");
		}
    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    			//if (ctx.get("${objectVar}")==null)return ;??
    			value = ctx.get("${objectVar}");
    			//if (value.toString().equals(${id}.getText())) return;    			 
				if (value!=null){
					try {
						Integer valor;
						if(value instanceof Integer)
							valor = ((Integer)value);
						else
							 valor = new Integer(${nf}.parse(value.toString()).toString());
						${id}.setText(valor +"");
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
		//--> fin Integer
		