<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// HTMLText
		final javax.swing.JEditorPane ${id} = new javax.swing.JEditorPane();
		${id}.setContentType("text/html");
		<#assign borde = "borde" + id >
		javax.swing.border.Border ${borde} = javax.swing.BorderFactory.createCompoundBorder(
	              javax.swing.BorderFactory.createLoweredBevelBorder(),
	              javax.swing.BorderFactory.createLineBorder(RunnerLF.getColor("TextAreaHTML.Color.Borde"),1));
		
		
		${id}.setBorder(${borde});
		${id}.setFont(RunnerLF.getFont("TextAreaHTML.font"));
		${id}.setForeground(RunnerLF.getColor("TextAreaHTML.colorletras"));
		
		<#if item.@data[0]??>
		<#assign data = item.@data>
		${id}.setText("${data}");
		</#if>
		<#if item.@variable[0]??>
		<#assign variable = item.@variable>
		 if (ctx.get("${variable}") != null){
		 	<#assign s = "s"+id>
			 String ${s}=(String)ctx.get("${variable}");
            ${id}.setText(${s});
		 }
		 ctx.addListener("${variable}", new ContextListener(){
			public void valueChanged(String key, Object value) {
				 if (ctx.get("${variable}")!= null){
					 String ${s}=(String)ctx.get("${variable}");
	                ${id}.setText(${s});
				 }
			}
			 
		 });
		 ${id}.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e) {
				ctx.put("${variable}", ${id}.getText());
			}
		 });
		 </#if>
		
        
        <#assign enableCond = item.@enableCond >
        <#if enableCond?is_string>
		${id}.setEnabled(ctx.check("${enableCond?j_string}"));
		
		if(!${id}.isEditable()){
				 ${id}.setForeground(RunnerLF.getColor("TextAreaHTML.colorletrasDesabilitadas"));	
		}else{
				${id}.setForeground(RunnerLF.getColor("TextAreaHTML.colorletras"));
			}
		
	    for(String v:ctx.evalVarNames("${enableCond?j_string}")){
	    	ctx.addListener(v,new ContextListener(){
				public void valueChanged(String key, Object value) {							
					${id}.setEnabled(ctx.check("${enableCond?j_string}"));
					if(!${id}.isEditable()){
				 		${id}.setForeground(RunnerLF.getColor("TextAreaHTML.colorletrasDesabilitadas"));	
					}else{
						${id}.setForeground(RunnerLF.getColor("TextAreaHTML.colorletras"));
				
					}	        			
	        	}});
	        }
		</#if>   
		 
		
		 <#assign tooltip    = item.@tooltip >

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
		// Fin-HTMLText
		
		
		