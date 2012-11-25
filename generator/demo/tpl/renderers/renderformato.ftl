		 <#import "../util/UtilEditor.ftl" as util>
		 final JFormattedTextField ${id} =new JFormattedTextField();
		 ${id}.setEditable(false);
    	 ${id}.setBorder(null);
		 ${id}.setOpaque(true);
		 ${id}.setFont(RunnerLF.getFont("EditorDouble.font"));
		 ${id}.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		<#assign formatText = "-1">
		<#assign editor = item.@editor>
		<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#assign ali="D">	
		<#if strParametrosEditor != "">
			<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 	<#assign formatText =valores[0]/>
				</#if>
			</#if>
		</#if>
		<@util.setAlineacion id=id ali=ali/>
		<#if  formatText!="-1">
		try {
			${id}.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("${formatText}")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		</#if>
		<#if item.@data[0]??>
			<#assign data =item.@data[0]>
		${id}.setValue("${data}");
		</#if>
		<#if item.@objectVar[0]??>
			<#assign objectVar = item.@objectVar>
    	${id}.setText((String)ctx.get("${objectVar}"));
    	ctx.addListener("${objectVar}", new ContextListener(){
    		public void valueChanged(String key,Object value){
    			if (ctx.get("${objectVar}")==null)return ;
    			if (value.toString().equals(${id}.getText())) return;
    			String  valor = (String)ctx.get("${item.@objectVar}");
				if (value!=null){
					${id}.setText(valor);
				}else{
					${id}.setText("");
				}
          	}
    	});
    	</#if>
   