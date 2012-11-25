
//editor ComboIcons

		 <#import "../util/UtilEditor.ftl" as util>
		 
		 final JLabel ${id}=new JLabel();
		 final HashMap<String, Icon> values = new HashMap<String, Icon>();
		 ${id}.setFont(RunnerLF.getFont("textfield.font"));
			
		<#assign dataCbo="-1">
		<#assign ali="D">	
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign datos = util.getListParametros(strParametrosEditor)> 
				<#list datos as pp>
					<#assign cad =  pp?split(":")>
		values.put("${cad[0]?trim}", es.indra.humandev.runner.core.utils.ImageLoader.loadIcon("${cad[1]}"));
				</#list>
			 </#if>
		</#if>
		<@util.setAlineacion id=id ali=ali/>
		
		 <#if item.@data[0]??>
			<#assign data =item.@data[0]>
		Icon icon = (Icon)values.get("${data}");
		if(icon != null){
			${id}.setIcon(icon);
			${id}.setText("${data}");
		}
		</#if>
		
		<#if item.@objectVar[0]??>
		<#assign objectVar = item.@objectVar>
		Icon icon = (Icon)values.get(ctx.get("${objectVar}"));
		if(icon != null){
			${id}.setIcon(icon);
			${id}.setText(ctx.get("${objectVar}")+ "");
		}
    	ctx.addListener("${item.@objectVar[0]}", new ContextListener(){
				@Override
				public void valueChanged(String key, Object value) {
					Icon icon = (Icon)values.get(ctx.get("${objectVar}"));
					if(icon != null){
						${id}.setIcon(icon);
						${id}.setText(ctx.get("${objectVar}")+ "");
					}
				}
			});
    	</#if>
    	
				
	
