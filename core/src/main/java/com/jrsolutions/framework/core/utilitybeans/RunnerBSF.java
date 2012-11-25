/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrsolutions.framework.core.utilitybeans;

import com.jrsolutions.framework.core.context.MInfoMeta;
import com.jrsolutions.framework.core.metamodel.MetaAttribute;
import com.jrsolutions.framework.core.metamodel.MetaEntity;
import com.jrsolutions.framework.core.metamodel.MetaInfoFactory;
import java.util.Iterator;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


/**
 * Ejecuta scripts BSF. 
 * 
 * <p>Simplemente en el ClassPath del runner hay que a�adir el .jar que implementa
 * el lenguaje y el .jar con el engineBSF correspondiente. Y ya se puede utilizar
 * en los otros beaans de utilidad. Por defecto se distribuye con las engines
 * de "jexl" y "beanShell"
 * 
 * 
 *  @see ScriptBean
 *  @see UBean
 *  
 *  
 *  
 * <p>Los BSFEngines para los distintos interpretes se pueden encontrar
 * en
 * <ul> 
 * <li>bsfengines.jar viene con la distribuci�n de XALAN
 * <li>jsr223-engines.zip Del proyecto https://scripting.dev.java.net/ (en la zona de "Documents & Files")
 * </ul>
 * Un art�culo interesante...
 * http://java.sys-con.com/node/36422
 * 
 * 
 */
public class RunnerBSF implements MInfoMeta {

    private String language;
    private final ScriptEngineManager factory;
    private ScriptEngine engine;
    
    private MetaInfoFactory infoFactory;
      
    public RunnerBSF() {
        factory = new ScriptEngineManager();
        setLanguage("beanshell");
    }
    public RunnerBSF(String lang) {
        factory = new ScriptEngineManager();
        setLanguage(lang);
    }
    
    public void setInfoFactory(MetaInfoFactory infoFactory){
    	this.infoFactory=infoFactory;
    }
    public Object execute(Object obj,String script) {       
        MetaEntity info=infoFactory.getTypeInfo(obj.getClass().getName());
        List<MetaAttribute> attrs=info.getAttributes();
        Iterator<MetaAttribute> it=attrs.iterator();
        while(it.hasNext()){
            MetaAttribute attr=(MetaAttribute)it.next();
            engine.put(attr.getName(),attr.getValue(obj));
        }
        engine.put("rec",obj);
        try{    
            return engine.eval(script);
        }catch(ScriptException ex){
            new RuntimeException("Ejecutando Script",ex);
            return null;
        }
    }
    
    public Object executeArgs(Object[]args,String script) {
        engine.put("arg",args);
        try{    
            return engine.eval(script);
        }catch(ScriptException ex){
            new RuntimeException("Ejecutando Script",ex);
            return null;
        }
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
