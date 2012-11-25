<#--
   Cada vez me gustan mas los comentarios
-->   
  		//editor TextArea
  		<#import "../util/UtilEditor.ftl" as util>	
	 	<#assign scroll = "scroll" + id>
		JScrollPane ${scroll} = new JScrollPane();
		final JTextArea ${id} = new JTextArea();
		${scroll}.setViewportView(${id});
		${id}.setEditable(false);
		${id}.setFont(RunnerLF.getFont("EditorString.font"));
		${id}.setBackground(RunnerLF.getColor("RenderTextAreadeshabilitado.colorfondo"));	   
		<#assign filas="-1">
		<#assign columnas="-1">			 
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign editores = util.getListParametros(strParametrosEditor)> 
				<#if editores[0]??>
				 	<#assign filas =editores[0]/>
				 </#if>
				<#if editores[1]??>
				 	<#assign columnas =editores[1]/>		
				</#if>	
			</#if>			
		</#if>			 
		<#if filas!="-1">
			${id}.setRows(Integer.parseInt(${filas}));	
		</#if>
		<#if columnas!="-1">
			 ${id}.setColumns(Integer.parseInt(${filas}));	
		</#if>
		<#if item.@data[0]??>
			<#assign data =item.@data[0]>
		${id}.setText("${data}");
		</#if>
		<#if item.@objectVar[0]??>
    	if (ctx.get("${item.@objectVar}")!=null && ctx.get("${item.@objectVar}").toString().trim().length()>0){
    				${id}.setText(ctx.get("${item.@objectVar}").toString());
    			}else{
    				${id}.setText("");
    			}
    	ctx.addListener("${item.@objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    			
				if (ctx.get("${item.@objectVar}")!=null){
				if (ctx.get("${item.@objectVar}").toString().equals(${id}.getText())) return;
					String valor = ctx.get("${item.@objectVar}").toString();
					${id}.setText(valor);
				}else{
					${id}.setText("");
				}
          }
    	});
    	</#if>    	
   
    	//fin editor TextArea
    	