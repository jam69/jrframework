<#--
    Plantilla de clases de Formulario
    se llama desde MainGenerate.procesaForm()
    Recibe las  variables:
     	"name":			Nombre del formulario (javaizado)
        "doc":			XML-DOC del formulario (DOM)
        "stripHTML":	Metodo que elimina el HTML ??
        "genID":		Metodo que genera ID únicos ??
        "escape":		Metodo que quotea las "
        "toJava":		Metodo que javaiza un String
    Notas:
    	La clase se genera en el packete: forms
    	Llama a las plantillas del directorio 'items'
    	Atencion que FreeMarker funciona en modo XML (DOM) con lo que se usa XPATH
-->  
/*
 *   Fichero generado automáticamente - NO EDITAR
 */
<#assign cols = doc.FormModel.@columns >
<#assign rows = doc.FormModel.@rows > 

package forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


import swtrunner.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ${name} {
	
	private Contexto ctx;
	
	public ${name}(Contexto ctx2,Composite shell){
		Group group=new Group(shell,SWT.NONE);
		this.ctx=ctx2;
		String columns="${cols}";
		String rows="${rows}";
		FormLayout layout=new FormLayout();
		group.setLayout(layout);
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		
		//Definimos todos los componentes del formulario
		
	<#list doc.FormModel.CellSpecModel as cell>    
		<#list cell.* as item>
		<#assign id = "c"+cell_index >
		<#assign formDataID = "formData"+cell_index >
		FormData ${formDataID} = new FormData();
		${formDataID}.top = new FormAttachment(0,60);
		${formDataID}.bottom = new FormAttachment(100,-5);
		${formDataID}.left = new FormAttachment(20,0);
		${formDataID}.right = new FormAttachment(100,-3);
			
		<#assign composite = "group">
		<#include "items/"+item?node_name+".ftl"/>		
		 // x=${cell.@col?string}+1
		 // y=${cell.@row?string}+1
		 // colSpan=${cell.@colSpan?string}
		 // rowSpan=${cell.@rowSpan?string}
		// No es necesario add(${id},cc);
		// button1.setLayoutData(${formDataID});	    		
		</#list>
	</#list>
		 
	}

}


