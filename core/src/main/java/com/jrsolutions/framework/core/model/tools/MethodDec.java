

package com.jrsolutions.framework.core.model.tools;


import com.jrsolutions.framework.core.context.Context;
import java.util.ArrayList;


/**
 * Describe un metodo del modelo de aplicaci�n.
 * 
 * @see Operacion
 */
public class MethodDec {
    
    
    private final String method;
    private String name;
    private String ret;
    private int npar;
    private Param[] parametros;
    
    /** Recibe la cadena que describe el metodo y lo descompone internamente en sus
     *  partes.
     *  
     * @param metodoCompleto
     */
    public MethodDec(String metodoCompleto) {
        method=metodoCompleto;
        if(metodoCompleto==null || metodoCompleto.length()==0){
            name="execute";
            ret=null;
            npar=0;
            parametros=new Param[0];
            return;
        }        
        int p=metodoCompleto.indexOf("(");
        if(p<0){
//            log.error("El m�todo "+metodoCompleto+" no tiene parentesis");
            throw new IllegalArgumentException("El m�todo "+metodoCompleto+" no tiene parentesis");
        }
        int pfin=metodoCompleto.lastIndexOf(")");
        if(pfin<0){
  //          log.error("El m�todo "+metodoCompleto+" no tiene parentesis");
            throw new IllegalArgumentException("El m�todo "+metodoCompleto+" no tiene parentesis");
        }
        String nombreMetodo=metodoCompleto.substring(0, p);
        String paramsMetodo=metodoCompleto.substring(p+1, pfin);
        String[] w=nombreMetodo.split(" ");
        if(w.length>1){
            ret=w[0];
            name=w[1];
        }else{
            name=nombreMetodo.trim();
        }
        // Ahora los parametros
        if(paramsMetodo.equals("")){
            parametros=new Param[0];
        }else{
         	String [] v2=splitQuotes(paramsMetodo); // TODO comprobar cte con comas
            parametros=new Param[v2.length];
            for(int i=0; i<v2.length;i++) {
                parametros[i]=createParam(v2[i]);
            }
        }
    }
    
    /** Devuelve los tipos de los parametros.
     * Si no tiene parametros devuelve un array de longitud 0.
     * @return
     */
    public Class<?>[] getParamTypes(){
        Class<?>[] ret=new Class[parametros.length];
        for(int i=0;i<ret.length;i++){
            ret[i]=parametros[i].clase;
        }
        return ret;
    }
    
    
    /** Devuelve un array con los valores de los parametros, obtenidos del contexto. */
    public Object[] getParamValues(Context ctx){
        if(parametros==null){
            return new Object [0]; 
        }
        
        Object[] res=new Object[parametros.length];
        for(int i=0;i<parametros.length;i++){
            if(parametros[i] instanceof ParamCtx){
                String var=((ParamCtx)parametros[i]).nombre;
                res[i]=(Object)ctx.get(var);
            }
            if(parametros[i] instanceof ParamCte){
                res[i]=(Object)((ParamCte)parametros[i]).obj;
            }
        }
        return res;
    }
    
    private Param createParam(String str){
        try{
            Object o=StringToObject.getObject(str);
            if(o==null){
                ParamCtx p= new ParamCtx();
                str=str.trim(); // quitamos los blancos del principio y final
                String v3[]=str.split("\\s+");
//    	        // El primero es la clase
                if(v3[0].equals("int")) p.clase=int.class;
                else if(v3[0].equals("string")) p.clase=String.class;
                else if(v3[0].equals("boolean")) p.clase=boolean.class;
                else if(v3[0].equals("float")) p.clase=float.class;
                else if(v3[0].equals("double")) p.clase=double.class;
                else if(v3[0].equals("long")) p.clase=long.class;
                else if(v3[0].equals("byte")) p.clase=byte.class;
                else if(v3[0].equals("char")) p.clase=char.class;
                else{
                    p.clase=Class.forName(v3[0]);
                }
                p.nombre=v3[1];
                return p;
            }else{
                ParamCte p=new ParamCte();
                p.clase=o.getClass();
                if(o==ParamCte.NULL){
                    p.obj=null;
                }else{
                    p.obj=o;
                }
                return p;
            }
        }catch(Exception e){
//            log.error("Error analizado la definicion de m�todo"+e.getLocalizedMessage());
            return null;
        }
    }
    
    /**
     * Divide el string quitando los blancos pero teniendo en cuenta
     * las comillas (simples y dobles).
     * @see String#split(String)
     * @param s
     * @return
     */
    private String[] splitQuotes(String s){
        ArrayList<String> res=new ArrayList<String>();
        int ini=0;
        int estado=0;
        int nivel=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            switch(estado){
                case 0:// saltando blancos
                    if(c=='\''){
                        estado=2;
                        ini=i;
                    }else if(c=='"'){
                        estado=3;
                        ini=i;
                    }else if(c==','){
                        estado=0;
                        res.add(s.substring(ini,i-1));
                        ini=i;
                    }else if(c!=' '&& c!='\t'){
                        estado=1;
                        ini=i;
                    }
                    break;
                case 1: // en elemento
                    if(c=='\''|| c=='"'){ //caso raro
                        estado=0;
                        res.add(s.substring(ini,i));
                        ini=i;
                    }
                    if(c==','){
                        estado=0;
                        res.add(s.substring(ini,i));
                        ini=i;
                    }
                    if(c=='('){
                        estado=4;
                    }
                    if(c==')'){
                        if(nivel>0){
                            nivel--;
                        }else{
                            estado=0;
                            i++;
                            res.add(s.substring(ini,i));
                            ini=i;
                        }
                    }
                    break;
                    
                case 2: // saltando hasta '
                    if(c=='\''){
                        estado=0;
                        i++;
                        res.add(s.substring(ini,i));
                        ini=i;
                    }
                    break;
                case 3: // saltando hasta "
                    if(c=='"'){
                        estado=0;
                        i++;
                        res.add(s.substring(ini,i));
                        ini=i;
                    }
                    break;
                case 4: // saltando hasta )
                    if(c=='('){
                        nivel++;
                    }
                    if(c==')'){
                        if(nivel>0){
                            nivel--;
                        }else{
                            estado=0;
                            i++;
                            res.add(s.substring(ini,i));
                            ini=i;
                        }
                    }
                    break;
            }
        }
        if(estado!=0)
            res.add(s.substring(ini,s.length()).trim());
        return (String[])res.toArray(new String[0]);
    }
    
    
    /** Devuelve la descripcion completa del metodo (lo que se le paso al constructor). */
    public String getMethod() {
        return method;
    }
    
    /** Devuelve el nombre del metodo (sin retorno, ni parametros, ni parentesis...) */
    public String getName() {
        return name;
    }
    
    /** Devuelve el nombre de la variable donde dejar el resultado del metodo. */
    public String getRet() {
        return ret;
    }
    
    /** Devuelve el numero de parametros del metodo. */
    public int paramCount() {
        return npar;
    }
    
    /** Devuelve la descripcion de los parametros. */
    public Param getPar(int n) {
        return parametros[n];
    }
    
    /** Reconstruye el metodo a partir de sus partes (para depuracion) */
    public String toString(){
        StringBuffer sb=new StringBuffer();
        sb.append(ret).append(" ").append(name).append("(");
        for(int i=0;i<npar;i++){
            if(i>0)sb.append(",");
            sb.append(parametros[i].toString());
        }
        sb.append(")");
        return sb.toString();
    }  
}
