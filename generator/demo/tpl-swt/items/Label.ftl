<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> Label.ftl  
		<#--		
		<#if item.@varName ??>
		Text ${id}=new Text(${composite},SWT.READ_ONLY);
		${id}.setText((String)ctx.get("${item.@varName}");
		ctx.addListener("${item.@varName}",new PropertyChangeListener(){
		     public void propertyChange(PropertyChangeEvent ev){
		        ${id}.setText((String)ctx.get("${item.@varName}"));
		        }
		    });    
		<#else>   -->
		Text ${id}=new Text(${composite},SWT.READ_ONLY);
		${id}.setText("${item.@text}");
		<#-- </#if>  -->
		${id}.setLayoutData(${formDataID});
		// Fin-Label
