
		<#import "../util/UtilEditor.ftl" as util>
		final JLabel ${id} =new JLabel();
		${id}.setOpaque(true);
		${id}.setFont(RunnerLF.getFont("EditorDouble.font"));
		
		<#assign enteros="-1">
		<#assign decimales="-1">
		<#assign locale="ES">
		 
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">	
				<#assign editores = util.getListParametros(strParametrosEditor)> 
					<#if editores[0]??>
					 	<#assign enteros =editores[0]/>
					 </#if>
					<#if editores[1]??>
					 	<#assign decimales =editores[1]/>		
					</#if>		
					<#if editores[2]??>
					 	<#assign locale =editores[2]/>		
					</#if>		
			</#if>			
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
		<#assign nf ="nf"+id>
		final NumberFormat ${nf} = NumberFormat.getNumberInstance(new Locale("${locale}"));
		<#if decimales!="-1">
		${nf}.setMaximumFractionDigits(${decimales});
		</#if>
		<#if enteros!="-1">
		${nf}.setMaximumIntegerDigits(${enteros});
		</#if>
		<#if item.@data[0]??>
		<#assign data =item.@data[0]>
		${id}.setText(${nf}.format(${data}));
		</#if>
		<#if  item.@objectVar[0] ??>
    	try {
			Double valor;
			if(ctx.get("${item.@objectVar}") instanceof Double){
				valor = ((Double)ctx.get("${item.@objectVar}"));
				${id}.setText(${nf}.format(valor));
			}
			else
				${id}.setText("");
			
		} catch (Exception e) {
			${id}.setText("");
		}
    	ctx.addListener("${item.@objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    		if (ctx.get("${item.@objectVar}")==null)return ;
    			value = ctx.get("${item.@objectVar}"); 
				if (value!=null){
				if (value.toString().equals(${id}.getText())) return;
					try {
						Double valor;
						if(value instanceof Double)
							valor = ((Double)value);
						else
							 valor = new Double(${nf}.parse(value.toString()).toString());
					
						${id}.setText(${nf}.format(valor));
					} catch (Exception e) {
						//para que no salte el listener de la perdida de foco
					}
				}else{
					${id}.setText("");
				}
          }
    	});
    	</#if>