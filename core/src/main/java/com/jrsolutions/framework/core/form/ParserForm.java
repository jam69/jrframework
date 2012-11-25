
package com.jrsolutions.framework.core.form;


import java.io.IOException;
import java.net.URL;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;



/**
 * Parsea formularios.
 * En realidad simplemente guarda la estructura, sin hacer ni anlizar nada.
 * De este modo s�lo tenemos 3 clases independientemente del n�mero de 
 * diferentes widgets que tenga la aplicacion o los plugins.
 * 
 * @see Form
 * @see Cell
 * @see Item
 */
public class ParserForm extends DefaultHandler{

	private final String APP_PREFIX="/application/"; // Temporalmente
	
    //Log log=LogFactory.getLog("ParserFormularios");
     
    private static final String SAXDRIVER="org.apache.xerces.parsers.SAXParser";
            // "org.apache.crimson.parser.XMLReaderImpl"; Para JDK 1.5
            // "org.apache.xerces.parsers.SAXParser"  Para Xerces
            // "gnu.xml.aelfred2.SAXDriver"   Para AElfred2
            // "gnu.xml.aelfred2.XmlDriver"   Para AElfred2
    
    private Form form;
    private Cell cell;
    private String appPath;
    
    /**
     * Creates a new instance of Parser14
     */
    public ParserForm(){
    }
    
    public void setAppPath(String str){
    	appPath=str;
    }
    public Form parse(URL url){
        try {
            XMLReader parser=XMLReaderFactory.createXMLReader();
            //XMLReader parser=XMLReaderFactory.createXMLReader(SAXDRIVER);
            
            parser.setContentHandler(this);
            InputSource is=new InputSource(url.openStream());
           // log.info("LoadForm:"+str);
            parser.parse(is);
            return form;
        } catch (SAXException ex) {
            //log.error("SAX-EXCEP"+ex.getLocalizedMessage(),ex);
            //log.error("SAX-EX2"+ex.getException(),ex.getException());
            //ex.printStackTrace();
            //System.out.println("-------------");
            //ex.getException().printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public Form parseForm(String str){
        try {
            XMLReader parser=XMLReaderFactory.createXMLReader();
            //XMLReader parser=XMLReaderFactory.createXMLReader(SAXDRIVER);
            
            parser.setContentHandler(this);
            InputSource is=new InputSource(appPath+"/"+str);
           // log.info("LoadForm:"+str);
            parser.parse(is);
            return form;
        } catch (SAXException ex) {
            //log.error("SAX-EXCEP"+ex.getLocalizedMessage(),ex);
            //log.error("SAX-EX2"+ex.getException(),ex.getException());
            //ex.printStackTrace();
            //System.out.println("-------------");
            //ex.getException().printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public Form parse(String str){
        try {
            XMLReader parser=XMLReaderFactory.createXMLReader();
            //XMLReader parser=XMLReaderFactory.createXMLReader(SAXDRIVER);
            
            parser.setContentHandler(this);
            InputSource is=new InputSource(getClass().getResourceAsStream(APP_PREFIX+str));
           // log.info("LoadForm:"+str);
            parser.parse(is);
            return form;
        } catch (SAXException ex) {
            //log.error("SAX-EXCEP"+ex.getLocalizedMessage(),ex);
            //log.error("SAX-EX2"+ex.getException(),ex.getException());
            //ex.printStackTrace();
            //System.out.println("-------------");
            //ex.getException().printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void startDocument(){
       
    }
    
    public void endDocument(){
       
    }
    
    public void startElement(String uri,String name,String qName,Attributes attrs){
        
        //System.out.println("<"+name+">");
        
        if(name.equals("FormModel")){
            form=new Form();
            form.setRows(attrs.getValue("rows"));
            form.setColumns(attrs.getValue("columns"));
        }else if(name.equals("CellSpecModel")){
            cell=new Cell();
            cell.col=Integer.parseInt(attrs.getValue("col"));
            cell.row=Integer.parseInt(attrs.getValue("row"));
            cell.colspan=Integer.parseInt(attrs.getValue("colSpan"));
            cell.rowspan=Integer.parseInt(attrs.getValue("rowSpan"));
            cell.hAligment=attrs.getValue("HAlignment");
            cell.vAligment=attrs.getValue("VAlignment");
            cell.insets=attrs.getValue("insets");
            if(form!=null){
                form.addCell(cell);
            }
        }else {
            Item item=new Item();
            item.type=name;
            for(int i=0;i<attrs.getLength();i++){
                String k=attrs.getQName(i);
                String v=attrs.getValue(i);
                // Strip HTML //quita el html xq no puede procesarlo
                //v=v.replaceAll("<[^>]*>","");
                item.setProp(k,v);
            }
            cell.item=item;
        }
        
    }

    public void endElement(String uri, String name, String qName) throws SAXException {
         //System.out.println("</"+name+">");
    }
    

    
}
