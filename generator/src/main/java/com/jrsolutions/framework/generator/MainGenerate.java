/*
 * Main.java
 *
 * Created on 22 de octubre de 2007, 23:06
 *
 * Es la clase que llama al gestor de plantillas, para generar las aplicaciones a
 * partir de la definici�n del Modeler.
 * 
 * 
 */

package com.jrsolutions.framework.generator;


import com.jrsolutions.framework.core.form.ParserForm;
import com.jrsolutions.framework.core.model.Application;
import com.jrsolutions.framework.core.model.Conversation;
import com.jrsolutions.framework.core.model.Window;
import com.jrsolutions.framework.core.model.tools.MethodDec;
import com.jrsolutions.framework.core.utils.Parser14;
import com.jrsolutions.framework.core.utils.constrainedpanel.ConstraintsParser;
import com.jrsolutions.framework.core.utils.tabledefinition.ParserModificadores;
import com.jrsolutions.framework.core.utils.tabledefinition.ParserTabla;
import com.jrsolutions.framework.generator.docgenerator.DocInfo;
import com.jrsolutions.framework.generator.internacionalizer.GestorMultilingualidad;
import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateMethodModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;



/**
 * Es la clase que llama al gestor de plantillas, para generar las aplicaciones a
 * partir de la definici�n del Modeler.
 * 
 * <h2>Argumentos</h2>
 * Para ver los param�tros ejecutar con '-h'
 * 
 * <h2>Dependencias</h2>
 * Utilizar la librer�a FreeMarker como motor de plantillas
 * {@link http://www.freemarker.org}
 * Utiliza tambi�n la librer�a Modeler-Core para leer los ficheros que describen
 * la aplicaci�n as� como otras utilidades.
 * 
 * <h2>Ficheros</h2>
 * Utiliza un fichero 'plugins_packages.properties' para conocer en los items
 * definidos en plugins, la clase que los implementa
<pre>
#
# Fichero con las clases que implementan los items de los plugins externos
#
# NombreItem:  Clase que lo implementa
  

IgeaGeographicModelerComponent: es.indra.isl.igea.external.modeler.IgeaGeographicModelerComponent
</pre>
 *
 * @author jamartinm@indra.es
 */
public class MainGenerate {
    
    private static final String APP_NAME = "APP_NAME";
    
    private String dirAppLocation="../app";
    private String destination="../generated/";
    private String convPackage="app";
    private String formsPackage="forms";
    private String config="/cofiguracion";
    private String fileSuffix=".java";
    
    private String nombreAplicacion;
    private Properties fileConversaciones = new Properties();
    private Properties pluginsPackages= new Properties();

    private String templateDir="tpl";

    /** Configuraci�n del FreeMarker */    
    private Configuration cfg;
    
    /** Creates a new instance of Main */
    public MainGenerate() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	System.out.println("Generando....");
        Properties a=new Properties();
        for(int i=0;i<args.length;i++){
            if(args[i].startsWith("-h")){
                System.out.println("Ayuda ejecutador de plantillas sobre el modeler");
                System.out.println(" java -jar FreeMarkerTest.jar [opt=valor] .....");
                System.out.println("       options:");
                System.out.println("           dirAppLocation   Localizacion del directorio con la aplicacion ");
                System.out.println("           destination      Destino de lo generado ");
                System.out.println("           templateDir      Localizacion de las plantillas ");
                System.out.println("           convPackage      Nombre del package de las conversaciones/ventanas[app] ");
                System.out.println("           formsPackage     Nombre del package de los formualarios[form]");
                System.out.println("           config		Nombre del directorio a copia la configuracion y los iconos [/cofiguracion]");
                System.out.println("           fileSuffix       Extensi�n de los ficheros generados [.java]");
                System.out.println();
                System.out.println();
                System.exit(-1);
            }         
            int p=args[i].indexOf("=");
            if(p>0){
                String k=args[i].substring(0,p);
                String v=args[i].substring(p+1);
                a.setProperty(k,v);
            }
        }
        MainGenerate m=new MainGenerate();
        if(a.getProperty("dirAppLocation")!=null)m.dirAppLocation=a.getProperty("dirAppLocation");
        if(a.getProperty("destination")!=null)m.destination=a.getProperty("destination");
        if(a.getProperty("templateDir")!=null)m.templateDir=a.getProperty("templateDir");
        if(a.getProperty("formsPackage")!=null)m.formsPackage=a.getProperty("formsPackage");
        if(a.getProperty("convPackage")!=null)m.convPackage=a.getProperty("convPackage");
        if(a.getProperty("config")!=null)m.convPackage=a.getProperty("config");
        if(a.getProperty("fileSuffix")!=null)m.fileSuffix=a.getProperty("fileSuffix");
        System.out.println("Aplicacion: "+m.dirAppLocation);
        
        m.borrarGeneradoAntes();
        m.leePluginsPackages();
        m.generaTodo();
        m.todosFormularios();
        m.copiaFicherosConfIcons();
        m.guardarFicheroConversaciones();        
    }
    
    private Application leeApp(){
        try{
            Parser14 p=new Parser14();            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setXIncludeAware(true);
            factory.setNamespaceAware(true);
            factory.newSAXParser().parse(dirAppLocation+"/application.xml",p);
            return p.getApp();
      
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Procesa las plantillas sobre la aplicaci�n, las conversaciones y las ventanas
     */
    public void generaTodo(){
    	Application app=leeApp();
    	nombreAplicacion = app.getName();
    	File out=new File(destination+"/"+convPackage);
    	initFM();
    	out.mkdirs();
    	for(Conversation c: app.getConversations()){
    		File fconv=new File(out,c.getId());
    		fconv.mkdirs();
    		for(Window w:c.getWindows()){
    			File fw=new File(fconv,w.getId().replaceAll(" ", "")+fileSuffix);
    			render(app,c,w,fw);                   
    		}
    		File fw=new File(fconv,c.getId()+fileSuffix);
    		render(app,c,fw);
    	}
    	File fw=new File(out,app.getId()+fileSuffix);
    	render(app,fw);
    }
    
    
    /**
     * Procesa las plantillas sobre todos los formularios.
     */
    public void todosFormularios(){
    	try {
			Template tmpl=cfg.getTemplate("Formulario.ftl");
		} catch (IOException e) {
			System.out.println("No hay plantilla para formularios: Formulario.ftl");
			return;
		}
        File p=new File(dirAppLocation);
        File[] files=p.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if(
                   name.endsWith(".frm") ||(
                   !name.equals("application.xml")&&
                   !name.equals("repos.xml")&&
                   !name.equals("menu_definition.xml") &&
                   !name.equals(".svn")&& 
                   !name.equals("uf_runner.properties")) && 
                   !dir.isDirectory()
                   )
                {
                    return true;
                }else{
                    return false;
                }
            }
        });
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        File d=new File(destination+"/"+formsPackage);
        d.mkdirs();
        for(int i=0;i<files.length;i++){
            procesaForm(files[i],d);
        }
    }
    
    /**
     * Procesa un formulario.
     * 
     * <br>Ejecuta la plantilla: Formulario.ftl
     * <br>Se procesa en modo DOM {@link http://freemarker.org/docs/xgui.html}
     *  
     * <p>Variables:
     * <table>
     * <tr><th>Nombre</th><th>Tipo</th><th>Descripcion</th></tr>
     * <tr><td>name</td><td>String</td><td>Nombre del Formulario</td></tr>
     * <tr><td>doc</td><td>freemarker.ext.dom.NodeModel</td><td>El documento DOM del formulario (</td></tr>
     * <tr><td>stripHTML</td><td>HTMLStripper</td><td>Utilidad para limpiar el c�digo HTML</td></tr>
     * <tr><td>genID</td><td>GenID</td><td>Utilidad para generar c�digos �nicos</td></tr>
     * <tr><td>escape</td><td>TextEscape</td><td>Utilidad para escapar las comillas</td></tr>
     * <tr><td>toJava</td><td>ToJavaName</td><td>Utilidad para convertir Strings a nombres java v�lidos</td></tr>
     * <tr><td>PluginsPackages</td><td>Properties</td><td>Mapea los nombres de los items de plugins con su package</td></tr>
     * </table>
     * 
     * @param f  La descripci�n del formulario
     * @param directorio El directorio donde dejara el resultado
     */
    private void procesaForm(File f,File directorio){
        
        try {
        	NodeModel nodeModel = NodeModel.parse(f);
        	if(!nodeModel.getNode().getFirstChild().getNodeName().equalsIgnoreCase("FormModel")) return;
        	ToJavaName toJava=new ToJavaName();
        	String name=toJava.toJava(f.getName());
        	Template tmpl=cfg.getTemplate("Formulario.ftl");
        	name= "Form"+name;
            File fw=new File(directorio,name+fileSuffix);
            FileOutputStream os=new FileOutputStream(fw);
            PrintWriter output=new PrintWriter(os);
            Map<String,Object> root=new HashMap<String,Object>();
            root.put("name",name);
            root.put("doc",nodeModel);
            root.put("stripHTML",new HTMLStripper());
            root.put("genID",new GenID());
            root.put("escape",new TextEscape());
            root.put("toJava",new ToJavaName());
            root.put("PluginsPackages",pluginsPackages);
            root.put("docInfo",new DocInfo());
            System.out.println("Formulario: "+f);
           
            tmpl.process(root, output);
            output.close();
        }catch (FileNotFoundException ex){
        	System.out.println("No tengo plantilla Formulario.ftl");
        }  catch (TemplateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch(SAXException ex){
            ex.printStackTrace();
        } catch (ParserConfigurationException ex){
            ex.printStackTrace();
        }
        
    }
    
    /** Inicializa el gestor de plantillas (FreeMarker)
     * Carga las plantillas del directorio indicado por la propiedad 'templateDir'
     */
    private void initFM(){
        cfg=new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(new File(templateDir));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
    }
    
    /**
     * Aplica la plantilla sobre el objeto 'Application'.
     * 
     * <br>Ejecuta la plantilla: Application.ftl
     * <br>Se procesa en modo Bean {@link http://freemarker.org/docs/pgui_misc_beanwrapper.html}
     *  
     * <p>Variables:
     * <table>
     * <tr><th>Nombre></th><th>Tipo</th><th>Descripcion</th></tr>
     * <tr><td>app</td><td>Application</td><td>La aplicaci�n</td></tr>
     * <tr><td>panelDefinitionParser</td><td>DefinitionsParser</td><td>Utilidad para analizar los parametros de Horizontal y Vertical panels</td></tr>
     * <tr><td>parserModificadores</td><td>ParserModificadores</td><td>Utilidad para parsear los "modificadores" de las tablas avanzadas</td></tr>
     * <tr><td>parserTabla</td><td>ParserTabla</td><td>Utilidad para parsear filtros, etc,.. de las tablas</td></tr>
     * <tr><td>stripHTML</td><td>HTMLStripper</td><td>Utilidad para limpiar el c�digo HTML</td></tr>
     * <tr><td>genID</td><td>GenID</td><td>Utilidad para generar c�digos �nicos</td></tr>
     * <tr><td>escape</td><td>TextEscape</td><td>Utilidad para escapar las comillas</td></tr>
     * <tr><td>toJava</td><td>ToJavaName</td><td>Utilidad para convertir Strings a nombres java v�lidos</td></tr>
     * <tr><td>traduce</td><td>GestorMultilingualidad</td><td>Utilidad para traducir la aplicacion</td></tr>
     * </table>
     * 
     * @param app Aplicaci�n
     * @param fa Fichero destino
     */
    public void render(Application app,File fa){
        try {
        	Template tmpl=cfg.getTemplate("Application.ftl");
            FileOutputStream os=new FileOutputStream(fa);
            PrintWriter out=new PrintWriter(os);
            Map<String,Object> root=new HashMap<String,Object>();
            root.put("app",app);
            root.put("panelDefinitionsParser",new DefinitionsParser());
            root.put("parserModificadores",new ParserModificadores());
            root.put("parserTabla",  new ParserTabla());
            root.put("stripHTML",new HTMLStripper());
            root.put("genID",new GenID());
            root.put("traduce", new GestorMultilingualidad());
            root.put("escape",new TextEscape());
            root.put("toJava",new ToJavaName());
            ParserForm pf=new ParserForm();
            pf.setAppPath(dirAppLocation);
            root.put("parserForm",pf);
            root.put("CUtil",new Add());            
            tmpl.process(root, out);
            os.close();
        } catch (FileNotFoundException ex){
        	System.out.println("No tengo plantilla Application.ftl");
        } catch (TemplateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Aplica la plantilla sobre el objeto 'Conversation'.
     * 
     * <br>Ejecuta la plantilla: Conversation.ftl
     * <br>Se procesa en modo Bean {@link http://freemarker.org/docs/pgui_misc_beanwrapper.html}
     *  
     * <p>Variables:
     * <table>
     * <tr><th>Nombre</th><th>Tipo</th><th>Descripcion</th></tr>
     * <tr><th>app</th><th>Application</th><th>Aplicacion a la que pertenece</th></tr>
     * <tr><td>conversation</td><td>Conversation</td><td>La conversacion a procesar</td></tr>
     * <tr><td>panelDefinitionParser</td><td>DefinitionsParser</td><td>Utilidad para analizar los parametros de Horizontal y Vertical panels</td></tr>
     * <tr><td>parserModificadores</td><td>ParserModificadores</td><td>Utilidad para parsear los "modificadores" de las tablas avanzadas</td></tr>
     * <tr><td>parserTabla</td><td>ParserTabla</td><td>Utilidad para parsear filtros, etc,.. de las tablas</td></tr>
     * <tr><td>stripHTML</td><td>HTMLStripper</td><td>Utilidad para limpiar el c�digo HTML</td></tr>
     * <tr><td>genID</td><td>GenID</td><td>Utilidad para generar c�digos �nicos</td></tr>
     * <tr><td>escape</td><td>TextEscape</td><td>Utilidad para escapar las comillas</td></tr>
     * <tr><td>toJava</td><td>ToJavaName</td><td>Utilidad para convertir Strings a nombres java v�lidos</td></tr>
     * <tr><td>parserForm</td><td>ParserForm</td><td>Utilidad para procesar formularios</td></tr>
     * <tr><td>CUtil</td><td>Add</td><td>Utilidad para acumular datos en una variable</td></tr>
     * </table>
     * 
     * @param conv Conversacion
     */
    public void render(Application app,Conversation conv,File fw){
        try {
        	String clase = convPackage + "." + conv.getId()+ "." +  conv.getId();
        	fileConversaciones.setProperty(conv.getId(), clase);
        	Template tmpl=cfg.getTemplate("Conversation.ftl");
        	FileOutputStream os=new FileOutputStream(fw);
            PrintWriter out=new PrintWriter(os);
            Map<String,Object> root=new HashMap<String,Object>();
            root.put("app",app);
            root.put("conversation",conv);
            root.put("panelDefinitionsParser",new DefinitionsParser());
            root.put("parserModificadores",new ParserModificadores());
            root.put("parserTabla",  new ParserTabla());
            root.put("stripHTML",new HTMLStripper());
            root.put("genID",new GenID());
            root.put("escape",new TextEscape());
            root.put("toJava",new ToJavaName());
            ParserForm pf=new ParserForm();
            pf.setAppPath(dirAppLocation);
            root.put("parserForm",pf);
            root.put("CUtil",new Add());       
            System.out.println("Conversation: -->"+conv.getId()); 
            tmpl.process(root, out);
            os.close(); 
        } catch (FileNotFoundException ex){
        	System.out.println("No tengo plantilla Conversation.ftl");
        } catch (TemplateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Aplica la plantilla sobre el objeto 'Window'.
     * 
     * <br>Ejecuta la plantilla: Window.ftl
     * <br>Se procesa en modo Bean {@link http://freemarker.org/docs/pgui_misc_beanwrapper.html}
     *  
     * <p>Variables:
     * <table>
     * <tr><th>Nombre</th><th>Tipo</th><th>Descripcion</th></tr>
     * <tr><td>window</td><td>Window</td><td>La ventana a procesar</td></tr>
     * <tr><td>conversation</td><td>Conversation</td><td>La conversacion a la que pertenece</td></tr>
     * <tr><td>app</td><td>Application</td><td>La aplicacion a la que pertenece</td></tr>
     * <tr><td>conv</td><td>ArrayList&lt;Conversation&gt;</td><td>La lista de conversaciones de la aplicaci�n</td></tr>
     * <tr><td>panelDefinitionParser</td><td>DefinitionsParser</td><td>Utilidad para analizar los parametros de Horizontal y Vertical panels</td></tr>
     * <tr><td>parserModificadores</td><td>ParserModificadores</td><td>Utilidad para parsear los "modificadores" de las tablas avanzadas</td></tr>
     * <tr><td>parserTabla</td><td>ParserTabla</td><td>Utilidad para parsear filtros, etc,.. de las tablas</td></tr>
     * <tr><td>stripHTML</td><td>HTMLStripper</td><td>Utilidad para limpiar el c�digo HTML</td></tr>
     * <tr><td>genID</td><td>GenID</td><td>Utilidad para generar c�digos �nicos</td></tr>
     * <tr><td>escape</td><td>TextEscape</td><td>Utilidad para escapar las comillas</td></tr>
     * <tr><td>toJava</td><td>ToJavaName</td><td>Utilidad para convertir Strings a nombres java v�lidos</td></tr>
     * <tr><td>parserForm</td><td>ParserForm</td><td>Utilidad para procesar formularios</td></tr>
     * <tr><td>CUtil</td><td>Add</td><td>Utilidad para acumular datos en una variable</td></tr>
     * </table>
     * @param app
     * @param fa
     */
    public void render(Application app,Conversation conv,Window w,File fw){
        try {       	
        	Template tmpl=cfg.getTemplate("Window.ftl");
        	FileOutputStream os=new FileOutputStream(fw);
            PrintWriter out=new PrintWriter(os);
            Map<String,Object> root=new HashMap<String,Object>();
            root.put("window",w);
            root.put("conversation",conv);
            root.put("app", app);
            root.put("conv",app.getConversations());
            root.put("panelDefinitionsParser",new DefinitionsParser());
            root.put("parserModificadores",new ParserModificadores());
            root.put("parserTabla",  new ParserTabla());
            root.put("methodUtil",new MethodUtil());
            root.put("stripHTML",new HTMLStripper());
            root.put("genID",new GenID());
            root.put("escape",new TextEscape());
            root.put("toJava",new ToJavaName());
            // solo documentacion
            root.put("docInfo",new DocInfo());
            ParserForm pf=new ParserForm();
            pf.setAppPath(dirAppLocation);
            root.put("parserForm",pf);
            root.put("CUtil",new Add());       
            System.out.println("           Ventana: -->"+w.getId());            
            tmpl.process(root, out);
            os.close();
        } catch (FileNotFoundException ex){
        	System.out.println("No tengo plantilla Window.ftl");
        } catch (TemplateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Borra los ficheros y directorios dentro del directorio de resultados
     */
    public void borrarGeneradoAntes(){
    	System.out.println("Borrando ficheros antiguos: "+destination);
    	File pathProyecto = new File(destination);
    	File[] ficheros = pathProyecto.listFiles();
    	if(ficheros==null)return;
    	for (int i = 0; i < ficheros.length; i++) {
			File file = ficheros[i];
			if(file.isDirectory()){
				borraDirectorio(file);	
				file.delete();
			}			
		}
    }
    /** Borra los ficheros y sus subdirectorios de un directorio.  */
    private void borraDirectorio(File path){
    	if( path.exists() ) {
    		File[] files = path.listFiles();
    		if(files!=null){
		    	for(int i=0; i<files.length; i++) {
		    		if(files[i].isDirectory()) {
		    			borraDirectorio(files[i]);
		    		}
		    		files[i].delete();		    	 
		    	}
    		}
    	}
    }
    
    /** Copia ficheros de configuraci�n: Propiedes,Ayuda,Menu e Iconos*/
    private void copiaFicherosConfIcons(){
    	File pathIcons = new File(dirAppLocation+"/icons");
    	if(pathIcons.exists()){
	    	File pathIconDest = new File(destination+"/icons");
			copyDirectory(pathIcons, pathIconDest);
    		}
    	////crea paquete de propiedades
    	File dirConfiguracion = new File(destination+config);
		if(!dirConfiguracion.exists()){
			dirConfiguracion.mkdir();
		}
		//lee archivo de configuracion si existe
		copiaArchivoPropidades();
		copiarAyuda();
		copiaArchivoMenu();
    }
    
    private void copiaArchivoMenu(){
    	File fileMenu = new File(dirAppLocation+"/menu_definition.xml");
    	if(fileMenu.exists()){
    		File fileMenuCopia = new File(destination+config + "/menu_definition.xml");
    		copiarFichero(fileMenu, fileMenuCopia);
    	}
    }
    
    /** Copia fichero de propiedades */
    private void copiaArchivoPropidades(){
    	//lee archivo de configuracion si existe
		Properties prop =new Properties();
		try {
	    	try {
	    		FileInputStream file = new FileInputStream(dirAppLocation+"/uf_runner.properties");
	    		prop.load(file);	
	    	}catch (FileNotFoundException e) {	
	    	}
	    	prop.put(APP_NAME, nombreAplicacion);
	    	FileOutputStream copyProperties = new FileOutputStream(destination +config + "/uf_runner.properties");
			prop.store(copyProperties, "Archivo de propiedades");
			copyProperties.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /** Copia fichero de ayuda */
    private void copiarAyuda(){
    	Properties prop = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream(dirAppLocation+"/uf_runner.properties");
			prop.load(file);
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		File fileAyuda = new File(destination+"/"+formsPackage+ "/" + prop.getProperty("APP_HELP_PATH"));
		String dir = prop.getProperty("APP_HELP_PATH");
		if (dir==null)return;
		int pos = dir.lastIndexOf("/");
		if(pos > -1)
			dir = dir.substring(0, pos);
		
		File filedestino = new File(destination + dir);
		if(fileAyuda.exists())
			copyDirectory(fileAyuda.getParentFile(), filedestino);	
    }
    
    /** Copia todos los ficheros y subdirectorios de un directorio a otro
     * 
     * @param sourceLocation Directorio origen. Si es un fichero, copia el fichero
     * @param targetLocation Directorio destino.Si no existe lo crea
     */
    static private void copyDirectory(File sourceLocation , File targetLocation) {
        
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdirs();
            }
            
            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {
        	copiarFichero(sourceLocation,targetLocation);
        }
    }
    
    /** Copia un fichero a un directorio.
     * 
     * @param sourceLocation Fichero a copiar
     * @param targetLocation Directorio destino (debe existir)
     */
    static private void copiarFichero(File sourceLocation , File targetLocation){
    	 InputStream in;
    	 OutputStream out;
		try {
			in = new FileInputStream(sourceLocation);
	        out = new FileOutputStream(targetLocation);
	            
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Guarda fichero conversaciones (conversacion.properties)
     * Las conversaciones se han obtenido al ejecutar el
     * m�todo {@link #render(Conversation, File)}
     */
    private void guardarFicheroConversaciones(){
    	File dir=new File(destination+config);
    	File fichero = new File(dir, "conversacion.properties");
    	try {
    		dir.mkdir();
			FileOutputStream stream = new FileOutputStream(fichero);
			fileConversaciones.store(stream, "Propiedades");
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private void leePluginsPackages(){
    	try {
			File plugins=new File(dirAppLocation,"plugins_packages.properties");
			pluginsPackages.load(new FileReader(plugins));
		} catch (FileNotFoundException e) {
			System.out.println("No encuentro el fichero de Plugins-packages (plugins_packages.properties)");
		} catch (IOException e) {			
			e.printStackTrace();
		}
    	
    }
} 
	

/* ==================================================================================
 * Clases especiales que se pasan a las plantillas como métodos de utilidad
 * ==================================================================================*/


/**
 * Genera ID,  simplemente, cada vez devuelve algo distinto.
 */
    class GenID implements TemplateMethodModel  {
        static int c=0;
        @SuppressWarnings("unchecked")
		public Object exec(List lista){
            return "IFM_"+(c++);
        }
    }
   
    /**
     * Quita todo el HTML que rodea a &lt;body&gt; &lt;/body&gt;
     */
    class HTMLStripper implements TemplateMethodModel  {
       
         // no fucniona con HTML o BODY (mayusculas), pero paso
        @SuppressWarnings("unchecked")
		public Object exec(List lista){
            String txt=(String)lista.get(0);
            if(txt==null||txt.length()==0)return "";
            if (txt.indexOf("<html>") > -1) {
                        int f=txt.indexOf("</html>");
                        if(f<0)f=txt.length();
			txt = txt.substring(6 + txt.indexOf("<html>"),f );
		}
		if (txt.indexOf("<body>") > -1) {
			txt = txt.substring(6 + txt.indexOf("<body>"), txt
					.indexOf("</body>"));
		}

		txt = txt.replaceAll("^\\s+", "");
		txt = txt.replaceAll("\\s+$", "");

		return txt;
        }
        
    }
    
    /** Escapa las comillas dobles (") */
    class TextEscape implements TemplateMethodModel  {
        
       @SuppressWarnings("unchecked")
	public Object exec(List lista){
           String txt=(String)lista.get(0);
           if(txt==null||txt.length()==0)return "";
           txt = txt.replaceAll("\"", "\\\\\""); // no me preguntes pero las quotea bien
		return txt;
       }
       
   }
 
    /** Convierte un String en un identificador Java v�lido.
     * Pone la primera letra en may�sculas, as� como las que siguen
     * a los separadores (blancos), deja el resto en may�sculas.
     * Tambi�n mantiene los '.' como parte del nombre
     */
    class ToJavaName implements TemplateMethodModel  {
        
        @SuppressWarnings("unchecked")
		public Object exec(List lista){
            String txt=(String)lista.get(0);
            if(txt==null||txt.length()==0)return "";
            return toJava(txt);
        }
        public String toJava(String txt){
        	boolean fg=false;
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<txt.length();i++){
            	char c=txt.charAt(i);
            	if(i==0 || fg){
            		c=Character.toUpperCase(c);
            		fg=false;
            	}
            	if(Character.isWhitespace(c) || c=='\''){
            	  // nos lo saltamos
            	  fg=true;	
            	}else if(c=='.'){
            		break;
            	}else{
            		sb.append(c);
            	}
            }
            return sb.toString();
        }
        
    }
    
    /** Aanaliza las definiciones de "vertical" y "horizontal" panels */
    class Definitions implements TemplateMethodModel {
       
        private ArrayList<?> definitions;
        
        @SuppressWarnings("unchecked")
		public Object exec(List lista){
            String definition=(String)lista.get(0);
            ConstraintsParser parser=new ConstraintsParser();
            definitions = parser.parse(definition);
            return definitions.toString();
        }
        
        public ArrayList<?> getParse(){
            return definitions;
        }
    
    }

    class MethodUtil implements TemplateMethodModel {
        @SuppressWarnings("unchecked")
		public Object exec(List lista){
            String method=(String)lista.get(0);
            return new MethodDec(method);
        }
    }
    
 
  
