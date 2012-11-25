<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// DialogButton  
		 
		final JButton ${id}=new JButton();
		<#assign dialog = "dialog" + id>
		final JDialog ${dialog} = new JDialog();
		// se cierra la pantalla
		${dialog}.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// desactivas las ventanas padre
		${dialog}.setModal(true);
		//operacion que se activa al cerrar el dialogo
		<#if item.@dismissOperation[0]??>
			<#assign operation =  item.@dismissOperation>
		${dialog}.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				Action a= (Action)ctx.get("${operation}");
	 	        a.actionPerformed(new ActionEvent(e.getSource(), e.getID(),"a"));
			}
		});
		</#if>
		<#assign botonera = "botonera" + id>
		final JPanel ${botonera} = new JPanel();
		${botonera}.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		
		<#assign ok = "ok" + id>

					
		<#if item.@labelButtonDialog[0]??>
			<#assign labelButtonDialog =  item.@labelButtonDialog>
		final JButton ${ok} = new JButton("${labelButtonDialog}");
		<#else>
		final JButton ${ok} = new JButton("OK");
		</#if>
		${ok}.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ev) {
						${dialog}.dispose();
					}
				});
		
		
		
		${id}.setFont(RunnerLF.getFont("botondialog.font"));
		
		<#if item.@text[0]??>
		<#assign text = item.@text >
		${id}.setText("${text}");
		</#if>
		
		<#if item.@pathIcono[0]??>
		<#assign pathIcono = item.@pathIcono >
		${id}.setIcon(new ImageIcon(es.indra.humandev.runner.core.utils.ImageLoader.urlIcon(${pathIcono})));
		</#if>
		<#assign content = "content" + id>
		<#if item.@xml[0]??>
		<#assign xml = item.@xml >
		<#assign indice = xml?index_of(".")>
		<#if (indice > 0)>
		<#assign form = "forms.form"+ xml?substring(0,indice)>
		${form} form = new ${form}(ctx, null);
		final JPanel ${content} = form;
		<#else>
		${xml} form = new ${xml}(ctx, null);
		final JPanel ${content} = form;
		</#if>
		</#if>
		
		${id}.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent v) {		
				//si pulsa el boton del dialogo este se cierra
				<#if item.@condicion[0]??>
				<#assign condicion =  item.@condicion>
				Object obj = ctx.get("{${condicion}");
				${ok}.setEnabled(obj != null);
				ctx.addListener("{${condicion}", new ContextListener() {
					@Override
					public void valueChanged(String key, Object value) {
						Object obj = ctx.get("{${condicion}");
						${ok}.setEnabled(obj != null);
						}
				});
				</#if>
				${botonera}.add(${ok});
				Container container = ${dialog}.getContentPane();
				container.add(BorderLayout.CENTER, ${content});
				container.add(BorderLayout.SOUTH, ${botonera});
				${dialog}.setTitle("${text}");
				${dialog}.pack();
			

			JButton b = (JButton) v.getSource();
			// optiene el frame padre del boton
			Frame f = JOptionPane.getFrameForComponent(b);
			// posicionamiento dentro de la ventana
			${dialog}.setLocationRelativeTo(f);
			${dialog}.setVisible(true);
				
			
		}});
		
	<#if enableCond?is_string>
	<#assign enableCond = enableCond?j_string>
			${id}.setEnabled(ctx.check("${enableCond}"));
   			for(String varName:ctx.evalVarNames("${enableCond}")){
   				ctx.addListener(varName, new ContextListener(){
    				public void valueChanged(String key,Object value){
    					${id}.setEnabled(ctx.check("${enableCond}"));
    				}
    			});
    		}    
</#if>
		// DialogButton
		
		