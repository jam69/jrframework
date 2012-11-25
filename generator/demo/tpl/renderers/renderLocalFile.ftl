
		<#import "../util/UtilEditor.ftl" as util>			
		final JLabel ${id} = new JLabel();
		
		
		<#assign filtroEditor="-1">
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#if strParametrosEditor != "">
		<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 <#assign filtroEditor =valores[0]/>
				</#if>
			</#if>
		</#if>
				
		<@util.setAlineacion id=id ali=ali/> 
		<#if item.@objectVar[0]??> 
		<#assign varSelected    = item.@objectVar >		
		ctx.addListener("${varSelected}", new ContextListener(){
		public void valueChanged(String key,Object value){
			if (ctx.get("${varSelected}")!=null){
				if (ctx.get("${varSelected}").equals(${id}.getText())){
					return;
					}
				String valor = ctx.get("${varSelected}").toString();
				${id}.setText(valor);
			}else{
				${id}.setText("");
				}
	  		}
		});
            
		</#if>
		
		
	
	
		
		