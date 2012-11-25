
		<#import "../util/UtilEditor.ftl" as util>
		<#assign formatDate = "dd/MM/yyyy">
		final JLabel ${id} =new JLabel();
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>		
		<#assign ali="D">
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>	
			<#if strParametrosEditor != "">	
				<#assign editores = util.getListParametros(strParametrosEditor)> 
				<#if editores[0]??>
				 	<#assign formatDate =editores[0]/>
				</#if>
			</#if>
		</#if>	
		
		<@util.setAlineacion id=id ali=ali/> 
		
		<#assign formato = "f"+id>
		final SimpleDateFormat ${formato} = new SimpleDateFormat("${formatDate}");
		
		<#if item.@objectVar[0]??>
		if(ctx.get("${item.@objectVar}")!=null){
			try {
				Object valor = ctx.get("${item.@objectVar}");
				if(valor instanceof Date){
					${id}.setText(${formato}.format(valor));
				}else{
					Date fecha = ${formato}.parse(ctx.get("var3").toString());
					${id}.setText(${formato}.format(fecha));
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
		ctx.addListener("${item.@objectVar}", new ContextListener(){
			@Override
			public void valueChanged(String key, Object value) {
				if(ctx.get("${item.@objectVar}")!=null){
					try {
						Object valor = ctx.get("${item.@objectVar}");
						if(valor instanceof Date){
							${id}.setText(${formato}.format(valor));
						}else{
							Date fecha =  ${formato}.parse(ctx.get("var3").toString());
							${id}.setText(${formato}.format(fecha));
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		<#else>
			<#if item.@data[0]??>
				<#assign data =item.@data[0]>
		try {
			Date fecha = ${formato}.parse("${data?j_string}");
			${id}.setText(${formato}.format(fecha));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			</#if>
		</#if>
	