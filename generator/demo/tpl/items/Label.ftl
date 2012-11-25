<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   

        <assign NULL_TEXT = "\"Un Texto\"" >
        -->	
        <#assign NULL_TEXT = "\"\"" >
		final JLabel ${id}=new JLabel();
		${id}.setFont(RunnerLF.getFont("label.font"));
		${id}.setForeground(RunnerLF.getColor("label.color"));
		<#if item.@text[0]??>
        	<#assign textExpr = item.@text >
        Object s=ctx.exprEval("${textExpr?j_string}",new ContextListener(){
        	public void valueChanged(String key,Object value){
               	Object s=ctx.exprEval("${textExpr?j_string}");
                ${id}.setText(s==null?${NULL_TEXT}:s.toString());
            }
        });
        ${id}.setText(s==null?${NULL_TEXT}:s.toString());
        </#if>         
        <#assign var = item.@variable >      
        <#if var?is_string> 
        Object s2=ctx.get("${var}");
        ${id}.setText(s2==null?${NULL_TEXT}:s2.toString());
        ctx.addListener("${var}",new ContextListener(){
        	public void valueChanged(String key,Object value){
            	Object s=ctx.get("${var}");
                ${id}.setText(s==null?${NULL_TEXT}:s.toString());
			}
		});
        </#if>
        <#if item.@tooltip[0]??  >
        	<#assign tooltip = item.@tooltip >
 			<#if tooltip?starts_with("$") >		
		${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
		ctx.addListener("${tooltip?substring(1)}", new ContextListener(){
			public void valueChanged(String key, Object value) {
				${id}.setToolTipText((String)ctx.get("${tooltip?substring(1)}"));
			}
		});	
			<#else>
		setToolTipText("${tooltip}");
			</#if>
		 </#if>
		 		 