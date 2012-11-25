#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// ComboBox  
		final Combo ${id}=new Combo(${composite},SWT.DROP_DOWN|SWT.READ_ONLY);
		${id}.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
		      	int i=${id}.getSelectionIndex();
		    	datos=ctx.get("${item.getProperty("varName")}"
		    	ctx.set("${item.getProperty("selectionVarName")}",datos.get(i));
		     }
		});
		Collection datos${id}=(Collection)ctx.get("${item.getProperty("varName")}");
		if (datos${id}!=null){
			Iterator it=datos${id}.iterator();
			while(it.hasNext()){
			  Object obj=it.next();
			  ${id}.add(obj.toString());
			  }
		}
		ctx.addListener("${item.getProperty("varName")}",new PropertyChangeListener(){
		    public void onPropertyChange(PropertyChangeEvent ev){
		        ${id}.clear();
		        ${id}.deselectAll();
		        Collection datos${id}=(Collection)ctx.get("${item.getProperty("varName")}");
				if (datos${id}!=null){
					Iterator it=datos${id}.iterator();
					while(it.hasNext()){
			  			Object obj=it.next();
			  			${id}.add(obj.toString());
			  		}
				}
			});
		ctx.addListener("${item.getProperty("selectionVarName")}",new PropertyChangeListener(){
		    public void onPropertyChange(PropertyChangeEvent ev){
		    	// Object obj=ctx.get("${item.getProperty("selectionVarName")}");
		    	// if(obj!=null){
		    	//       String s=obj.toString();
		    	//       // busca s y selecciona
		    	// }else{
		    	//     ${id}.select(-1);
		    	// }
		    	}
		    });
		// Fin-ComboBox