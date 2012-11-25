		 <#import "../util/UtilEditor.ftl" as util>
		 final JLabel ${id} =new JLabel();
		 ${id}.setOpaque(true);
		 ${id}.setFont(RunnerLF.getFont("EditorDouble.font"));
		 
		<#assign decimales="-1">
		<#assign locale="ES">
		 
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 	<#assign decimales =valores[0]/>
				</#if>
				<#if valores[1]??>
					 	<#assign locale =valores[1]/>
				</#if>
			</#if>
			
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
		<#assign nf ="nf"+id>
		final NumberFormat ${nf} = NumberFormat.getNumberInstance(new Locale("${locale}"));
		
		<#if ((decimales?number)  >0) >
	 	${nf}.setMinimumFractionDigits(${enteros});
		${nf}.setMaximumFractionDigits(${enteros});
		<#else>
		${nf}.setMinimumFractionDigits(0);
		${nf}.setMaximumFractionDigits(0);
		</#if>
		<#if item.@data[0]??>
		<#assign data =item.@data[0]>
		${id}.setText(${nf}.format(${data}));
		</#if>
		<#if item.@objectVar[0]??>
		<#assign objectVar = item.@objectVar>
    	try {
			Object valor;
			if(ctx.get("${objectVar}") instanceof Integer || 
				ctx.get("${objectVar}") instanceof Double){
				valor = ctx.get("${objectVar}");
				${id}.setText(${nf}.format(valor));
			}
			else
				${id}.setText("");
			
		} catch (Exception e) {
			${id}.setText("");
		}
    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    		if (ctx.get("${objectVar}")==null)return ;
    		if (value.toString().equals(${id}.getText())) return;
    			value = ctx.get("${item.@objectVar}");
				if (value!=null){
					try {
						Number valor;
						if(value instanceof Integer || value instanceof Double)
							${id}.setText(${nf}.format(value));
						else{
							<#if (decimales?number>0)>
							valor = new Double(${nf}.parse(value.toString()).toString());
							<#else>
							valor = new Integer(${nf}.parse(value.toString()).toString());
							</#if>
							${id}.setText(${nf}.format(valor));
						}
			
					} catch (Exception e) {
						//para que no salte el listener de la perdida de foco
					}
				}else{
					${id}.setText("");
				}
          }
    	});
    	</#if>
    	