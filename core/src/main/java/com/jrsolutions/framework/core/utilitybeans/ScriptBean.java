/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrsolutions.framework.core.utilitybeans;


import com.jrsolutions.framework.core.context.Context;
import com.jrsolutions.framework.core.context.MService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;



/**
 * Ejecuta un script.
 * 
 * <p> El script recibe el contexto como variable 'ctx'
 * <p> Por defecto lo ejecuta con el interprete de 'beanshell'
 *  TODO: �leemos scripts de ficheros?
 */
public class ScriptBean implements MService{
    private String script;
    private String language;
    private final ScriptEngineManager factory;
    private ScriptEngine engine;
    private Context ctx;

    public void setContext(Context ctx) {
        this.ctx=ctx;
    }
    public ScriptBean() {
        factory = new ScriptEngineManager();
       setLanguage("beanshell");
    }
    
    public Object execute() {
    	try{
    		engine.put("ctx", ctx);
    		if(script.startsWith("./")){
    			try {
					BufferedReader reader = new BufferedReader(new FileReader(ClassLoader.getSystemResource(script).getFile()));
					return engine.eval(reader);
    			} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			return null;
    		}
    		else
    			return engine.eval(script);
    	}catch(ScriptException ex){
    		ex.printStackTrace();
    		new RuntimeException("Ejecutando Script",ex);
    		return null;
    	}
    	//engine.eval(new FileReader(fileName));
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        engine = factory.getEngineByName(language);
        if (engine == null) {
            throw new IllegalArgumentException("No puedo encontrar el interprete de [" + language + "]");
        }
        this.language = language;
    }

 
}
