package com.jrsolutions.framework.generator.docgenerator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freemarker.template.TemplateMethodModel;

public class DocInfo {
	
	private static Map<String,Tabla> map;
	private static Map<String,String> alias=new HashMap<String,String>();
	static {
		alias.put("Aerodromes Data","Aerodromes Guide");
		alias.put("Airways Data","Airways Guide");
		alias.put("Aerodromes Runways Data","Aerodrome Runways");
		alias.put("User Data","Users Guide");
		alias.put("CMD Passwords Data Screen","Users Guide");
		alias.put("Aerodromes Selection","Aerodromes Guide");
	}
	public DocInfo(){
		
	}
	
	private static void leeInfo(){
		try {
			PropsParser p=new PropsParser();
			map=p.parse(new FileReader("texto.out"));
			System.out.println(map.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Object infoTabla(String name){
		if(map==null)leeInfo();
		Tabla res=map.get(name);
		if(res!=null)return res;
		String aliasName=alias.get(name);
		if(aliasName!=null){
			return map.get(aliasName);
		}
		System.out.println("Busco ayuda para:"+name);		
		String par=parecido(name,map.keySet());
		Tabla t=map.get(par);
		return t;
	}
	
	public String toKey(String campo){
		if(campo.startsWith("filtercb")){
			campo=campo.substring("filtercb".length());
		}
		if(campo.startsWith("selectedcb")){
			campo=campo.substring("selectedcb".length());
		}
		if(campo.startsWith("selCb")){
			campo=campo.substring("selCb".length());
		}
		if(campo.startsWith("selcb")){
			campo=campo.substring("selcb".length());
		}
		String campo2=campo.toLowerCase();
		int p=campo.indexOf(".");
		if(p>0){
			campo2=campo.substring(p+1);
		}
		return campo2;
	}
	public Field infoCampo(Tabla t,String campo){
		if(map==null)leeInfo();
		String campo2=toKey(campo);
		Field f=t.campos.get(campo2);
		if(f!=null){
			return f;
		}
		System.out.println("Busco campo("+campo+") en tabla "+t.name);
		String par=parecido(campo2,t.campos.keySet());
		return t.campos.get(par);
	}
	
	private String parecido(String str,Set<String> lista){
		String res=null;
		System.out.println("Busco parecido para:["+str+"]");
		float max=-1;
		for(String cand:lista){
			float score=likeless(str,cand);
			if(score>1)System.out.println("      >>["+cand+"]("+score+"%)");
			if(score>max){
				max=score;
				res=cand;
			}
		}
		System.out.println("Busco parecido para:"+str+" ["+res+"]("+max+"%)");
		return res;
	}
	
	private float likelessWords(String str,String cand){
		if(str.equalsIgnoreCase(cand))return 100f;
		String cand2=cand.toLowerCase();
		String str2=str.toLowerCase();
		if(cand2.indexOf(str2)>=0)return 99f;
		float x=0;
		for(int i=0;i<str2.length();i++){
			char c=str2.charAt(i);
			if(cand2.indexOf(c)>=0) x+=1f/str.length()*20f;
		}
		return x;
	}
	
	private float likeless(String str,String cand){
		if(str.equalsIgnoreCase(cand))return 100f;
		String cand2=cand.toLowerCase();
		String str2=str.toLowerCase();
		if(cand2.indexOf(str2)>=0)return 99f;
		String wordsStr[]=str2.split(" ");
		Set<String> wordsCand=split2(cand.split(" "));
		int count=0;
		float microcount=0f;
		for(String h:wordsStr){			
			if(wordsCand.contains(h)){
				microcount+=h.length()*1.2f;
				count++;
			}
		}
		if(count>0){
			return (float)count/wordsStr.length*70f+20f+microcount;
		}
						
		return likelessWords(str,cand);
	}
	
	private Set<String> split2(String[] x){
		if(x.length==1){
			return split3(x[0]);
		}
		Set<String>res=new HashSet<String>();
		for(String a:x){
			res.add(a.toLowerCase());
		}
		return res;
	}
	private Set<String> split3(String x){
		Set<String>res=new HashSet<String>();
		int a=0;
		for(int i=0;i<x.length();i++){
			char c=x.charAt(i);
			if(Character.isUpperCase(c)){
				if(a>0){
					res.add(x.substring(a,i).toLowerCase());
				}
				a=i;
			}
		}
		res.add(x.substring(a).toLowerCase());							
		return res;
	}
}

