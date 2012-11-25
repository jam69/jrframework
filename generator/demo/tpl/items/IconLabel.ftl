<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> IconLabel  
		final JLabel ${id}=new JLabel();
		 <#if item.@image[0]?? >
			<#assign imagen = item.@image >
			<#if imagen?starts_with("$")>
				<#if imagen?substring(1)!= "null">
					if(ctx.get("${imagen?substring(1)}") instanceof String)
						${id}.setIcon(new javax.swing.ImageIcon(es.indra.humandev.runner.core.utils.ImageLoader.urlIcon((String)ctx.get("${imagen?substring(1)}"))));
					else
						${id}.setIcon((javax.swing.ImageIcon) ctx.get("${imagen?substring(1)}"));
						
				</#if>			
				ctx.addListener("${imagen?substring(1)}",new ContextListener(){
					public void valueChanged(String key, Object value) {
						if (ctx.get(key)!=null){							
							if(ctx.get(key) instanceof String)
								${id}.setIcon(new ImageIcon(es.indra.humandev.runner.core.utils.ImageLoader.urlIcon((String)ctx.get(key))));
							else
								${id}.setIcon((ImageIcon) ctx.get(key));
						}else{
							${id}.setIcon(null);
						}
					}
				});
			<#else>
				 ${id}.setIcon(new javax.swing.ImageIcon(es.indra.humandev.runner.core.utils.ImageLoader.urlIcon("${imagen}")));
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
		
		// Fin-IconLabel
		