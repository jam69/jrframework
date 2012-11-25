package com.jrsolutions.framework.core.utilitybeans;

import com.jrsolutions.framework.core.context.MInfoMeta;
import com.jrsolutions.framework.core.metamodel.MetaAttribute;
import com.jrsolutions.framework.core.metamodel.MetaEntity;
import com.jrsolutions.framework.core.metamodel.MetaInfoFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * Genera la suma de los atributos de los elementos de una collecci�n.
 *
 * <p>Necesita dos atributos (el nombre de la clase, y una lista de los
 * atributos a sumar).
 * <br>Los atributos a sumar deben ser "double".
 *
 * <p>Modo de uso:
 * <ul>
 * <li>Se debe crear un Bean: con esta clase y las siguientes propiedades
 * <ul>
 * 	<li>cname: Nombre de la clase a totalizar
 * 	<li>attrs: Lista de los nombres de los campos (separados por comas).
 * </ul>
 * <li>Se crea una Operacion con las siguientes propiedades:
 * <ul>
 * <li>Activacion: onvar COLLECCION_DATOS
 * <li>Bean: El indicado anteriormente
 * <li>Metodo: COLLECTION_TOTALES totaliza(java.lang.Collection COLLECTION_DATOS)
 * </ul>
 * <li>En la table definir los totales. Con esta colleci�n, y los campos
 * que se computen.
 * </ul>
 * 
 *
 */
public class Totalizador implements MInfoMeta{

	private String attrs;
	private String cname;

	private MetaInfoFactory factory;

	private MetaAttribute[] props;

	public Totalizador(){
		
	}
	public void setInfoFactory(MetaInfoFactory fac){
		this.factory=fac;
	}
	
	/** Nombre de la clase (o tipo) de los elementos de la colecci�n. */
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
		if(attrs!=null)leeProps();
	}

	/** Lista de atributos a sumar (separados por comas) */
	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
		if(cname!=null)leeProps();
	}
	
	private void leeProps(){
		MetaEntity info=factory.getTypeInfo(cname);
		String a[]=attrs.split(",");
		props=new MetaAttribute[a.length];
		for(int i=0;i<a.length;i++){
			props[i]=info.getAttribute(a[i]);
		}
	}

	/** Ejecuta el servicio y devuelve la colecci�n con los totales */
	public Collection<?> totaliza(ArrayList<?> c){
		return totaliza((Collection)c);
	}
	
	/** Ejecuta el servicio y devuelve la colecci�n con los totales */
	public Collection<?> totaliza(Collection<Object> c){
		Collection<Object> res=new ArrayList<Object>();
		if(c==null ||c.size()==0)return res;
		Object o=getFirst(c);
		Object proto=clona(o);
		double sum[]=new double[props.length];
		Iterator<Object> it=c.iterator();
		while(it.hasNext()){
			Object e=it.next();
			for(int i=0;i<props.length;i++){
				Number n=(Number)props[i].getValue(e);
				sum[i]+=n.doubleValue();
			}
		}
		for(int i=0;i<props.length;i++){
			props[i].setValue(proto,new Double(sum[i]));
		}
		res.add(proto);
		return res;
	}

	private Object getFirst(Collection<?> c){
		Iterator<?> it=c.iterator();
		return it.next();
	}

	private Object clona(Object o){
		try{
			Class<?> c=o.getClass();
			Object r=c.newInstance();
			return r;
		}catch (Exception ex){
			return null;
		}
	}
}
