<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> ListFiles  
		JScrollPane ${id}=new JScrollPane();
		<#assign list=id +"list">	
		final JList ${list} = new JList();
		${id}.setViewportView(${list});
		${list}.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		${list}.setCellRenderer(new swingrunner.render.MiListCellRenderer());
		${list}.setBackground(RunnerLF.getColor("ListaFiles.fondo"));
		${list}.setFont(RunnerLF.getFont("boton.font"));
		<#assign borde="borde" +id>	
		javax.swing.border.Border ${borde} = javax.swing.BorderFactory.createLineBorder(RunnerLF
				.getColor("ListaFiles.color.borde"), 1);
		${list}.setBorder(${borde});
		${list}.setFont(RunnerLF.getFont("ListaFiles.font"));
		
		${list}.setAutoscrolls(true);
		
		<#assign tooltip    = item.@tooltip >
		<#if tooltip?is_string> 
 			 <#if tooltip?starts_with("$")>		
				${list}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
				ctx.addListener("${tooltip?substring(1)}", new ContextListener(){
				public void valueChanged(String key, Object value) {
					 ${list}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
					}
				});		
			 <#else>
				${list}.setToolTipText("${tooltip}");
			 </#if>
		</#if>	
		
		 <#assign var        = item.@variable >
		 <#if var[0]??>
		 java.util.List s=(java.util.List)ctx.get("${var}");
		 if(ctx.get("${var}")!=null)
			 ${list}.setListData(s!=null? s.toArray(): null);
		 ctx.addListener("${var}", new ContextListener() {
				public void valueChanged(String key, Object value) {
					Object object = ctx.get(key);
					if(object!=null){
						if(object instanceof  java.util.List)
						{
							java.util.List list=(java.util.List)object;
				            ${list}.setListData(list!=null? list.toArray(): null);	
						}

					}
				}
			});
		<#else>
			<#assign data   = item.@data >
			<#if data[0]??>
				java.util.List lis = (java.util.List) es.indra.humandev.runner.core.utils.StringUtils.toList("${data}");
				${list}.setListData(lis!=null? lis.toArray(): null);
			</#if>
		 </#if>
		 <#assign varSelected        = item.@varSelected >
		 <#if varSelected[0]??>
			${list}.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					ctx.put("${varSelected}", ${list}.getSelectedValue());
				}

			});
			ctx.addListener("${varSelected}", new ContextListener() {
				public void valueChanged(String key, Object value) {
					${list}.setSelectedValue(ctx.get(key), true);
				}

			});
		</#if>
		 
		 <#assign enableCond = item.@enableCond >
		 
		<#if enableCond?is_string>
		${list}.setEnabled(ctx.check("${enableCond?j_string}"));
		if (!${list}.isEnabled()) {
				${list}.setForeground(RunnerLF.getColor("ListaFiles.desactivada"));
			} else {
				${list}.setForeground(RunnerLF.getColor("ListaFiles.activada"));

			}
	    for(String v:ctx.evalVarNames("${enableCond?j_string}")){
	    	ctx.addListener(v,new ContextListener(){
				public void valueChanged(String key, Object value) {							
					${list}.setEnabled(ctx.check("${enableCond?j_string}"));
					if (!${list}.isEnabled()) {
						${list}.setForeground(RunnerLF.getColor("ListaFiles.desactivada"));
					} else {
						${list}.setForeground(RunnerLF.getColor("ListaFiles.activada"));
		
					}
					}	        			
	        	});
	        }
		</#if>
		 
		// Fin-ListFiles
		