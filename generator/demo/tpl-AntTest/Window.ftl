
<#-- macros ------------------------------------------------------>
  
<#macro UnPanel panel nombrePanel items >
      <#if panel.modelProperty("xml")?? >
          // -> xml=${panel.modelProperty("xml")!}
	      <#local form = parserForm.parseForm(panel.modelProperty("xml")) >  
	      <#list form.getCells() as cell>
              <#local item = cell.item >
              <#-- para depuracion -->
          //ITEM:  ${item.type}
              <#list item.props.keySet() as attr>
          //   - ${attr} = ${item.props.get(attr)}
              </#list>
              <#-- fin para depuracion -->
              <#local name= nombrePanel+"_item_"+cell_index />             
              <#local x = item.setProp("id",name) />
              <#local x = CUtil.add(items,item) />              
          </#list>	    	
	   <#else>
	       <#list panel.getPanels() as p>
	          <@UnPanel p nombrePanel+"_"+p_index items/>
	       </#list>
	   </#if> 	
</#macro>	   

/*   
 *
 * Fichero generado automaticamente -- NO EDITAR 
 *
 */
package app.${conversation.id};

import es.indra.modeler.runnerme.*;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
           

/** Implementacion de la ventana ${window.id} de la conversacion ${conversation.id} */
public class ${window.id} extends Form implements CommandListener, ItemStateListener{
	
	private ConvState cs;
	private Contexto  ctx;
	
	// Operaciones
	<#list window.getOperations() as ope>
    Command comand_${ope.name} = new Command("${ope.label}",Command.SCREEN,1);
    </#list>
    // Beans
    <#list window.getBeans() as bean>
    ${bean.className} bean_${bean.name} = new ${bean.className}();
    </#list>
    
    <#-- metemos todos los items en la lista items2 -->
    <#assign panel = window.getPanel() />
    <#assign items2 = CUtil.create() />
	<@UnPanel panel "p" items2 />
	
	/* items */
    Item[]              misItems=new Item[${items2?size}];
    ItemStateListener[] misItemsListener=new ItemStateListener[${items2?size}];
    	
	    	
	public ${window.id}(ConvState cs2){
		super("Ventana: ${window.name}");
		this.cs=cs2;
		ctx=cs.getContexto();
	    <#list window.getOperations() as ope>
	    addCommand(comand_${ope.name});
	    </#list>
	    
	    // - Items -	    			     	    		
	    <#list items2 as item>	
	      <#import "items/"+item.type+".ftl" as ItemMacro>
          <@ItemMacro.Item item item_index  />		    		    	    	
		</#list>	
			    		    	    	    		    	    	  	
		setCommandListener(this);
		setItemStateListener(this);
		cs.setForm(this);
	}
    
    public void commandAction(Command c, Displayable s) {    
     <#list window.getOperations() as ope>
          if(c == comand_${ope.name}){
              <#if ope.beanName?? >
          	  // execute  ${ope.method!} sobre ${ope.beanName!}
          	  <#if ope.method??>
          	  bean_${ope.beanName}.${ope.method};
          	  <#else>
          	  bean_${ope.beanName}.execute();
          	  </#if>
          	  </#if>
          	  <#list ope.getOptions() as opc>
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
          	  </#list>          	  
          }
    </#list>      
    }
    
    public void itemStateChanged(Item item){
    	for(int i=0;i<${items2?size};i++){
    		if(misItems[i]==null)continue;
    		if(misItems[i]==item){
    			misItemsListener[i].itemStateChanged(item);
    			break;
    		}
    	}		
	}
	
}

    
