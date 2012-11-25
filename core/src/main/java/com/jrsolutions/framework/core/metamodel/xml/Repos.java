package com.jrsolutions.framework.core.metamodel.xml;

import com.jrsolutions.framework.core.metamodel.MetaEntity;
import com.jrsolutions.framework.core.metamodel.MetaInfoFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;



/**
 * Factory para informaci�n de tipos (clases) basado en un repositorio
 * XML.
 * 
 * @see Repos
 * @see ReposParser
 */
public class Repos implements MetaInfoFactory {
	
	private static Logger log = Logger.getLogger(Repos.class.getName()); //$NON-NLS-1$
	  

	private  Map<String,MetaEntity> types;
	




	public Repos() {
		if(types==null){
			types = new HashMap<String,MetaEntity>();
		}
	}
	
	
	/**
	 * A�ade un nuevo tipo al repositorio.
	 * @param info
	 */
	public void add(MetaEntity info){
		types.put(info.getName(), info);
	}
	
	/**
	 * Devuelve la informaci�n para el tipo (clase) pedido.
	 * @param className  nombre del tipo o clase
	 * @return la informaci�n correspondiente.
	 */
	public MetaEntity getTypeInfo(String className) {
		return types.get(className);
	}
	
//	 public void toXml(File f){
//		try {
//			PrintStream out=null;
//			if(f==null){
//				out=new PrintStream(new FileOutputStream("repos.xml"));
//			}else{
//				out=new PrintStream(new FileOutputStream(f.getPath()+File.separator+"repos.xml"));
//			}
//			 out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
//			 //out.println("<!DOCTYPE Application PUBLIC '-//Sun Microsystems Inc.//DTD Application Server 8.0 Domain//EN' 'app.dtd'>");
//			 out.println("<Repos>");
//			 Iterator it=types.values().iterator();
//			 while(it.hasNext()){
//				 MetaEntity info=(MetaEntity)it.next();
//				 toXML(out,info);
//			 }			 
//			 out.println("</Repos>");
//			 out.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	static void toXML(PrintStream out,MetaEntity info){
//		out.print("   <Type ");
//		out.print(" displayName=\""+info.getDisplayName()+"\"");
//		out.print(" name=\""+info.getName()+"\"");
////		if(info.getDocumentation()!=null)
////			out.print(" documentation=\""+Util.escapeText(info.getDocumentation())+"\"");
//		out.println(" >");
//		MetaAttribute [] a=info.getAttributesA();
//		for(int i=0;i<a.length;i++){
//			toXML(out,a[i]);
//		}
//		out.println("   </Type>");		
//	}	
//	
//	// TODO:   faltan atributos,....
//	
//	static void toXML(PrintStream out,MetaAttribute desc){
//		out.print("        <Prop ");
//		out.print(" name=\""+desc.getName()+"\" ");
//		out.print(" displayName=\""+desc.getDisplayName()+"\" ");
//		out.print(" className=\""+desc.getTypeName()+"\" ");
//		out.print(" orden=\""+desc.getOrden()+"\" ");
////		if(desc.getToolTipText()!=null) {
////			out.print(" toolTipText=\""+Util.escapeText(desc.getToolTipText())+"\" ");
////		}
//		if(desc.isHidden()) {
//			out.print(" hidden=\"true\" ");
//		}
////		if(desc.isMandatory()) {
////			out.print(" mandatory=\"true\" ");
////		}		
//		String str=desc.getEditor();
//		if(str!=null){
//			out.print(" editorString=\""+str+"\" ");				
//		}
////		if(desc.getDocumentation()!=null){
////			out.print(" documentacion=\""+Util.escapeText(d.getDocumentation()) +"\" ");
////		}
//		out.println(" />");		
//	}
	
	

	
}
