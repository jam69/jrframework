<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// LocalFile
		<#import "util/UtilEditor.ftl" as util>
		 <#assign varSelected    = item.@varSelected >		
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
		/*es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(
				RunnerLF.getLabel("LocalFile.icons"))); //$NON-NLS-1$
			*/
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
				if (option == ${fileChooser}.APPROVE_OPTION) {
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
			
			Object obj = ctx.get("${varSelected}")==null ? "" : ctx.get("${varSelected}");
			if(obj!=null){
				if (obj instanceof java.io.File){
					${fileChooser}.setSelectedFile((java.io.File)obj);
					${textfield}.setText(((java.io.File)obj).getAbsolutePath());
				}else{
					${fileChooser}.setSelectedFile(new java.io.File((String)obj));
					${textfield}.setText(obj.toString());
				}
			}
			ctx.addListener("${varSelected}", new ContextListener(){
				public void valueChanged(String key, Object value) {
					if (value instanceof java.io.File){
    					${fileChooser}.setSelectedFile((java.io.File)value);
    					${textfield}.setText(((java.io.File)value).getAbsolutePath());
    				}else{
    					${fileChooser}.setSelectedFile(new java.io.File(value.toString()));
    					${textfield}.setText(value.toString());
    				}
				}
				
			});
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
		
		// LocalFile
		
		