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

<html>
<head>
<style type="text/css">
.title1 {
	 font-family:"Times New Roman" ;
     font-size:48; 
     font-weight:bold; 
     color:blue;
     }
.title2 {
	 font-family:"Arial" ;
     font-size:36;
     font-weight:bold;
     color:black;
	 }
table{
	border-style:dotted;
	border-width:1;
	border-color:black;
	}

td,th {
	background: rgb(176,198,208);
	border-style:dotted;
	border-width:1;
	border-color:black;
	}     
hr {
    color:blue;
	} 
p {
     margin-left:20px;
   }
	
</style>
<title>Help Center</title>

</head>
<body>
<span class="title1">HELP CENTER</span><br>
<span class="title2">${window.name}</span>
<hr>
<h2>Paneles</h2>  
    
    <#-- metemos todos los items en la lista items2 -->
    <#assign panel = window.getPanel() />
    <#assign items2 = CUtil.create() />
	<@UnPanel panel "p" items2 />
<hr>
${window.name} tiene de ayuda ${docInfo(window.name).name!}
<hr>	
<table border="1" >
<tr><th>Name</th><th>Description</th><th>Acceptable Values</th></tr>
 <#list items2 as item>	
      <#import "items/"+item.type+".ftl" as ItemMacro>
      <@ItemMacro.Item item item_index  />		    		    	    	
</#list>	
</table>

<h2>Operaciones</h2>			    		    	    	    		    	    	  	
 
 <table border="1">
 <tr><th>Name</th><th>Bean</th><th>Method</th></tr>
     <#list window.getOperations() as ope>
        <tr><td>${ope.name}</td><td>${ope.beanName!}</td><td>${ope.method!}</td></tr>
<!--          	  <#list ope.getOptions() as opc>  
          	  <#if opc.result??>
          	  if (${opc.result}) {
          	  </#if>
          	  <#if opc.dest?? >
          	  	<#if opc.dest == "BACK" >
          	  	cs.getDisplay().setCurrent(cs.getForm());
          	  	<#elseif opc.dest== "FIRST" >
          	  	cs.getDisplay().setCurrent(cs.getFirst().getForm());
          	  	<#elseif opc.dest== "CLOSE" >
          	  	app.${app.id}.showInit();
          	  	<#else>
          	  	cs.getDisplay().setCurrent(new ${opc.dest}(new ConvState(cs)));
          	  	</#if>
          	  </#if>	
          	  <#if opc.result??>
          	  }
          	  </#if>
          	  </#list>  -->          	  
    </#list>
</table>
</body>
</html>    
