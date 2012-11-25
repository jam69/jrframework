package com.jrsolutions.framework.generator;

import com.jrsolutions.framework.core.model.Application;
import com.jrsolutions.framework.core.model.Conversation;
import com.jrsolutions.framework.core.model.Window;
import com.jrsolutions.framework.core.utils.Parser14;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Tarea ANT que invoca al generador de c�digo.
 * 
 * La sintaxis de la tarea es:
 * 
 * &lt;ModelerGen 
 *    tmplDir= "tpl"
 *    appDir=   "../vadmsModeler"
 *    outDir=   "../SwingGenerated/VADMSsrggen/"
 *    outExt=   ".java" >
 *     <param name= value= />
 *     <classPath />  
 * </ModelerGen>
 *    
 *    
 * En tmpl-dir debe haber los siguientes ficheros.
 *     Application.ftl
 *     Conversation.ftl
 *     Window.ftl
 *     Form.ftl
 *     item/   (directorio)
 *        label.ftl           (etc,....)
 *        combobox.ftl 
 *     panel/   (directorio
 *        FlowPanel.ftl
 *           
 * 
 * @author jamartinm
 *
 */
public class AntTask2 extends Task {

	private static Logger log=Logger.getLogger("GenerationTask");
	
    String tmplDir;
    public void setTmplDir(String str) {
        tmplDir = str;
    }
    String appDir;
    public void setAppDir(String str) {
        appDir = str;
    }
    String outDir;
    public void setOutDir(String str) {
        outDir = str;
    }
    String outExt;
    public void setOutExt(String str) {
        outExt = str;
    }
    String convPkg;
    public void setConvPkg(String str) {
    	convPkg = str;
    }
    
    
   
    /** Do the work. */
    
    public void execute() {

    	System.out.println("HHHHHHHHola");
    	Map<String,String> props=new HashMap<String,String>();
        for (Param p:params) {
            props.put(p.name,p.value);
        }
        // Empezamos a procesar
//       app=leeApp();
       
        generaTodo();
      
        
    }


    /** Store nested 'message's. */
    ArrayList<Param> params = new ArrayList<Param>();

    /** Factory method for creating nested 'message's. */
    public Param createParam() {
        Param param = new Param();
        params.add(param);
        return param;
    }

    /** A nested 'message'. */
    public class Param {
        // Bean constructor
        public Param() {}

        /** Message to print. */
        String name;
        String value;
        public void setName(String name) { this.name = name; }
        public String getName() { return name; }
        public void setValue(String value) { this.value = value; }
        public String getValue() { return value; }
    }

//--------------------------------------------------------------------------------------------
    Properties pluginsPackages;
    Configuration cfg;
    
    private Application leeApp(){
        try{
            Parser14 p=new Parser14();            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setXIncludeAware(true);
            factory.setNamespaceAware(true);
            factory.newSAXParser().parse(appDir+"/application.xml",p);
            return p.getApp();
      
        }catch(Exception e){
        	 throw new BuildException("Al leer la aplicaci�n:"+e.getLocalizedMessage(),e);
        }
    }
    
    private Properties  leePluginsPackages(){
    	try {
    		Properties pluginsPackages=new Properties();
			File plugins=new File(appDir,"plugins_packages.properties");
			pluginsPackages.load(new FileReader(plugins));
			return pluginsPackages;
		} catch (FileNotFoundException e) {
			System.out.println("No encuentro el fichero de Plugins-packages (plugins_packages.properties)");
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return null;    	
    }
    
    private void generaTodo(){
    	// cargamos la aplicacion
    	
    	Application app=leeApp();
    	pluginsPackages=leePluginsPackages();
//    	nombreAplicacion = app.getName();
    	// inicidalizamos freeMaker
    	cfg=new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(new File(tmplDir));
        } catch (IOException ex) {
            ex.printStackTrace();
        
        }
        // empezamos a generar
    	File out=new File(outDir+"/"+convPkg);    	    	
    	out.mkdirs();
    	for(Conversation c: app.getConversations()){
//    		File fconv=new File(out,c.getId());
//    		fconv.mkdirs();
    		for(Window w:c.getWindows()){
//    			File fw=new File(fconv,w.getId().replaceAll(" ", "")+outExt);
    			render("Window",app,c,w);
    		}
//    		File fw=new File(fconv,c.getId()+outExt);
    		render("Conversation",app,c,null);
    	}
    	//File fw=new File(out,app.getId()+outExt);
    	render("Application",app,null,null);
    }
    
    
    public void render(String template,Application app,Conversation conv,Window w){
    	try {
			Template tmpl=cfg.getTemplate("Application"+".ftl");
			Reader reader=new FileReader(tmplDir+"/Application"+".js");
			Map<String,Object> root=new HashMap<String,Object>();
			
			ClassLoader cl=getClass().getClassLoader();
			try {
				Class c=cl.loadClass("es.indra.humandev.generator.Hola");
				System.out.println("CLASE:"+c);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ScriptEngineManager factory = new ScriptEngineManager(cl);
		    ScriptEngine engine = factory.getEngineByName("rhino");
		    
		    
			root.put("app",app);
			root.put("conv",conv);
			root.put("window",w);

			
			engine.put("out","pathOut");
			engine.put("root",root);			
			engine.put("app",app);
			engine.put("conv",conv);
			engine.put("window",w);
			engine.put("params",params);
			engine.put("pluginsPackages",pluginsPackages);
			
			 DefinitionsParser p=new DefinitionsParser();
			 engine.put("mierda",p);
			
			engine.eval(reader);			
			PrintWriter out=new PrintWriter(outDir+"/"+engine.get("out"));
			// root �ha cambiado?
			tmpl.process(root, out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			log.warning("Error evaluando script: "+e.getLocalizedMessage()+
					  "\n       FILE:"+e.getFileName()+
					  "\n       line:"+e.getLineNumber()+
					  "\n     column:"+e.getColumnNumber()+
					  "\n      cause:"+e.getCause());
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    	   
}

