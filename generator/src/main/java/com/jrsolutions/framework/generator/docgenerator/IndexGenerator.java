package com.jrsolutions.framework.generator.docgenerator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class IndexGenerator {

	String dirPath="doc-gen/app";

	/**
	 * @param args
	 */
	public static void main(String[] args){
		IndexGenerator gen=new IndexGenerator();
		gen.run(args);
	}

	public void run(String[] args){
		try {
			dirPath="doc-gen"+File.separator+"app";
			PrintWriter out=new PrintWriter(new FileWriter("TOC.xml"));
			out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>");
			out.println("<toc version=\"1.0\">");
			out.println("<tocitem image=\"toplevelfolder\" text=\"VADMS HELP CENTER\" >");			
			File dir=new File(dirPath);
			indexa(dir,out);
			out.println("</tocitem>");
			out.println("</toc>");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void indexa(File dir,PrintWriter out){
		File[] listado=dir.listFiles();
		for(File f:listado){
			if(f.isDirectory()){
				String text=f.getName();							
				out.println("<tocitem image=\"openbook\" text=\""+text+"\"" + toTarget(f)+" >");
				indexa(f,out);
				out.println("</tocitem>");
			}else{
				String text=f.getName();
				out.println("\t<tocitem image=\"chapter\" text=\""+text+"\"" + toTarget(f)+" >");
				try {
					analiza(f,out);
				} catch (IOException e) {
					e.printStackTrace();
				}
				out.println("\t</tocitem>");
			}
		}
	}
	
	private String toTarget(File f){
		String p= f.getPath();
		String p2=p.replace(dirPath,"");
		String p3=p2.replace(File.separatorChar,'.');
		if(p3.length()>4){
			String p4=p3.substring(1,p3.length()-4);
			return " target=\""+p4+"\"";
		}else{
			return "";
		}
		
	}
	
	private void analiza(File f,PrintWriter out) throws IOException{
		String str=readFile(f) ;
		int pos=0;
		while(pos>=0){
		//  <helpID conv="UCAirways" win="AirwaysDataScreen" key="AirwayIdentity" key2="selairwayclass" />	
			int pIni=str.indexOf("<helpID",pos);
			if(pIni<0)break;
			int pFin=str.indexOf("/>",pIni);			
			String hID=str.substring(pIni,pFin);
			String[]aux=hID.split("\"");
			// <name>Airway Identity</name>
			int p2Ini=str.indexOf("<name>",pFin);
			if(p2Ini<0)break;
			int p2Fin=str.indexOf("</name>",p2Ini);			
			String text=str.substring(p2Ini+6,p2Fin); // <name>texto</name>
//			String text=aux[5]; //key2
			String target=aux[1]+"."+aux[3]+"."+aux[7]; //conv+win+key2
			out.println("\t\t<tocitem image=\"topic\" text=\""+text+"\" target=\""+target+"\" />");
			pos=pFin;
		}
	}
	
	private String readFile(File f) throws IOException{
		FileReader r=new FileReader(f);
		StringBuffer sb=new StringBuffer();
		char[] buff=new char[5000];
		int n;
		while( (n=r.read(buff))>0){
			sb.append(buff,0,n);
		}
		r.close();
		return sb.toString();
	}	
}
