		//--> Integer
		
		 <#import "../util/UtilEditor.ftl" as util>
		 
		 final JLabel ${id} =new JLabel();
		 ${id}.setOpaque(true);
		 ${id}.setFont(RunnerLF.getFont("EditorCurrency.font"));
		 
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
					 <#assign locale =valores[0]/>
				</#if>
			</#if>
			
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
	
		<#assign nfEdicion ="nfEdicion"+id/>
		<#assign nfDisplay ="nfDisplay"+id/>
		final NumberFormat ${nfEdicion}= NumberFormat.getNumberInstance(
					es.indra.humandev.runner.core.utils.LocaleUtils.getLanguage("${locale}"));
		final NumberFormat  ${nfDisplay}= NumberFormat.getCurrencyInstance(
				es.indra.humandev.runner.core.utils.LocaleUtils.getLanguage("${locale}"));
		
		<#assign nfFormatterEdicion ="formatterEdicion"+id/>
		<#assign nfFormatterDisplay ="formatterDisplay"+id/>
		final javax.swing.text.NumberFormatter ${nfFormatterEdicion} = new javax.swing.text.NumberFormatter(${nfEdicion});
		final javax.swing.text.NumberFormatter ${nfFormatterDisplay} =new javax.swing.text.NumberFormatter(${nfDisplay});
		
		${nfDisplay}.setGroupingUsed(false);
		${nfFormatterEdicion}.setAllowsInvalid(true);
		
		
		
		
		<#if item.@data[0]??>
		<#assign data =item.@data[0]>
		${id}.setText(${nf}.format(${data}));
		</#if>
		<#if item.@objectVar[0]??>
		<#assign objectVar = item.@objectVar>
    	try {
    		if(ctx.get("${objectVar}") != null){
				try {
					Object valor = ctx.get("${objectVar}");
					if(valor instanceof Number)
						${id}.setText(${nfDisplay}.format(valor));
					else
						${id}.setText(${nfDisplay}.format((${nfEdicion}.parse(valor.toString()))));
					} 
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					}
				else{
					${id}.setText("");
					}
				}
			catch (Exception e) {
				${id}.setText("");
				ctx.put("${objectVar}", null);
			}

    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    		 {
	    		if(ctx.get("${objectVar}") != null){
	    			Object valor=null;
					try {
						valor = ctx.get("${objectVar}");
						if(valor instanceof Number)
							${id}.setText(${nfDisplay}.format(valor));
						else
							${id}.setText(${nfDisplay}.format((${nfEdicion}.parse(valor.toString()))));
						} 
					catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
						}
					else{
						${id}.setText("");
						}
				}
			}
    	});
    	</#if>
		
		//--> fin Integer
		