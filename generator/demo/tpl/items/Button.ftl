<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// Button  ${id}
		final JButton ${id}=new JButton();
		${id}.setFont(SwingUtils.getFont("Verdana,0,10"));
		${id}.setForeground(new Color(8,96,137));
        <#if item.@text[0]?? >
       		<#assign textExpr = item.@text >
        ${id}.setText("${textExpr}");
        </#if>
        <#if item.@variableTexto[0] ??>
	        <#assign varTexto = item.@variableTexto >       
        {
			Object s=ctx.get("${varTexto}");
			${id}.setText(s==null?"":s.toString());
			ctx.addListener("${varTexto}",new ContextListener(){
            	public void valueChanged(String key,Object value){
                	Object s=ctx.get("${varTexto}");
                    ${id}.setText(s==null?"":s.toString());
                }
            });
        }
		</#if>
		<#if item.@tooltip[0]??  >
	   		<#assign tooltip = item.@tooltip >
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
	<#if item.@enableCond[0]??>
		<#assign enableCond =item.@enableCond?j_string>
		${id}.setEnabled(ctx.check("${enableCond}"));
		<#assign listaVar = "listaVar"+id>
		for(String s:ctx.evalVarNames("${enableCond}")){
			ctx.addListener(s, new ContextListener(){
				@Override
				public void valueChanged(String key, Object value) {
					${id}.setEnabled(ctx.check("${enableCond}"));
				}
			});
		}	
	</#if>
		// End Button ${id}
		
		