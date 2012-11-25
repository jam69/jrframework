<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// EditorLista
		final JTextField ${id}=new JTextField();
		${id}.setFont(RunnerLF.getFont("editorlista.font"));
		<#assign asistente = "asistente" + id >
		final swingrunner.componentes.listcomponetutils.EditorAssistant ${asistente};
		
        <#if item.@varCandidatos[0]??>
        	<#assign varCandidatos = item.@varCandidatos[0] >
        	<#assign candidatos = "candidatos"+ id >
        Collection ${candidatos}= (Collection) ctx.get("${varCandidatos}");
        ${asistente} = new swingrunner.componentes.listcomponetutils.EditorAssistant(${id}, ${candidatos}, new swingrunner.componentes.listcomponetutils.DefaultFilterAssistant(), 
		         new swingrunner.componentes.listcomponetutils.DefaultTextAssistantHandler(${id}));  
        if (${candidatos} != null)
        	${asistente}.setData((Collection) ctx.get("${varCandidatos}")); 		
        ctx.addListener("${varCandidatos}",new ContextListener(){            
            public void valueChanged(String key,Object value){
            	${asistente}.setData((Collection) ctx.get("${varCandidatos}"));
            }
        });
        <#else>
        	<#assign data = "data"+ id >
		ArrayList ${data} = new ArrayList();
		${data}.add("AAAAA");${data}.add("AABBB");${data}.add("ACCDE");
		${data}.add("BBBCC");${data}.add("BBAAA");${data}.add("DCCDE");
		${data}.add("YYAAA");${data}.add("JABBB");${data}.add("ZCCDE");
		${data}.add("QAAAA");${data}.add("SSBBB");${data}.add("BCCDE");
		${asistente} = new swingrunner.componentes.listcomponetutils.EditorAssistant(${id}, ${data}, new swingrunner.componentes.listcomponetutils.DefaultFilterAssistant(), 
		         new swingrunner.componentes.listcomponetutils.DefaultTextAssistantHandler(${id}));  
		</#if>
		<#if item.@variable[0]??>
			<#assign varItem = item.@variable>
			<#assign var = "var"+ id >
        String ${var}=(String)ctx.get("${varItem}");
        ${id}.setText(${var});
        
        ctx.addListener("${varItem}",new ContextListener(){
            public void valueChanged(String key,Object value){
                Object o=ctx.get("${varItem}");
                ${id}.setText(o==null ? "" : o.toString());
            }
        });
        
       ${id}.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	if( ${asistente}.isSeleccionListaMedianteTeclas())
            		ctx.put("${varItem}", ${id}.getText());
            	else
            		${asistente}.setSeleccionListaMedianteTeclas(false);
            }                
        });
        
       ${id}.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent ev){
            	if(!${asistente}.isPinchaunavez())
            		ctx.put("${varItem}",${id}.getText());                    
            }
        });
        </#if>  
        <#if item.@varResulParcial[0]??>
        <#assign strResulParcial = item.@varResulParcial>
        <#assign varResulParcial = item.@varResulParcial +id >
        String ${varResulParcial}="${strResulParcial}";
        if(${varResulParcial}!=null)
            ${id}.addKeyListener(new swingrunner.componentes.listcomponetutils.ParcialListener(ctx, ${id}, ${varResulParcial}));     
        </#if>
        
        <#assign enableCond = item.@enableCond >
        <#if enableCond?is_string>
		${id}.setEnabled(ctx.check("${enableCond?j_string}"));
	    for(String v:ctx.evalVarNames("${enableCond?j_string}")){
	    	ctx.addListener(v,new ContextListener(){
				public void valueChanged(String key, Object value) {							
					${id}.setEnabled(ctx.check("${enableCond?j_string}"));
					}	        			
	        	});
	        }
		</#if>   
		 
		
		 <#assign tooltip    = item.@tooltip >

		<#if tooltip?is_string> 
 			 <#if tooltip?starts_with("$") >		
		${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
		ctx.addListener("${tooltip?substring(1)}", new ContextListener(){
		public void valueChanged(String key, Object value) {
			 ${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
			}
		});		
			 <#else>
		${id}.setToolTipText("${tooltip}");
			 </#if>
		</#if>	
		// Fin-EditorLista
		
		
		