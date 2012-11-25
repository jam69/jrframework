/*
 * Parser14.java
 *
 * Created on 21 de enero de 2008, 16:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.generator.docgenerator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Parsea un fichero de aplicacion.
 * 
 * @see Application
 * 
 */
public class PropsParser extends DefaultHandler{

    private Map<String,Tabla> map;
    
    public Map<String,Tabla> getMap() {
		return map;
	}

    private static final String SAXDRIVER=null;
            // "org.apache.crimson.parser.XMLReaderImpl";
            // "org.apache.xerces.parsers.SAXParser"  Para Xerces
            // "gnu.xml.aelfred2.SAXDriver"   Para AElfred2
            // "gnu.xml.aelfred2.XmlDriver"   Para AElfred2
    
    /**
     * Creates a new instance of Parser14
     */
    public PropsParser(){
    }
    
    /** 
     * Parsea la url indicada (puede ser local, remota, en un jar, etc,.. y
     * devuelve el modelo de aplicacion que representa.
     * 
     * @param url
     * @return
     */
    public Map<String,Tabla> parse(Reader reader){
        try {
            //XMLReader parser=XMLReaderFactory.createXMLReader(SAXDRIVER);
            XMLReader parser=XMLReaderFactory.createXMLReader();
            //parser.setFeature("http://xml.org/sax/features/validation",true);
            parser.setContentHandler(this);
            map=new HashMap<String,Tabla>();
            parser.parse(new InputSource(reader));
            return map;
        } catch (SAXException ex) {
            System.out.println("SAX-EXCEP "+ex.getLocalizedMessage());
            System.out.println("SAX-EX2 "+ex.getException());
            ex.printStackTrace();
            System.out.println(" ------------- ");
            ex.getException().printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void startDocument(){ }
    
    public void endDocument(){ }
    
    Tabla tabla;
    Field campo;
    public void startElement(String uri,String name,String qName,Attributes attrs){
    	
        if(name.equals("table")){        	
            tabla=new Tabla();
            tabla.name=attrs.getValue("name");
            map.put(tabla.name,tabla);
            System.out.println("tabla>"+tabla.name);
        }else if(name.equals("field")){
        	campo=new Field();
            campo.name=attrs.getValue("name");
            tabla.campos.put(campo.name,campo);
//            System.out.println("   field>"+campo.name);
        }
        chars.setLength(0);
        
    }

    StringBuffer chars=new StringBuffer();
    public void endElement(String uri, String name, String qName) throws SAXException {
    	if(name.equals("desc")){
    		campo.desc=chars.toString();
    	}else if(name.equals("type")){
    		campo.type=chars.toString();
    	}else if(name.equals("units")){
    		campo.units=chars.toString();
    	}else if(name.equals("range")){
    		campo.range=chars.toString();
    	}else if(name.equals("tableDesc")){
    		tabla.desc=chars.toString();
    	}
    	
    	chars.setLength(0);
    }

    @Override
	public void characters(char[] ch, int start, int length)throws SAXException	{
    	chars.append(ch,start,length);
	}

	public static void main(String args[]){
        try {
			PropsParser p=new PropsParser();
			Map map=p.parse(new FileReader("texto.out"));
			System.out.println(map.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void dumpStack(){
        System.out.println("-----DUMPSTACK------");
//        Iterator<Object> it=stack.iterator();
//        while(it.hasNext()){
//            System.out.println("----->"+it.next());
//        }
//        System.out.println("-----ENDDUMP------");
    }
}