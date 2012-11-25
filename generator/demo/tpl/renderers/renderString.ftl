<#--
        un comentario nunca viene mal
        
-->        
		<#import "../util/UtilEditor.ftl" as util>
		final JLabel ${id}=new JLabel();
		${id}.setOpaque(true);
		${id}.setFont(RunnerLF.getFont("EditorString.font"));
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>			
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>	
			<#if strParametrosEditor != "">		
				<#assign editores = util.getListParametros(strParametrosEditor)> 
				<#if editores[0]??>
				 	<#assign longitud =editores[0]/>
				 </#if>
			</#if>						
		</#if>
		<#if item.@data[0]??>
			<#assign data =item.@data[0]>
		${id}.setText("${data}");
		</#if>
		<#if item.@objectVar[0]??>
        Object v=ctx.get("${item.@objectVar}");
		if (v!=null && v.toString().trim().length()>0){
			${id}.setText(ctx.get("${item.@objectVar}").toString());
   			}else{
    			${id}.setText("");
		}
		ctx.addListener("${item.@objectVar}", new ContextListener(){
			public void valueChanged(String key,Object value){
				if (ctx.get("${item.@objectVar}")!=null){
					if (ctx.get("${item.@objectVar}").toString().equals(${id}.getText())){
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
    