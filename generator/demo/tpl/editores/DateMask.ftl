<#--
     Los comentarios son tus amigos
  -->
		<#import "../util/UtilEditor.ftl" as util>
		<#assign formatDate = "dd/MM/yyyy">
		final JFormattedTextField ${id} =new JFormattedTextField();
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>	
			<#if strParametrosEditor != "">	
				<#assign editores = util.getListParametros(strParametrosEditor)> 
					<#if editores[0]??>
					 	<#assign formatDate =editores[0]/>
					 </#if>
			 </#if>
		</#if>
	 	<#assign formato = "f"+id>			
	 	final SimpleDateFormat ${formato} = new SimpleDateFormat("${formatDate}");
	 	${formato}.setLenient(false);	 
		<@util.setAlineacion id=id ali=ali/> 
		${id}.setFont(RunnerLF.getFont("EditorDateMask.font"));
		<#if item.@objectVar[0]??>
			<#assign var =item.@objectVar[0]>
		if(ctx.get("${var}")!=null){
			${id}.setText(${formato}.format(ctx.get("${var}")));
		} 
			<#if item.@data[0]??>
				<#assign data =item.@data[0]>
		else{
			try {
				Date fecha = ${formato}.parse("${data?j_string}");
				${id}.setText(${formato}.format(fecha));
				ctx.put("${var}", fecha);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
			</#if>
		ctx.addListener("${var}", new ContextListener(){
			@Override
			public void valueChanged(String key, Object value) {
				value = ctx.get("${var}");
				if (value!=null && value.toString().equals(${id}.getText())) return;
				if (value==null || value.toString().length()==0){
					 ${id}.setText("");
				}else{
					try {
						if(value instanceof String){
								Date fecha = ${formato}.parse(value.toString());
								${id}.setText(${formato}.format(fecha));
						}else{
							${id}.setText(${formato}.format(value));
						}		
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		${id}.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
      				String value = ${id}.getText();
                	if (value==null || value.length()==0){
						 ctx.put("${var}", null);
					}else{
						try {
							Date fecha = ${formato}.parse(value);
							 ctx.put("${var}", fecha);
			
						} catch (ParseException ex) {
							 ctx.put("${var}", null);
						}
					}
                }                
            });
		${id}.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
			String value = ${id}.getText();
                	if (value==null || value.length()==0){
						 ctx.put("${var}", null);
					}else{
						try {
							Date fecha = ${formato}.parse(value);
							 ctx.put("${var}", fecha);
			
						} catch (ParseException ex) {
							 ctx.put("${var}", null);
						}
					}
			}
		});
		<#else>
			<#if item.@data[0]??>
				<#assign data =item.@data[0]>
		try {
			Date fecha = ${formato}.parse("${data?j_string}");
			${id}.setText(${formato}.format(fecha));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		</#if>
		
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
		<#assign nameLista = "error"+id>
		
		for(String varName: ctx.evalVarNames("${errorCond}")){
			ctx.addListener(varName, new ContextListener(){
				public void valueChanged(String key,Object value){
					if(ctx.check("${errorCond}")){
						${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
						}
					else{
						${id}.setBackground(${colorBackGro});
						}
				}
			});
		}  
		</#if>		
		<#if item.@enableCond[0]??>
			<#assign condicion =item.@enableCond?j_string>
		${id}.setEnabled(ctx.check("${condicion}"));
		for(String varName:ctx.evalVarNames("${condicion}")){
			ctx.addListener(varName, new ContextListener(){
				@Override
				public void valueChanged(String key, Object value) {
					${id}.setEnabled(ctx.check("${condicion}"));				
				}			
			});		
		}		
		</#if>
		
			
