 <#--
 Variables que recibe:
 	"app"  					  Application
    "panelDefinitionsParser"  DefinitionsParser
    "parserModificadores" 	  ParserModificadores
    "parserTabla"  			  ParserTabla
    "stripHTML"				  HTMLStripper
    "genID"				      GenID
    "traduce"				  GestorMultilingualidad
    "escape"				  TextEscape
    "toJava"				  ToJavaName
    "parserForm"			  ParserForm
    "CUtil"       			  Add
-->                        
<html>
<header>
<title>${app.id} - Application Documentation</title>
</header>
<body>
<h1>${app.id} User Manual</h1>

List of UseCase:
<ul>
	  <#list app.getConversations()  as conv>
    <li><a href="${conv.id}/${conv.id}.html">${conv.name}</a></li>
	  </#list>   
</ul>

</body>    