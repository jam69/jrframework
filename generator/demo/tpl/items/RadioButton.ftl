<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> RadioButton.ftl  
		
		final JPanel ${id}=new JPanel();
		${id}.setOpaque(false);
		${id}.setBackground(RunnerLF.getColor("RadioButton.fondo"));
		${id}.setFont(RunnerLF.getFont("RadioButton.font"));
		
		<#if item.@nullName[0]??>
			<#assign nullName=item.@nullName>
			<#assign radioButtonnulo="radioButtonnulo"+id+"nulo1">
			final JRadioButton ${radioButtonnulo} = new JRadioButton("${nullName}");
			${radioButtonnulo}.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
								ctx.put("${item.@varSelected}",((JRadioButton)e.getSource()).getText());
							}
						 });		
		</#if>
		
		<#if item.@data[0]??>
			<#assign bg="bg"+id>
			ButtonGroup ${bg} = new ButtonGroup();
			<#assign indiceRadioButton=0>
			<#list item.@data?split(",")as dato>
				<#assign nameRadio="RadioButton"+id+indiceRadioButton>
				final JRadioButton ${nameRadio} = new JRadioButton("${dato}");
				<#if item.@varSelected[0]??>
					${nameRadio}.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e) {
									ctx.put("${item.@varSelected}",((JRadioButton)e.getSource()).getText());
							}
				    	});		
				</#if>
				${bg}.add(${nameRadio});
				${id}.add(${nameRadio});
				<#assign indiceRadioButton=indiceRadioButton+1>
			</#list>
			<#if nullName??>
				${bg}.add(${radioButtonnulo});
				${id}.add(${radioButtonnulo});
			</#if>
		</#if>

		<#if item.@variable[0]??>
		<#assign lisRadioVar="lisRadioVar"+id>
		ArrayList ${lisRadioVar} = (ArrayList)ctx.get("${item.@variable}");	
		if (${lisRadioVar}!=null){
			${id}.removeAll();
			<#assign bgradioVar="bgradioVar"+id>
					ButtonGroup ${bgradioVar}= new ButtonGroup();
						for (int i =0;i<${lisRadioVar}.size();i++){
						<#assign radioButtonVar = "radioButton" + id>
				    		JRadioButton ${radioButtonVar} = new JRadioButton(${lisRadioVar}.get(i).toString());
				    		${radioButtonVar}.setAlignmentX(JRadioButton.LEFT_ALIGNMENT);
				    		${radioButtonVar}.setFont(RunnerLF.getFont("RadioButton.font"));
				    		${radioButtonVar}.setForeground(RunnerLF.getColor("RadioButton.color"));
				    		${radioButtonVar}.setHorizontalAlignment(JRadioButton.LEFT);
				    		<#if item.@varSelected[0]??>
				    		${radioButtonVar}.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e) {
										ctx.put("${item.@varSelected}",((JRadioButton)e.getSource()).getText());
									}
					    		});
					    	
					    	</#if>
					    	${bgradioVar}.add(${radioButtonVar});
					    	
					    	${id}.add(${radioButtonVar});
				    		}
				    <#if nullName??>
						${bgradioVar}.add(${radioButtonnulo});
						${id}.add(${radioButtonnulo});
					</#if>
				}
		ctx.addListener("${item.@variable}",new ContextListener(){
				public void valueChanged(String key, Object value) {
					${id}.removeAll();
					ArrayList lis = (ArrayList)ctx.get("${item.@variable}");
					<#assign bgradioVar="bgradioVar"+id>
					ButtonGroup ${bgradioVar}= new ButtonGroup();
						for (int i =0;i<lis.size();i++){
				    		<#assign radioButtonVar = "radioButton" + id>
				    		JRadioButton ${radioButtonVar} = new JRadioButton(lis.get(i).toString());
				    		${radioButtonVar}.setAlignmentX(Component.LEFT_ALIGNMENT);
				    		${radioButtonVar}.setAlignmentX(JRadioButton.LEFT_ALIGNMENT);
				    		${radioButtonVar}.setFont(RunnerLF.getFont("RadioButton.font"));
				    		${radioButtonVar}.setForeground(RunnerLF.getColor("RadioButton.color"));
				    		${radioButtonVar}.setHorizontalAlignment(JRadioButton.LEFT);
				    		<#if item.@varSelected[0]??>
				    		${radioButtonVar}.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e) {
										ctx.put("${item.@varSelected}",((JRadioButton)e.getSource()).getText());
								}
					    	});
					    	</#if>
					    	${bgradioVar}.add(${radioButtonVar});
					    	${id}.add(${radioButtonVar});
				    	}
				    
				<#if nullName??>
					${radioButtonnulo}.setEnabled(true);
					${bgradioVar}.add(${radioButtonnulo});
					${id}.add(${radioButtonnulo});
				</#if>
				    	
				}
		});
		</#if>

		<#if item.@varSelected[0]??>
			<#assign obj="obj"+id>
			String ${obj} =(String)ctx.get("${item.@varSelected}");
			if (${obj}!=null){
				for (int i =0;i<${id}.getComponentCount();i++){
					JRadioButton rb= (JRadioButton)${id}.getComponent(i);
					if (rb.getText().equals(${obj})){
						rb.setSelected(true);
					}
				}
			}
			<#if nullName??>
				else{
					for (int i =0;i<${id}.getComponentCount();i++){
						JRadioButton rb= (JRadioButton)${id}.getComponent(i);
						if (rb.getText().equals("${nullName}")){
							rb.setSelected(true);
							}
						}
					}		
			</#if>
			ctx.addListener("${item.@varSelected}",new ContextListener(){
				public void valueChanged(String key, Object value) {
					String obj =(String)ctx.get("${item.@varSelected}");
					if (obj!=null){
						for (int i =0;i<${id}.getComponentCount();i++){
							JRadioButton rb= (JRadioButton)${id}.getComponent(i);
							if (rb.getText().equals(obj)){
								rb.setSelected(true);
							}
						}
					}
					<#if nullName??>
					else{
						for (int i =0;i<${id}.getComponentCount();i++){
							JRadioButton rb= (JRadioButton)${id}.getComponent(i);
							if (rb.getText().equals("${nullName}")){
								rb.setSelected(true);
								}
							}
						}		
					</#if>		
				
				}
			});
		
		</#if>
		
		<#if item.@orientation=="HORIZONTAL">
			${id}.setLayout(new BoxLayout(${id},BoxLayout.X_AXIS));
		<#else>
			${id}.setLayout(new BoxLayout(${id},BoxLayout.Y_AXIS));
		</#if>		
					
		<#assign enableCond = item.@enableCond >
		
		<#if enableCond?is_string>{
			boolean opc = ctx.check("${enableCond?j_string}");
			${id}.setEnabled(opc);
			for(int j=0;j<${id}.getComponentCount();j++){
				JRadioButton rd= (JRadioButton)${id}.getComponent(j);
				rd.setEnabled(opc);
				if(!rd.isEnabled()){
					rd.setForeground(RunnerLF.getColor("RadioButtonDesactivado.color"));
					}
				else{
					rd.setForeground(RunnerLF.getColor("RadioButton.color"));
					}
				}
			
		    for(String v:ctx.evalVarNames("${enableCond?j_string}")){
		    	ctx.addListener(v,new ContextListener(){
					public void valueChanged(String key, Object value) {							
						boolean opc = ctx.check("${enableCond?j_string}");
						${id}.setEnabled(opc);
						for(int j=0;j<${id}.getComponentCount();j++){
							JRadioButton rd= (JRadioButton)${id}.getComponent(j);
							rd.setEnabled(opc);
							if(!rd.isEnabled()){
								rd.setForeground(RunnerLF.getColor("RadioButtonDesactivado.color"));
								}
							else{
								rd.setForeground(RunnerLF.getColor("RadioButton.color"));
								}
							}
						}	        			
		        	});
		        }
		     }
		</#if>
		
		// Fin-RadioButton
		