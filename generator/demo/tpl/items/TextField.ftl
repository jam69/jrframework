<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
    <#assign NULL_TEXT = "\"\"" >
    <#assign var        = item.@variable >
    <#assign tooltip    = item.@tooltip >
	<#assign enableCond = item.@enableCond >
	<#assign text       = item.@text >
	<#assign resultado  = item.@errorCond >
	<#assign mandatory  = item.@mandatory >
		// TextField <@dumpProps item/>
        final JTextField ${id}=new JTextField();
        {
    		${id}.setFont(SwingUtils.getFont("Verdana,0,10"));
	<#if text?is_string>
        	${id}.setText("${text}");
	</#if>         
    <#if var?is_string>
	    	${id}.setText( ctx.get("${var}")==null?${NULL_TEXT}: ctx.get("${var}").toString());
			ctx.addListener("${var}",new ContextListener(){
			public void valueChanged(String key,Object value){
				Object v=ctx.get("${var}");
				${id}.setText( v==null?${NULL_TEXT}: v.toString());
				}
			});
			${id}.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					Object v=ctx.get("${var}");
					if (v==null || !v.equals(${id}.getText())){
						ctx.put("${var}",${id}.getText());
					}                	                    
				}                
			});
			${id}.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent ev){
					Object v=ctx.get("${var}");
					if (v==null || !v.equals(${id}.getText())){
						ctx.put("${var}",${id}.getText());          
            	        }          
            	    }
           	});                
	</#if>		
	<#if tooltip?is_string> 
		<#if tooltip?starts_with("$") >		
			${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
			ctx.addListener("${tooltip?substring(1)}", new ContextListener(){
				public void valueChanged(String key, Object value) {
					 ${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
				}
			});		
	<#else>
			${id}.setToolTipText("${tooltip}");
	</#if>
</#if>	
<#if enableCond?is_string>
	<#assign enableCond = enableCond?j_string>
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
		}	// Fin-TextField	
    		