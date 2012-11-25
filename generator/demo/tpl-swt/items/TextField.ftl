<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> TextField.ftl  
		final Text ${id}=new Text(${composite},SWT.SINGLE);
		Object val${id}=ctx.get("${item.@variable}");
		${id}.setText(val${id}!=null?(String)val${id}.toString():"");
		ctx.addChangeListener("${item.@variable}",new PropertyChangeListener(){
		     public void propertyChange(PropertyChangeEvent ev){
		        ${id}.setText((String)ctx.get("${item.@variable}"));
		        }
		    });  
		${id}.addModifyListener(new ModifyListener(){
		     public void modifyText(ModifyEvent ev){
		        ctx.put("${item.@variable}",${id}.getText());
		        }
		 });      
		// Fin-TextField
		