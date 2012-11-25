<#-- plantilla para TextField -->
<#macro Item item pos>
		// TextField
		String v_${pos}=(String)ctx.get("${item.prop("variable")}");       
		misItems[${pos}]=new TextField("Item:",v_${pos},10,TextField.PLAIN);
		append(misItems[${pos}]);
		misItemsListener[${pos}]=new ItemStateListener(){
			public void itemStateChanged(Item item){
				String v=((TextField)misItems[${pos}]).getString();
				System.out.println("Ha cambiado TextField "+item+" v="+v);
				ctx.put("${item.prop("variable")}",v);
			}
		};
		ctx.addChangeListener("${item.prop("variable")}",new ContextListener(){
			public void onChanged(String key,Object value){
			   ((TextField)misItems[${pos}]).setString((String)value);
			}
		});
			   
</#macro>  