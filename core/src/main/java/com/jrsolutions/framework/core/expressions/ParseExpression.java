package com.jrsolutions.framework.core.expressions;

import com.jrsolutions.framework.core.context.Context;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *  Parsea una Expresion y devuelve una estructura con la info correspondiente.
 * 
 * REGLAS
 * 
 * expr : 
 *      | '(' expr ')'     // agrupaci�n por parentesis
 *      | expr (OPLOG expr)*  // operacion logica
 *      | NOT expr         // negacion binaria
 *      | dato OPREL dato  // operador relacional
 * dato : ID                // Nombre variable
 *      | ID '.' PROP       // nombre de variable con propiedad
 *      | QUOTE             // algo entre comillas
 *      | NUMBER            // Una constante numerica
 *      
 *  
 * OPLOG : '||' | '&&'
 * NOT   : '!'   
 * OPREL : '<' | '>' | '>=' | '<=' | '!=' | '='
 * ID    : identificador java valido (sin blancos ni caracteres raros)
 * PROP  : identificador java valido (sin blancos ni caracteres raros)
 * QUOTE : cadena entre comillas
 * NUMBER:  una constante entera           
 * 
 */
public class ParseExpression {

	Logger log  = Logger.getLogger(this.getClass().getName());
	
	private Scanner s;
	
	
	public ParseExpression() {
	}

	public Expression compile(String str) throws IOException {
		s = new Scanner(str);
		return parseExprAND();
	}

	/* 
	 */

	public Expression parseExprAND() throws IOException {
		Expression e1 = parseExprOR();
		Token t2 = s.next();
		while (t2.getTipo() == Token.OPLOG) {
			String opLog = t2.getTxt();
			Expression e2 = parseExprOR();
			Expression e3 = new BooleanBinario(opLog, e1, e2);
			e1 = e3;
			t2 = s.next();
		}
		s.pushBack2();
		return e1;
	}

	public Expression parseExprOR() throws IOException {
		Expression e1 = parseExprNOT();
		Token t2 = s.next();
		while (t2.getTipo() == Token.OPLOG) {
			String opLog = t2.getTxt();
			Expression e2 = parseExprNOT();
			Expression e3 = new BooleanBinario(opLog, e1, e2);
			e1 = e3;
			t2 = s.next();
		}
		s.pushBack2();
		return e1;
	}

	public Expression parseExprNOT() throws IOException {		
		Token t2 = s.next();
		if (t2.getTipo() == Token.OPNOT) {
			Expression e1 = parseExprRel();
			return new BooleanNot(e1);
		}
		s.pushBack2();
		return parseExprRel();
	}

	public Expression parseExprRel() throws IOException {
		Expression e1 = parseDato();
		Token t2 = s.next();
		if (t2.getTipo() == Token.OPREL) {
			String opLog = t2.getTxt();
			Expression e2 = parseExprRel();
			return new BooleanComp(opLog, e1, e2);
		}
		s.pushBack2();
		return e1;
	}

	public Expression parseDato() throws IOException {
		Token t2 = s.next();
		if (t2.getTipo() == Token.QUOTE) {
			return new Constant(t2.getTxt());
		} else if (t2.getTipo() == Token.STR) {
			if (t2.getTxt().indexOf(".")>0){
				//se comprueba si la variable tiene el operador punto
				addVariable(t2.getTxt().substring(0, t2.getTxt().indexOf(".")));
				return new VariableProp(t2.getTxt().substring(0, t2.getTxt().indexOf(".")),t2.getTxt().substring(t2.getTxt().indexOf(".")+1 ));
			}else{
				addVariable(t2.getTxt());
				return new Variable(t2.getTxt());
			}
		} else if (t2.getTipo() == Token.LPAR) {
			Expression e = parseExprAND();
			t2 = s.next(); // ')'
			return e;
		}else if (t2.getTipo()==Token.NUMBER){
			return new Numeric(Double.valueOf(t2.getDval()));
		}
		
		s.pushBack2();
		return null;
	}

	public void addVariable(String var){
		if (!variables.contains(var)){
			variables.add(var);
		}
	}
	
//	public static void main(String[] args) throws IOException {
////		ParseExpression p = new ParseExpression();
//		Context ctx = new Context(null);
//		//ctx.put("a", new ejemplo("a","b"));
//		ctx.put("a","dos");
//		ctx.put("$b", "holas");
//		ctx.put("c",null);
//		String str = "(a== \"dos\"   && $b==\"hola\") || c ";
////		Expression e = p.compile(str);
////		System.out.println("STR:[" + str + "] valor=" + e.eval(ctx));
////		 for (int i=0;i<variables.size();i++){
////			 System.err.println("variable -->"+variables.get(i));
////		 }
//		System.out.println("STR:[" + str + "] valor=" + checkExpression(str, ctx));
//	}

	static ArrayList<String>variables= new ArrayList<String>();
	
	public static ArrayList<String> varListExpresion(String str,Context ctx){
		try {
			ParseExpression p = new ParseExpression();
			p.compile(str);
			return variables;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}
	
	public static boolean checkExpression(String str, Context ctx){
		if (str==null || str.trim().length()==0) return false;
		try {
			ParseExpression p = new ParseExpression();
			Expression e = p.compile(str);
			if (e.eval(ctx)==null)return false;
			if (!e.eval(ctx).toString().equalsIgnoreCase("true")&& !e.eval(ctx).toString().equalsIgnoreCase("false"))return true;
		return e.eval(ctx).toString().equalsIgnoreCase("true")?true:false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}

class Token {
	public static final int NULL = 0;
	public static final int LPAR = 1;
	public static final int RPAR = 2;
	public static final int OPLOG = 3;
	public static final int OPREL = 4;
	public static final int OPNOT = 5;
	public static final int STR = 10;
	public static final int QUOTE = 11;
	public static final int NUMBER = 12;
	public static final int DOT = 20;
	public static final int END = 100;
	public static final int DOLAR =36;

	private final int tipo;
	private final String txt;
	private final double dval;

	public Token(int tipo, String txt, double dval) {
		this.tipo = tipo;
		this.txt = txt;
		this.dval = dval;
		// System.out.println("TOKEN:"+tipo+" "+txt+" "+dval);
	}

	public int getTipo() {
		return tipo;
	}

	public String getTxt() {
		return txt;
	}
	
	public double getDval(){
		return dval;
	}
}

class Scanner extends StreamTokenizer {
	Logger log  = Logger.getLogger(this.getClass().getName());

	public Scanner(String txt) {
		super(new StringReader(txt));
		// super.eolIsSignificant(false);
		super.quoteChar(34);
		// super.parseNumbers();
		// super.ordinaryChars('=','=');
		// super.wordChars('=','=');
		super.ordinaryChar(Token.DOLAR);
		super.wordChars(Token.DOLAR, Token.DOLAR);
	}

	Token last;
	Token future;
	
	public void pushBack2(){
		future=last;
	}
	
	public Token next() throws IOException {
		if(future!=null){
			Token aux=future;
			future=null;
			return aux;
		}
		int a = super.nextToken();
		int tipo = 0;
		String txt = "";
		double dval = 0;
		// System.out.println("SCAN:"+a+" txt="+sval+" val="+nval);
		switch (a) {
		case '(':
			tipo = Token.LPAR;
			break;
		case ')':
			tipo = Token.RPAR;
			break;
		case '\"':
			tipo = Token.QUOTE;
			txt = sval;
			break;
		case '>':
			tipo = Token.OPREL;
			int b = super.nextToken();
			if (b == '=') {
				txt = ">=";
			} else {
				txt = ">";
				pushBack();
			}
			break;
		case '<':
			tipo = Token.OPREL;
			int b2 = super.nextToken();
			if (b2 == '=') {
				txt = "<=";
			} else {
				txt = "<";
				pushBack();
			}
			break;
		case '!':
			int b3 = super.nextToken();
			if (b3 == '=') {
				tipo = Token.OPREL;
				txt = "!=";
			} else {
				txt = "!";
				tipo = Token.OPNOT;
				pushBack();
			}
			break;
		case '&':
			 int b4=super.nextToken();
			 if(b4=='&'){
				 tipo = Token.OPLOG;
				 txt = "AND";
			 }else{
			 // Error
				 pushBack();
			 }
			break;
		case '|':
			 int b5=super.nextToken();
			 if(b5=='|'){
				 tipo = Token.OPLOG;
				 txt = "OR";
			 }else{
			 // Error
			 pushBack();
			 }
			break;
		case '=':
			int b6 = super.nextToken();
			if (b6 == '=') {
				tipo = Token.OPREL;
				txt = "==";
			} else {
				// Error
				pushBack();
			}
			break;
		case '.':
				tipo = Token.DOT;				
			break;
		case TT_WORD:
			txt = sval;
			if (txt.equals("||") || txt.equals("&&")) {
				tipo = Token.OPLOG;
				// }else if(txt.equals("<=") ||
				// txt.equals(">=")||txt.equals("==")){
				// tipo=Token.OPREL;
			} else {
				tipo = Token.STR;
			}
			break;
		case TT_EOF:
			tipo = Token.END;
			txt = "";
			break;
		case TT_EOL:
			System.out.println("EOL " + toString());
			break;
		case TT_NUMBER:
			dval=nval;
			tipo = Token.NUMBER;
			break;
		/**
		 * TODO controlar el $
		 */
		default:
			System.out.println("ERROR" + toString() + "<" + a + ">");
		}

		last=new Token(tipo, txt, dval);
		return last;
	}
}

