<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> List  
		final JList ${id}=new JList();

    	${id}.setBorder(BorderFactory.createLineBorder(RunnerLF.getColor("Lista.color.borde"),1));
    	
	   <#if item.@tooltip[0]??>
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
		
		
		<#if item.@selectionMultiple =="true">
			${id}.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		<#else>
			${id}.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		</#if>
    		
		<#if item.@data[0]??>
		<#assign datos =  item.@data?split(",")>
		<#assign data = "data"+id>
		ArrayList ${data}= new ArrayList();
		<#list datos as pp>
		${data}.add("${pp}");
		</#list>
		${id}.setListData(${data}.toArray());
		</#if>
		<#if item.@varSelected[0]??>
		<#assign variableSel =item.@varSelected>
		${id}.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
					<#if item.@selectionMultiple =="false">
						ctx.put("${variableSel}",${id}.getSelectedValue() );
					<#else>
					<#assign variableSelMultipleArray= variableSel+id+"a">
					<#assign variableSelMultipleOb= variableSel+id+"o">
					Object[] ${variableSelMultipleOb}= ${id}.getSelectedValues();
					if (${variableSelMultipleOb}==null)return;
					ArrayList ${variableSelMultipleArray}= new ArrayList();
					for (int i=0;i<${variableSelMultipleOb}.length;i++){
						${variableSelMultipleArray}.add(${variableSelMultipleOb}[i]);
					}
					ArrayList obj = (ArrayList)ctx.get("${variableSel}");
			       			if((obj==null)||(${variableSelMultipleArray}!=null && !${variableSelMultipleArray}.equals((ArrayList)obj))){
								ctx.put("${variableSel}",${variableSelMultipleArray});
					}
					</#if>
                }
            });			
			ctx.addListener("${variableSel}", new ContextListener(){
				@Override
				public void valueChanged(String key, Object value) {
					<#if item.@selectionMultiple =="false">
						${id}.setSelectedValue(value, true);
					<#else>
					<#assign variableSelMultipleArray= variableSel+id+"a">
					<#assign variableSelMultipleOb= variableSel+id+"o">
					<#assign iterator= variableSel+id+"it">
					<#assign selected= variableSel+id+"sel">
					<#assign i= variableSel+id+"i">
					ArrayList ${variableSelMultipleArray}= (ArrayList)ctx.get("${variableSel}");
					Iterator ${iterator} = ${variableSelMultipleArray}.iterator();
					int[] ${selected} = new int[${variableSelMultipleArray}.size()];
					int ${i}=0;
					<#assign val= variableSel+id+"val">
					Object[] arrLista = ${id}.getSelectedValues();
	            		ArrayList arr3 = new ArrayList();
	            		for (int h =0; h<arrLista.length;h++){
	            			arr3.add(arrLista[h]);
	            		}
	            		if (!arr3.equals(${variableSelMultipleArray})){
					while(${iterator}.hasNext()){
						Object ${val} = ${iterator}.next();
						for(int j=0; j<${id}.getModel().getSize();j++){
							<#assign listElement= variableSel+id+"listE">
							Object ${listElement} = ${id}.getModel().getElementAt(j);
							if(${val}!=null && ${val}.equals(${listElement})){
	            					${selected}[${i}]=j;
	            					${i}++;
	            				}
						}
					}
					${id}.setSelectedIndices(${selected});
					}
					</#if>
					
				}
			});
		</#if> 
		
		<#if item.@variable[0]??>
		//${item.@variable}
		<#assign nameVariable = "var"+id>
		${id}.removeAll();
			
		ArrayList ${nameVariable}= (ArrayList)ctx.get("${item.@variable}");
		if (${nameVariable}!=null){
		
				<#--for (int i=0;i<${nameVariable}.size();i++){
					${id}.add(${nameVariable}.get(i));		
				}-->
				${id}.setListData(${nameVariable}.toArray());
		}
		ctx.addListener("${item.@variable}", new ContextListener(){
		@Override
				public void valueChanged(String key, Object value) {
				${id}.removeAll();
					ArrayList ${nameVariable}= (ArrayList)ctx.get("${item.@variable}");
					if (${nameVariable}==null){					
						return;
					}
					
					${id}.setListData(${nameVariable}.toArray());	
				
				}
		});
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
		<#assign mandatory  = item.@mandatory >
		<#if mandatory?has_content> 
		<#if mandatory=="true"> 
        ${id}.setBorder(BorderFactory.createLineBorder(java.awt.Color.red));
        </#if>
		</#if>	
		
		
		// Fin-List
		
		
		
		