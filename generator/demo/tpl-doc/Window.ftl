<#--
    Plantilla de Clases de Ventana
    se llama desde la plantilla  MainGenerate.render(Conversation,Ventana,...)
    Recibe las  variables:
         	"window":		Ventana a convertir en Clase
            "conversation":	Conversacion a la que pertenece
            "appName":		Nombre de la Aplicacion ??
            "conv":			Lista de conversaciones ??
            "definitions"   Metodo que parsea las Definiciones (tablas)
            "defs"			Metodo que parsea las definiciones (vertiacal/horizontal panel)
            "stripHTML" 	Metodo que elimina el HTML ??
            "genID"			Metodo que genera ID únicos ??
            "escape"		Metodo que quotea las " en un texto
            "toJava"		Metodo que Javaiza un nombre de fichero
    Notas:
    	Llama a las Plantillas del directorio 'panels'
-->   
<#-- macros ------------------------------------------------------>
<#macro UnPanel panel nombrePanel items >
      <#if panel.modelProperty("xml")?? >
          <#-- xml=${panel.modelProperty("xml")!} -->
	      <#local form = parserForm.parseForm(panel.modelProperty("xml")) >  
	      <#list form.getCells() as cell>
              <#local item = cell.item >
              <#-- para depuracion
          ITEM:  ${item.type}
              <#list item.props.keySet() as attr>
              	<#if attr=="text">
			 - ${attr} = ${stripHTML(item.props.get(attr))}
			    <#else>              	
             - ${attr} = ${item.props.get(attr)}
                </#if>
              </#list>
               fin para depuracion -->
              <#local name= nombrePanel+"_"+cell_index />             
              <#local x = item.setProp("id",name) />
              <#local x = CUtil.add(items,item) />              
          </#list>
	   <#else>
	       <#list panel.getPanels() as p>
	          <@UnPanel p nombrePanel+"_"+p_index items/>
	       </#list>
	   </#if> 	
</#macro>	   
<xdoc>
<name>${window.name}</name>
<#assign t = docInfo.infoTabla(window.name) > 
<#if t ??>
<!-- Encontrada: ${t.name!} -->
<desc>${t.desc}</desc>
<#else>
<!-- No he encontrado ayuda -->
<desc>${window.name}</desc>
</#if>

    <#-- metemos todos los items en la lista items2 -->
    <#assign panel = window.getPanel() />
    <#assign items2 = CUtil.create() />
	<@UnPanel panel "c" items2 />
	<#list window.getDialogs() as dialog>
		<@UnPanel dialog.panel dialog.name+"c" items2 />
	</#list>
	
<attributelist>
<#list items2 as item>
	<#assign infoc = "xx">
	<!-- ID:${item.prop("id")!}-->
	<#if item.prop("objectVar") ??>
    	<#assign cname = item.prop("objectVar")>
    	<!-- var: ${cname} -->
    	<#assign infoc = docInfo.infoCampo(t,cname)>    
	<#elseif item.prop("varSelected") ??>
    	<#assign cname = item.prop("varSelected")>
    	<!-- var: ${cname} -->
    	<#assign infoc = docInfo.infoCampo(t,cname)>    
	<#elseif item.prop("variable") ??>
    	<#assign cname = item.prop("variable")>
    	<!-- var: ${cname} -->
    	<#assign infoc = docInfo.infoCampo(t,cname)>     
	</#if>
	<#-- Si tengo información externa -->
	<#if infoc != "xx">
	<attribute>
		<!-- var: ${cname} -->
	    <helpID conv="${conversation.id}" win="${window.id}" key="${toJava(infoc.name)}" key2="${docInfo.toKey(cname)}" />
		<id>${item.prop("id")!}</id>
        <name>${infoc.name}</name>
        <description>${infoc.desc!}</description>
        <type>${infoc.type!}</type>
        <units>${infoc.units!}</units>
        <range>${infoc.range!}</range>
 	</attribute>        
	<#else>
      	<#--la obtengo a partir del componente -->
    	<#import "items/"+item.type+".ftl" as ItemMacro>
    	<@ItemMacro.Item item item_index  />
	</#if>      		    		    	    	
</#list>	
</attributelist>
</xdoc>    
