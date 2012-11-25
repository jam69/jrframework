/*

 */

package es.indra.humandev.runner.core.utils;


import java.awt.Color;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Convierte un String al tipo indicado.
 * Si no sabemos el tipo se debería utilizar StringToObject
 * 
 * @see StringToObject
 * 
 */
public class StringToObject2 {
    
    interface Converter {
        public Object convert(String s)throws Exception;
        public String toString(Object obj);
    }
    
    private final static Map<Class<?>,Converter> map =new HashMap<Class<?>,Converter>();
    
    static {
        // Conversion de int
        map.put(int.class,new Converter(){
            public Object convert(String s)throws Exception{
                return new Integer(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        // Conversion de double
        map.put(double.class,new Converter(){
            public Object convert(String s)throws Exception{
                return new Double(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        //Conversion de float
        map.put(float.class,new Converter(){
            public Object convert(String s)throws Exception{
                return new Float(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        //Conversion de boolean
        map.put(boolean.class,new Converter(){
            public Object convert(String s)throws Exception{
                return Boolean.valueOf(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        //Conversion de Long
        map.put(long.class,new Converter(){
            public Object convert(String s)throws Exception{
                return new Long(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        // Conversion de Integer
        map.put(Integer.class,new Converter(){
            public Object convert(String s)throws Exception{
                return new Integer(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        // Conversion de Double
        map.put(Double.class,new Converter(){
            public Object convert(String s)throws Exception{
                return new Double(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        //Conversion de Float
        map.put(Float.class,new Converter(){
            public Object convert(String s)throws Exception{
                return new Float(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        //Conversion de Boolean
        map.put(Boolean.class,new Converter(){
            public Object convert(String s)throws Exception{
                return Boolean.valueOf(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        //Conversion de Long
        map.put(Long.class,new Converter(){
            public Object convert(String s)throws Exception{
                return new Long(s);
            }
            public String toString(Object obj){
                return obj.toString();
            }
        });
        
        //Conversion de String (trivial)
        map.put(String.class,new Converter(){
            public Object convert(String s)throws Exception{
                return s;
            }
            public String toString(Object obj){
                return (String)obj;
            }
        });
        
        // Conversion de fechas (formato d/M/y)
        final SimpleDateFormat fmt=new SimpleDateFormat("d/M/y");
        map.put(Date.class,new Converter(){
            public Object convert(String s)throws Exception{
                return fmt.parse(s);
            }
            public String toString(Object obj){
                return fmt.format(obj);
            }
        });
        
        // Conversion de Color
        map.put(Color.class,new Converter(){
            public Object convert(String s)throws Exception{
                int p=s.indexOf(',');
                if(p>0){
                    String[] ss=s.split(",");
                    if(ss.length!=3){
                        throw new IllegalArgumentException("El color tiene 3 componentes ("+s+")");
                    }
                    int r=Integer.parseInt(ss[0]);
                    int g=Integer.parseInt(ss[1]);
                    int b=Integer.parseInt(ss[2]);
                    return new Color(r,g,b);
                }
                if(s.length()==6){
                    int r=Integer.parseInt(s.substring(0,2),16);
                    int g=Integer.parseInt(s.substring(2,4),16);
                    int b=Integer.parseInt(s.substring(4,6),16);
                    return new Color(r,g,b);
                }
                if(s.length()==7){ // HTML #rrggbb
                    int r=Integer.parseInt(s.substring(1,3),16);
                    int g=Integer.parseInt(s.substring(3,5),16);
                    int b=Integer.parseInt(s.substring(5,7),16);
                    return new Color(r,g,b);
                }
                throw new IllegalArgumentException("El color no tiene formato válido ("+s+")");
            }
            public String toString(Object obj){
                Color col=(Color) obj;
                return col.getRed()+","+col.getGreen()+","+col.getBlue();
            }
        });
        
    }
    
    private static Converter buscaConverter(Class c){
        return (Converter) map.get(c);
    }
    
    /**
     * Convierte un string a objeto
     *
     * OjO. Que devuelve Objetos, (no int sino Integer, etc,..)
     * </ul>
     *
     *
     * @param str String a convertir
     * @param c Clase destino
     * @return El objeto instancia de c
     */
    public static Object getObject(String str,Class c) throws Exception{
        str=str.trim();
        if(str.length()==0)return null;
        Converter f=buscaConverter(c);
        if(f==null){
            throw new Exception("Conversión de String a ["+c+"] no conocida");
        }
        Object r=f.convert(str);
        return r;
    }
    
    public static String toString(Object obj){
        // Itera props
        StringBuffer sb=new StringBuffer();
        try {
            boolean firstProperty=true;
            Class c = obj.getClass();
            BeanInfo info = Introspector.getBeanInfo(c);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for(int i=0;i<props.length;i++){
                Method m2=props[i].getWriteMethod();
                if(m2==null)continue; // nos saltamos las props ROnly
				if(Collection.class.isAssignableFrom(props[i].getPropertyType())){
                    continue; // Nos saltamos las listas
                }
                if(!firstProperty)sb.append(" ");
                else firstProperty=false;
                Method m=props[i].getReadMethod();
                sb.append(props[i].getName());
                sb.append("=\"");
                Object o=m.invoke(obj,new Object[]{});
                if(o!=null){
                    Class<?> cp=o.getClass();
                    Converter f=buscaConverter(cp);
                    if(f==null){
                        sb.append(o.toString());
                    }else{
                        sb.append(f.toString(o));
                    }               
                }
                sb.append("\"");
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
    
    
    //-----------------------Tests --------------------------
    public static void main(String args[]){
        test("34343",Integer.class);
        test("-34",Integer.class);
        test("432432",Double.class);
        test("432432.432",Double.class);
        test("-344.2e-3",Double.class);
        test("algo",String.class);
        test("12/03/2007",Date.class);
        test("1,1,1",Color.class);
        test("#FFFFFF",Color.class);
        test("FFFFFF",Color.class);
        test("255,255,255",Color.class);
        test("true",Boolean.class);
        test("false",Boolean.class);
        
    }
    
    
    private  static  void test(String str,Class c){
        try{
            Object obj=StringToObject2.getObject(str,c);
            System.out.println("\""+str+"\" --> ["+c+"]("+obj.toString()+")");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
