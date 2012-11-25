<#macro setAlineacionPanelButtonPreview id ali>
	<#if ali!="">
		<#assign ali=ali?upper_case>
		<#if ali=="C">
	  	${id}.setAli(javax.swing.JTextField.CENTER);
  		<#elseif ali=="D">
  		${id}.setAli(javax.swing.JTextField.RIGHT);
  		<#elseif ali=="I">
  		${id}.setAli(javax.swing.JTextField.LEFT);
		</#if>		 
	</#if>
</#macro>
<#macro setAlineacion id ali>
	<#if ali!="">
		<#assign ali=ali?upper_case>
		<#if ali=="C">
	  	${id}.setHorizontalAlignment(javax.swing.JTextField.CENTER);
  		<#elseif ali=="D">
  		${id}.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
  		<#elseif ali=="I">
  		${id}.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		</#if>		 
	</#if>
</#macro>

<#function getStrParametrosEditor editor>
<#local izqCor = editor?index_of ("(")>
		 <#if (izqCor?? && izqCor > 0) >
	 		<#local derCor = editor?index_of (")")>
	 		<#if (derCor?? && derCor> 0) >	
			 <#local strParametros = editor?substring(izqCor+1, derCor)>
				<#return strParametros>
			</#if>	
		 </#if>	
		<#return "">		
</#function >


<#function getListParametros editor>
	<#return editor?split(",")>	
</#function >


<#function getAlineacion strEditor>
	<#local cadena = strEditor?upper_case>
	<#local parametros = getListParametros(cadena)>
	<#list parametros as parametro>
		<#if (parametro?trim?length == 1) >
			<#local lista = [parametro?index_of("C"), parametro?index_of("D"), parametro?index_of("I")]>
			<#list lista as pos>
				<#if pos != -1 >
					<#return parametro?substring(pos,pos+1)>	
				</#if>	
			</#list>
		</#if>		
	</#list>	
	<#return "D">	
</#function >


<#function quitarAlineacion strEditor alineacion>
	<#local cadena=strEditor?upper_case>
	<#local parametros = getListParametros(cadena)>
	<#local posComaAnt = 0>
	<#local posComaSig = 0>
	
	<#list parametros as parametro>
		<#local posComaSig = strEditor?index_of(",", posComaSig+1)>
		<#if (parametro?trim?length == 1) >	
			<#local pos = parametro?index_of(alineacion)>
			<#if (pos != -1) >
				<#if posComaSig != -1 >
					 <#local strEditor = strEditor?substring(0,posComaAnt) + strEditor?substring(posComaSig)>
					 <#local posComaSig = posComaAnt>	
				<#else>
					 <#local strEditor = strEditor?substring(0,posComaAnt)>
				</#if>	
			<#else>
				<#local posComaAnt = posComaSig>	
			</#if>
		<#else>
			<#local posComaAnt = posComaSig>
		</#if>		
	</#list>	
	
	<#return strEditor>	
</#function >
