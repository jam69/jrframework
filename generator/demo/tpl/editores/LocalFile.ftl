<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		<#import "../util/UtilEditor.ftl" as util>
		<#assign varSelected    = item.@objectVar >		
		<#assign fileChooser = "fileChooser"+id >
		final JFileChooser ${fileChooser} = new JFileChooser("c:/");
		<#assign panel = id >
		final JPanel ${panel} = new JPanel();
		<#assign textfield = "textfieldLF" + id >
		final JTextField ${textfield} = new JTextField(10);
		${textfield}.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		${textfield}.setEditable(false);
		
		<#assign boton = "botonLC" +id>
		final JButton ${boton} = new JButton();
		${boton}.setIcon(es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(
				RunnerLF.getLabel("LocalFile.icons")));	
		${boton}.setPreferredSize(new Dimension(${boton}.getPreferredSize().width,
				${textfield}.getPreferredSize().height));
		${panel}.setLayout(new java.awt.BorderLayout());
		${panel}.add(${textfield}, java.awt.BorderLayout.CENTER);
		${panel}.add(${boton}, java.awt.BorderLayout.EAST);
		
		
		${boton}.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
						
				int option = ${fileChooser} .showSaveDialog(JOptionPane
					.getFrameForComponent(${panel}));
				if (option == JFileChooser.APPROVE_OPTION) {
					${textfield}.setText(${fileChooser}.getSelectedFile().getPath());		
					<#if varSelected?is_string> 
					 ctx.put("${varSelected}", ${textfield}.getText());
					</#if>
					}
			}});
			
	  <#assign enableCond = item.@enableCond >
		<#if enableCond?is_string>
		${boton}.setEnabled(ctx.check("${enableCond?j_string}"));
		${textfield}.setEnabled(ctx.check("${enableCond?j_string}"));
	    for(String v:ctx.evalVarNames("${enableCond?j_string}")){
	    	ctx.addListener(v,new ContextListener(){
				public void valueChanged(String key, Object value) {							
					${boton}.setEnabled(ctx.check("${enableCond?j_string}"));
					${textfield}.setEnabled(ctx.check("${enableCond?j_string}"));
					}	        			
	        	});
	        }
		</#if>
		
		<#assign tooltip    = item.@tooltip >
		<#if tooltip?is_string> 
 			 <#if tooltip?starts_with("$") >		
 		${boton}.setToolTipText(ctx.get("${tooltip?substring(1)}")+ "");
		${textfield}.setToolTipText(ctx.get("${tooltip?substring(1)}")+ "");
		ctx.addListener("${tooltip?substring(1)}", new ContextListener(){
		public void valueChanged(String key, Object value) {
			 ${boton}.setToolTipText((String)ctx.get(key));
			 ${textfield}.setToolTipText((String)ctx.get(key));
			}
		});		
			 <#else>
		${boton}.setToolTipText("${tooltip}");
		${textfield}.setToolTipText("${tooltip}");
			 </#if>
		</#if>	
		
		<#if varSelected?is_string> 
						
			${textfield}.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                	 ctx.put("${varSelected}", ${textfield}.getText());
                }                
            });
			${textfield}.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent ev){
                	 ctx.put("${varSelected}", ${textfield}.getText());       
                }
            });
		</#if>
		
		<#assign controlValBack="false"> 	
		<#assign mandatory  = item.@mandatory >
		<#if mandatory?has_content> 
			<#if mandatory=="true"> 
			<#assign valBack="valBack" + id> 
			<#assign controlValBack="true"> 	
		Object ${valBack} = RunnerLF.getLabel("mandatory.background");
    	if(${valBack}!=null &&
			!${valBack}.toString().equals("")&&
			!${valBack}.toString().equalsIgnoreCase("default")){			
				${valBack} = RunnerLF.getColor("mandatory.background");
				${textfield}.setBackground((Color)${valBack});
			}
		else{
			${valBack}=null;
			}
			<#assign valBorder="valBorder" + id> 
		Object ${valBorder} = RunnerLF.getLabel("mandatory.border");
			if(${valBorder}!=null &&
				!${valBorder}.toString().equals("")&&
				!${valBorder}.toString().equalsIgnoreCase("default")){
					${textfield}.setBorder(BorderFactory.createLineBorder(RunnerLF.getColor("mandatory.border")));
		}
			</#if>	
		</#if>	
		
		<#if item.@errorCond[0]??>
			<#assign colorBackGro =id + "ColorBack">		
			<#if controlValBack == "true">
		final Color ${colorBackGro} = (Color)${valBack};
			<#else>
		final Color ${colorBackGro} = null;
			</#if>
			<#assign errorCond = item.@errorCond?j_string>
		if(ctx.check("${errorCond}")){
			${textfield}.setBackground(RunnerLF.getColor("CompBackground.error"));
			}
		else{
			${textfield}.setBackground(${colorBackGro});
			}
		for(String varName:ctx.evalVarNames("${errorCond}")){
			ctx.addListener(varName, new ContextListener(){
				public void valueChanged(String key,Object value){
					if(ctx.check("${errorCond}")){
						${textfield}.setBackground(RunnerLF.getColor("CompBackground.error"));
					}else{
						${textfield}.setBackground(${colorBackGro});
						}
				}
			});
		}
		</#if>
		
		<#assign filtro = item.@filtro  >
		<#if filtro?is_string> 
			<#if filtro?starts_with("$")> 
		ctx.addListener(${filtro?substring(1)}, new ContextListener() {
			public void valueChanged(String str, Object obj) {
				es.indra.humandev.runner.core.utils.FileFilterUtil.seleccionFiltro(obj.toString(), ${fileChooser});
			}
		});
			<#else>
		es.indra.humandev.runner.core.utils.FileFilterUtil.seleccionFiltro("${filtro}", ${fileChooser});
			</#if>
		</#if>
	<#assign filtroEditor="-1">
	<#assign editor = item.@editor>
	<#assign strParametrosEditor = util.getStrParametrosEditor(editor)>	
		<#if strParametrosEditor != "">
		<#assign ali=util.getAlineacion(strParametrosEditor)>
			<#assign strParametrosEditor = util.quitarAlineacion(strParametrosEditor, ali)>		
			<#if strParametrosEditor != "">
				<#assign valores = util.getListParametros(strParametrosEditor)> 
				<#if valores[0]??>
					 	<#assign filtroEditor =valores[0]/>
				</#if>
			</#if>
		</#if>
		<#if filtroEditor!="-1">
		es.indra.humandev.runner.core.utils.FileFilterUtil.seleccionFiltro("${filtroEditor}", ${fileChooser});
		</#if>
		
		