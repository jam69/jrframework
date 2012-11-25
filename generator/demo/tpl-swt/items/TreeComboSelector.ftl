<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// -> Spinner
		final JTree ${id}=new JTree();
		Object obj=ctx.get("${item.@variable}");
		${id}.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				ctx.put("${item.@variable}",c1.getSelectedItem());
			}
		});
		ctx.addChangeListener("${item.@variable}",new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent ev){
				Object obj=ctx.get("${item.@variable}");
				//${id}.setText(obj!=null?obj.toString():"");
			}
		});		
		// Fin-Spinner