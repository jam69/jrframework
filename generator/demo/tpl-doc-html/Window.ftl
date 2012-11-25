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
          <!-- xml=${panel.modelProperty("xml")!}
	      <#local form = parserForm.parseForm(panel.modelProperty("xml")) >  
	      <#list form.getCells() as cell>
              <#local item = cell.item >
              <#-- para depuracion -->
          ITEM:  ${item.type}
              <#list item.props.keySet() as attr>
              	<#if attr=="text">
			 - ${attr} = ${stripHTML(item.props.get(attr))}
			    <#else>              	
             - ${attr} = ${item.props.get(attr)}
                </#if>
              </#list>
              <#-- fin para depuracion -->
              <#local name= nombrePanel+"_item_"+cell_index />             
              <#local x = item.setProp("id",name) />
              <#local x = CUtil.add(items,item) />              
          </#list>
          -->	    	
	   <#else>
	       <#list panel.getPanels() as p>
	          <@UnPanel p nombrePanel+"_"+p_index items/>
	       </#list>
	   </#if> 	
</#macro>	   

<xdoc>

<name>${window.name}</name>
<desc>
${window.name} tiene de ayuda ${docInfo(window.name).name!}
</desc>    
    <#-- metemos todos los items en la lista items2 -->
    <#assign panel = window.getPanel() />
    <#assign items2 = CUtil.create() />
	<@UnPanel panel "p" items2 />
<hr>	
<attributelist>
 <#list items2 as item>	
      <#import "items/"+item.type+".ftl" as ItemMacro>
      <@ItemMacro.Item item item_index  />		    		    	    	
</#list>	
</attributelist>


</xdoc>    
