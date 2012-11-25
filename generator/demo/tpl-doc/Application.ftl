 <#--
 Variables que recibe:
 	"app"  					  Application
    "panelDefinitionsParser"  DefinitionsParser
    "parserModificadores" 	  ParserModificadores
    "parserTabla"  			  ParserTabla
    "stripHTML"				  HTMLStripper
    "genID"				      GenID
    "traduce"				  GestorMultilingualidad
    "escape"				  TextEscape
    "toJava"				  ToJavaName
    "parserForm"			  ParserForm
    "CUtil"       			  Add
-->
<algo>
</algo>
<#--
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import swingrunner.Main;
import swingrunner.Window;

import com.jrsolutions.framework.Contexto;

public class Imagenes {

	static private int width=800;
	static private int height=600;

	public static void main(String[]args){
		File dir;
		Window w;
		JComponent comp;
		BufferedImage image;
		Main main=new Main();
		Contexto ctx=new Contexto(null);
		
	  <#list app.getConversations()  as conv>
    	dir=new File("imagenes/${conv.id}");
   		dir.mkdirs();
    	<#list conv.getWindows()  as win>
    		w=new app.${conv.id}.${win.id}(main);
    		salvaImage(w.getPanel(ctx),new File(dir,"${win.id}.jpg"));    		
        </#list>
        
	  </#list>
	  }   
	  
	private static void salvaImage(JComponent comp,File path){
    	BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
    	Graphics gr=image.getGraphics();
    	comp.setSize(new Dimension(width,height));
    	comp.paint(gr);
    	try {
			ImageIO.write(image,"jpeg",path);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}-->



