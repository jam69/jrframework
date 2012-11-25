/*
 * ParserConClase.java
 *
 * Created on 12 de enero de 2008, 22:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.constrainedpanel;

import com.jrsolutions.framework.core.utils.BeanExecutor;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;

public     class ParserConClase {
        private final Scanner s;
        private final Class c;
        public ParserConClase(String str,Class c){
            s=new Scanner(str);
            this.c=c;
        }
        
        public ArrayList parse(){
            ArrayList lista =new ArrayList();
            try {
                while(true){
                    Token t=s.next();
                    if(t.getTipo()==Token.END){
                        return lista;
                    }
                    if(t.getTipo()!=Token.LCOR){
                        System.out.println("ERRRORO:"+" Debe empezar por '['");
                        return null;
                    }
                    Object obj=c.newInstance();
                    Token tAtrib=s.next();
                    while(tAtrib.getTipo()==Token.STR){
                        
                        if(tAtrib.getTipo()!=Token.STR){
                            System.out.println("ERRROR1:"+" Debe ser atrib=\"valor\" ");
                            return null;
                        }
                        Token tEqual=s.next();
                        if(tEqual.getTipo()!=Token.EQ){
                            System.out.println("ERRROR2:"+" Debe ser atrib=\"valor\" ");
                            return null;
                        }

                        Token tValor=s.next();
                        if(tValor.getTipo()!=Token.STR){
                            System.out.println("ERRROR3:"+" Debe ser atrib=\"valor\" ");
                            return null;
                        }
                        BeanExecutor.setProperty(obj,tAtrib.getTxt(),tValor.getTxt());
                        tAtrib=s.next();
                    }
                    if(tAtrib.getTipo()!=Token.RCOR){
                        System.out.println("ERRROR4:"+" Falta ]");
                        return null;
                    }
                    lista.add(obj);
                }
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        return null;
        }
        
        
    }
    
   
    
    class Token {
    public static final int NULL = 0;
    public static final int LCOR = 1;
    public static final int RCOR = 2;
    public static final int EQ   = 3;
    public static final int STR   = 4;
    public static final int QUOTE  = 6;
    public static final int END  =100;
    
        private final int tipo;
        private final String txt;
        private final double dval;
        
        public Token(int tipo,String txt,double dval){
            this.tipo=tipo;
            this.txt=txt;
            this.dval=dval;
            //System.out.println("TOKEN:"+tipo+" "+txt+" "+dval);
        }
        public int getTipo() {
            return tipo;
        }
        
        public String getTxt() {
            return txt;
        }
    }
    class Scanner extends StreamTokenizer{
        public Scanner(String txt){
            super(new StringReader(txt));
//            super.eolIsSignificant(false);
            super.quoteChar(34);
//            super.parseNumbers();
        }
        
        public Token next() throws IOException{
            int a=super.nextToken();
            int tipo=0;
            String txt="";
            double dval=0;
            //System.out.println("SCAN:"+a+" txt="+sval+" val="+nval);
            switch(a){            
                case '[':tipo=Token.LCOR;break;
                case ']':tipo=Token.RCOR;break;
                case '=':tipo=Token.EQ;break;
                case '\"':tipo=Token.STR;txt=sval;break;
                case TT_WORD: txt=sval;tipo=Token.STR; break;
                case TT_EOF: tipo=Token.END;txt="";break;
                case TT_EOL: System.out.println("EOL "+toString());break;
                default: System.out.println("ERROR"+toString()+"<"+a+">");
            }
            
            return new Token(tipo,txt,dval);
        }
    }
    
