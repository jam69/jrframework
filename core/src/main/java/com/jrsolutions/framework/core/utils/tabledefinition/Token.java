/*
 * Token.java
 *
 * Created on 9 de enero de 2008, 18:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jrsolutions.framework.core.utils.tabledefinition;

public class Token {
    public static final int NULL = 0;
    public static final int LCOR = 1;
    public static final int RCOR = 2;
    public static final int EQ   = 3;
    public static final int STR  = 4;
    public static final int NUM  = 5;
    public static final int QUOTE= 6;
    public static final int LPAR = 7;
    public static final int RPAR = 8;
    public static final int COMA = 9;
    public static final int END  =10;
    
    public static final String[] names={
        "NULL","LCOR","RCOR","EQ","STR","NUM","QUOTE","LPAR","RPAR","COMA","END"
    };
    private final int tipo;
    private final String txt;
    
    public Token(int tipo,String txt){
        this.tipo=tipo;
        this.txt=txt;
    }
    
    public String toString(){
        return "Token:"+tipo+" "+names[tipo]+" '"+txt+"'";
    }
    public int getTipo() {
        return tipo;
    }
    
    public String getTxt() {
        return txt;
    }
}