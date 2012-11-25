<#--
    editor TimeStanSpin
-->   
		// -> TimeStampSpin
		<#import "../util/UtilEditor.ftl" as util>
		<#assign id=id+"p">
		<#assign formatDate = "dd/MM/yyyy">
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign visoreditor = "editor" + id>
				final swingrunner.JSpinnerTimeStamp ${id} =new swingrunner.JSpinnerTimeStamp(new javax.swing.SpinnerDateModel());
				${id}.setFont(RunnerLF.getFont("EditorDateSpin.font"));
				javax.swing.JFormattedTextField ${visoreditor} = ((JSpinner.DefaultEditor)${id}.getEditor()).getTextField();		
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign pos = strParametrosEditor?index_of(ali)>
			<@util.setAlineacion id=visoreditor ali=ali/> 
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>			
			<#if strParametrosEditor != "">
				<#assign editores = util.getListParametros(strParametrosEditor)> 
					<#if editores[0]??>
					 	<#assign formatDate =editores[0]?trim/>
				${id}.setEditor(new javax.swing.JSpinner.DateEditor(${id}, "${formatDate}"));	
				</#if>	
			</#if>	
		</#if>
		<#assign editor = "deeditor" + id>
				final JSpinner.DefaultEditor ${editor} = (JSpinner.DefaultEditor)${id}.getEditor();
				${editor}.getTextField().setUI(new javax.swing.plaf.basic.BasicFormattedTextFieldUI());	
				${editor}.getTextField().setBorder(BorderFactory.createCompoundBorder(
		  											BorderFactory.createMatteBorder(2, 0, 2, 0, Color.white), 
		  											BorderFactory.createMatteBorder(1, 1, 1, 0, Color.black)));
		<#assign textfield= "textfield" +id> 
				javax.swing.JFormattedTextField ${textfield} = (((javax.swing.JSpinner.DefaultEditor)(${id}.getEditor())).getTextField());
		<@util.setAlineacion id=textfield ali=ali/>
		<#assign formatear = "formatear" + id>
				java.text.SimpleDateFormat ${formatear} = new java.text.SimpleDateFormat("${formatDate}");
		<#if item.@data[0]??>
				try {
					${id}.setValue(${formatear}.parse("${item.@data}"));	
				} catch (ParseException e) {
					e.printStackTrace();
				}				
		</#if>	 
		<#if item.@objectVar[0]??>		
				${id}.getModel().addChangeListener(new ChangeListener(){
					@Override
					public void stateChanged(ChangeEvent changeevent) {
						ctx.put("${item.@objectVar}", ${id}.getValue());
						}
				});
				ctx.addListener("${item.@objectVar}", new ContextListener(){
		    		public void valueChanged(String key,Object value){
		    			value = ctx.get("${item.@objectVar}");
		    			if(!value.equals(${id}.getValue())){
							${id}.setValue(value!=null?value:"");
						}
		          }
		    	});		
		 </#if>	
		 <#assign tooltip = item.@tooltip > 
		 <#if tooltip[0]?? && tooltip?is_string> 
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
						${editor}.getTextField().setBackground((Color)${valBack});
				}else{
					${valBack}=null;
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
				final Color ${colorBackGro} = (Color)${valBack};
			<#else>
				final Color ${colorBackGro} = null;
			</#if>	
			<#assign errorCond = item.@errorCond?j_string>
				if(ctx.check("${errorCond}")){
					${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
					${editor}.getTextField().setBackground(RunnerLF.getColor("CompBackground.error"));
				}else{
					${id}.setBackground( ${colorBackGro});
					${editor}.getTextField().setBackground(${colorBackGro});
				}
				for(String varName:ctx.evalVarNames("${errorCond}")){
					ctx.addListener(varName, new ContextListener(){
						public void valueChanged(String key,Object value){
							if(ctx.check("${errorCond}")){
								${id}.setBackground(RunnerLF.getColor("CompBackground.error"));
								${editor}.getTextField().setBackground(RunnerLF.getColor("CompBackground.error"));
							}else{
								${id}.setBackground( ${colorBackGro});
								${editor}.getTextField().setBackground(${colorBackGro});
							}
						}
					});
				}
		</#if>	
		// -> Fin TimeStampSpin
