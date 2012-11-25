		<#import "../util/UtilEditor.ftl" as util>
		 
		final JLabel ${id}=new JLabel();
		${id}.setFont(RunnerLF.getFont("textfield.font"));
		final ArrayList<Object> datos = new ArrayList<Object>();
		 
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">	
				<#assign datos = util.getListParametros(strParametrosEditor)> 
				<#list datos as pp>
					datos.add("${pp}");
				</#list>
			</#if>			
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
	
		<#if item.@data[0]??>
		<#assign data =item.@data[0]>
		Object v=ctx.get("${data}");
		if (v!=null && v.toString().trim().length()>0){
			if(datos.contains(ctx.get("${data}")))
				${id}.setText(ctx.get("${data}").toString());
   			
		}
		</#if>
		<#if item.@objectVar[0]??>
		<#assign objectVar = item.@objectVar>
   		Object v=ctx.get("${objectVar}");
		if (v!=null && v.toString().trim().length()>0){
			if(datos.contains(ctx.get("${objectVar}")))
				${id}.setText(ctx.get("${objectVar}").toString());
   			
		}
    	ctx.addListener("${objectVar}", new ContextListener(){
				@Override
				public void valueChanged(String key, Object value) {
					if(datos.contains(ctx.get("${objectVar}")))
						${id}.setText(ctx.get("${objectVar}").toString());
					}
			});
    	</#if>	

