  //editor Radio
  		class PanelRadio extends JPanel{
  			@Override
  			public void setEnabled(boolean enabled) {
  				super.setEnabled(enabled);
  				for(int i=0;i<getComponentCount();i++){
  					((JRadioButton)getComponent(i)).setEnabled(enabled);
  				} 
  			}

  		}
  		
		<#import "../util/UtilEditor.ftl" as util>  
		<#assign buttonGroup = "buttonGroup" + id>
		final PanelRadio ${id}=new PanelRadio();
		final javax.swing.ButtonGroup ${buttonGroup} = new javax.swing.ButtonGroup(); 
		${id}.setFont(RunnerLF.getFont("EditorRadio.font"));
		 
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">	
				<#assign parametros = util.getListParametros(strParametrosEditor)> 
				<#assign contador = 1> 
				<#list parametros as cadena>	
					<#assign radioButton = "radio" +id+ contador>
		JRadioButton ${radioButton} = new JRadioButton("${cadena}");
		${buttonGroup}.add(${radioButton});
		${id}.add(${radioButton});
		${radioButton}.setFont(RunnerLF.getFont("EditorRadio.font"));	
					<#if item.@objectVar[0]??>
		${radioButton}.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					ctx.put("${item.@objectVar}", 
						((JRadioButton)e.getSource()).getText());	
							}
						});
					</#if>
					<#assign contador = contador +1> 
				</#list>	
			</#if>			
		</#if>
		
		 <#if item.@data[0]??>
			 <#assign data =item.@data[0]>
			 <#if data != "">
		for(int i=0;i<${id}.getComponentCount();i++){
			if(((JRadioButton)${id}.getComponent(i)).getText().equalsIgnoreCase("${data}")){
				((JRadioButton)${id}.getComponent(i)).setSelected(true);
			}
		}	
			</#if>	
		</#if>
		
		<#if item.@objectVar[0]??>
		 <#assign objectVar = item.@objectVar[0]>
    	if (ctx.get("${objectVar}")!=null && ctx.get("${objectVar}").toString().trim().length()>0){
				for(int i=0;i<${id}.getComponentCount();i++){
				if(((JRadioButton)${id}.getComponent(i)).getText().equalsIgnoreCase(ctx.get("${objectVar}").toString())){
					((JRadioButton)${id}.getComponent(i)).setSelected(true);
				}
    		}
    	}
    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    			Object valor = ctx.get("${objectVar}");
				if (valor != null){
					for(int i=0;i<${id}.getComponentCount();i++){
						if(((JRadioButton)${id}.getComponent(i))
								.getText().equalsIgnoreCase(ctx.get("${objectVar}").toString())){
							if (((JRadioButton)${id}.getComponent(i)).isSelected()) return;
							((JRadioButton)${id}.getComponent(i)).setSelected(true);
						}
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
		
    	//fin editor radio
    	