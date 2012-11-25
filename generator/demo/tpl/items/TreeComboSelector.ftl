<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> TreeComboSelector
		 		
		final swingrunner.TreeCombo ${id}=new swingrunner.TreeCombo();
		<#if item.@treeModelVar[0]??>
			<#assign obj="obj"+id>
			Object ${obj}=ctx.get("${item.@treeModelVar}");
			<#assign model ="model"+id>
			javax.swing.tree.DefaultTreeModel ${model}=(javax.swing.tree.DefaultTreeModel) ${obj};
            ${id}.setTreeModel(${model});
            ctx.addListener("${item.@treeModelVar}", new ContextListener(){            
                public void valueChanged(String key,Object value){
                    javax.swing.tree.DefaultTreeModel o=(javax.swing.tree.DefaultTreeModel) ctx.get(key);
                    ${id}.setTreeModel(o);
                  	${id}.updateInitialLevel();
              		 }
           		 });			
		</#if>
		
		<#if item.@selectionVar[0]??>
			<#assign variableSel =item.@selectionVar>
			<#assign isOnlyLeafsSelectable = item.@isOnlyLeafsSelectable>
			<#assign sModel="sModel"+id>
			javax.swing.tree.TreeSelectionModel ${sModel}= ${id}.tree.getSelectionModel();
    		${sModel}.setSelectionMode(${sModel}.SINGLE_TREE_SELECTION);
        	${sModel}.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
    			public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
    				Object obj=e.getPath().getLastPathComponent();
    				if(${id}.isOnlyLeafsSelectable()==true){
    					javax.swing.tree.TreeNode tn=(javax.swing.tree.TreeNode)obj;
    					if(!tn.isLeaf()) return;
    				}
    				Object ret=((javax.swing.tree.DefaultMutableTreeNode)obj).getUserObject();
    				if(${id}.getDisabled()!=null && ${id}.getDisabled().contains(obj)) return;    				
    				
    				if(!e.isAddedPath() && ${id}.isNullSelectionAllowed()){
    					ctx.put("${variableSel}", null);
    					return;
    				}
    				ctx.put("${variableSel}", obj);
    			}			
    			});
		</#if>	
		
		
		<#if item.@disabledVar[0]??>
			${id}.setDisabled( (Collection)ctx.get("${item.@disabledVar}"));
			  ctx.addListener("${item.@disabledVar}", new ContextListener(){            
                public void valueChanged(String key,Object value){
                    	${id}.setDisabled( (Collection)ctx.get(key));
              		 }
           		 });			
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
		
		<#if item.@rootVisible??>	
		  	${id}.setOnlyRootVis(${item.@rootVisible});
		</#if> 
		
		<#if item.@allowParentSelection??>	
		  	${id}.setOnlyLeafsSelectable(!(${item.@allowParentSelection}));
		</#if> 
		<#if item.@nullSelectionAllowed??>	
		  	${id}.setNullSelectionAllowed(${item.@nullSelectionAllowed});
		</#if> 
		
		<#if item.@enableCond[0]??>
		<#assign enableCond =item.@enableCond?j_string>
		${id}.setEnabledTree(ctx.check("${enableCond}"));
		<#assign listaVar = "listaVar"+id>
		ArrayList ${listaVar}= ctx.evalVarNames("${enableCond}");
		for(int i =0;i<${listaVar}.size();i++ ){
		ctx.addListener(${listaVar}.get(i).toString(), new ContextListener(){

				@Override
				public void valueChanged(String key, Object value) {
					${id}.setEnabledTree(ctx.check("${enableCond}"));
					
				}
				
			});
		
		}
		</#if>
        <#if item.@nullLabel[0]??>
        	${id}.setlabelSelectionNull("${item.@nullLabel}");	
        	${id}.textField.setText("${item.@nullLabel}");
        </#if> 
         <#if item.@expansionLevel[0]??>
        	${id}.setInitialLevel(${item.@expansionLevel});	
        	${id}.updateInitialLevel();
        </#if> 
		// Fin- TreeComboSelector
		