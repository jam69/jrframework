<#--
   Que bonitos son los comentarios, cuando existen
-->     
		//editor Password 
  		final JPasswordField ${id}=new JPasswordField();
  		${id}.setEditable(false);
  		${id}.setBorder(null);
   		${id}.setOpaque(true);
   		${id}.setFont(RunnerLF.getFont("EditorString.font"));
   		
   		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 <#assign enteros =valores[0]/>
				</#if>
			</#if>
		</#if>
		
		<@util.setAlineacion id=id ali=ali/> 
		<#if item.@objectVar[0]??>
		
		<#assign valor =id+ "Aux"/>
        String ${valor}=(String)ctx.get("${item.@objectVar}"); 
    	if (${valor} != null && ${valor}.trim().length()>0){
    		${id}.setText(${valor}.trim());
    	}else{
    		${id}.setText("");
    	}
    	ctx.addListener("${item.@objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){    			
				if (ctx.get("${item.@objectVar}")!=null){
					if (ctx.get("${item.@objectVar}").toString().equals(${id}.getPassword())){
						return;
					}
					String valor = ctx.get("${item.@objectVar}").toString();
					${id}.setText(valor);
				}else{
					${id}.setText("");
				}
          	}
    	});
    	</#if>    	
    	//fin editor Password
    	