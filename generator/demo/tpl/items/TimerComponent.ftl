		 final JLabel ${id} = new JLabel();
		 
		 final javax.swing.Timer timer;
		 		 
		 <#assign variable=item.@variable> 	
		 <#if variable?is_string>
		 
		 <#assign tiempo=item.@tiempo> 	
		 
		<#if tiempo?is_string>
		class TimerActionListerner implements ActionListener{
			boolean cambia = true;
			public void actionPerformed(ActionEvent e) {
				if ("${variable}"!=null){
						cambia = !cambia;
						ctx.put("${variable}",!cambia);
				}
			}
		
		};  	
		
		int time = es.indra.humandev.runner.core.utils.DateUtils.calcularTiempoMilisegundos("${tiempo}");
		timer= new javax.swing.Timer(time,new TimerActionListerner());
	
		</#if>
		<#assign start=item.@start> 	
		<#if start?is_string>
		${id}.setIcon(es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(RunnerLF.getLabel("Temporizador.procesando")));
		ctx.addListener("${start}", new ContextListener(){
			public void valueChanged(String key, Object value) {
				if (value.toString().equalsIgnoreCase("start")){
					timer.start();
					 ${id}.setIcon(es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(RunnerLF.getLabel("Temporizador.procesando2")));
				}else{	
					timer.stop();
					 ${id}.setIcon(es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(RunnerLF.getLabel("Temporizador.procesando")));
				}
				
			}
			
		});
		</#if>
		</#if>