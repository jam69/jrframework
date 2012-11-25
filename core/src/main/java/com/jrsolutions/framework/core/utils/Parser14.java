/*
 * Parser14.java
 *
 * Created on 21 de enero de 2008, 16:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils;

import com.jrsolutions.framework.core.model.Application;
import com.jrsolutions.framework.core.model.Bean;
import com.jrsolutions.framework.core.model.Conversation;
import com.jrsolutions.framework.core.model.Dialog;
import com.jrsolutions.framework.core.model.HasModelProperties;
import com.jrsolutions.framework.core.model.ModelProperty;
import com.jrsolutions.framework.core.model.Operation;
import com.jrsolutions.framework.core.model.Option;
import com.jrsolutions.framework.core.model.Panel;
import com.jrsolutions.framework.core.model.Window;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Stack;

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
public class Parser14 extends DefaultHandler{
    
    private static final String APPLICATION="Application";
    private static final String CONVERSATION="Conversation";
    private static final String WINDOW="Window";
    private static final String BEAN="Bean";
    private static final String PANEL="Panel";
    private static final String PROPERTY="Property";
    private static final String DIALOG="Dialog";
    private static final String OPERATION="Operation";
    private static final String OPTION="Option";
    
    private static final String ATTR_ID="id";
    private static final String ATTR_NAME="name";
    private static final String ATTR_ROLES="roles";
    private static final String ATTR_INIT="init";
    private static final String ATTR_CLASSNAME="className";
    private static final String ATTR_INITMETHOD="initMethod";
    private static final String ATTR_DESTROYMETHOD="destroyMethod";
    private static final String ATTR_VALUE="value";
    private static final String ATTR_TITLE="title";
    private static final String ATTR_DISMISSOPERATION="dissmissOperation";
    private static final String ATTR_LABEL="label";
    private static final String ATTR_ACTIVATION="activation";
    private static final String ATTR_BEANNAME="beanName";
    private static final String ATTR_WARNING="warning";
    private static final String ATTR_METHOD="method";
    private static final String ATTR_DEST="dest";
    private static final String ATTR_DIALOG="dialog";
    private static final String ATTR_RESULT="res";
    private static final String ATTR_VISIBILITY="visible";
    private static final String ATTR_MESSAGE="msgVar";
    
    private static final String ATTR_TYPE="className";
    // Operation: tooltip
    
    
    private static final String SAXDRIVER=null;
            // "org.apache.crimson.parser.XMLReaderImpl";
            // "org.apache.xerces.parsers.SAXParser"  Para Xerces
            // "gnu.xml.aelfred2.SAXDriver"   Para AElfred2
            // "gnu.xml.aelfred2.XmlDriver"   Para AElfred2
    
    private Stack<Object> stack=new Stack<Object>();
    
    private Application app;
//    private Conversation conv;
//    private Window window;
//    private Bean bean;
//    private Panel panel;
    
    /**
     * Creates a new instance of Parser14
     */
    public Parser14(){
    }
    
    /**
     * Retorna la aplicación leída.
     * @return  La aplicacion parseada
     */
    public Application getApp(){
        return app;
    }
    
    /** 
     * Parsea la url indicada (puede ser local, remota, en un jar, etc,.. y
     * devuelve el modelo de aplicacion que representa.
     * 
     * @param url
     * @return
     */
    public Application parse(URL url){
        try {
            //XMLReader parser=XMLReaderFactory.createXMLReader(SAXDRIVER);
            XMLReader parser=XMLReaderFactory.createXMLReader();
            //parser.setFeature("http://xml.org/sax/features/validation",true);
            parser.setContentHandler(this);
            parser.parse(new InputSource(url.openStream()));
            return app;
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
     public Application parse(InputStream is){
        try {
            //XMLReader parser=XMLReaderFactory.createXMLReader(SAXDRIVER);
            XMLReader parser=XMLReaderFactory.createXMLReader();
            //parser.setFeature("http://xml.org/sax/features/validation",true);
            parser.setContentHandler(this);
            parser.parse(new InputSource(is));
            return app;
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
     
    public Application parse(String str){
        try {
            //XMLReader parser=XMLReaderFactory.createXMLReader(SAXDRIVER);
            XMLReader parser=XMLReaderFactory.createXMLReader();
            //parser.setFeature("http://xml.org/sax/features/validation",true);
            parser.setContentHandler(this);
            parser.parse(str);
            return app;
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
    
    public void startElement(String uri,String name,String qName,Attributes attrs){
        if(name.equals(APPLICATION)){
            app=new Application();
            app.setId(attrs.getValue(ATTR_ID));
            app.setName(attrs.getValue(ATTR_NAME));
            //app.setWindows();
            //app.setConversations();
            stack.push(app);
        }else if(name.equals(CONVERSATION)){
            Conversation conv=new Conversation();
            app.addConversation(conv);
            conv.setId(attrs.getValue(ATTR_ID));
            conv.setName(attrs.getValue(ATTR_NAME));
            conv.setRoles(attrs.getValue(ATTR_ROLES));
            conv.setInitWindowName(attrs.getValue(ATTR_INIT));
            //conv.setWindows();
            stack.push(conv);
        }else if(name.equals(WINDOW)){
            Window window=new Window();
            Object obj=stack.peek();
            if(obj instanceof Application){
               ((Application)obj).addWindow(window);
            }else if(obj instanceof Conversation){
               ((Conversation)obj).addWindow(window);
            }
            window.setId(attrs.getValue(ATTR_ID));
            window.setName(attrs.getValue(ATTR_NAME));
            //w.setPanel(attrs.getValue());
            //w.setBeans();
            //w.setOperations();
            //w.setDialogs();
            stack.push(window);
        }else if(name.equals(DIALOG)){
            Dialog dialog=new Dialog();
            Object obj=stack.peek();
            if(obj instanceof Window){
                ((Window)obj).addDialog(dialog);
            }
            dialog.setName(attrs.getValue(ATTR_NAME));
            dialog.setTitle(attrs.getValue(ATTR_TITLE));
            dialog.setDismissOperation(attrs.getValue(ATTR_DISMISSOPERATION));
            //bean.setPanel()
            stack.push(dialog);
        }else if(name.equals(BEAN)){
            Bean bean=new Bean();
            Object obj=stack.peek();
            if(obj instanceof Window){
                ((Window)obj).addBean(bean);
            }
            bean.setName(attrs.getValue(ATTR_NAME));
            bean.setClassName(attrs.getValue(ATTR_CLASSNAME));
            bean.setDestroyMethod(attrs.getValue(ATTR_DESTROYMETHOD));
            bean.setInitMethod(attrs.getValue(ATTR_INITMETHOD));
            bean.setValue(attrs.getValue(ATTR_VALUE));
            
            //bean.setPanel()
            stack.push(bean);
        }else if(name.equals(PROPERTY)){
            ModelProperty p=new ModelProperty();
            Object obj=stack.peek();
            if(obj instanceof HasModelProperties){
                HasModelProperties hmp=(HasModelProperties)obj;
                hmp.addModelProperty(p);
            }
            p.setName(attrs.getValue(ATTR_NAME));
            p.setValue(attrs.getValue(ATTR_VALUE));
            //bean.modelProperty()
        }else if(name.equals(PANEL)){
            Panel p=new Panel();
            Object obj=stack.peek();
            if(obj instanceof Panel){
                ((Panel)obj).addPanel(p);
            }else if(obj instanceof Dialog){
                ((Dialog)obj).setPanel(p);
            }else if(obj instanceof Window){
                ((Window)obj).setPanel(p);
            }        
            p.setName(attrs.getValue(ATTR_NAME));
            p.setLabel(attrs.getValue(ATTR_LABEL));
            p.setType(attrs.getValue(ATTR_TYPE));
            // TODO: roles
            //p.modelProperty()
            stack.push(p);
        }else if(name.equals(OPERATION)){
            Operation ope=new Operation();
            Object obj=stack.peek();
            ((Window)obj).addOperation(ope);
            ope.setName(attrs.getValue(ATTR_NAME));
            ope.setActivation(attrs.getValue(ATTR_ACTIVATION));
            ope.setBeanName(attrs.getValue(ATTR_BEANNAME));
            ope.setLabel(attrs.getValue(ATTR_LABEL));
            ope.setMethod(attrs.getValue(ATTR_METHOD));
            ope.setRoles(attrs.getValue(ATTR_ROLES));
            ope.setWarning(attrs.getValue(ATTR_WARNING));
            ope.setVisibility(attrs.getValue(ATTR_VISIBILITY));
            ope.setMessage(attrs.getValue(ATTR_MESSAGE));
            // Options
            stack.push(ope);
        }else if(name.equals(OPTION)){
            Option op=new Option();
            Object obj=stack.peek();
            ((Operation)obj).addOption(op);
            op.setDest(attrs.getValue(ATTR_DEST));
            op.setDialog(attrs.getValue(ATTR_DIALOG));
            op.setResult(attrs.getValue(ATTR_RESULT));            
        }
    }

    public void endElement(String uri, String name, String qName) throws SAXException {
        if(name.equals(CONVERSATION)
        || name.equals(WINDOW)
        || name.equals(BEAN)
        || name.equals(DIALOG)
        || name.equals(PANEL)
        || name.equals(OPERATION)
        ){
            stack.pop();            
        }
         //System.out.println("</"+name+">");
    }
    

    public static void main(String args[]){
        Parser14 p=new Parser14();
        Application app=p.parse("C:/netbeansWS/FreeMakerTest/application.xml");
        System.out.println(app.toString());
    }
    
    private void dumpStack(){
        System.out.println("-----DUMPSTACK------");
        Iterator<Object> it=stack.iterator();
        while(it.hasNext()){
            System.out.println("----->"+it.next());
        }
        System.out.println("-----ENDDUMP------");
    }
}