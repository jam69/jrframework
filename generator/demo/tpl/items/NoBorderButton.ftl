<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// NoBorderButton <@dumpProps item/>
		final JButton ${id}=new JButton();
		{
			${id}.setUI(new BasicButtonUI());
		<#if item.@border[0]??>
			<#assign border = item.@border >
			<#if border == "true"> 
			${id}.setBorderPainted(Boolean.TRUE);
			<#else>
			${id}.setBorderPainted(Boolean.FALSE);
			</#if>
		<#else>
			${id}.setBorderPainted(Boolean.FALSE);
		</#if>	
		<#if item.@iconPath[0]??>
			<#assign icon = item.@iconPath[0]>
			<#if icon?starts_with("$")>
			if(ctx.get("${icon?substring(1)}") !=null){
				${id}.setIcon(new ImageIcon(es.indra.humandev.runner.core.utils.ImageLoader.urlIcon(ctx.get("${icon?substring(1)}").toString())));
			}
			ctx.addListener("${icon?substring(1)}", new ContextListener() {
				@Override
				public void valueChanged(String key, Object value) {
					String valor_iconPath = (String) ctx.get(key);
					if(ctx.get(key)!=null){
						${id}.setIcon(new ImageIcon(es.indra.humandev.runner.core.utils.ImageLoader.urlIcon(ctx.get(key).toString())));
					}
				}
			});
			<#else>	
			${id}.setIcon(es.indra.humandev.runner.core.utils.ImageLoader.loadIcon("${icon}"));
			</#if>
		</#if>
		<#if item.@iconROPath[0]??>
			<#assign iconRO = item.@iconROPath[0]>
			<#if iconRO?starts_with("$")>
			if(ctx.get("${iconRO?substring(1)}") !=null){
				${id}.setRolloverIcon(new ImageIcon(es.indra.humandev.runner.core.utils.ImageLoader.urlIcon(ctx.get("${iconRO?substring(1)}").toString())));
			}
			ctx.addListener("${iconRO?substring(1)}", new ContextListener() {
				@Override
				public void valueChanged(String key, Object value) {
					String valor_iconPath = (String) ctx.get(key);
					if(ctx.get(key)!=null){
						${id}.setRolloverIcon(new ImageIcon(es.indra.humandev.runner.core.utils.ImageLoader.urlIcon(ctx.get(key).toString())));
					}
				}
			});
			<#else>	
			${id}.setRolloverIcon(es.indra.humandev.runner.core.utils.ImageLoader.loadIcon("${iconRO}"));
			</#if>
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
		<#assign tooltip = item.@tooltip >
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
		<#if item.@closeDialog[0]=="true"  >
	 		${id}.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
			<#if item.@operation?has_content>
				<#assign operation = item.@operation[0] >
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
	    	<#if item.@operation?has_content &&  item.@operation != "null">
	        	<#assign operation = item.@operation>
			${id}.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
			    	Action a= (Action)ctx.get("${operation}");
					a.actionPerformed(arg0);
			    }
			});
			 </#if>	
		</#if>	
		}	// Fin NoBorderButton
		