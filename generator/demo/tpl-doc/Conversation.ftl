<#--
    Plantilla de Clases de Conversacion
    se llama desde la plantilla  MainGenerate.render(Conversation,...)
    Recibe las  variables:
    		"app"			Application a la que pertenece
            "conversation"	Conversacion a la que pertenece
            "definitions"   Metodo que parsea las Definiciones (tablas) ??
            "defs"			Metodo que parsea las definiciones (vertiacal/horizontal panel)??
            "stripHTML" 	Metodo que elimina el HTML ??
            "genID"			Metodo que genera ID únicos ??
            "escape"		Metodo que quotea las " en un texto
            "toJava"		Metodo que Javaiza un nombre de fichero
    Notas:
    	
-->   
<html>
<header>
<title>${app.id} - ${conversation.id} - Application Documentation</title>
</header>
<body>
<h1>${conversation.id} User Manual</h1>

List of Windows:
<ul>
	  <#list conversation.windows  as win>
    <li><a href="./${win.id}.html">${win.name}</a></li>
	  </#list>   
</ul>

</body>  
</html> 

    
