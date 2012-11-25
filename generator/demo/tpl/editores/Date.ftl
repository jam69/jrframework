<#--
    Los comentarios casi nunca sobran.
-->    
		<#import "../util/UtilEditor.ftl" as util>
		<#assign idpanel=id+"p">
		<#assign formatDate = "dd/MM/yyyy">
	 	final PanelButtonPreviewPropertyEditor ${idpanel} =new PanelButtonPreviewPropertyEditor();
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#assign editores = util.getListParametros(strParametrosEditor)> 
				<#if editores[0]??>
				 	<#assign formatDate =editores[0]/>
				 </#if>
		</#if>
	 	<#assign formato = "f"+id>
	 	final SimpleDateFormat ${formato} = new SimpleDateFormat("${formatDate}");
		<#assign comp = "comp"+id>
	 	final CCalendar	${comp} = new CCalendar();
	 	${idpanel}.setPanelEditor(new PanelEditor(){		 
			@Override
			public SimpleDateFormat getFormat() {
				return ${formato};
			}
			@Override
			public Object getValue() {
				return ${comp}.getDate();
			}
			@Override
			public void setValue(Object obj) {
				if (obj== null || obj.toString().length()==0){
					return ;
				}
			Date date =null;
			try{
				date = (Date)obj;
				
			}catch (Exception e){
				try {
					${formato}.setLenient(false);
					date=${formato}.parse(obj.toString());
				} catch (ParseException ex) {
					date=null;
					e.printStackTrace();
				}
			}
			${comp}.setDate(date);
			${idpanel}.getCustomEditor().setText(${formato}.format(date));
			<#if item.@objectVar[0]??>
			if (ctx.get("${item.@objectVar}")!=null && ctx.get("${item.@objectVar}").equals( obj)){
				return;
				}
			ctx.put("${item.@objectVar}",date);
		</#if>
			}
		});
	  	${idpanel}.add(${idpanel}.getCustomEditor());
	  	<@util.setAlineacionPanelButtonPreview id=idpanel ali=ali/> 	 
	  	${idpanel}.getPanelEditor().setPreferredSize(new Dimension(100,150));
	  	${idpanel}.getPanelEditor().setLayout(new BorderLayout());
	  	${idpanel}.getPanelEditor().add(BorderLayout.CENTER,${comp});
	  	${idpanel}.getCustomEditor().setEditableText(false);
	  	<#if item.@objectVar[0]??>
	  	if(ctx.get("${item.@objectVar}")!=null){
	  		${idpanel}.getPanelEditor().setValue(ctx.get("${item.@objectVar}"));
	  		} 
	  	ctx.addListener("${item.@objectVar}", new ContextListener(){
			@Override
			public void valueChanged(String key, Object value) {
				${idpanel}.getPanelEditor().setValue(ctx.get("${item.@objectVar}"));				
			}			
		});
		</#if>
		<#if item.@enableCond[0]??>
				
		<#assign enableCond = item.@enableCond?j_string>
    	${idpanel}.getCustomEditor().setEnabled(ctx.check("${enableCond}"));
		for(String varName: ctx.evalVarNames("${enableCond}")){
			ctx.addListener(varName, new ContextListener(){
				public void valueChanged(String key,Object value){
					${idpanel}.getCustomEditor().setEnabled(ctx.check("${enableCond}"));
				}
			});
		} 
				
		</#if>
		final CustomEditor ${id}=${idpanel}.getCustomEditor();		
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
			${id}.setBackground( ${colorBackGro});
		}

		for(String varName: ctx.evalVarNames("${errorCond}")){
			ctx.addListener(varName, new ContextListener(){
				public void valueChanged(String key,Object value){
					if(ctx.check("${errorCond}")){
						${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
					}else{
						${id}.setBackground( ${colorBackGro});
					}
				}
			});
		}  
			
		</#if>
	  