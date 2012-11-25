
//editor Combo

		 <#import "../util/UtilEditor.ftl" as util>
		 
		 final JComboBox ${id}=new JComboBox();
		 ${id}.setFont(RunnerLF.getFont("textfield.font"));
		 ${id}.setBackground(null);
		 
		<#assign dataCbo="-1">
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>
		 <#if strParametrosEditor!="-1">
		<#assign datos =  strParametrosEditor?split(",")>
		<#list datos as pp>
		${id}.addItem("${pp}");
		</#list>
		 </#if>
		<#if item.@objectVar[0]??>
		<#assign objectVar = item.@objectVar>
		
    	${id}.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
					ctx.put("${item.@objectVar[0]}",${id}.getSelectedItem());
                }
            });			
            if (ctx.get("${item.@objectVar[0]}")!=null){
            	${id}.setSelectedItem(ctx.get("${item.@objectVar[0]}"));
            }
    	ctx.addListener("${item.@objectVar[0]}", new ContextListener(){
				@Override
				public void valueChanged(String key, Object value) {
						${id}.setSelectedItem(ctx.get("${item.@objectVar[0]}"));	
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
		



//Fin editor Combo
