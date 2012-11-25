/*
 * Scanner.java
 *
 * Created on 9 de enero de 2008, 18:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.tabledefinition;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

/**
 *
 * @author UF768023
 */
public class Scanner extends StreamTokenizer{
    
    private boolean debug;
    
    public Scanner(String txt){
        super(new StringReader(txt));
//            super.eolIsSignificant(false); // Da igual solo hay 1 linea
        super.quoteChar('"');
//            super.parseNumbers();   // No se parsean 
    }
    
    public Token next() throws IOException{
        int a=super.nextToken();
        int tipo=0;
        String txt="";
        //System.out.println("SCAN:"+a+" txt="+sval+" val="+nval);
        switch(a){
            case '[':tipo=Token.LCOR;break;
            case ']':tipo=Token.RCOR;break;
            case '(':tipo=Token.LPAR;break;
            case ')':tipo=Token.RPAR;break;
            case '=':tipo=Token.EQ;break;
            case '\"':tipo=Token.STR;
                txt=sval;
                int b=super.nextToken();
                while(b=='"'){
                    txt=txt+"\""+sval;
                    b=super.nextToken();
                }
                pushBack();
                break;
            case ',':tipo=Token.COMA;break;
            case TT_WORD: txt=sval;tipo=Token.STR; break;
            case TT_EOF: tipo=Token.END;txt="";break;
            case TT_EOL: System.out.println("EOL "+toString());break;
            default: System.out.println("ERROR"+toString()+"<"+a+">");
        }
        
        Token t=new Token(tipo,txt);
        if(isDebug())System.out.println(">"+t);
        return t;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
