<#-- 
     renderer del integer
-->
		//--> Integer
		 <#import "../util/UtilEditor.ftl" as util>
		 final JLabel ${id} =new JLabel ();
		 ${id}.setOpaque(true);
		 ${id}.setFont(RunnerLF.getFont("EditorDouble.font"));
		 ${id}.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		<#assign enteros="-1">
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
					 	<#assign enteros =valores[0]/>
				</#if>
			</#if>
		</#if>
		<@util.setAlineacion id=id ali=ali/> 
		<#assign nf ="nf"+id>
		final NumberFormat ${nf} = NumberFormat.getNumberInstance(new Locale("${locale}"));
		<#if enteros!="-1">
		${nf}.setMaximumIntegerDigits(${enteros});
		</#if>
		<#if item.@data[0]??>
			<#assign data =item.@data[0]>
		${id}.setText(${nf}.format(${data}));
		</#if>
		<#if item.@objectVar[0]??>
			<#assign objectVar = item.@objectVar>    	
			try {
			Integer valor;
			if(ctx.get("${objectVar}") instanceof Integer){
				valor = ((Integer)ctx.get("${objectVar}"));
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
						Integer valor;
						if(value instanceof Integer)
							valor = ((Integer)value);
						else
							 valor = new Integer(${nf}.parse(value.toString()).toString());
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
    			//--> fin Integer
		