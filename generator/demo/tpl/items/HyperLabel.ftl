
<#--
	COMPONENTE HYPERLABEL
	ecibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->

	//hyperlabel
	final HyperLabel ${id}= new HyperLabel();
	${id}.setUI(new BasicButtonUI());
	${id}.setOpaque(false);
	${id}.setHorizontalAlignment(JButton.LEFT);
	
	
	<#if item.@texto[0]??>
		<#if item.@texto?starts_with("$")>
		${id}.setText((String)ctx.get("${item.@texto}".substring(1)));
				 ctx.addListener("${item.@texto}".substring(1),new ContextListener(){
	
					public void valueChanged(String key, Object value) {
						 ${id}.setText((String)ctx.get("${item.@texto}".substring(1)));
					}
				 });
		<#else>
		${id}.setText("${item.@texto}");
		</#if>
	</#if>
	
	<#if item.@tooltip[0]??>
		<#if item.@tooltip?starts_with("$")>
		${id}.setToolTipText((String)ctx.get("${item.@tooltip}".substring(1)));
				 ctx.addListener("${item.@tooltip}".substring(1),new ContextListener(){
					public void valueChanged(String key, Object value) {
						 ${id}.setToolTipText((String)ctx.get("${item.@tooltip}".substring(1)));
					}
				 });
		<#else>
		${id}.setToolTipText("${item.@tooltip}");
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
	
	 <#if item.@closeDialog[0]=="true"  >
	 		 	${id}.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                
                <#if item.@operacion?has_content>
					 <#assign operation = item.@operacion[0] >
		 	         Action a= (Action)ctx.get("${operation}");
		 	         if (a!=null){
		 	         	a.actionPerformed(arg0);
		 	         	}
                	
				 </#if>	
				JDialog d=(JDialog)SwingUtilities.windowForComponent(${id});
                	if(d!=null){
                		d.dispose();                        	
                	}
                	 }
            });
    <#else>
         <#if item.@operacion?has_content &&  item.@operacion != "null">
        	<#assign operation = item.@operacion>
			 	${id}.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent arg0) {
	                	 Action a= (Action)ctx.get("${operation}");
			 	         a.actionPerformed(arg0);
	            		 }
	           		 });
		 </#if>	
	</#if>	
	
	//fin hyperlabel
	
