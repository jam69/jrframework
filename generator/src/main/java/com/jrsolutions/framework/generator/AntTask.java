package com.jrsolutions.framework.generator;


import java.util.ArrayList;
import org.apache.tools.ant.Task;

/**
 * Tarea ANT que invoca al generador de c�digo.
 * 
 * La sintaxis de la tarea es:
 * 
 * &lt;ModelerGen 
 *    tmplDir= "tpl"
 *    appDir=   "../vadmsModeler"
 *    outDir=   "../SwingGenerated/VADMSsrggen/"
 *    outExt=   ".java"
 *    convPkg=  "app"
 *    formsPkg= "forms"
 *    config=   "/cofiguracion"  
 *   />
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
public class AntTask extends Task {


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
    String formsPkg;
    public void setFormsPkg(String str) {
    	formsPkg = str;
    }
    String config;
    public void setConfig(String str) {
    	config = str;
    }

    /** Do the work. */
    public void execute() {
   
    	ArrayList<String> str=new ArrayList<String>();
    	
    	if(tmplDir!=null){
    		str.add("templateDir");
    		str.add(tmplDir);
    	}
    	if(appDir!=null){
    		str.add("dirAppLocation");
    		str.add(appDir);
    	}
    	if(outDir!=null){
    		str.add("destination");
    		str.add(outDir);
    	}
    	if(outExt!=null){
    		str.add("fileSuffix");
    		str.add(outExt);
    	}
    	if(convPkg!=null){
    		str.add("convPackage");
    		str.add(convPkg);
    	}
    	if(formsPkg!=null){
    		str.add("formsPackage");
    		str.add(formsPkg);
    	}
    	if(config!=null){
    		str.add("config");
    		str.add(config);
    	}
    	
    	MainGenerate.main(str.toArray(new String[0]));
    		
//        // handle nested elements
//        for (Iterator it=params.iterator(); it.hasNext(); ) {
//            Param p = (Param)it.next();
//            log(p.getName()+":"+p.getValue());
//        }
    }


    /** Store nested 'message's. */
    ArrayList<Param> params = new ArrayList<Param>();

    /** Factory method for creating nested 'message's. */
    public Param createMessage() {
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

}

