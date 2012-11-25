		
		<#import "../util/UtilEditor.ftl" as util>  
		<#assign buttonGroup = "buttonGroup" + id>
		final JLabel ${id}=new JLabel();
		${id}.setFont(RunnerLF.getFont("EditorRadio.font"));
		<#assign arrayDatos = id + "arrayDatos" >
		final ArrayList<Object> ${arrayDatos} = new ArrayList<Object>();
		 
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">	
				<#assign parametros = util.getListParametros(strParametrosEditor)> 
				<#list parametros as cadena>	
		${arrayDatos}.add("${cadena}"); 
				</#list>	
			</#if>			
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
		
		 <#if item.@data[0]??>
			 <#assign data =item.@data[0]>
			 <#if data != "">
		Object v=ctx.get("${data}");
		if (v!=null && v.toString().trim().length()>0){
			if(${arrayDatos}.contains(ctx.get("${data}")))
				${id}.setText(ctx.get("${data}").toString());
   			
		}	 
			</#if>	
		</#if>
		
		<#if item.@objectVar[0]??>
		 <#assign objectVar = item.@objectVar[0]>
    	Object v=ctx.get("${objectVar}");
		if (v!=null && v.toString().trim().length()>0){
			if(${arrayDatos}.contains(ctx.get("${objectVar}")))
				${id}.setText(ctx.get("${objectVar}").toString());
   			
		}
		
    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    			Object v=ctx.get("${objectVar}");
				if (v!=null && v.toString().trim().length()>0){
					if(${arrayDatos}.contains(ctx.get("${objectVar}")))
						${id}.setText(ctx.get("${objectVar}").toString());
		   			
				}
    		}
    	});
    	</#if>
    			
    	