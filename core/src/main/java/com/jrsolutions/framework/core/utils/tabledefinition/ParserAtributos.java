package com.jrsolutions.framework.core.utils.tabledefinition;

import java.util.ArrayList;


public class ParserAtributos {

	
	static public ArrayList parse (String str){
	
		 try {
			Scanner s=new Scanner(str);
			// s.setDebug(true);
			 ParserObject parser=new ParserObject(s);
			 s.next(); // [
			 return  parser.readArray(AttributeDefinition.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
