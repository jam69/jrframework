package com.jrsolutions.framework.core.metamodel;

/**
 * Implementacion dinamica de un descriptor de attributo generados dinamicamente
 * 
 * @see Entity
 * @see MetaEntityDyn
 *
 */
public class MetaAttrDyn implements MetaAttribute {

	private String name;
	private String displayName;
	private String editor;
	private int orden;
	private String typeName;
	private boolean hidden;
	private boolean nullable;
	
	
	public String toString(){
		return "MetaAttrDyn: name="+name+" dName="+displayName+" editor="+editor+" orden="+orden+" typeName="+typeName;		
	}
	
	
	@Override
	public Object getValue(Object obj) {
		if(obj instanceof Entity){
			Entity ent=(Entity)obj;
			return ent.getProperty(name);
		}else{
			throw new IllegalArgumentException("Solo se hacer get de Entitys");
		}
	}
	@Override
	public void setValue(Object obj, Object value) {
		if(obj instanceof Entity){
			Entity ent=(Entity)obj;
			ent.setProperty(name, value);
		}else{
			throw new IllegalArgumentException("Solo se setear Entitys");
		}
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String getEditor() {
		return editor;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getOrden() {
		return orden;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}


	@Override
	public boolean isHidden() {
		return hidden;
	}

	@Override
	public boolean isNullable() {
		return nullable;
	}


}
