<!-- cada uno de los paneles expandables -->
<script language="JavaScript" type="text/JavaScript">
<#if exp = "">
 <#assign exp = "false"/>  
  </#if>
Ext.onReady(function(){
    var pExp = new Ext.Panel({
        title: "${panel.label!'T&iacute;tulo'}",
        collapsible: ${exp},    
        autoHeight:true,   
        html: document.getElementById("${panel.name!}${panel.label!}ContenidoExp").innerHTML
    });

    var fitExp = new Ext.Panel({
	    renderTo: '${panel.label!}${panel.name!}PanelExpandable',
	    border:false,
	    layout:'fit',
	    items: [pExp]
	});
    
});
</script>
<div id="${panel.label!}${panel.name!}PanelExpandable">
</div>
<div style="height:0;width:0;visibility:hidden;display:none">
	<div id="${panel.name!}${panel.label!}ContenidoExp" style='height:100%;width:100%'> 
		<#include panel+".ftl" />
	</div>
</div>