 			
 		<#import "../util/UtilEditor.ftl" as util>
		<#assign min="0">
		<#assign max="100">
		<#assign inc="-1">
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign editores = util.getListParametros(strParametrosEditor)> 
					<#if editores[0]??>
					 	<#assign min =editores[0]/>
					 </#if>
					 <#if editores[1]??>
					 	<#assign max =editores[1]/>		
					</#if>	
					<#if editores[2]??>
					 	<#assign inc =editores[2]/>		
					</#if>	
			</#if>			
		</#if>
		final JLabel ${id}=new JLabel();
		<@util.setAlineacion id=id ali=ali/> 	
		
		<#if item.@data[0]??>
			<#assign data =item.@data[0]>
		${id}.setText(new Integer(${data}));
		</#if>
		<#if item.@objectVar[0]??>
			<#assign objectVar = item.@objectVar>    	
		try {
			Integer valor;
			if(ctx.get("${objectVar}") instanceof Integer){
				valor = ((Integer)ctx.get("${objectVar}"));
				${id}.setText(valor.toString());
		}
		else
			${id}.setText("");
		
		} catch (Exception e) {
			${id}.setText("");
		}
    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    		value = ctx.get("${objectVar}");
				if (value!=null){
				   if (value.equals(${id}.getText())) return;
					try {
						Integer valor;
						if(value instanceof Integer)
							valor = ((Integer)value);
						else
							 valor = new Integer(value.toString());
						${id}.setText(valor.toString());
					} catch (Exception e) {
						//para que no salte el listener de la perdida de foco
					}
				}else{
					//${id}.setValue(null);
				}
          }
    	});
    	</#if>
		
		
		 