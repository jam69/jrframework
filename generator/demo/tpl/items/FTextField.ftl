<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->

		// FTextField.ftl	<@dumpProps item/>  
		final JFormattedTextField ${id}=new JFormattedTextField();
		{
			//si se pulsa el enter se transfiere el foco al siguienter componente
			${id}.addKeyListener(new java.awt.event.KeyAdapter(){
			public void keyPressed(java.awt.event.KeyEvent keyevent){
				if (keyevent.getKeyCode() == keyevent.VK_ENTER){
				  	${id}.transferFocus();
				}
			}
		});
		<#if item.@formato[0]?? && item.@formato[0]!="null">
			<#assign format   = item.@formato[0]>
			try {
		
			<#if format?starts_with("f") || format?starts_with("F")>
				<#assign formatFecha = forFecha>
				private SimpleDateFormat ${formatFecha}=new SimpleDateFormat("${format?substring(1)}");
			<#assign formato="formato"+id>
				DateFormatter ${formato}= new DateFormatter(${formatFecha});
				formato.setCommitsOnValidEdit(true);
				${id}.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
				${id}.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(${formato}));
			<#else>
				DateFormatter ${formato}= new DateFormatter(${formatFecha});
				MaskFormatter ${formato} = new MaskFormatter("${formato}");
				${formato}.setCommitsOnValidEdit(true);
				${id}.setFormatterFactory(new DefaultFormatterFactory(${formato}));
			</#if>
			} catch (java.text.ParseException e1) {
				e1.printStackTrace();
			}
		</#if>

		<#if item.@text[0]??>
			<#assign text = item.@text>	
			<#if text!="null">
			${id}.setValue("${text}");
			</#if>
		</#if>
		
		<#if item.@variable[0]?? && item.@variable[0]!="null">
			<#assign var = item.@variable>
			<#assign varContext = "varContext"+id>
			Object ${varContext}=ctx.get("${var}");
			if(${varContext}!=null){
				<#if formatFecha??>
				if(${varContext} instanceof String){
					try {
						${varContext} =  ${formatFecha}.parse(${varContext}.toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				</#if>
				${id}.setValue(${varContext});
			}
			ctx.addListener("${var}", new ContextListener() {
				public void valueChanged(String key, Object value) {
					Object ${varContext}=ctx.get(key);
					if(${varContext}!=null){
						<#if formatFecha??>
						if(${varContext} instanceof String){
						try {
							${varContext} =  ${formatFecha}.parse(${varContext}.toString());
						} catch (ParseException e) {
							e.printStackTrace();
							}	
						</#if>
					${id}.setValue(${varContext});
					}
				}
			});
			${id}.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    ctx.put("${var}", ${id}.getValue());
                }                
            });
			${id}.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					ctx.put("${var}", ${id}.getValue());
				}
			});
		</#if>	
		<#assign enableCond = item.@enableCond >
		<#if enableCond?is_string>
			${id}.setEnabled(ctx.check("${enableCond?j_string}"));
	    	for(String v:ctx.evalVarNames("${enableCond?j_string}")){
	    		ctx.addListener(v,new ContextListener(){
					public void valueChanged(String key, Object value) {							
						${id}.setEnabled(ctx.check("${enableCond?j_string}"));
						}	        			
	    	    	});
	    	    }
		</#if>
		}	// Fin-FTextField
		