package com.jrsolutions.framework.core.utilitybeans;

import com.jrsolutions.framework.core.context.Context;
import com.jrsolutions.framework.core.context.MInfoMeta;
import com.jrsolutions.framework.core.context.MService;
import com.jrsolutions.framework.core.metamodel.MetaAttribute;
import com.jrsolutions.framework.core.metamodel.MetaEntity;
import com.jrsolutions.framework.core.metamodel.MetaInfoFactory;
import com.jrsolutions.framework.core.metamodel.creators.DataCreator;
import com.jrsolutions.framework.core.metamodel.creators.DataCreatorRegister;
import com.jrsolutions.framework.core.utils.StringUtils;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;




/**
 * Bean para prototipos. Genera datos aleatorios.
 * Genera datos para inicializar variables complejas o
 * colecciones en los prototipos del framework.
 * Tambien sirve para simular servicios.
 * 
 * <ul>Properties:
 * <li>varName: Nombre de la variable donde se deja el objeto o colecci�n</li>
 * <li>varType:    Nombre del tipo o la clase  a generar</li>
 * <li>size:    Numero de elementos de la colleci�n, Si se pone 0 genera un solo 
 *              elemento (no una collecion)</li>
 * <li>generators: Algoritmos a utilizar en la generaci�n de datos.</li>
 * </ul>
 * 
 * @see DataCreator
 * @see DataCreatorRegister
 * 
 * @author Jose Antonio
 *
 */
public class DataGenerator implements MService,MInfoMeta {

	private Context ctx;
	private String varName;
	private String varType;
	private int size=0;
	private String generators;
	private String genTree="no";

	private MetaInfoFactory factory;
	
	
	public DataGenerator() {
	}

	/**
	 * Genera los datos de acuerdo a las propiedades pasadas
	 */
	public Object execute() {
		if(size==0){
			ctx.put(varName,generateUno(varType,generators));
		}
		if(genTree!=null && genTree.equalsIgnoreCase("yes")){
			ctx.put(varName,generateTree(varType,generators));
		}else{
			ctx.put(varName,generateMany(size,varType,generators));	
		}		
		return null;		
	}
	
	/**
	 * Genera un objeto del tipo indicado con los generadores indicados.
	 * @param tipo  Nombre del tipo o clase
	 * @param generators Lista de generadores separados por comas.
	 * 
	 * @see DataGenerator
	 * 
	 * @return El objeto de la clase deseada
	 */
	public Object generateUno(String tipo,String generators){
		Object res=generate(tipo,StringUtils.divide(generators));
		return res;
	}
	
	/**
	 * Genera una coleccion de objetos del tipo deseado con los generadores indicados
	 * 
	 * @param size  Numero de objetos a generar
	 * @param tipo  Nombre del tipo o la clase de los objetos.
	 * @param generators Lista de generadores separados por comas.
	 * 
	 * @see DataGenerator
	 * 
	 * @return Una coleccion de objetos del tipo deseado
	 */
	public Object generateMany(int size,String tipo,String generators){
		ArrayList<Object> res=new ArrayList<Object>();
		String[] param=StringUtils.divide(generators);
		for(int j=0;j<size;j++){
			res.add(generate(tipo,param));
		}
		return res;
	}
	
	/**
	 * Genera un arbol de objetos del tipo indicado.
	 * 
	 * @param tipo  Nombre del tipo o la clase de los objetos.
	 * @param generators  Lista de generadores separados por comas.
	 * 
	 * @return Un arbol con objetos del tipo deseado (10x10x10 elementos, 3 niveles)
	 */
	public Object generateTree(String tipo,String generators){
		String[] param=StringUtils.divide(generators);
		DefaultMutableTreeNode root=new DefaultMutableTreeNode(generate(tipo,param));
		for(int j=0;j<10;j++){
			DefaultMutableTreeNode n=new DefaultMutableTreeNode(generate(tipo,param));
			for(int i=0;i<10;i++){
				DefaultMutableTreeNode n2=new DefaultMutableTreeNode(generate(tipo,param));
				n.add(n2);
				for(int k=0;k<10;k++){
					DefaultMutableTreeNode n3=new DefaultMutableTreeNode(generate(tipo,param));
					n2.add(n3);
				}
			}
			root.add(n);
		}
		return new DefaultTreeModel(root);
	}
	

	/** genera un unico objeto del tipo indicado. */
	private Object generate(String tipo,String[] param){
		MetaEntity info=factory.getTypeInfo(tipo);
		Object obj=info.newInstance();
		MetaAttribute[] attrs=info.getAttributesA();
		for(int a=0;a<attrs.length;a++){
			if(param.length>a){
				Object value= DataCreatorRegister.create(tipo,param[a]);
				attrs[a].setValue(obj, value);
			}else{
				Object value= DataCreatorRegister.create(attrs[a].getTypeName());
				attrs[a].setValue(obj, value);
			}
		}			
		return obj;
	}


	
		
	public void setInfoFactory(MetaInfoFactory factory){
		this.factory=factory;
	}
	public void setContext(Context ctx) {
		this.ctx=ctx;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarType() {
		return varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}

	public String getGenTree() {
		return genTree;
	}

	public void setGenTree(String genTree) {
		this.genTree = genTree;
	}
	
	/** Devuelve el string que define los generadores.
	 * Si no se especifica es utiliza el generador por defecto para la clase
	 * de la propiedad.
	 * Los generadores deben estar configurados en la clase {@link DataCreatorRegister}
	 * 
	 * P.ej:  "String(10),Nombres,Apellidos,Apellidos,DNI,Integer(50,100),Double(0,1)"
	 * 
	 * 
	 * @see DataCreator
	 * 
	 * @return
	 */
	public String getGenerators() {
		return generators;
	}
	
	public void setGenerators(String generators) {
		this.generators = generators;
	}

	public void setFactory(MetaInfoFactory factory){
		this.factory=factory;
	}
	
}
