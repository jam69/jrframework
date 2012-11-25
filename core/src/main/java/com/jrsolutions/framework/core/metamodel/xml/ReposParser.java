package com.jrsolutions.framework.core.metamodel.xml;

import com.jrsolutions.framework.core.metamodel.MetaAttrDyn;
import com.jrsolutions.framework.core.metamodel.MetaEntityDyn;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *  Obtiene un Repositorio con los tipos o clases, a partir de un
 *  fichero XML.
 */
public class ReposParser extends DefaultHandler {

	private static Logger log = Logger.getLogger(ReposParser.class.getName()); //$NON-NLS-1$
	  
	private static final String NO_REPOS_FILE="fichero repos.xml no encontrado"; 

//	private Digester digester;

	
	 
	 private static final String SAXDRIVER=null;
     // "org.apache.crimson.parser.XMLReaderImpl";
     // "org.apache.xerces.parsers.SAXParser"  Para Xerces
     // "gnu.xml.aelfred2.SAXDriver"   Para AElfred2
     // "gnu.xml.aelfred2.XmlDriver"   Para AElfred2
	 
	 private static final String REPOS="Repos";
	 private static final String TYPE="Type";
	 private static final String PROP="Prop";
	    
	 private static final String ATTR_NAME="name";
	 private static final String ATTR_DISPLAY_NAME="displayName";
	 private static final String ATTR_CLASS_NAME="className";
	 private static final String ATTR_ORDEN="orden";
	 private static final String ATTR_EDITOR="editorString";
	 
	 
	 // private Stack stack=new Stack(); No tenemos anidacion
	 private Repos repos;
	 private MetaEntityDyn info;
	 
	public ReposParser() {

	}

	static public Repos readRepos(URL url) {
		 try {
        //XMLReader parser=XMLReaderFactory.createXMLReader(SAXDRIVER);
        XMLReader parser=XMLReaderFactory.createXMLReader();
        //parser.setFeature("http://xml.org/sax/features/validation",true);
        ReposParser rp=new ReposParser();
        parser.setContentHandler(rp);
        parser.parse(new InputSource(url.openStream()));
        return rp.repos;
    } catch (SAXException ex) {
        System.out.println("SAX-EXCEP "+ex.getLocalizedMessage());
        System.out.println("SAX-EX2 "+ex.getException());
        ex.printStackTrace();
        System.out.println(" ------------- ");
        ex.getException().printStackTrace();
    } catch (IOException ex) {
    	log.warning(NO_REPOS_FILE);
//        ex.printStackTrace();
    }
    return null;	
	}
	


	 public void startDocument(){ }
	    
	    public void endDocument(){ }
	    
	    public void startElement(String uri,String name,String qName,Attributes attrs){
	        if(name.equals(REPOS)){
	            repos=new Repos();	          
//	            stack.push(repos);
	        }else if(name.equals(TYPE)){
	            info=new MetaEntityDyn();	            
	            info.setName(attrs.getValue(ATTR_NAME));
	            info.setDisplayName(attrs.getValue(ATTR_DISPLAY_NAME));
	            repos.add(info);
//	            stack.push(info);
	        }else if(name.equals(PROP)){
	            MetaAttrDyn attr=new MetaAttrDyn();	            
	            attr.setName(attrs.getValue(ATTR_NAME));
	            attr.setDisplayName(attrs.getValue(ATTR_DISPLAY_NAME));
	            attr.setTypeName(attrs.getValue(ATTR_CLASS_NAME));
	            attr.setEditor(attrs.getValue(ATTR_EDITOR));
	            attr.setOrden(Integer.parseInt(attrs.getValue(ATTR_ORDEN)));	            
	            //attr.setTypeName(attrs.getValue(TOOLTIPTEXT));
	            info.add(attr);
//	            stack.push(attr);
	        }
	    }
	    public void endElement(String uri, String name, String qName) throws SAXException {
//	        if(name.equals(REPOS)
//	        || name.equals(TYPE)
//	        || name.equals(PROP)
//	        ){
//	            stack.pop();            
//	        }
	         //System.out.println("</"+name+">");
	    }

}
