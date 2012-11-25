<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// Tree  
		final JScrollPane ${id} = new JScrollPane();
		<#assign  tree= "tree" + id> 
		final javax.swing.JTree ${tree} = new javax.swing.JTree();
		<#assign  borde= "borde" + id> 
		javax.swing.border.Border ${borde} = javax.swing.BorderFactory.createLineBorder(RunnerLF
				.getColor("Lista.color.borde"), 1);
		${id}.setBorder(${borde});
		${id}.setViewportView(${tree});
		
		
		
		<#if item.@variable[0]??  && item.@variable!="null" >
		    <#assign var = item.@variable[0]>       
			javax.swing.tree.DefaultTreeModel model = (javax.swing.tree.DefaultTreeModel) ctx.get("${var}");
			${tree}.setModel(model);
			ctx.addListener("${var}", new ContextListener() {
				public void valueChanged(String key, Object value) {
					javax.swing.tree.DefaultTreeModel o = (javax.swing.tree.DefaultTreeModel) ctx.get(key);
					${tree}.setModel(o);
				}
			});
		</#if>
		
		<#if item.@varSelected[0]?? && item.@varSelected!="null" >
			<#assign varSelected = item.@varSelected[0]>       
			javax.swing.tree.TreeSelectionModel sModel = ${tree}.getSelectionModel();
			sModel.setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
			sModel.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
				public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
					Object obj = e.getPath().getLastPathComponent();
					Object ret = ((javax.swing.tree.DefaultMutableTreeNode) obj).getUserObject();
					ctx.put("${varSelected}", obj);
					}
				});
		</#if>
		<#if item.@enableCond[0]??>
		<#assign enableCond =item.@enableCond?j_string>
		${tree}.setEnabled(ctx.check("${enableCond}"));
		
		if (!${tree}.isEnabled()) {
			${tree}.setForeground(RunnerLF.getColor("Lista.desactivada"));
		} else {
			${tree}.setForeground(RunnerLF.getColor("Lista.activada"));
		}
		<#assign listaVar = "listaVar"+id>
		ArrayList ${listaVar}= ctx.evalVarNames("${enableCond}");
		for(int i =0;i<${listaVar}.size();i++ ){
		ctx.addListener(${listaVar}.get(i).toString(), new ContextListener(){

				@Override
				public void valueChanged(String key, Object value) {
					${tree}.setEnabled(ctx.check("${enableCond}"));
					if (!${tree}.isEnabled()) {
						${tree}.setForeground(RunnerLF.getColor("Lista.desactivada"));
					} else {
						${tree}.setForeground(RunnerLF.getColor("Lista.activada"));
					}	
				}
				
			});
		}
		</#if>
		
		// Tree
		
		