<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
		-->
		<#assign readOnly = item.@readOnly/>
		<#if readOnly="true">
				final JLabel ${id}=new JLabel();
			<#if item.@objectVar[0] ?? >
				<#assign obj ="obj"+id>
		Object ${obj}=ctx.get("${item.@objectVar}");
		${id}.setText(${obj}!=null?${obj}.toString():"");
		ctx.addListener("${item.@objectVar}",new ContextListener(){
			public void valueChanged(String key,Object value){
				Object obj=ctx.get("${item.@objectVar}");
				${id}.setText(obj!=null?obj.toString():"");
			}
		});	
			</#if>	
		<#--
				<#assign editor = item.@editor?split("(")[0]>
			<#include "../renderers/render" + editor+".ftl"/>-->
		<#else>
			<#assign editor = item.@editor?split("(")[0]>
			<#include "../editores/" + editor+".ftl"/>
		</#if>		
		 